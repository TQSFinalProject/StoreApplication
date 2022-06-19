package com.tqs.chateauduvin.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;

import com.tqs.chateauduvin.model.Order;

public class HttpRequests {
    public void sendNewOrder(String URL, Order order) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(URL+"/api/orders"))
            .header("Content-Type", "application/json")
            .POST(BodyPublishers.ofByteArray(JsonUtils.toJson(order)))
            .build();
        System.out.println("AAAAAAAAAAAA");
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("BBBBBBBBBBBB");
        System.out.println(response.statusCode());
        if(response.statusCode() != 200) throw new Exception();

    }
}
