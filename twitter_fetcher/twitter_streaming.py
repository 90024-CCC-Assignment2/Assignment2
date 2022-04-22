import configparser
from distutils.command.config import config
import requests
import json

from mpi4py import MPI
import tweepy


######################
parser = configparser.ConfigParser()
parser.read("credentials.ini")
# Define some static variables
DBS = []  # our databases
USER_NAME = parser['CouchDB']['admin']
PASSWORD = parser['CouchDB']['password']

RULES = []
###################### 
communicator = MPI.COMM_WORLD


class Grid():
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




class TwitterAuthenticator():
    """
    We need to use multiple twitter api credentials to harvest twitters. (In our case, 4)
    We use id to assign the api credential of this particular process (process id in a multi-process situaiton)
    """

    def __init__(self, id):
        parser = configparser.ConfigParser()
        parser.read("credential.ini")
        self.api_key = parser["twitter{}".format(id)]["api_key"]
        self.api_key_secret = parser["twitter{}".format(id)]["api_key_secret"]
        self.access_token = parser["twitter{}".format(id)]["access_token"]
        self.access_toekn_secret = parser["twitter{}".format(id)]["access_token_secret"]
    
    def authenticate(self):
        auth = tweepy.OAuthHandler(self.api_key, self.api_key_secret)
        auth.set_access_token(self.access_toekn, self.access_toekn_secret)
        
        return auth


class TwitterStreamListner(tweepy.StreamListener):

    """
    Class for processing streaming twitters
    """
    def on_status(self, status):
        self.process(status)

        return True
    
    def on_error(self, status_code):
        if status_code == "420":
            return False
        print(status_code)

    def process(status):
        """
        for now we just hand the twitter over tocouchdb and do area counting
        """
        # TODO not quite sure if it is a good strategy to establish short http connection to couchdb everytime receiving a new json data 
        data_to_store = json.dumps(status._json)
        # TODO How are we actually going to do this? 
        db = DBS[communicator.Get_rank()]
        response = requests.post("http://" + USER_NAME + ":" + PASSWORD + "@localhost:5984" + db, json=data_to_store)
        



class TwitterStream():
    """
    Streaming class
    """

    def __init__(self, listener, rank, reenter=0):
        self.reenter = reenter
        self.rank = rank
        self.stream = tweepy.Stream(TwitterAuthenticator(self.rank).authenticate(), listener)

    def begin(self):
        pass

if __name__ == "__main__":
    print("hello world")
    tweet_api = tweepy.api(TwitterAuthenticator().authenticate(), wait_on_rate_limit=True)
    melbourne = Grid(5, 5)
    process_rank = communicator.Get_rank()
    streamer = TwitterStream(TwitterStreamListner(), process_rank)


