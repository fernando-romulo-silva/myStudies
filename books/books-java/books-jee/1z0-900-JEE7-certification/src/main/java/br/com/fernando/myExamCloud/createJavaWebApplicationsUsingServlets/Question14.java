package br.com.fernando.myExamCloud.createJavaWebApplicationsUsingServlets;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Question14 {

    // EPractize Labs Online Skill Evaluation Lab uses descriptive type questions to support image and text answer input.
    // Examinee can also draw and upload their images from local system.
    // To support this requirement a developer decided to use Java Servlet.
    // Which of the following statements about the client request and the servlet are true, considering request is of type HttpServletRequest? [ Choose two ]
    //
    // Choice A
    // The client request can be either "GET" or "POST"
    //
    // Choice B
    // The method request.getReader() should be used to retrieve the file data
    //
    // Choice C
    // The client request must be "POST"
    //
    // Choice D
    // The method request.getInputStream() should be used to retrieve the file data]
    //
    // Choice E
    // The method request.getParameterValues(String reqParameter) should be used to retrieve the file data
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
    // Choice C and D are correct answers.
    //
    // "GET" uses the URL to transfer the parameters and hence can be used only for textual data.
    // Also the amount of data that can be sent by "GET" is limited. On the other hand "POST" uses the body of the request to send the
    // data and there is no limit in the size of the data that can be transferred. So "POST" is ideal for file uploads.
    //
    //
    // The ServletRequest has the following two methods, which can be used to read the body of the request:
    //
    // * BufferedReader getReader(): Retrieves the body of the request as character data using a BufferedReader.
    //
    // * ServletInputStream getInputStream(): Retrieves the body of the request as binary data using a ServletInputStream.
    //
    //
    // For file uploads, the encoding type of the request should be specified as "mutipart/form-data."
    // The container will populate the parameter map only when the encoding type is "application/x-www-form-urlencoded"
    // (which is the default when encoding type is unspecified).
    //
    // A typical file upload HTML page with the encoding type looks like below:
    /**
     * <pre>
     * 
     *    <html>
     *      <form action="/uploadServlet " enctype=" mutipart/form-data" method=post>
     *        <input type=file>
     *        <input type=submit>
     *      </form>
     *    </html>
     * 
     * </pre>
     */

    @MultipartConfig
    @WebServlet("/uploadServlet")
    public static class UploadServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	    InputStream filecontent = request.getInputStream();
	    // ...
	}
    }
}