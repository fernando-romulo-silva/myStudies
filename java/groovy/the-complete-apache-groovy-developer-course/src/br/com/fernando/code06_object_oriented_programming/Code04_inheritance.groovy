package br.com.fernando.code06_object_oriented_programming

import groovy.transform.ToString

class Phone {
	String name
	String os
	String appStore
	
	def powerOn() {
		
	}
	
	def powerOff() {
		
	}
	
	def ring() {
		
	}
}

@ToString
class IPhone extends Phone {

	String iosVersion;
	
	def homeButtonPressed() {
		
	}
	
	def airPlay() {
		
	}
	
	@Override
	def powerOn() {
		
	}
}

Phone phone = new IPhone(name: "Iphone 1", os: "ios", appStore: "Apple Store")

