package com.example.demoapi.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.restclient.test.autoconfigure.AutoConfigureRestClient;
import org.springframework.boot.restclient.test.autoconfigure.RestClientTest;
import org.springframework.boot.test.context.SpringBootTest;

@AutoConfigureRestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class JWTSecurityTest {

    @Autowired
    private RestClientTest restClient;

    // TODO comprendre comment utiliser le MockMvc
    @Test
    public void unauthenticated_get_commande() throws Exception {
        // faire fonctionner
    }
}
