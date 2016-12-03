package com.socialnetwork.jsp_tags;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

import javax.servlet.ServletContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.File;
import java.io.IOException;

/**
 * Created by Vasiliy Bobkov on 29.11.2016.
 */
@Log4j
@Setter
@Getter
public class PhotoLink extends TagSupport {
    private String id;
    private String type;
    private String alt;
    private final String FULL = "ava";
    private final String MIN = "min_ava";
    private final String MICRO = "micro_ava";

    @Override
    public int doStartTag() throws JspException {
        ServletContext servletContext = pageContext.getServletContext();
        String uri = "photo/" + id + "/" + type + ".jpg";
        String filePath = servletContext.getRealPath("/") + uri;
        JspWriter out = pageContext.getOut();
        try {
            if (new File(filePath).exists())
                out.write("/" + uri);
            else {
                out.write(alt);
            }
        } catch (IOException e) {
            log.error("PhotoLink tag error", e);
        }
        return SKIP_BODY;
    }
}