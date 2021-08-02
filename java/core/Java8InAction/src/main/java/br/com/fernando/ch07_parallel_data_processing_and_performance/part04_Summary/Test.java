package br.com.fernando.ch07_parallel_data_processing_and_performance.part04_Summary;

// Summary
public class Test {
    
    //  Internal iteration allows you to process a stream in parallel without the need to explicitly use and
    // coordinate different threads in your code. 
    // 
    //  Even if processing a stream in parallel is so easy, there’s no guarantee that doing so will make your
    // programs run faster under all circumstances. Behavior and performance of parallel software can
    // sometimes be counterintuitive, and for this reason it’s always necessary to measure them and be sure
    // that you’re not actually slowing your programs down. 
    // 
    //  Parallel execution of an operation on a set of data, as done by a parallel stream, can provide a
    // performance boost, especially when the number of elements to be processed is huge or the processing
    // of each single element is particularly time consuming. 
    // 
    //  From a performance point of view, using the right data structure, for instance, employing primitive
    // streams instead of nonspecialized ones whenever possible, is almost always more important than
    // trying to parallelize some operations. 
    // 
    //  The fork/join framework lets you recursively split a parallelizable task into smaller tasks, execute
    // them on different threads, and then combine the results of each subtask in order to produce the
    // overall result. 
    // 
    //  Spliterators define how a parallel stream can split the data it traverses.

}
