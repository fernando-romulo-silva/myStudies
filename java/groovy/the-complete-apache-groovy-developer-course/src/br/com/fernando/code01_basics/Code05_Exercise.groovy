package br.com.fernando.code01_basics

import groovy.transform.ToString

@ToString
class Tweet {
	String userName;
	String text;
	Integer retweets;
	Integer favorites;
	Date createOn;
	
	Tweet(String user, String tweet) {
		
		userName = user;
		text = tweet;
		
		retweets = 0;
		favorites = 0;
		
		createOn = new Date();
	}
	
	void addToRetweets() {
		retweets += 1;
	}
	
	void addToFavorites() {
		favorites += 1;
	}
}

def tweet = new Tweet("fernando", "tweet 1")

println tweet;

tweet.addToFavorites();

println tweet;
