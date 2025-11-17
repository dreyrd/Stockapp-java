package br.com.ifsp.StockApp.service;

import br.com.ifsp.StockApp.model.predict.StockPrediction;
import br.com.ifsp.StockApp.model.predict.StockPredictionDataCreation;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class StockPredictionService {

    private HttpClient client;

    public StockPredictionService(){
        client = HttpClient.newHttpClient();
    }

    public StockPrediction getLrPrediction(String predictionRequest) throws IOException, InterruptedException {
        String url = "http://127.0.0.1:8000/api/v1/lr/prediction";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .version(HttpClient.Version.HTTP_1_1)
                .POST(HttpRequest.BodyPublishers.ofString(predictionRequest, StandardCharsets.UTF_8))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String body = response.body();

        Gson gson = new Gson();

        StockPredictionDataCreation stockPredictionDataCreation = gson.fromJson(body, StockPredictionDataCreation.class);
        StockPrediction stockPrediction = new StockPrediction(stockPredictionDataCreation);

        return stockPrediction;
    }

}
