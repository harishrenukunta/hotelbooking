package com.equalexperts.hotelbooking.stepdefs;

import io.cucumber.spring.ScenarioScope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@ScenarioScope
public class ScenarioContext {

    private Map<String, Object> scenarioData = new HashMap<>();

    public void saveData(final String key, final Object val){
        scenarioData.put(key, val);
    }

    public Object getData(final String key){
        return scenarioData.get(key);
    }
}
