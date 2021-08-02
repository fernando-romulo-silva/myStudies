package murach.tags;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;

import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.TagSupport;

public class CurrentDateTag extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
	Date currentDate = new Date();
	DateFormat shortDate = DateFormat.getDateInstance(DateFormat.LONG);
	String currentDateFormatted = shortDate.format(currentDate);

	try {
	    JspWriter out = pageContext.getOut();
	    out.print(currentDateFormatted);
	} catch (IOException ioe) {
	    System.out.println(ioe);
	}
	return SKIP_BODY;
    }
}