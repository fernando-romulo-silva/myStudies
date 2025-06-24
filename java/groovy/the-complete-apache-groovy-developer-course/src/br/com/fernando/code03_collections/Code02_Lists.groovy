package br.com.fernando.code03_collections

def nums = [1, 2, 3, 4, 5, 3, 4, 8, 9]
println nums
println nums.class.name // ArrayList

// --------------------------------

def nums2 = [1, 2, 3, 4, 5, 3, 4, 8, 9] as LinkedList
println nums2
println nums2.class.name // LinkedList

// --------------------------------

nums.push(99)
println nums

nums.putAt(0, 77)
println nums

nums.add(66)
println nums

println nums + [12, 13, 14]

// --------------------------------

nums.pop()
println nums

nums.removeAt(0)
println nums
println nums - [1, 2, 3]

// --------------------------------

println nums.getAt(0..3)

for (x in nums) {
	println x
}

// --------------------------------

// flatten

nums << [58, 45, 67]
nums << [76, 33]

println nums
println nums.flatten()

println nums.unique()

// --------------------------------

def numbers = [34, 12, 1, 23, 45, 8, 9, 34, 23] as SortedSet
println numbers








