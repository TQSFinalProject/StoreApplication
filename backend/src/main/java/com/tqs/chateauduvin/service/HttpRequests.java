package com.tqs.chateauduvin.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.tqs.chateauduvin.dto.StoreDTO;
import com.tqs.chateauduvin.model.Order;

public class HttpRequests {
    public Long sendNewOrder(String url, Order order) throws Exception {
        System.out.println(order);
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url+"/api/orders"))
            .header("Content-Type", "application/json")
            .POST(BodyPublishers.ofByteArray(JsonUtils.toJson(order)))
            .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        if(response.statusCode() != 200) throw new Exception();
        else {
            JSONObject obj = (JSONObject) new JSONParser().parse(response.body());
            Long orderId = (Long) obj.get("id");
            return orderId;
        }
    }

    public Long registerCDV(String url) throws Exception {
        StoreDTO chateauDuVin = new StoreDTO("Chateau Du Vin", 1.99, "Universidade de Aveiro, 3810-193 Aveiro", 40.630692, -8.654120);
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url+"/api/stores"))
            .header("Content-Type", "application/json")
            .POST(BodyPublishers.ofByteArray(JsonUtils.toJson(chateauDuVin)))
            .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.statusCode());
        if(response.statusCode() == 200 || response.statusCode() == 409) {
            JSONObject obj = (JSONObject) new JSONParser().parse(response.body());
            Long storeId = (Long) obj.get("id");
            return storeId;
        }
        else throw new Exception();
    }
}
