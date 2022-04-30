import json
from utils.db_client import DBClient
from TweepyClientCrawlCelebrity import process

def main():
    db_client = DBClient('admin', 'password1234', 'http://localhost:5984/')
    with open("External_Data/areas.json") as f_areas:
        json_area = json.load(f_areas)
        Areas = json_area["data"]
    with open('/Users/gaohaoyu/historical_twitter/twitter-melb.json') as f:
        f.readline()
        while True:
            try:
                tweet = json.loads(f.readline().strip().strip(','))['doc']
            except:
                print("finish")
                break
            try:
                process(db_client, tweet, Areas)
            except Exception as e:
                print(e)
                continue


if __name__ == '__main__':
    main()
