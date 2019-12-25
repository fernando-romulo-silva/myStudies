package br.com.fernando.chapter15_batchProcessing.part01_chunkOrientedProcessing;

import javax.batch.api.chunk.ItemProcessor;
import javax.inject.Named;

// The ItemProcessor interface operates on an input item and produces an output item by transforming or applying other business processing.
@Named // @Named annotation ensures that this bean can be referenced in Job XML.
//
// We mark MyItemProcessor as an item processor by implementing the ItemProcessor interface.
public class MyItemProcessor implements ItemProcessor {

    // The processItem method is part of a chunk step.
    // This method accepts an input from the item reader and returns an output that gets passed to the output writer for aggregation.
    @Override
    //
    // In this case, the method receives an item of type MyInputRecord, applies the business logic, and returns an output item of type MyOutputRecord. 
    // The output item is then aggregated and written. 
    public MyOutputRecord processItem(Object t) {
        System.out.println("processItem: " + t);

        //  In the real world, the business logic of generating an email record from the account will be defined here.
        //
        // Returning null indicates that the item should not continue to be processed. 
        // This effectively enables processItem to filter out unwanted input items.
        return (((MyInputRecord) t).getId() % 2 == 0) ? null : new MyOutputRecord(((MyInputRecord) t).getId() * 2);
    }
}
