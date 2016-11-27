package com.socialnetwork.jsp_tags;

import org.apache.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeFormatter extends TagSupport {
    private static final Logger log = Logger.getLogger(TimeFormatter.class);
    private LocalDateTime time;

    @Override
    public int doStartTag() throws JspException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm dd-MM-yyyy");
        JspWriter out = pageContext.getOut();
        try {
            out.write(formatter.format(time));
        } catch (IOException e) {
            log.error("Error in tag class TimeFormatter");
        }
        return SKIP_BODY;
    }

    public LocalDateTime getTime() {
        return this.time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}