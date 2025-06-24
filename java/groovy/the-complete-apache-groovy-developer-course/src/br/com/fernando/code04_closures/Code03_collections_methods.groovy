package br.com.fernando.code04_closures

// each & eachWithIndex

def favNums = [2, 21, 44, 35, 8, 4]

for (num in favNums) {
	println num
}

favNums.each {
	println it
}


for (int i=0; i<favNums.size(); i++) {
	println "$i:${favNums[i]}"
}


favNums.eachWithIndex { num, idx ->
	println "$idx:$num"
}

// findAll

def days = ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"]

def weekends = days.findAll { it.startsWith("S") }

println days
println weekends

// collect

def nums = [1, 2, 2, 7, 2, 4, 6]

def numsTimeTen = []

nums.each { num ->
	numsTimeTen << num * 10
}

println nums
println numsTimeTen


def newTimesTen = nums.collect { num -> num * 10 }

println newTimesTen


