package com.mobilelife.nuni.Currency.services;

import com.mobilelife.nuni.Currency.models.CurrencyLayerModel;
import com.mobilelife.nuni.Currency.repository.CurrencyRedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
public class CurrencyLayerServiceProxy {

    @Value("${api.base_url}")
    private String BASE_URL;

    @Value("${api.key}")
    private String API_KEY;

    @Autowired
    CurrencyRedisRepository currencyRedisRepository;

    @Async
    public CompletableFuture<Map<String,Double>> getLiveExchangeRates() {
        try {
            CurrencyLayerModel results = currencyRedisRepository.get();
            if (results != null) {
                return CompletableFuture.completedFuture(results.getQuotes());
            } else {
                CurrencyLayerModel live = new RestTemplate().getForObject(BASE_URL + "/live?access_key=" + API_KEY, CurrencyLayerModel.class);
                return CompletableFuture.supplyAsync(() -> {
                    if (live.getSuccess()) {
                        currencyRedisRepository.save(live);
                        return live.getQuotes();
                    } else {
                        return new HashMap<>();
                    }
                });
            }
        }catch (Exception e){
            return CompletableFuture.completedFuture(new HashMap<>());
        }
    }
}
