package br.com.ifsp.StockApp.service;

import br.com.ifsp.StockApp.model.predict.StockHistory;
import br.com.ifsp.StockApp.model.predict.StockPredictionDataApi;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.nio.charset.StandardCharsets;

public class StockPredictionService {

    private HttpClient client;

    public StockPredictionService(){
        client = HttpClient.newHttpClient();
    }

    public void getLrPrediction(){
        String url = "http://127.0.0.1:8000/api/v1/lr/prediction";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .version(HttpClient.Version.HTTP_1_1)
                .POST(HttpRequest.BodyPublishers(jason, StandardCharsets.UTF_8))
                .build();


    }

}
