interface User {
    name: string;
    age: number;
    isDeveloper: boolean;

    speak(word: string) : void;
    spend(value: number) : number;
}

// Merge declaration
interface User { // same name!

    walk() : void; 

}


const firstUser: User = {
    name: 'Jane',
    age: 25,
    isDeveloper: true,

    speak(word: string) : void {
        console.log(word);        
    },

    spend(value: number) : number {
        console.log(`I spend ${value}`);
        return value;
    },

    walk() { // Property 'walk' is missing in type

    }
};

const greetPerson = (person: User) => {
    console.log(person.speak('bla bla'));   
}

greetPerson(firstUser);




