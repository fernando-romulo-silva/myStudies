package br.com.fernando.myExamCloud.useBatchApiJavaEE7Applications;

import java.util.List;

import javax.batch.api.chunk.AbstractItemReader;
import javax.batch.api.chunk.AbstractItemWriter;
import javax.batch.api.chunk.ItemProcessor;
import javax.batch.runtime.context.JobContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class Question01 {

    // The PayrollJob batch job involves reading input data for payroll processing from a XL sheet.
    // Each line in the file contains an employee ID and the base salary (per month) for one employee.
    // The batch job then calculates the tax to be withheld, the bonus, and the net salary.
    // The job finally needs to write out the processed payroll records into a database table.

    @Named
    public class SimpleItemReader extends AbstractItemReader {

	@Inject
	private JobContext jobContext;
	// ...

	@Override
	public Object readItem() throws Exception {
	    // TODO Auto-generated method stub
	    return null;
	}
    }

    public class SimpleItemProcessor implements ItemProcessor {

	@Inject
	private JobContext jobContext;

	public Object processItem(Object obj) throws Exception {
	    // ....

	    return null;
	}
    }

    @Named
    public class SimpleItemWriter extends AbstractItemWriter {

	@PersistenceContext
	EntityManager em;

	public void writeItems(List list) throws Exception {
	    for (Object obj : list) {
		System.out.println("PayrollRecord: " + obj);
		em.persist(obj);
	    }
	}
	// ....
    }

    // Which JSL correctly defines chunk-style step for these batch jobs?
    //
    // Choice A
    /**
     * <pre>
     * <job id="PayrollJob" xmlns=http://xmlns.jcp.org/xml/ns/javaee version="1.0">
     *     <step id="process">
     *         <chunk item-count="2">
     *             <reader ref="simpleItemReader"/>
     *             <processor ref="simpleItemProcessor"/>
     *             <writer ref="simpleItemWriter"/>
     *         </chunk>
     *     </step>
     * </job>
     * </pre>
     */
    //
    // Choice B
    /**
     * <pre>
     * <job id="PayrollJob" xmlns=http://xmlns.jcp.org/xml/ns/javaee version="1.0">
     *     <step id="process">
     *         <chunk item-count="2">
     *             <reader class="SimpleItemReader"/>
     *             <processor class="SimpleItemProcessor"/>
     *             <writer class="SimpleItemWriter"/>
     *         </chunk>
     *     </step>
     * </job>
     * </pre>
     */
    //
    // Choice C
    /**
     * <pre>
     *  <job id="PayrollJob" xmlns=http://xmlns.jcp.org/xml/ns/javaee version="1.0">
     *      <step id="process">
     *          <chunk item-count="2">
     *              <reader name="simpleItemReader"/>
     *              <processor name="simpleItemProcessor"/>
     *              <writer name="simpleItemWriter"/>
     *          </chunk>
     *      </step>
     *  </job>
     * </pre>
     */
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
    //
    //
    //
    //
    //
    //
    //
    // Choice A is correct.
    //
    // It is a chunk-style step and has (as required for a chunk-style step), an ItemReader, an ItemProcessor, and an ItemWriter.
    // The implementations for ItemReader, ItemProcessor, and ItemWriter for this step are
    // specified using the ref attribute in the <reader>, <processor>, and <writer> elements.

}
