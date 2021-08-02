package br.com.fernando.chapter15_batchProcessing.part01_chunkOrientedProcessing;

import java.io.Serializable;
import java.util.StringTokenizer;

import javax.batch.api.chunk.AbstractItemReader;
import javax.inject.Named;

// The ItemReader interface is used to read a stream of items, one item at a time.
// An ItemReader provides an indicator when it has exhausted the items it can supply.
//
// The item reader is an implementation of the ItemReader interface or extends the AbstractItemReader class:
@Named // The @Named annotation ensures that this bean can be referenced in Job XML.
public class MyItemReader extends AbstractItemReader {

    private StringTokenizer tokens;

    // The open method prepares the reader to read items. List<String> is initialized in this method.
    //
    // The input parameter 'checkpoint' represents the last checkpoint for this reader in a given job instance. 
    // The checkpoint data is defined by this reader and is provided by the checkpointInfo method. 
    // The checkpoint data provides the reader whatever information it needs to resume reading items upon restart. 
    // A checkpoint value of null is passed upon initial start.
    @Override
    public void open(Serializable checkpoint) throws Exception {
        tokens = new StringTokenizer("1,2,3,4,5,6,7,8,9,10", ",");
    }

    // The readItem method returns the next item for chunk processing. 
    // For all strings read from the List, a new MyInputRecord instance is created and returned from the readItem method. 
    // Returning a null indicates the end of stream.
    @Override
    public MyInputRecord readItem() {
        if (tokens.hasMoreTokens()) {
            return new MyInputRecord(Integer.valueOf(tokens.nextToken()));
        }
        return null;
    }
}
