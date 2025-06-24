package br.com.fernando.code01_basics

@Grapes(
	@Grab(group='org.apache.commons', module='commons-lang3', version='3.4')
)

import org.apache.commons.lang3.text.WordUtils;

String name = "Fernando Silva";

def wordUtils = new WordUtils();

println wordUtils.initials(name)
