package br.com.fernando.chapter15_batchProcessing.part01_chunkOrientedProcessing;

import java.util.List;

import javax.batch.api.chunk.AbstractItemWriter;
import javax.inject.Named;

// The ItemWriter interface is used to write a stream of a “chunk- number of aggregated items.
// Generally, an item writer has no knowledge of the input it will receive next, only the item that was passed in its current invocation.
//
// The item writer is an implementation of the ItemWriter interface or extends AbstractItemWriter class:
@Named // @Named ensures that this bean can be referenced in Job XML.
public class MyItemWriter extends AbstractItemWriter {

    //
    // The writeItems method receives the aggregated items and implements the write logic for the item writer. 
    // A List of MyOutputRecord is received in this case. 
    @Override
    public void writeItems(@SuppressWarnings("rawtypes") List list) {
        System.out.println("writeItems: " + list);
    }
}
