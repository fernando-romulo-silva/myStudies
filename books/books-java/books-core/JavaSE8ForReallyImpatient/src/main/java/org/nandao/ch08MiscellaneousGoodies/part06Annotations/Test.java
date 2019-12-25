package org.nandao.ch08MiscellaneousGoodies.part06Annotations;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.nandao.ch02TheStreamAPI.part10CollectingIntoMaps.Test.Person;

public class Test {
    // Repeated Annotations
    // When annotations were first created, they were envisioned to mark methods and
    // fields for processing, for example
    @interface Color {
        String name();
    }

    // Soon, more and more uses for annotations emerged, leading to situations where
    // one would have liked to repeat the same annotation.
    @Color(name = "red")
    // @Color(name = "blue")
    // @Color(name = "green") // @Color annotation repeated 3 times
    // You can't use the same annotation
    static class Shirt {

    }

    @interface Colors {
        Color[] value();
    }

    // That’s pretty ugly, and it is no longer necessary in Java 8.
    // As an annotation user, that is all you need to know. If your framework provider
    // has enabled repeated annotations, you can just use them.
    // For a framework implementor, the story is not quite as simple. After all, the
    // AnnotatedElement interface has a method
    @Colors({ //
            @Color(name = "red"), //
            @Color(name = "blue") })
    static class TShirt {

    }

    @Repeatable(ColorsNew.class)
    @interface ColorNew {
        String name();
    }

    @Retention(RetentionPolicy.RUNTIME)
    @interface ColorsNew {
        ColorNew[] value();
    }

    @ColorNew(name = "red")
    @ColorNew(name = "blue")
    @ColorNew(name = "green")
    class ShirtNew {
    }

    // Type Use Annotations

    // The attribute 'names1' can't be null
    @NonNull
    private final List<String> names1 = new ArrayList<>();

    //
    private @NonNull final List<String> names2 = new ArrayList<>();

    public static void test2() {
        // In Java 8, you can annotate any type use. This can be useful in combination with
        // tools that check for common programming errors. One common error is throwing
        // a NullPointerException because the programmer didn’t anticipate that a reference
        // might be null. Now suppose you annotated variables that you never want to
        // be null as @NonNull.

        @NonNull
        final List<String> names3 = new ArrayList<>();

        // In the preceding example, the names variable was declared as @NonNull. That annotation
        // was possible before Java 8. But how can one express that the list elements
        // should be non-null? Logically, that would be
        final List<@NonNull String> names4;

        // It is this kind of annotation that was not possible before Java 8 but has now
        // become legal.

        // Type use annotations can appear in the following places:
        // • With generic type arguments: List<@NonNull String>, Comparator.<@NonNull String> reverseOrder().
        //
        // • In any position of an array: @NonNull String[][] words (words[i][j] is not null),
        // String @NonNull [][] words (words is not null), String[] @NonNull [] words (words[i] is not null).
        //
        // • With superclasses and implemented interfaces: class Image implements @Rectangular Shape.
        //
        // • With constructor invocations: new @Path String("/usr/bin").
        //
        // • With casts and instanceof checks: (@Path String) input, if (input instanceof @Path
        // String). (The annotations are only for use by external tools. They have no effect
        // on the behavior of a cast or an instanceof check.)
        //
        // • With exception specifications: public Person read() throws @Localized IOException.
        //
        // • With wildcards and type bounds: List<@ReadOnly ? extends Person>, List<? extends @ReadOnly> Person.
        //
        // • With method and constructor references: @Immutable Person::getName
    }

    // Method Parameter Reflection
    // The names of parameters are now available through reflection. That is promising
    // because it can reduce annotation boilerplate. Consider a typical JAX-RS method
    public Person getEmployee(@PathParam("dept") Long dept, @QueryParam("id") Long id) {
        return null;
    }

    // In almost all cases, the parameter names are the same as the annotation arguments,
    // or they can be made to be the same. If the annotation processor could
    // read the parameter names, then one could simply write
    // public Person getEmployee2(@PathParam Long dept, @QueryParam Long id) {
    //    return null;
    // }
    
    // This is possible in Java 8, with the new class java.lang.reflect.Parameter.
    // Unfortunately, for the necessary information to appear in the classfile, the source
    // must be compiled as javac -parameters SourceFile.java. Let’s hope annotation writers
    // will enthusiastically embrace this mechanism, so there will be momentum to
    //drop that compiler flag in the future.

    public static void test3() {

    }

    public static void main(String[] args) {

    }
}
