package br.com.fernando.chapter01_introductionToDoker.part02_dockerConcepts;

public class Part02_Layer {

    // Each image consists of a series of layers which are stacked, one on top of the another.
    // In fact, every layer is an intermediate image.
    //
    // By using the union filesystem, Docker combines all these layers into a single image entity.
    //
    // You should always be aware of the additivity of layers and try to optimize the image at every step of your Dockerfile,
    // the same as using the command chaining
    //
    // Because layers are additive, they provide a full history of how a specific image was built.
    // This gives you another great feature: the possibility to make a rollback to a certain point in the image's history.
    // Since every image contains all of its building steps, we can easily go back to a previous step if we want to.
}
