package br.fernando.ch02_object_orientation.par02_inheritance_and_polymorphism;

class Test01 {

    // =========================================================================================================================================
    // Inheritance and Polymorphism
    static void test01() {
        Animal animal01 = new Animal();
        Animal animal02 = new Animal();

        if (!animal01.equals(animal02)) {
            System.out.println("They're not equal");
        }

        // I’m sure you’re way ahead of us here, but it turns out that every class in
        // Java is a subclass of class Object (except, of course, class Object itself)
        if (animal01 instanceof Object) {
            System.out.println("animal01's an Object");
        }
    }

    static class Animal {

    }

    // =========================================================================================================================================
    // The Evolution of Inheritance
    // It’s also important to understand that the two most common reasons to use inheritance are To promote code reuse and To use polymorphism
    static void test02() {
        //
        PlayerPiece player = new PlayerPiece();
        player.displayShape();
        player.movePiece();

        // The beautiful thing about polymorphism (“many forms“) is that you can treat any
        // subclass of GameShape as a GameShape .

        TilePiece tile = new TilePiece();

        doShapes(player);
        doShapes(tile);

    }

    static void doShapes(GameShape shape) {
        shape.displayShape();
    }

    // -------------------------------

    static class GameShape {

        void displayShape() {
            System.out.println("displaying shape");
        }
    }

    static class PlayerPiece extends GameShape {

        void movePiece() {
            System.out.println("moving game piece");
        }
    }

    static class TilePiece extends GameShape {

        void getadjacent() {
            System.out.println("getting adjacent tiles");
        }
    }

    // =========================================================================================================================================
    public static void main(String[] args) {
        test01();
    }
}
