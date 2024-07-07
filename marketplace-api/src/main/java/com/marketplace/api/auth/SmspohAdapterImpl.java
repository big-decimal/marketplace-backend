package com.marketplace.api.auth;

import java.io.IOException;
import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marketplace.domain.ApplicationException;
import com.marketplace.domain.auth.SmspohAdapter;
import com.marketplace.domain.auth.SmspohResult;

@Component
public class SmspohAdapterImpl implements SmspohAdapter {

	private static final Logger log = LoggerFactory.getLogger(SmspohAdapter.class);

	@Value("${app.smspoh.api-key}")
	private String apiKey;

	@Autowired
	private RestClient restClient;

	@Autowired
	private ObjectMapper objectMapper;

	@Override
	public SmspohResult requestOTP(String phone) {
		try {
			var url = String.format("https://verify.smspoh.com/api/v2/request?access-token=%s&to=%s&channel=sms&brand_name=ShoppingMM", apiKey, phone);
			var response = restClient.get()
					.uri(new URI(url))
					.retrieve()
					.toEntity(String.class);
			
			if (!response.getStatusCode().is2xxSuccessful()) {
				throw new ApplicationException("OTP request failed");
			}

			var json = objectMapper.readTree(response.getBody());
			var result = new SmspohResult();
			result.setStatus(json.get("status").booleanValue());
			result.setRequestId(json.get("request_id").longValue());
			return result;
		} catch (Exception e) {
			log.error("OTP request failed: {}", e.getMessage());
			throw new ApplicationException("OTP request failed");
		}

	}

	@Override
	public SmspohResult verifyOTP(long requestId, String code) {
		try {
			var url = String.format("https://verify.smspoh.com/api/v1/verify?access-token=%s&request_id=%d&code=%s", apiKey, requestId, code);
			var response = restClient.get()
					.uri(new URI(url))
					.retrieve()
					.onStatus(new DefaultResponseErrorHandler() {
						@Override
						protected void handleError(ClientHttpResponse response, HttpStatusCode statusCode)
								throws IOException {
							if (response.getStatusCode().is4xxClientError()) {
								var json = objectMapper.readTree(response.getBody());
								throw new ApplicationException(json.get("message").textValue());
							}
							super.handleError(response, statusCode);
						}
					})
					.toEntity(String.class);
			
			if (!response.getStatusCode().is2xxSuccessful()) {
				throw new ApplicationException("OTP verify failed");
			}

			var json = objectMapper.readTree(response.getBody());
			var result = new SmspohResult();
			result.setStatus(json.get("status").booleanValue());
			result.setRequestId(json.get("request_id").longValue());
			return result;
		} catch (Exception e) {
//			log.error("OTP verify failed: {}", e.getMessage());
			throw new ApplicationException(e.getMessage());
		}
	}

}
