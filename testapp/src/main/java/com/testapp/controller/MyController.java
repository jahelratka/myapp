package com.testapp.controller;

import com.testapp.model.MyModel;
import com.testapp.service.MyService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.ProgressListener;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@RestController
@RequestMapping("/my-test")
@RequiredArgsConstructor
public class MyController {

    private final MyService myService;

    @GetMapping
    public String returnFromService() {
        return myService.myServiceMethod();
    }

    @PostMapping("/parameter/{id}")
    public String postParameter(@PathVariable String id) {
        return String.format("ID that you post is: %s", id);
    }

    @PostMapping("/body")
    public String postParameter(@RequestBody MyModel model) {
        return String.format("First Name: %s, Last Name: %s", model.getFirstName(), model.getLastName());
    }

    @PostMapping("/upload")
    public String postParameter(@RequestParam("file") MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws IOException, InterruptedException {

        HttpSession session = request.getSession(true);
        ProgressListener progressListener = (ProgressListener) session.getAttribute("progressListener");
        long currentTime = System.currentTimeMillis(); //present time
        System.out.println("currentTime = "+ currentTime);
        if (!file.isEmpty()) {
            String uploadsDir = "/uploads/";
            String realPathtoUploads =  request.getServletContext().getRealPath(uploadsDir);
            if(! new File(realPathtoUploads).exists())
            {
                new File(realPathtoUploads).mkdir();
            }



            String orgName = file.getOriginalFilename();
            String filePath = realPathtoUploads + orgName;
            System.out.println("realPathtoUploads = {}"+ Paths.get(filePath).toString());
            Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);

        }
        //response.wait();
        System.out.println("awaitTime: "+String.valueOf(System.currentTimeMillis()));
        System.out.println("fileSize: "+String.valueOf(file.getSize()));
        return String.format("Header: %s, FileName: %s, TimeStamp: %s", request.getHeader("X-Upload-File"), file.getOriginalFilename(),String.valueOf(System.currentTimeMillis()));
    }

}
