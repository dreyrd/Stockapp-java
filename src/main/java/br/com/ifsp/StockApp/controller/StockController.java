package br.com.ifsp.StockApp.controller;

import br.com.ifsp.StockApp.model.Stock;
import br.com.ifsp.StockApp.model.StockDataCreation;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/stocks")
public class StockController {
    List<Stock> stockList = new ArrayList<>();

    @PostMapping
    public Map<String, String> postStock(@RequestBody StockDataCreation stockDataCreation){
        Map<String, String> response = new HashMap<>();
        Stock newStock = new Stock(stockDataCreation);
        this.stockList.add(newStock);

        response.put("Id", newStock.getStockId().toString());
        response.put("Name", newStock.getStockName());
        response.put("Symbol", newStock.getStockSymbol());
        return response;
    }

    @GetMapping
    public Map<String, String> getStock(){
        Map<String, String> response = new HashMap<>();
        stockList.forEach(stock -> response.put(stock.getStockId().toString(), stock.getStockName()));
        return response;
    }
}
