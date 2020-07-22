package ru.domru.logger.kafka.receiver.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class EventsBuilder {
    ObjectMapper mapper = new ObjectMapper();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    List<Map<String, Object>> getEvents(String strEvents) throws JsonProcessingException {
        Map<String, Object> map = mapper.readValue(strEvents, new TypeReference<Map<String, Object>>() {
        });
        Object arrEvents = map.get("events");
        return (List<Map<String, Object>>) arrEvents;
    }

    public List<Map<String, Object>> prepareEvents(Map<String, Object> info, String strEvents) throws JsonProcessingException {
        List<Map<String, Object>> events = getEvents(strEvents);
        events.forEach(event -> {
            event.put("timestamp", decodeTimestamp(event.get("timestamp")));
            event.putAll(info);
        });
        return events;
    }

    String decodeTimestamp(Object timestamp) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
        Date t = new Date();
        if (timestamp instanceof Integer) {
            t = new Date(((Integer) timestamp).longValue() * 1000);
        }
        if (timestamp instanceof String) {
            String st = (String) timestamp;
            Long lt = Long.decode(st);
            t = new Date(lt * 1000);
        }
        return df.format(t);
    }
}
