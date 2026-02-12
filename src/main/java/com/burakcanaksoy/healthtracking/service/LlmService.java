package com.burakcanaksoy.healthtracking.service;

import com.burakcanaksoy.healthtracking.config.LlmConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class LlmService {

    private final RestTemplate restTemplate;
    private final LlmConfig llmConfig;

    public String getRecommendation(String prompt) {
        String url = llmConfig.getApiUrl();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + llmConfig.getApiKey());
        // OpenRouter recommended headers
        headers.set("HTTP-Referer", "http://localhost:8080"); // Optional: Your site URL
        headers.set("X-Title", "HealthTrackingApp"); // Optional: Your app name

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", llmConfig.getModel());

        Map<String, String> message = new HashMap<>();
        message.put("role", "user");
        message.put("content", prompt);

        requestBody.put("messages", List.of(message));
        requestBody.put("max_tokens", 500);
        requestBody.put("temperature", 0.7);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        try {
            Map response = restTemplate.postForObject(url, entity, Map.class);

            if (response != null && response.containsKey("choices")) {
                List choices = (List) response.get("choices");
                if (!choices.isEmpty()) {
                    Map choice = (Map) choices.get(0);
                    Map messageMap = (Map) choice.get("message");
                    return (String) messageMap.get("content");
                }
            }
        } catch (org.springframework.web.client.HttpClientErrorException e) {
            System.err.println("LLM API Error: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
            if (e.getStatusCode().value() == 401) {
                return "Authentication failed. Please check your LLM API Key.";
            } else if (e.getStatusCode().value() == 402) {
                return "Insufficient credits. Please check your OpenRouter account balance.";
            }
            return "Error generating recommendation: " + e.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            return "Unable to generate recommendation at this time. Please try again later.";
        }
        return "No recommendation generated.";
    }
}
