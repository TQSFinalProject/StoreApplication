package com.tqs.chateauduvin.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.tqs.chateauduvin.model.Order;

public class HttpRequests {
    public Order sendNewOrder(Order order) throws IOException, InterruptedException, ParseException {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("http://ti:8080/api/orders"))
            .header("Content-Type", "application/json")
            .POST(BodyPublishers.ofByteArray(JsonUtils.toJson(order)))
            .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.statusCode());
        System.out.println(response.uri());
        System.out.println(response.body());
        // JSONObject obj = (JSONObject) new JSONParser().parse(response.body());
        // System.out.println("H");
        // System.out.println(obj.toString());
        return null;
    }
}
