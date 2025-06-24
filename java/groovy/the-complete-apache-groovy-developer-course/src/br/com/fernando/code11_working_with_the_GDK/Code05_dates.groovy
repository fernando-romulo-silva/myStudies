package br.com.fernando.code11_working_with_the_GDK

import java.util.Date

// create a new date
def today = new Date()
println today
println "----------------"

def bday = new Date("08/21/1978")
println bday
println "----------------"

// add & subtract

today.plus(7);
today.minus(7);
