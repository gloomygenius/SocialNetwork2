package com.socialnetwork.servlets;

import com.socialnetwork.models.User;
import com.socialnetwork.services.Validator;
import lombok.extern.log4j.Log4j;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import static com.socialnetwork.servlets.ErrorHandler.ErrorCode.FILE_UPLOAD_ERROR;
import static com.socialnetwork.servlets.ErrorHandler.ErrorCode.SELECT_FILE_ERROR;

@Log4j
public class UploadServlet extends CommonHttpServlet {

    private String filePath;
    private int maxFileSize = 10000 * 1024;
    private int maxMemSize = 50 * 1024;

    public void init() {
        super.init();
        // Get the file location where it would be stored.
        filePath =
                getServletContext().getRealPath("/") + "photo/";
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response)
            throws ServletException, java.io.IOException {

        // Check that we have a file upload request
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        log.debug("isMultipart:" + isMultipart);
        if (!isMultipart) {
            request.setAttribute(ERROR_MSG, SELECT_FILE_ERROR.getPropertyName());
        } else {
            DiskFileItemFactory factory = new DiskFileItemFactory();
            // maximum size that will be stored in memory
            factory.setSizeThreshold(maxMemSize);
            // Location to save data that is larger than maxMemSize.
            ServletContext context = request.getServletContext();
            String pathToDir = context.getRealPath("/");
            factory.setRepository(new File(pathToDir + "tmp/"));

            // Create a new file upload handler
            ServletFileUpload upload = new ServletFileUpload(factory);
            upload.setSizeMax(maxFileSize);

            try {
                List fileItems = upload.parseRequest(request);
                Iterator i = fileItems.iterator();
                log.debug("get iterator");
                while (i.hasNext()) {
                    log.debug("iterator has next");
                    FileItem fi = (FileItem) i.next();
                    log.debug("is form field:" + fi.isFormField());
                    if (!fi.isFormField()) {
                        // Write the file
                        HttpSession session = request.getSession();
                        User currentUser = (User) session.getAttribute(CURRENT_USER);
                        File file = new File(filePath + currentUser.getId() + "/");
                        if (!file.exists()) {
                            file.mkdir();
                        }
                        file = new File(filePath + currentUser.getId() + "/ava.jpg");
                        log.debug("write to disk:" + file.getAbsolutePath());
                        fi.write(file);
                        createResizedImage(file);
                        request.setAttribute(SUCCESS_MSG, Validator.ValidCode.SUCCESS.getPropertyName());
                    }
                }
            } catch (Exception e) {
                log.debug("Upload file exception", e);
                request.setAttribute(ERROR_MSG, FILE_UPLOAD_ERROR.getPropertyName());
            }
        }
        request.getRequestDispatcher("/edit_profile").forward(request, response);
    }

    private Validator.ValidCode createResizedImage(File file) throws IOException {
        Validator.ValidCode code = Validator.ValidCode.INVALID_PIXEL_SIZE;
        try (ImageInputStream in = ImageIO.createImageInputStream(file)) {
            final Iterator<ImageReader> readers = ImageIO.getImageReaders(in);
            if (readers.hasNext()) {
                ImageReader reader = readers.next();
                reader.setInput(in);
                BufferedImage originaImage = reader.read(0);
                BufferedImage scaledImage = Scalr.resize(originaImage, 200);
                String path = file.getAbsolutePath();
                code = Validator.validateImage(reader);
                if (code == Validator.ValidCode.SUCCESS) {
                    log.debug("last index:" + path.lastIndexOf("\\"));
                    log.debug(path.substring(0, path.lastIndexOf("\\")) + "\\min_" + path.substring(path.lastIndexOf("\\")) + 1);
                    file = new File(path.substring(0, path.lastIndexOf("\\")) + "\\min_" + path.substring(path.lastIndexOf("\\") + 1));
                    ImageIO.write(scaledImage, "jpg", file);
                }
            }
        }
        return code;
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws ServletException, java.io.IOException {

        throw new ServletException("GET method used with " +
                getClass().getName() + ": POST method required.");
    }
}