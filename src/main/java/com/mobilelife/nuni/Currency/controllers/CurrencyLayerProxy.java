package com.mobilelife.nuni.Currency.controllers;

import com.mobilelife.nuni.Currency.models.CurrencyLayerModel;
import com.mobilelife.nuni.Currency.services.CurrencyLayerServiceProxy;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
public class CurrencyLayerProxy {

    @Autowired
    CurrencyLayerServiceProxy currencyProxyService;

    @SneakyThrows
    @GetMapping("/live")
    public CompletableFuture<Map<String,Double>> getLiveExchangeRates() {
        return currencyProxyService.getLiveExchangeRates();
    }
}
