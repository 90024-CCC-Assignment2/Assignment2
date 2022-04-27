import json
import requests
import tweepy
api_key = "T7A3QS2KpZ7gLquq2YQmGFRVT"
api_key_secret = "O91ujNgwI0PjxVIM4mRjZNrYDbAI6TxdeAxkYEodMpplXafPp9"
bearer_token = "AAAAAAAAAAAAAAAAAAAAAL6ebwEAAAAAUgjIxZ1LxDR9GOQAamnqDUSZvvY%3DnTjGukeZWaysdNDSOm3vHXwCbnupkVOqDQ17zyNP6bj1NEJEGl"
access_token = "1518824432869208065-LveG04DpyB9iFqllV8ETa5h8m0g0Ay"
access_token_secret = "gBREHjjrGNejrAFw10x5fp8GUcxGs03P3yLm1XOyRE2zn"

AREAS = ["Melbourne", "(Port Phillip)", "Stonnington", "Malvern", "Yarra", "Richmond"]

KEYWORDS = ["gas", "price"]

query = "(" + " OR ".join(AREAS) + ") (" + " ".join(KEYWORDS) + ")"

# ==========================Tweepy Client================================
# Client = tweepy.Client(bearer_token=bearer_token, return_type=requests.Response)
# response = Client.search_recent_tweets(query=query, tweet_fields=['context_annotations', 'created_at', 'geo'], place_fields=['place_type', 'geo'], expansions='geo.place_id', max_results=100)

## display tweet data
# for tweet in response.data:
#     print(tweet)

# Save tweet samples
# total_tweet = dict()
# for tweet in response.json()['data']:
#     total_tweet[tweet['id']] = tweet
# with open("AREAS_KEYWORDS_FILTER_sample.json", "w") as f_out:
#     json.dump(total_tweet, f_out)

#=========================================================================

# ===============================Tweepy Stream============================
class IDPrinter(tweepy.StreamingClient):

    def on_tweet(self, tweet):
        print(f"{tweet.id} {tweet.created_at} ({tweet.author_id}): {tweet.text}")

printer = IDPrinter(bearer_token=bearer_token)

# clean-up pre-existing rules
rule_ids = []
result = printer.get_rules()
for rule in result.data:
    print(f"rule marked to delete: {rule.id} - {rule.value}")
    rule_ids.append(rule.id)

if (len(rule_ids) > 0):
    printer.delete_rules(rule_ids)
    printer = IDPrinter(bearer_token)
else:
    print("no rules to delete")

rules = tweepy.StreamRule(value=query)
printer.add_rules(rules)
printer.filter(expansions="author_id", tweet_fields="created_at")

#=========================================================================
