package com.tqs.chateauduvin.service;

import java.io.IOException;
import java.net.ConnectException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tqs.chateauduvin.dto.LogInRequestDTO;
import com.tqs.chateauduvin.dto.OrderIntermediaryDTO;
import com.tqs.chateauduvin.dto.StoreDTO;
import com.tqs.chateauduvin.model.Order;

public class HttpRequests {
    private static final String contType = "Content-Type";
    private static final String appJson = "application/json";
    private static final String estDelTime = "estimatedDeliveryTime";
    private static final String delTime = "deliveryTime";

    public Order sendNewOrder(String url, Order order, String username, String password) throws Exception {
        String token = getToken(url, username, password);
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url+"/api/store/order"))
            .header(contType, appJson)
            .header("Authorization", "Bearer "+token)
            .POST(BodyPublishers.ofByteArray(JsonUtils.toJson(order)))
            .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        if(response.statusCode() != 200) throw new ConnectException();
        else {
            try {
                JSONObject obj = (JSONObject) new JSONParser().parse(response.body());
                return getOrderFromJSONObject(obj);
            } catch (JsonGenerationException e) {
                throw e;
            } catch (JsonMappingException e) {
                throw e;
            } catch (IOException e) {
                throw e;
            } 
        }
    }

    public Map<String,Object> getOrderById(String url, Long mgmtOrderId, String username, String password) throws Exception {
        String token = getToken(url, username, password);
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url+"/api/store/order/"+mgmtOrderId))
            .header(contType, appJson)
            .header("Authorization", "Bearer "+token)
            .GET()
            .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        if(response.statusCode() != 200) throw new ConnectException();
        else {
            try {
                JSONObject responseJSON = (JSONObject) new JSONParser().parse(response.body());
                JSONObject obj = (JSONObject) responseJSON.get("order");
                Order orderResponse = getOrderFromJSONObject(obj);
                JSONObject rider = (JSONObject) responseJSON.get("rider");
                Map<String,Object> ret = new HashMap<>();
                ret.put("order", orderResponse);
                ret.put("rider", rider);
                return ret;
            } catch (JsonGenerationException e) {
                throw e;
            } catch (JsonMappingException e) {
                throw e;
            } catch (IOException e) {
                throw e;
            } 
        }
    }

    public String getToken(String url, String username, String password) throws Exception {
        StoreDTO chateauDuVin = new StoreDTO("Chateau Du Vin", 1.99, "Universidade de Aveiro, 3810-193 Aveiro", 40.630692, -8.654120, password, username);
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url+"/registration/store"))
            .header(contType, appJson)
            .POST(BodyPublishers.ofByteArray(JsonUtils.toJson(chateauDuVin)))
            .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        if(response.statusCode() == 200 || response.statusCode() == 409) {
            LogInRequestDTO req = new LogInRequestDTO(username, password);
            HttpRequest request2 = HttpRequest.newBuilder()
            .uri(URI.create(url+"/authentication"))
            .header(contType, appJson)
            .POST(BodyPublishers.ofByteArray(JsonUtils.toJson(req)))
            .build();
            HttpResponse<String> response2 = HttpClient.newHttpClient().send(request2, HttpResponse.BodyHandlers.ofString());
            JSONObject obj = (JSONObject) new JSONParser().parse(response2.body());
            String token = (String) obj.get("token");
            return token;
        }
        else throw new ConnectException();
    }

    public Order getOrderFromJSONObject(JSONObject obj) throws JsonMappingException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        LocalDateTime estimatedDeliveryTime = obj.get(estDelTime) == null ? null : LocalDateTime.parse((String)obj.remove(estDelTime));
        LocalDateTime deliveryTime = obj.get(delTime) == null ? null : LocalDateTime.parse((String)obj.remove(delTime));
        LocalDateTime submitedTime = LocalDateTime.parse((String)obj.remove("submitedTime"));
        if(deliveryTime == null) obj.remove(delTime);
        if(estimatedDeliveryTime == null) obj.remove(estDelTime);
        OrderIntermediaryDTO tempOrder = mapper.readValue(obj.toJSONString(), OrderIntermediaryDTO.class);
        Order orderResponse = tempOrder.toOrderEntity();
        orderResponse.setEstimatedDeliveryTime(estimatedDeliveryTime);
        orderResponse.setDeliveryTime(deliveryTime);
        orderResponse.setSubmitedTime(submitedTime);
        return orderResponse;
    }
}
