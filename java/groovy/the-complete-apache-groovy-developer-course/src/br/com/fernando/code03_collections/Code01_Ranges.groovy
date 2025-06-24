package br.com.fernando.code03_collections

for(int x = 1; x <= 10; ++x) {
	println x	
}

println ""

def letters = ['a', 'b', 'c']

println ""

// ---------------------------------------

Range r = 1..10
println r
println r.class.name

println "from " + r.from + " to " + r.to

assert(0..10).contains(0)

// ---------------------------------------

@Grapes(
    @Grab(group='org.codehaus.groovy', module='groovy-dateutil', version='3.0.23')
)


def today = new Date()
def oneWeekAway = today + 7

println today
println oneWeekAway

Range days = today..oneWeekAway

println days

// ---------------------------------------

Range letters2 = 'a'..'z'
println letters2

