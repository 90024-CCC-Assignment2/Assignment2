from utils import db_client
from Configuration import *
from utils.Tweet import TweetAttributeFilter
from nltk.tokenize import TweetTokenizer

import argparse

import tweepy
import time

"""
For now I don't think I have already incorporate rate limit waiting mechanism into the code. (New tweepy calls it differently?)
But when doing test, only filtering on Melbourne, it appears that we cannot reach the upper limit given that the speed is 
around 10-15 tweets/min.
"""

######################
# Define some static variables
THRESHOLD = 3
BAD_WORDS = set()
with open("External_Data/negative_words.txt") as f:
    for line in f.readlines():
        BAD_WORDS.add(line.strip())
SENSITIVE_WORDS = set()
with open("External_Data/inappropriate_words.txt") as f:
    for line in f.readlines():
        SENSITIVE_WORDS.add(line.strip())
######################


class TwitterAuthenticator:
    """
    We need to use multiple twitter api credentials to harvest twitters. (In our case, 4)
    We use id to assign the api credential of this particular process (process id in a multi-process situaiton)
    """

    def __init__(self, id):
        parser = configparser.ConfigParser()
        parser.read("credentials.ini")
        self.api_key = parser["Twitter-{}".format(id)]["api_key"]
        self.api_key_secret = parser["Twitter-{}".format(id)]["api_key_secret"]
        self.bearer_token = parser["Twitter-{}".format(id)]['bearer_token']
        self.access_token = parser["Twitter-{}".format(id)]["access_token"]
        self.access_token_secret = parser["Twitter-{}".format(id)]["access_token_secret"]
    
    def authenticate(self):
        auth = tweepy.OAuthHandler(self.api_key, self.api_key_secret)
        auth.set_access_token(self.access_token, self.access_token_secret)

        return auth

    def get_keys(self):
        return self.api_key, self.api_key_secret, self.bearer_token, self.access_token, self.access_token_secret


class TwitterStreamListener(tweepy.Stream):
    """
    Class for processing streaming twitters
    """
    def __init__(self, consumer_key, consumer_secret, access_token, access_token_secret, id, **kwargs):
        super().__init__(consumer_key, consumer_secret, access_token, access_token_secret, **kwargs)
        # self.grid_worker = LocationCounter(rows, columns)
        self.buffer = []
        try:
            client1 = db_client.DBClient(USER_NAME_1, PASSWORD_1, URL_1)
        except:
            client1 = None
        try:
            client2 = db_client.DBClient(USER_NAME_2, PASSWORD_2, URL_2)
        except:
            client2 = None
        try:
            client3 = db_client.DBClient(USER_NAME_3, PASSWORD_3, URL_3)
        except:
            client3 = None
        self.db_clients = [client1, client2, client3]
        self.id = id

        self.external_cuisines = json.load(open('External_Data/Cuisine.json'))['list']
        self.tokenizer = TweetTokenizer()
        self.tweet_filter = TweetAttributeFilter()

    def on_status(self, status):
        self.buffer.append(status)
        print(len(self.buffer))
        if len(self.buffer) > THRESHOLD:
            print("start processing")
            self.process()
            # avoid rate limit
            time.sleep(850)
            self.buffer.clear()

        return True

    def on_error(self, status_code):
        if status_code == "420":
            time.sleep(100)
            return True
        print(status_code)

    def get_client(self, id):
        client = self.db_clients[id]
        failed = 0
        while client is None:
            id = (id + 1) % 3
            client = self.db_clients[id]
            failed += 1
            if failed == 3:
                print("Failed to connect to any of the database")
                raise ConnectionError

        return client

    def process(self):
        """
        for now we just hand the twitter over tocouchdb and do area counting
        """
        for status in self.buffer:
            try:
                raw_json = status._json
                text = raw_json['text']
                tokens = self.tokenizer.tokenize(text)
                for country_cuisine in self.external_cuisines:
                    country = country_cuisine['country']
                    cuisine = set(country_cuisine['cuisine'])
                    flag, tag = 0, 1
                    for token in tokens:
                        if token.lower() in cuisine:
                            flag = 1

                    if flag == 1:
                        for token in tokens:
                            if token in BAD_WORDS:
                                tag = 0

                            db_json = self.tweet_filter.to_db_json(raw_json, country, tag, None)
                            try:
                                self.get_client(self.id).put_record("streaming", db_json)
                            except Exception:  # cannot put a record, database connection lost
                                self.get_client(self.id).put_record("streaming", db_json)
                        break
            except:
                continue


def main(id):
    api_key, api_key_secret, bearer_token, access_token, access_token_secret = TwitterAuthenticator(id).get_keys()
    print("123123123")
    streamer = TwitterStreamListener(api_key, api_key_secret, access_token, access_token_secret, id)
    print("Start...")
    streamer.filter(track=['Melbourne', 'Carlton', 'Monash', 'Docklands', 'Southbank', 'Parkiville', 'Lygon'
                           'Flemington', 'Swanston', 'Yarra', 'Queen Victoria Market', 'Wharf', 'Aussie',
                           'Melbourne China Town'])


if __name__ == "__main__":
    parser = argparse.ArgumentParser()
    parser.add_argument("--id", type=int, help="Specify the machine this process reside on")
    args = parser.parse_args()
    id = args.id
    main(id)