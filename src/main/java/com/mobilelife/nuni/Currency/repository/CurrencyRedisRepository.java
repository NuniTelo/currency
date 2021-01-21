package com.mobilelife.nuni.Currency.repository;

import com.mobilelife.nuni.Currency.models.CurrencyLayerModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CurrencyRedisRepository {

    @Qualifier("redisTemplate")
    @Autowired
    private RedisTemplate template;

    public CurrencyLayerModel save(CurrencyLayerModel model) {
        template.opsForHash().put("currency","1",model);
        return model;
    }
    public CurrencyLayerModel get() {
        return (CurrencyLayerModel) template.opsForHash().get("currency","1");
    }
}
