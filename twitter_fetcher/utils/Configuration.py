import json
import configparser


parser = configparser.ConfigParser()
parser.read("../credentials.ini")
RULES = []
USER_NAME = parser['CouchDB']['username']
PASSWORD = parser['CouchDB']['password']
URL = parser['CouchDB']['URL']





