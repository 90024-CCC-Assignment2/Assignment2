import json
import requests
import tweepy
from utils.db_client import *

# ==================================KEY==================================
api_key = "T7A3QS2KpZ7gLquq2YQmGFRVT"
api_key_secret = "O91ujNgwI0PjxVIM4mRjZNrYDbAI6TxdeAxkYEodMpplXafPp9"
bearer_token = "AAAAAAAAAAAAAAAAAAAAAL6ebwEAAAAAUgjIxZ1LxDR9GOQAamnqDUSZvvY%3DnTjGukeZWaysdNDSOm3vHXwCbnupkVOqDQ17zyNP6bj1NEJEGl"
access_token = "1518824432869208065-LveG04DpyB9iFqllV8ETa5h8m0g0Ay"
access_token_secret = "gBREHjjrGNejrAFw10x5fp8GUcxGs03P3yLm1XOyRE2zn"

# =======================================================================

# ==========================Tweepy Client================================
Client = tweepy.Client(bearer_token=bearer_token)
# ==========================Tweepy Api================================
auth = tweepy.OAuthHandler(api_key, api_key_secret)
auth.set_access_token(access_token, access_token_secret)
Api = tweepy.API(auth, wait_on_rate_limit=True)

# 1 Connect to CouchDB
client = DBClient('admin', 'password1234', 'http://localhost:5984/')

# 2 Read prepared Melbourne celebrities
with open("External_Data/Celebrity.json") as f_celebrity:
    json_celebrity = json.load(f_celebrity)
    Celebrities = json_celebrity['list']

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
        response = Api.user_timeline(user_id=Follower_ID, count=200)
        for status in response:
            tweet = json.loads(status._json)
            Tweet_ID = tweet.id
            Tweet_TEXT = tweet.text
            Tweet_CREATEDAT = tweet.created_at

            # 5 Manual filter
            # tweet is filtered by cuisine types, e.g. dumpling
            # REF = https://edition.cnn.com/travel/article/world-best-foods-readers-choice/index.html
            #     = https://www.allrecipes.com/recipes/700/world-cuisine/asian/korean/
            #     = https://edition.cnn.com/travel/article/world-best-food-cultures/index.html
            #     = https://www.usnews.com/news/best-countries/articles/2016-04-15/the-10-countries-with-the-best-food-ranked-by-perception
            #     = https://yonderbound.com/blog/20-best-countries-world-food/
            #     = https://www.mappr.co/countries-best-cuisine/
            with open("./External_Data/Cuisine.json") as f_cuisine:
                json_cuisine = json.load(f_cuisine)
                Cuisines = json_cuisine['list']
                for country_cuisine in Cuisines:
                    country = country_cuisine["country"]
                    cuisines = country_cuisine["cuisine"]
                    # put tweet in the database if the tweet contains food information
                    if any(map(lambda food: food.lower() in Tweet_TEXT.lower(), cuisines)):

                        # 6 Tag based on Classification
                        # 1 is like , 0 is dislike
                        Tag = 1

                        # 7 Put in database
                        client.put_record("test", {'_id': Tweet_ID, 'tweet_text': Tweet_TEXT, 'tweet_created_at': Tweet_CREATEDAT, 'author_id': Follower_ID, 'author_username': Follower_name, 'author_profile': Follower_PROFILE, 'food_country': country, 'food_preference': Tag})