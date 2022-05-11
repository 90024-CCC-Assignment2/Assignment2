import json
import requests
import tweepy
from utils.db_client import DBClient
from utils.Tweet import TweetAttributeFilter
from nltk import TweetTokenizer
from twitter_streaming import TwitterAuthenticator, BAD_WORDS
import configparser
import time
from datetime import datetime


def process(client, tweet, Areas, db='Restful'):
    with open("./External_Data/Cuisine.json") as f_cuisine:
        json_cuisine = json.load(f_cuisine)
        Cuisines = json_cuisine['list']
        for country_cuisine in Cuisines:
            country = country_cuisine["country"]
            cuisines = set(country_cuisine["cuisine"])
            # put tweet in the database if the tweet contains food information
            tokenizer = TweetTokenizer()
            tokens = tokenizer.tokenize(tweet['text'])
            flag = False
            for token in tokens:
                if token in cuisines:
                    flag = True
            if flag:
                Tag = 1
                # 6 Tag based on Classification
                # 1 is like , 0 is dislike
                # print(len(BAD_WORDS))
                for token in tokens:
                    if token in BAD_WORDS:
                        Tag = 0
                        break

                # 7 Put in database
                client.put_record(db, TweetAttributeFilter().to_db_json(tweet, country, Tag, Areas))
                break

# ==================================KEY==================================
authenticator = TwitterAuthenticator(0)
api_key, api_key_secret, bearer_token, access_token, access_token_secret = authenticator.get_keys()


def main():

    # ==========================Tweepy Client================================
    Client = tweepy.Client(bearer_token=bearer_token)
    # ==========================Tweepy Api================================
    auth = tweepy.OAuthHandler(api_key, api_key_secret)
    auth.set_access_token(access_token, access_token_secret)
    Api = tweepy.API(auth, wait_on_rate_limit=True)
    json_filer = TweetAttributeFilter()

    # 1 Connect to CouchDB
    client = DBClient('admin', 'password1234', 'http://localhost:5984/')

    # 2 Read prepared Melbourne celebrities
    with open("External_Data/Celebrity.json") as f_celebrity:
        json_celebrity = json.load(f_celebrity)
        Celebrities = json_celebrity['list']

    with open("External_Data/areas.json") as f_areas:
        json_area = json.load(f_areas)
        Areas = json_area["data"]

    # 3 Crawl the followers of Celebrities
    # we believe that followers are Melbourne local citizens
    # REF = https://docs.tweepy.org/en/stable/client.html#tweepy.Client.get_users_followers
    for Celebrity in Celebrities:
        Celebrity_ID = Celebrity['id']
        for Follower in tweepy.Paginator(Client.get_users_followers, Celebrity_ID, user_fields=['profile_image_url'], max_results=1000).flatten(limit=5000):
            Follower_ID = Follower.id
            Follower_name = Follower.username
            Follower_PROFILE = Follower.profile_image_url
            # 4 Crawl the tweets of followers
            # REF = https://docs.tweepy.org/en/stable/client.html#tweepy.Client.get_users_tweets
            # Save to database CouchDb
            try:
                response = Api.user_timeline(user_id=Follower_ID, count=200)
            except:
                continue

            for status in response:
                tweet = status._json
                if int(tweet['created_at'].split(' ')[-1]) < 2020:
                    continue
                # 5 Manual filter
                # tweet is filtered by cuisine types, e.g. dumpling
                # REF = https://edition.cnn.com/travel/article/world-best-foods-readers-choice/index.html
                #     = https://www.allrecipes.com/recipes/700/world-cuisine/asian/korean/
                #     = https://edition.cnn.com/travel/article/world-best-food-cultures/index.html
                #     = https://www.usnews.com/news/best-countries/articles/2016-04-15/the-10-countries-with-the-best-food-ranked-by-perception
                #     = https://yonderbound.com/blog/20-best-countries-world-food/
                #     = https://www.mappr.co/countries-best-cuisine/
                process(client, tweet, Areas)


if __name__ == '__main__':
    main()
