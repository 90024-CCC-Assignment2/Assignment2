package com.cb.repo;

import com.cb.module.Tweet;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TweetRepo extends CouchbaseRepository<Tweet, Integer> {

}
