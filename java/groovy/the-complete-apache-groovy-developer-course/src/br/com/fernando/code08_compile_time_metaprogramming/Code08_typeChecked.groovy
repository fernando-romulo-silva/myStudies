package br.com.fernando.code08_compile_time_metaprogramming

import groovy.transform.TypeChecked

@TypeChecked
class Leader {
	String first;
	String last;
	
	String getFullName() {
		"$first $last"
	}
}
