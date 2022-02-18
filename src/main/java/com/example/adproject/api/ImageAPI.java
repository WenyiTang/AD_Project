package com.example.adproject.api;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("api/image")
public class ImageAPI {
//
//    @GetMapping( value = "/demo",produces = MediaType.IMAGE_JPEG_VALUE, params = {})
//    public @ResponseBody byte[] getImageWithMediaTypeDemo() throws IOException {
//        InputStream in = getClass()
//                        .getResourceAsStream("/static/blog/images/banmian.jpeg");
//                        return IOUtils.toByteArray(in);
//    }

    @GetMapping( value = "/get",produces = MediaType.IMAGE_JPEG_VALUE, params = {"imagePath"})
    public @ResponseBody byte[] getImageWithMediaType(@RequestParam String imagePath) throws IOException {
  
        InputStream in = getClass()
                        .getResourceAsStream(imagePath);
                        return IOUtils.toByteArray(in);
    }
    
}
