package com.vinsguru.aggregator.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vinsguru.aggregator.service.TradeService;
import com.vinsguru.user.StockTradeRequest;
import com.vinsguru.user.StockTradeResponse;

@RestController
@RequestMapping("trade")
public class TradeController {

    private final TradeService tradeService;

    public TradeController(TradeService tradeService) {
        this.tradeService = tradeService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public StockTradeResponse trade(@RequestBody StockTradeRequest request) {
        return this.tradeService.trade(request);
    }

}
