package murach.tags;

import java.io.IOException;

import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.TagSupport;

public class IfEmptyMarkTag extends TagSupport {

    private String field;
    private String color = "green";

    public void setField(String field) {
	this.field = field;
    }

    public void setColor(String color) {
	this.color = color;
    }

    @Override
    public int doStartTag() throws JspException {
	try {
	    JspWriter out = pageContext.getOut();
	    if (field == null || field.length() == 0) {
		out.print("<font color=" + color + "> *</font>");
	    }
	} catch (IOException ioe) {
	    System.out.println(ioe);
	}
	return SKIP_BODY;
    }
}
