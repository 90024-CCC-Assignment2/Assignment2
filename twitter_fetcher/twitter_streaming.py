import configparser
import requests
import json
from collections import Counter

from mpi4py import MPI
import tweepy


"""
For now I don't think I have already incorporate rate limit waiting mechanism into the code. (New tweepy calls it differently?)
But when doing test, only filtering on Melbourne, it appears that we cannot reach the upper limit given that the speed is 
around 10-15 tweets/min.
"""

######################
parser = configparser.ConfigParser()
parser.read("credentials.ini")
# Define some static variables
DBS = ["test"]  # our databases
USER_NAME = parser['CouchDB']['admin']
PASSWORD = parser['CouchDB']['password']

MELBOURNE_CITY_BOUNDING_BOX = [144.932, -37.882, 144.996, -37.775]  # ????

RULES = ["graffiti", "street art", "painting"]  # TODO to be added
###################### 
communicator = MPI.COMM_WORLD


class Grid:
    """
    Class for defining a grid (for now just in city of Melbourne). We want to divide the city into multiple areas
    and collect meta-data (counts for instance) for front-end live demo.
    """
    def __init__(self, rows, columns):
        # directly hard coding it.
        self.north = -37.775
        self.south = -37.882 
        self.east = 144.996
        self.west = 144.932
        self.rows = rows
        self.columns = columns
        self.partitions = rows * columns
        # for simplicity, we start from the southwest corner and traverse it row by row
        self.lats = [self.west + (i * (self.east - self.west) / self.columns) for i in range(columns)]
        self.longs = [self.south + (i * (self.north - self.south) / self.rows) for i in range(rows)]

    def get_block_spot(self, id):
        """
        Input the id of a particular block, return 4 coordinates of its corners.
        """
        pass

    def get_grid_no(self, coordinate):
        """
        Input the coordinate, return its grid number, -1 if not in our map
        """
        lat, longi = coordinate


class TwitterAuthenticator:
    """
    We need to use multiple twitter api credentials to harvest twitters. (In our case, 4)
    We use id to assign the api credential of this particular process (process id in a multi-process situaiton)
    """

    def __init__(self, id):
        # parser = configparser.ConfigParser()
        # parser.read("credential.ini")
        self.api_key = parser["Twitter{}".format(id)]["api_key"]
        self.api_key_secret = parser["Twitter{}".format(id)]["api_key_secret"]
        self.access_token = parser["Twitter{}".format(id)]["access_token"]
        self.access_token_secret = parser["Twitter{}".format(id)]["access_token_secret"]
    
    def authenticate(self):
        auth = tweepy.OAuthHandler(self.api_key, self.api_key_secret)
        auth.set_access_token(self.access_token, self.access_token_secret)
        
        return auth

    def get_keys(self):
        return self.api_key, self.api_key_secret, self.access_token, self.access_token_secret


class TwitterStreamListner(tweepy.Stream):
    """
    Class for processing streaming twitters
    """
    def __init__(self, consumer_key, consumer_secret, access_token, access_token_secret, rows, columns, **kwargs):
        super().__init__(consumer_key, consumer_secret, access_token, access_token_secret, **kwargs)
        self.grid_worker = LocationCounter(rows, columns)

    def on_status(self, status):
        self.process(status)

        return True

    def on_error(self, status_code):
        if status_code == "420":
            return False
        print(status_code)

    def process(self, status):
        """
        for now we just hand the twitter over tocouchdb and do area counting
        """
        # TODO not quite sure if it is a good strategy to establish short http connection to couchdb everytime receiving a new json data 
        try:
            data_to_store = status._json
        except:
            return
        # TODO How are we actually going to do this? 
        db = DBS[communicator.Get_rank()]
        response = requests.post("http://" + USER_NAME + ":" + PASSWORD + "@localhost:5984/" + db, json=data_to_store)
        print("one write operation with {}".format(response.status_code))
        self.grid_worker.work(data_to_store)


class LocationCounter:
    """
    For now it is just a location counter, we might upgrade this class for it to do more ambitious tasks in the future.
    """
    def __init__(self, rows, columns):
        self.grid = Grid(rows, columns)
        self.counts = Counter()
        self.rank = communicator.Get_rank()
    
    def work(self, twitter):
        """
        This grid counter can work only based on the provided coordinates attribute in the json file.
        However, one interesting worth mentioning is that among twitters with geo information, most of
        them
        """
        coordinates = twitter['coordinates']
        if coordinates is not None:
            pass


if __name__ == "__main__":
    tweet_api = tweepy.API(TwitterAuthenticator(communicator.Get_rank()).authenticate(), wait_on_rate_limit=True)
    melbourne = Grid(5, 5)
    process_rank = communicator.Get_rank()
    api_key, api_key_secret, access_token, access_token_secret = TwitterAuthenticator(communicator.Get_rank()).get_keys()
    streamer = TwitterStreamListner(api_key, api_key_secret, access_token, access_token_secret, 5, 5)
    if process_rank == 0:
        # streamer.filter(track=[""])  # TODO fill in the coordinate filter rule
        streamer.filter(track=RULES, locations=MELBOURNE_CITY_BOUNDING_BOX)
        # pass
    else:
        rule = RULES[process_rank-1]
        streamer.filter(track=["melbourne {}".format(rule)], languages=["en"])
 

