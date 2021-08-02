package resourcePackagingWar01;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/servletPrincipal")
public class ServletPrincipal extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try (final PrintWriter out = response.getWriter()) {

            out.println("<html><head>");
            out.println("<title>Servlet Test!</title>");
            out.println("</head><body>");
            out.println("<h1>Servlet Test!</h1>");
            out.println("</body></html>");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
