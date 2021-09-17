package murach.http;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/headersSpreadsheet")
public class RequestHeadersSpreadsheetServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {

        // create workbook and sheet for spreadsheet
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("Request Headers");

        Enumeration headerNames = request.getHeaderNames();
        int i = 0;
        while (headerNames.hasMoreElements()) {
            String name = (String) headerNames.nextElement();
            String value = request.getHeader(name);

            // create the row and store data in its cells
            Row row = sheet.createRow(i);
            row.createCell(0).setCellValue(name);
            row.createCell(1).setCellValue(value);
            i++;
        }

        // set the response headers to return an attached .xls file
        response.setHeader("content-disposition",
                "attachment; filename=request_headers.xls");
        response.setHeader("cache-control", "no-cache");        

        // get the output stream and send the workbook to the browser
        OutputStream out = response.getOutputStream();
        workbook.write(out);
        out.close();
    }
}