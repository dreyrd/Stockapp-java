package br.com.ifsp.StockApp.service;

import br.com.ifsp.StockApp.model.predict.PredictionRequest;
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
    private Gson gson;

    public StockPredictionService(){
        client = HttpClient.newHttpClient();
        gson = new Gson();
    }

    public StockPrediction getLrPrediction(PredictionRequest predictionRequest) throws IOException, InterruptedException {
        String url = "http://127.0.0.1:8000/api/v1/lr/prediction";

        String bodyPredictionRequest = gson.toJson(predictionRequest);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .version(HttpClient.Version.HTTP_1_1)
                .POST(HttpRequest.BodyPublishers.ofString(bodyPredictionRequest, StandardCharsets.UTF_8))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String body = response.body();

        StockPredictionDataCreation stockPredictionDataCreation = gson.fromJson(body, StockPredictionDataCreation.class);
        StockPrediction stockPrediction = new StockPrediction(stockPredictionDataCreation);

        return stockPrediction;
    }

}
