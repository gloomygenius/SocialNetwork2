package com.socialnetwork.servlets;

import com.socialnetwork.entities.User;
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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import static com.socialnetwork.filters.SecurityFilter.CURRENT_USER;
import static com.socialnetwork.servlets.ErrorHandler.ERROR_MSG;
import static com.socialnetwork.servlets.ErrorHandler.ErrorCode.FILE_UPLOAD_ERROR;
import static com.socialnetwork.servlets.ErrorHandler.ErrorCode.SELECT_FILE_ERROR;

@Log4j
public class UploadServlet extends HttpServlet {

    private boolean isMultipart;
    private String filePath;
    private int maxFileSize = 5000 * 1024;
    private int maxMemSize = 100 * 1024;
    private File file;

    public void init() {
        // Get the file location where it would be stored.
        filePath =
                getServletContext().getRealPath("/") + "photo/";
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response)
            throws ServletException, java.io.IOException {

        // Check that we have a file upload request
        isMultipart = ServletFileUpload.isMultipartContent(request);
        log.info("isMultipart:" + isMultipart);
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
            // maximum file size to be uploaded.
            upload.setSizeMax(maxFileSize);

            try {
                // Parse the request to get file items.
                List fileItems = upload.parseRequest(request);

                // Process the uploaded file items
                Iterator i = fileItems.iterator();
                log.info("get iterator");
                while (i.hasNext()) {
                    log.info("iterator has next");
                    FileItem fi = (FileItem) i.next();
                    log.info("is form field:" + fi.isFormField());
                    if (!fi.isFormField()) {
                        // Get the uploaded file parameters
                        String fieldName = fi.getFieldName();
                        String fileName = fi.getName();
                        String contentType = fi.getContentType();
                        boolean isInMemory = fi.isInMemory();
                        long sizeInBytes = fi.getSize();
                        // Write the file
                        HttpSession session = request.getSession();
                        User currentUser = (User) session.getAttribute(CURRENT_USER);
                        file = new File(filePath + currentUser.getId() + "/");
                        if (!file.exists()) {
                            //Создаем его.
                            file.mkdir();
                        }
                        file = new File(filePath + currentUser.getId() + "/ava.jpg");
                        log.debug("write to disk:" + file.getAbsolutePath());
                        fi.write(file);
                        createResizedImage(file);
                    }
                }
            } catch (Exception e) {
                log.info("Upload file exception", e);
                request.setAttribute(ERROR_MSG, FILE_UPLOAD_ERROR.getPropertyName());
            }
        }
        request.getRequestDispatcher("/edit_profile").forward(request, response);
    }

    private Validator.ValidCode createResizedImage(File file) throws IOException {
        Validator.ValidCode code= Validator.ValidCode.INVALID_PIXEL_SIZE;
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
                    log.info("last index:"+path.lastIndexOf("\\"));
                    log.info(path.substring(0, path.lastIndexOf("\\")) + "\\min_" + path.substring(path.lastIndexOf("\\")));
                    file = new File(path.substring(0, path.lastIndexOf("\\")) + "\\min_" + path.substring(path.lastIndexOf("\\")+1));
                    ImageIO.write(scaledImage, "png", file);
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