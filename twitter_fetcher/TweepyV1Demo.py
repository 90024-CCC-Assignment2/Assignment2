import tweepy
api_key = "T7A3QS2KpZ7gLquq2YQmGFRVT"
api_key_secret = "O91ujNgwI0PjxVIM4mRjZNrYDbAI6TxdeAxkYEodMpplXafPp9"
bearer_token = "AAAAAAAAAAAAAAAAAAAAAL6ebwEAAAAAUgjIxZ1LxDR9GOQAamnqDUSZvvY%3DnTjGukeZWaysdNDSOm3vHXwCbnupkVOqDQ17zyNP6bj1NEJEGl"
access_token = "1518824432869208065-LveG04DpyB9iFqllV8ETa5h8m0g0Ay"
access_token_secret = "gBREHjjrGNejrAFw10x5fp8GUcxGs03P3yLm1XOyRE2zn"


class MyStreamListener(tweepy.Stream):

    def on_status(self, status):
        print("--------")
        print(status.text)

myStreamListener = MyStreamListener(
    consumer_key=api_key, consumer_secret=api_key_secret,
    access_token=access_token, access_token_secret=access_token_secret,)

myStreamListener.filter(track=["good", "girl"])
