import tweepy


def get_auth_object(keys):
    auth = tweepy.OAuthHandler(keys[0], keys[1])
    auth.set_access_token(keys[2], keys[3])
    return tweepy.API(auth, wait_on_rate_limit=True)

