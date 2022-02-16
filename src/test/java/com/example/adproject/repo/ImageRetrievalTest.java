package com.example.adproject.repo;

import com.example.adproject.AdProjectApplication;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = AdProjectApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ImageRetrievalTest {

    @Test
    @Order(1)
    public void listFilesFromUpload() {
        File[] files = new File("./upload").listFiles();

        for (File file : files) {
            System.out.println(file.getClass());
            System.out.println(file.getName());
            System.out.println();
        }
    }

    @Test
    @Order(2)
    public void getSingleFileFromUpload() {
        File[] files = new File("./upload").listFiles();
        for (File file : files) {
            try {
                BufferedImage img = null;
                img = ImageIO.read(file);
                System.out.println(file.getName());
                System.out.println(file.getAbsolutePath());
                System.out.println(img.getWidth());
                System.out.println(img.getHeight());
                System.out.println();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
