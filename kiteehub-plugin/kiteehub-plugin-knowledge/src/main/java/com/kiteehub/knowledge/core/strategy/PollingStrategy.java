package com.kiteehub.knowledge.core.strategy;

import com.unfbx.chatgpt.function.KeyStrategyFunction;

import java.util.List;

/**
 * Created by Ranger on 2023/05/15.
 */
public class PollingStrategy implements KeyStrategyFunction<List<String>, String> {
    private int pointer;
    @Override
    public String apply(List<String> apiKeys) {
        return getNextKey(apiKeys);
    }

    public synchronized String getNextKey(List<String> apiKeys) {
        if (apiKeys.size() == 0) {
            throw new RuntimeException("api Key pool is empty");
        }
        if(pointer >= apiKeys.size()){
            pointer = 0;
        }
        String key = apiKeys.get(pointer);
        pointer = (pointer + 1) % apiKeys.size();
        return key;
    }
}
