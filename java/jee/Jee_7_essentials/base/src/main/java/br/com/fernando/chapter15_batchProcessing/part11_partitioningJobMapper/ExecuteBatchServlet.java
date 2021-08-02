package br.com.fernando.chapter15_batchProcessing.part11_partitioningJobMapper;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Servlet")
public class ExecuteBatchServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @EJB
    private ExecuteBatchEJB executeBatchEJB;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            executeBatchEJB.doIt();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
