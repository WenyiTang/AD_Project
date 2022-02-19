package com.example.adproject.api;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("api/image")
public class ImageAPI {


    @GetMapping( value = "/get",produces = MediaType.IMAGE_JPEG_VALUE, params = {"imagePath"})
    public @ResponseBody byte[] getImageWithMediaType(@RequestParam String imagePath) throws IOException {
        File file = new File(imagePath);
		byte[] fileContent = Files.readAllBytes(file.toPath());
        return fileContent;

        
     
    }
    
}
