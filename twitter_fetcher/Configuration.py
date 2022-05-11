import json
import configparser


parser = configparser.ConfigParser()
parser.read("credentials.ini")
RULES = []
USER_NAME_1 = parser['CouchDB-0']['admin']
PASSWORD_1 = parser['CouchDB-0']['password']
URL_1 = parser['CouchDB-0']['URL']

USER_NAME_2 = parser['CouchDB-1']['admin']
PASSWORD_2 = parser['CouchDB-1']['password']
URL_2 = parser['CouchDB-1']['URL']

USER_NAME_3 = parser['CouchDB-2']['admin']
PASSWORD_3 = parser['CouchDB-2']['password']
URL_3 = parser['CouchDB-2']['URL']

