package br.com.fernando;


import javax.servlet.annotation.WebServlet;

//
// A servlet is defined using the @WebServlet annotation on a POJO, and must extend the
// javax.servlet.http.HttpServlet class.
@WebServlet("/account01")
public class Servlet01 extends javax.servlet.http.HttpServlet {

    private static final long serialVersionUID = 1L;

}
