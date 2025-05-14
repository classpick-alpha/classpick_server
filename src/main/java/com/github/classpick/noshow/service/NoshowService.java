package com.github.classpick.noshow.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.github.classpick.file.upload.util.S3KeyFactory;
import com.github.classpick.global.property.AwsProperty;
import com.github.classpick.noshow.repository.NoshowEntity;
import com.github.classpick.noshow.repository.NoshowRepository;
import com.github.classpick.reservation.exception.ReservationException;
import com.github.classpick.reservation.exception.ReservationExceptionCode;
import com.github.classpick.reservation.repository.ReservationEntity;
import com.github.classpick.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Date;

@RequiredArgsConstructor
@Service
public class NoshowService {

    private final NoshowRepository noshowRepository;
    private final ReservationRepository reservationRepository;
    private final AwsProperty awsProperty;
    private final AmazonS3Client amazonS3Client;

    public boolean verifyOcr(Long reservationId) {

        ReservationEntity reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ReservationException(ReservationExceptionCode.RESERVATION_NOT_FOUND));

        String unitNumber = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ReservationException(ReservationExceptionCode.RESERVATION_NOT_FOUND))
                .getRoom()
                .getUnitNumber();

        String key = S3KeyFactory.reservatioOcrKey(reservationId);
        String presignedUrl = generatePresignedUrl(key);

        BufferedImage image;
        try {
            image = downloadImage(presignedUrl);
        } catch (IOException e) {
            throw new IllegalStateException("이미지 다운로드 실패", e);
        }

        boolean result = ocrImage(unitNumber, image);

        NoshowEntity noshow = NoshowEntity.builder()
                .reservation(reservation)
                .noshow(result)
                .ocrImage(key)
                .build();

        noshowRepository.save(noshow);

        return result;
    }

    private String generatePresignedUrl(String path) {

        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(awsProperty.getS3().getBucket(), path)
                .withMethod(HttpMethod.GET)
                .withExpiration(new Date(System.currentTimeMillis() + 1000 * 60));

        generatePresignedUrlRequest.addRequestParameter(Headers.S3_CANNED_ACL, CannedAccessControlList.PublicRead.toString());

        return amazonS3Client.generatePresignedUrl(generatePresignedUrlRequest).toString();
    }

    private BufferedImage downloadImage(String presignedUrl) throws IOException {
        URL url = URI.create(presignedUrl).toURL();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        try (InputStream inputStream = connection.getInputStream()) {
            return ImageIO.read(inputStream);
        }
    }

    private boolean ocrImage(String unitNumber, BufferedImage image) {
        Tesseract tesseract = new Tesseract();
        //TODO: 서버 환경에 따른 DataPath 경로 지정 필요
        tesseract.setDatapath("/opt/homebrew/Cellar/tesseract/5.5.0_1/share/tessdata");
        tesseract.setLanguage("eng");
        tesseract.setVariable("tessedit_char_whitelist", "0123456789");
        tesseract.setVariable("tessedit_char_blacklist", "abcdefghijklmnopqrstuvwxyz");

        try {
            String result = tesseract.doOCR(image).replaceAll("\\s", "");
            return result.contains(unitNumber);
        } catch (TesseractException e) {
            throw new RuntimeException("OCR 분석 실패", e);
        }
    }
}
