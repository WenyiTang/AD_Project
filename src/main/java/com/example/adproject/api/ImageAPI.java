package com.example.adproject.api;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/image")
public class ImageAPI {

    @GetMapping( value = "/get",produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getImageWithMediaType() throws IOException {
        InputStream in = getClass()
                        .getResourceAsStream("/static/blog/images/banmian.jpeg");
                        return IOUtils.toByteArray(in);
    }
    
}
