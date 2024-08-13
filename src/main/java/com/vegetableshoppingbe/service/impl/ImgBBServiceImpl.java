package com.vegetableshoppingbe.service.impl;

import com.vegetableshoppingbe.service.ImgBBService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.Map;

@Service
public class ImgBBServiceImpl implements ImgBBService {

    @Value("${imgbb.api.key}")
    private String apiKey;

    @Value("${imgbb.api.url}")
    private String apiUrl;

    @Override
    public String uploadToImgBB(MultipartFile file) {
        String url = apiUrl + "?key=" + apiKey;

        RestTemplate restTemplate = new RestTemplate();

        // Sử dụng MultiValueMap để gửi tệp dưới dạng multipart/form-data
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("image", file.getResource());

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(url, requestEntity, Map.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                Map<String, Object> responseBody = response.getBody();

                if (responseBody != null && responseBody.containsKey("data")) {
                    Map<String, Object> data = (Map<String, Object>) responseBody.get("data");
                    return (String) data.get("url");
                }
            }
        } catch (Exception e) {
            System.err.println("Error uploading image to ImgBB: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }


}
