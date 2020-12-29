package br.fernando.ch02_object_orientation.par02_inheritance_and_polymorphism;

public class Test03 {

    // =========================================================================================================================================
    //
    static void test01() {

        // So now we have a PlayerPiece that passes the IS-A test for both the GameShape class and the Animatable interface.
        // That means a PlayerPiece can be treated polymorphically as one of four things at any given time, depending on the declared
        // type of the reference variable:
        //
        // An Object (since any object inherits from Object )
        // A GameShape (since PlayerPiece extends GameShape )
        // A PlayerPiece (since that’s what it really is)
        // An Animatable (since PlayerPiece implements Animatable

        PlayerPiece player = new PlayerPiece();
        Object o = player;
        GameShape shape = player;
        Animatable mover = player;

        // Polymorphic method invocations apply only to instance methods. You can
        // always refer to an object with a more general reference variable type (a
        // superclass or interface), but at runtime, the ONLY things that are dynamically
        // selected based on the actual object (rather than the reference type) are instance
        // methods. Not static methods. Not variables. Only overridden instance methods
        // are dynamically invoked based on the real object’s type
    }

    static interface Animatable {

        public void animated();
    }

    static class GameShape implements Animatable {

        void displayShape() {
            System.out.println("displaying shape");
        }

        public void animated() {
            System.out.println("animating...");
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
