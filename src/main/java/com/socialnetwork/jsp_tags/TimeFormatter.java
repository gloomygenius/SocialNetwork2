package com.socialnetwork.jsp_tags;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Log4j
@Setter
@Getter
public class TimeFormatter extends TagSupport {
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
}