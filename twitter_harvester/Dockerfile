#
# COMP90024 Cluster and Cloud Computing Assignment2 - Team 42 
# 
# Authors: 
#
#  - Haoyu Gao (Student ID: 1141213)
#  - Runqi Zhao (Student ID: 1159719)
#  - Shukai Liu (Student ID: 1233459)
#  - Siyu Liu (Student ID：1094521)
#  - Wenjun Wang (Student ID: 1249890)
#
# Location: Melbourne
#
FROM python:3

ADD . /

# Install dependencies
RUN pip install tweepy
RUN pip install nltk
RUN pip install cloudant

CMD [ "python", "./main.py"]
