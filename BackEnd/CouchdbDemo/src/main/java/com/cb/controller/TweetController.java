package com.cb.controller;

import com.cb.module.Tweet;
import com.cb.repo.TweetRepo;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@RestController
@RequestMapping("/tweets")
public class TweetController {

    @Autowired
    private TweetRepo tweetRepo;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String saveTweet(@RequestBody Tweet tweet){
        tweetRepo.save(tweet);
        return "Tweet saved successfully";
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Tweet> getAllTweets(){
        return tweetRepo.findAll();
    }

}
