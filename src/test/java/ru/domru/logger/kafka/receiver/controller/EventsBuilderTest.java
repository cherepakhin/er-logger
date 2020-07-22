package ru.domru.logger.kafka.receiver.controller;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class EventsBuilderTest {
    @Test
    public void getEvents() throws IOException {
        File resource = new ClassPathResource(
                "events.json").getFile();
        String body = new String(
                Files.readAllBytes(resource.toPath()));

        EventsBuilder eventsBuilder = new EventsBuilder();
        List<Map<String, Object>> events = eventsBuilder.getEvents(body);
        assertEquals(2, events.size());

        Map<String, Object> event = events.get(0);
        assertTrue(event.containsKey("data"));
        assertTrue(event.containsKey("name"));
        assertTrue(event.containsKey("timestamp"));
        assertTrue(event.containsKey("uuid"));
    }

    @Test
    public void prepareEvents() throws IOException {
        Map<String, Object> info = new HashMap<>();
        info.put("INFO1", "1");
        info.put("INFO2", "3");

        File resource = new ClassPathResource(
                "events.json").getFile();
        String body = new String(
                Files.readAllBytes(resource.toPath()));

        EventsBuilder eventsBuilder = new EventsBuilder();
        List<Map<String, Object>> events = eventsBuilder.prepareEvents(info, body);
        assertEquals(2, events.size());

        Map<String, Object> event = events.get(0);
        assertTrue(event.containsKey("INFO1"));
        assertTrue(event.containsKey("INFO2"));
    }

    @Test
    @Ignore
    public void decodeTimestamp() {
        EventsBuilder eventsBuilder = new EventsBuilder();
        assertEquals("2020-04-10T11:58Z", eventsBuilder.decodeTimestamp(1586501905));
        assertEquals("2020-04-10T11:58Z", eventsBuilder.decodeTimestamp("1586501905"));
    }
}