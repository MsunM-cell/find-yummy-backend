package com.example.demo;

import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class RequestsToResponses {

    public static Map<Long, Collection<Long>> map = new HashMap<Long, Collection<Long>>();

    public Map<Long, Collection<Long>> getMap() {
        return map;
    }

    public void addNewElementToMap(Long id, Collection<Long> ids) {
        map.put(id, ids);
    }
}
