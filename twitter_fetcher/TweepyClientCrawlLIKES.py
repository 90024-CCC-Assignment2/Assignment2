# ==================================KEY==================================
api_key = "T7A3QS2KpZ7gLquq2YQmGFRVT"
api_key_secret = "O91ujNgwI0PjxVIM4mRjZNrYDbAI6TxdeAxkYEodMpplXafPp9"
bearer_token = "AAAAAAAAAAAAAAAAAAAAAL6ebwEAAAAAUgjIxZ1LxDR9GOQAamnqDUSZvvY%3DnTjGukeZWaysdNDSOm3vHXwCbnupkVOqDQ17zyNP6bj1NEJEGl"
access_token = "1518824432869208065-LveG04DpyB9iFqllV8ETa5h8m0g0Ay"
access_token_secret = "gBREHjjrGNejrAFw10x5fp8GUcxGs03P3yLm1XOyRE2zn"

def main():
    # =======================================================================

    # ==========================Tweepy Client================================
    Client = tweepy.Client(bearer_token=bearer_token)
    # ==========================Tweepy Api================================
    auth = tweepy.OAuthHandler(api_key, api_key_secret)
    auth.set_access_token(access_token, access_token_secret)
    Api = tweepy.API(auth, wait_on_rate_limit=True)
    json_filer = TweetAttributeFilter()

    # 1 Connect to CouchDB
    client = DBClient('admin', 'password1234', 'http://localhost:5984/')

    # 2 Read prepared Melbourne Restaurants
    with open("External_Data/Restaurant.json") as f_restaurant:
        json_restaurant = json.load(f_restaurant)
        Restaurant = json_restaurant['list']

    for restaurant in Restaurant:
        restaurant_response = Api.user_timeline(user_id=restaurant["id"], count=10)
        ids = [tweet.id for tweet in restaurant_response.data]
        tweets_response = Client.get_tweets(ids)


if __name__ == '__main__':
    main()