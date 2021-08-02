package murach.tags;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;

import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.TagSupport;

public class CurrentTimeTag extends TagSupport {

    @Override
    public int doStartTag() throws JspException {

	Date currentTime = new Date();
	DateFormat shortDate = DateFormat.getTimeInstance(DateFormat.LONG);
	String currentTimeFormatted = shortDate.format(currentTime);

	try {
	    JspWriter out = pageContext.getOut();
	    out.print(currentTimeFormatted);
	} catch (IOException ioe) {
	    System.out.println(ioe);
	}
	return SKIP_BODY;
    }
}