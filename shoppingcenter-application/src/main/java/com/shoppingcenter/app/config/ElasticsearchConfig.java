package com.shoppingcenter.app.config;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.SSLContext;

import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

//@Configuration
//@EnableElasticsearchRepositories(basePackages = "com.shoppingcenter.search")
public class ElasticsearchConfig extends ElasticsearchConfiguration {

    @Value("${app.elasticsearch.username}")
    private String username;

    @Value("${app.elasticsearch.password}")
    private String password;

    @Override
    public ClientConfiguration clientConfiguration() {
        try {
            SSLContextBuilder sslBuilder = SSLContexts.custom()
                    .loadTrustMaterial(null, (x509Certificates, s) -> true);

            final SSLContext sslContext = sslBuilder.build();

            ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                    .connectedTo("localhost:9200")
                    .usingSsl(sslContext)
                    .withBasicAuth(username, password)
                    .build();

            return clientConfiguration;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (Exception e) {
			System.err.println(e.getMessage());
		}

        return null;
    }

}
