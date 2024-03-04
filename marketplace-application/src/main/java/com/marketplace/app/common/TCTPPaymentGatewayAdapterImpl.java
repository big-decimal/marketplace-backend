package com.marketplace.app.common;

import java.net.URI;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marketplace.domain.ApplicationException;
import com.marketplace.domain.payment.PaymentResult;
import com.marketplace.domain.payment.PaymentTokenRequest;
import com.marketplace.domain.payment.PaymentTokenResponse;
import com.marketplace.domain.payment.TCTPPaymentGatewayAdapter;

import jakarta.annotation.PostConstruct;

@Component
public class TCTPPaymentGatewayAdapterImpl implements TCTPPaymentGatewayAdapter {
	
	private static final Logger log = LoggerFactory.getLogger(TCTPPaymentGatewayAdapter.class);
	
	@Value("${app.payment.merchant-sha-key}") 
	private String merchantShaKey;
	
	@Value("${app.payment.merchant-id}")
	private String merchantId;
	
	@Value("${app.payment.token-request-url}")
	private String tokenRequestUrl;
	
	@Value("${app.misc.website-url}")
	private String consumerUrl;
	
	@Autowired
	private RestClient restClient;
	
	@Autowired
	private ObjectMapper objectMapper;
	
//	private SecretKey key;
	
	private Algorithm algorithm;
	
	@PostConstruct
	public void init() {
//		this.key = Keys.hmacShaKeyFor(merchantShaKey.getBytes());
		this.algorithm = Algorithm.HMAC256(merchantShaKey);
	}

//	@Retryable
	@Override
	public PaymentTokenResponse requestPaymentToken(PaymentTokenRequest input) {
		try {
			var claims = new HashMap<String, Object>();
			claims.put("merchantID", merchantId);
			claims.put("invoiceNo", input.getInvoiceNo());
			claims.put("description", input.getDescription());
			claims.put("amount", input.getAmount().doubleValue());
			claims.put("currencyCode", "MMK"); 
			
			var frontendReturnUrl = String.format("%s/profile/shops/%d/payment", consumerUrl, input.getShopId());
			claims.put("frontendReturnUrl", frontendReturnUrl);
			//claims.put("paymentChannel", new String[] {"MPU", "WEBPAY", "EWALLET", "QRC", "IMBANK"});

//			var encoded = Jwts.builder()
//					.claims()
//					.add(claims)
//					.and()
//					.signWith(key, Jwts.SIG.HS256)
//					.compact();
			
			var encoded = JWT.create()
					.withPayload(claims)
					.sign(algorithm);
			
			var body = new HashMap<String, Object>();
			body.put("payload", encoded);
			
			var response = restClient.post()
					.uri(new URI(tokenRequestUrl))
					.contentType(MediaType.APPLICATION_JSON)
					.body(objectMapper.writeValueAsString(body))
					.retrieve()
					.toEntity(String.class);
			
			if (!response.getStatusCode().is2xxSuccessful()) {
				throw new ApplicationException("Payment token request failed");
			}
			
			var json = objectMapper.readTree(response.getBody());
			var payload = json.get("payload").textValue();
			
//			var decoded = Jwts.parser()
//	            .verifyWith(key)
//	            .build()
//	            .parseSignedClaims(payload)
//	            .getPayload();
			
			var verifier = JWT.require(algorithm)
					.build();
			verifier.verify(payload);
			
			var jwt = JWT.decode(payload);
			
			var decoded = jwt.getClaims();
			
			var result = new PaymentTokenResponse();
			result.setWebPaymentUrl(decoded.get("webPaymentUrl").asString());
			result.setPaymentToken(decoded.get("paymentToken").asString());
			result.setRespCode(decoded.get("respCode").asString());
			result.setRespDesc(decoded.get("respDesc").asString());
			
			return result;
		} catch (Exception e) {
			log.error("Payment token request error: {}", e.getMessage());
			throw new ApplicationException("Payment token request failed");
		}
	}

	@Override
	public PaymentResult decodeResultPayload(String payload) {
		try {
//			var decoded = Jwts.parser()
//					.verifyWith(key)
//		            .build()
//		            .parseSignedClaims(payload)
//		            .getPayload();
			
			var verifier = JWT.require(algorithm)
					.build();
			verifier.verify(payload);
			
			var jwt = JWT.decode(payload);
			
			var decoded = jwt.getClaims();
			
			var result = new PaymentResult();
			result.setMerchantId(decoded.get("merchantID").asString());
			result.setInvoiceNo(decoded.get("invoiceNo").asString());
			result.setCardNo(decoded.get("cardNo").asString());
			result.setAmount(decoded.get("amount").asDouble());
			result.setCurrencyCode(decoded.get("currencyCode").asString());
			result.setTranRef(decoded.get("tranRef").asString());
			result.setReferenceNo(decoded.get("referenceNo").asString());
			result.setAgentCode(decoded.get("agentCode").asString());
			result.setChannelCode(decoded.get("channelCode").asString());
			result.setApprovalCode(decoded.get("approvalCode").asString());
			result.setEci(decoded.get("eci").asString());
			result.setTransactionDateTime(decoded.get("transactionDateTime").asString());
			result.setRespCode(decoded.get("respCode").asString());
			result.setRespDesc(decoded.get("respDesc").asString());
			return result;
		} catch (Exception e) {
			log.error("Failed to decode payment result: {}", e.getMessage());
		}
		return null;
	}

}
