import json
from collections import defaultdict
import os
from utils.db_client import DBClient

cloud_path = "/home/ubuntu/aurin/com_clue_cafe_restaurant_bistro_seats_2015.json"
local_path = "/Users/gaohaoyu/aurin/com_clue_cafe_restaurant_bistro_seats_2015.json"


def get_aurin_rules(file_path):
    rules = dict()
    with open(file_path, 'r') as f:
        rule_list = json.load(f)['list']
        for rule in rule_list:
            rules[rule['name']] = set(rule["bow"])

    return rules


def process_aurin_data(file_path, db_client, filter_rules):
    with open(file_path, 'r') as f:
        json_data = json.load(f)
        restaurants = json_data["features"]
        i = 0
        for restaurant in restaurants:
            i += 1
            coord = tuple(restaurant["geometry"]["coordinates"])
            name = restaurant['properties']['trading_name']
            found = False
            for country, words in filter_rules.items():
                letters = name.split(' ')
                for letter in letters:
                    letter = letter.lower()
                    if letter in words:
                        record = {"trading_name": name, "coordinate": coord, "country": country}
                        db_client.put_record("aurin_2015", record)
                        found = True
                        break
                if found:
                    break
        print(i)


if __name__ == '__main__':
    rule = get_aurin_rules("External_Data/AurinRule.json")
    db_client = DBClient("admin", "password1234", "http://localhost:5984/")
    process_aurin_data(local_path, db_client, rule)
