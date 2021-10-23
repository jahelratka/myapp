package com.testapp.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Iterator;

@Service
@RequiredArgsConstructor
public class MyService {

    private final static String bufferPath = "/UploadRepository";
    private final static String destPath = "/UploadStore";


    public String myServiceMethod() {
        return "Return from service.";
    }


}
