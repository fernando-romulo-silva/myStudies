package br.com.fernando.myExamCloud.useConcurrencyAPIJavaEE7Applications;

import java.io.IOException;
import java.util.concurrent.Future;

import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Question01 {

    // Given

    public class ReportTask implements Runnable {
	public final int NEW_REPORT = 1;
	public final int OLD_REPORT = 2;
	int reportType = NEW_REPORT;

	public ReportTask() {
	}

	public ReportTask(int reportType) {
	    this.reportType = reportType;
	}

	public void run() {
	    // Logic here
	}
    }

    public class ReportServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Resource(name = "concurrent/MyAppTasksExecutor")
	ManagedExecutorService mes;

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    // Code here
	}
    }

    // Which of the following inserted at //code here correctly submit ReportTask to MyAppTasksExecutor?
    //
    // Choice A
    // ReportTask reportTask = new ReportTask();
    // reportTask.start();
    //
    // Choice B
    // ReportTask reportTask = new ReportTask();
    // Future reportFuture = mes.run(reportTask);
    //
    // Choice C
    // ReportTask reportTask = new ReportTask();
    // Future reportFuture = mes.submit(reportTask);
    //
    // Choice D
    // ReportTask reportTask = new ReportTask();
    // Future reportFuture = mes.join(reportTask);
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
    // Explanation :
    // Choice C is correct.
    //
    // The submit method submit the task to the ManagedExecutorService.
    // To handle to the task, the Future is cached so that the client can query the results of the report.
    // The Future will contain the results once the task has completed.
    //
    @Resource(name = "concurrent/MyAppTasksExecutor")
    ManagedExecutorService mes;
    
    public void doSomething() {
	ReportTask reportTask = new ReportTask();        
	Future reportFuture = mes.submit(reportTask);  
	
    }

}
