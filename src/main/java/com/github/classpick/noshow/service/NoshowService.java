package com.github.classpick.noshow.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@RequiredArgsConstructor
@Service
public class NoshowService {

    public void ocrImage() {
        Tesseract tesseract = new Tesseract();
        tesseract.setDatapath("/opt/homebrew/Cellar/tesseract/5.5.0_1/share/tessdata");
        tesseract.setLanguage("eng");
        tesseract.setVariable("tessedit_char_whitelist", "0123456789");
        tesseract.setVariable("tessedit_char_blacklist", "abcdefghijklmnopqrstuvwxyz");

        try {
            File imageFile = new File("src/main/java/com/github/classpick/noshow/test.png");
            BufferedImage bufferedImage = ImageIO.read(imageFile);

            String result = tesseract.doOCR(bufferedImage);
            System.out.println("ocr result: " + result);

        } catch (IOException | TesseractException e) {
            e.printStackTrace();
        }
    }

    @PostConstruct
    public void main() {
        new NoshowService().ocrImage();
    }
}
