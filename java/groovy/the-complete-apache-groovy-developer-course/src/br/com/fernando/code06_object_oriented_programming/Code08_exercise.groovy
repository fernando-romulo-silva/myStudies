package br.com.fernando.code06_object_oriented_programming

import groovy.transform.Canonical

@Canonical
class Tweet {
	String post
	String userName
	Date postDateTime
	
	private List favorites = []
	private List retweets = []
	private List mentions = []
	private List hashTags = []
	
	def favorite(String userName) {
		favorites << userName
	}
	
	def retweet(String userName) {
		retweets << userName
	}
	
	List getMentions() {
		def pattern = /\B@[a-z0-9_-]+/
		post.findAll(pattern)
	}
}

def tweet = new Tweet(post: "This is a groovy course @silva", userName: "@silva", postDateTime: new Date())

tweet.retweet("@ApacheGroovy")
tweet.favorite("@ApacheGroovy")

println tweet

println tweet.mentions
