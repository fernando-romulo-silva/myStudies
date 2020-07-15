package br.com.fernando.myExamCloud.useCdiBeans;

public class Question02 {

    // Which of the following elements cannot be injected by using an @Inject annotation?
    //
    //
    // Choice A - static fields
    //
    // Choice B - instance fields declared final
    //
    // Choice C - concrete methods
    //
    // Choice D - abstract methods
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    // Explanation :
    // Choice B and D are correct answers.
    //
    // The @Inject annotation may apply to static as well as instance members.
    // An injectable member may have any access modifier (private, package-private, protected, public). Constructors are injected first,
    // followed by fields, and then methods. Fields and methods in superclasses are injected before those in subclasses.
    //
    // Ordering of injection among fields and among methods in the same class is not specified.
    //
    // Injectable fields:
    //
    // - are annotated with @Inject.
    // - are not final.
    // - may have any otherwise valid name.
    // - @Inject FieldModifiersopt Type VariableDeclarators;
    //
    //
    // Injectable methods:
    //
    // - are annotated with @Inject.
    // - are not abstract.
    // - do not declare type parameters of their own.
    // - may return a result
    // - may have any otherwise valid name.
    // - accept zero or more dependencies as arguments.
}
