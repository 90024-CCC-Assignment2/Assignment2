import json
import requests
import tweepy

# ==================================KEY==================================
api_key = "T7A3QS2KpZ7gLquq2YQmGFRVT"
api_key_secret = "O91ujNgwI0PjxVIM4mRjZNrYDbAI6TxdeAxkYEodMpplXafPp9"
bearer_token = "AAAAAAAAAAAAAAAAAAAAAL6ebwEAAAAAUgjIxZ1LxDR9GOQAamnqDUSZvvY%3DnTjGukeZWaysdNDSOm3vHXwCbnupkVOqDQ17zyNP6bj1NEJEGl"
access_token = "1518824432869208065-LveG04DpyB9iFqllV8ETa5h8m0g0Ay"
access_token_secret = "gBREHjjrGNejrAFw10x5fp8GUcxGs03P3yLm1XOyRE2zn"

# =======================================================================

# ==========================Tweepy Client================================
Client = tweepy.Client(bearer_token=bearer_token)

with open("./Celebrity/Celebrity.json") as f:
    json_f = json.load(f)
    Celebrities = json_f['list']

    # Crawl the followers of Celebrities
    # we believe that followers are Melbourne local citizens
    # REF = https://docs.tweepy.org/en/stable/client.html#tweepy.Client.get_users_followers
    for Celebrity in Celebrities:
        Celebrity_ID = Celebrity['id']
        for Follower in tweepy.Paginator(Client.get_users_followers, Celebrity_ID, user_fields=['profile_image_url'], max_results=1000).flatten(limit=5000):
            Follower_ID = Follower.id
            Follower_PROFILE = Follower.profile_image_url
            # Crawl the tweets of followers
            # REF = https://docs.tweepy.org/en/stable/client.html#tweepy.Client.get_users_tweets
            # Save to database CouchDb
            for tweet in tweepy.Paginator(Client.get_users_tweets, Follower_ID, tweet_fields=['created_at'], max_results=100).flatten(limit=300):
                Tweet_ID = tweet.id
                Tweet_TEXT = tweet.text
                Tweet_CREATEDAT = tweet.created_at

