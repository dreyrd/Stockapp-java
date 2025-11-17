package br.com.ifsp.StockApp.controller;

import br.com.ifsp.StockApp.model.predict.StockPrediction;
import br.com.ifsp.StockApp.model.predict.StockPredictionDataCreation;
import br.com.ifsp.StockApp.model.predict.StockPredictionDataResponse;
import br.com.ifsp.StockApp.model.stock.Stock;
import br.com.ifsp.StockApp.model.stock.StockDataCreation;
import br.com.ifsp.StockApp.model.stock.StockDataResponse;
import br.com.ifsp.StockApp.model.stock.StockRepository;
import br.com.ifsp.StockApp.service.StockPredictionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;


@RestController
@RequestMapping("/stocks")
public class StockController {
    @Autowired
    StockRepository repository;

    @PostMapping
    public ResponseEntity<StockDataResponse> postStock(@RequestBody StockDataCreation stockDataCreation, UriComponentsBuilder uriComponentsBuilder){
        var newStock = new Stock(stockDataCreation);
        repository.save(newStock);
        var uri = uriComponentsBuilder.path("/stocks/{stockId}").buildAndExpand(newStock.getStockId()).toUri();
        return ResponseEntity.created(uri).body(new StockDataResponse(newStock));
    }

    @GetMapping
    public ResponseEntity<Page<StockDataResponse>> getStock(Pageable pageable){
        var page = repository.findAllByEnableTrue(pageable).map(StockDataResponse::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{stockId}")
    public ResponseEntity<StockDataResponse> getStockById(@PathVariable Integer stockId){
        var stock = repository.getReferenceById(stockId);
        return ResponseEntity.ok(new StockDataResponse(stock));
    }

    @GetMapping("/symbol/{stockSymbol}")
    public ResponseEntity<StockDataResponse> getStockBySymbol(@PathVariable String stockSymbol){
        var stock = repository.getReferenceByStockSymbol(stockSymbol);
        return ResponseEntity.ok(new StockDataResponse(stock));
    }

    @PostMapping("/symbol/{stockSymbol}/predict")
    public ResponseEntity<StockPredictionDataResponse> postStockPredict(@PathVariable String stockSymbol) throws IOException, InterruptedException {
        StockPredictionService stockPredictionService = new StockPredictionService();

        StockPrediction stockPrediction = stockPredictionService.getLrPrediction("""
                {
                  "ticker": "AAPL",
                  "history": [
                    {
                      "date": "2024-12-24",
                      "o": 3156.26,
                      "h": 3158.87,
                      "l": 3134.86,
                      "c": 3143.47,
                      "v": 9312404
                    },
                    {
                      "date": "2024-12-25",
                      "o": 3121.51,
                      "h": 3127.26,
                      "l": 3090.66,
                      "c": 3119.28,
                      "v": 9312404
                    },
                    {
                      "date": "2024-12-26",
                      "o": 3051.51,
                      "h": 3060.58,
                      "l": 3046.52,
                      "c": 3053.21,
                      "v": 9312404
                    },
                    {
                      "date": "2024-12-27",
                      "o": 3090.67,
                      "h": 3102.42,
                      "l": 3072.04,
                      "c": 3089.04,
                      "v": 9312404
                    },
                    {
                      "date": "2024-12-30",
                      "o": 3089.39,
                      "h": 3109.88,
                      "l": 3081.74,
                      "c": 3094.18,
                      "v": 9312404
                    }
                  ]
                }
                """);

        return ResponseEntity.ok(new StockPredictionDataResponse(stockPrediction));
    }
}
