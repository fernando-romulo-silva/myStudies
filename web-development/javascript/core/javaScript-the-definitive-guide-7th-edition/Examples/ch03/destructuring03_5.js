Symbols
let strname = "string name";      // A string to use as a property name
let symname = Symbol("propname"); // A Symbol to use as a property name

typeof strname                    // => "string": strname is a string
typeof symname                    // => "symbol": symname is a symbol

let o = {};                       // Create a new object
o[strname] = 1;                   // Define a property with a string name
o[symname] = 2;                   // Define a property with a Symbol name

o[strname]                        // => 1: access the string-named property
o[symname]  



let s = Symbol("sym_x");
s.toString()  

let s = Symbol.for("shared");
let t = Symbol.for("shared");
s === t          // => true
s.toString()     // => "Symbol(shared)"
Symbol.keyFor(t) // => "shared"