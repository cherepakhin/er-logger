package ru.domru.logger.kafka.receiver.controller;

import org.junit.Test;

import java.io.IOException;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TokenDecoderSimpleImplTest {

    final String TOKEN = "eyJkYXRhIjoie1wiZXhwaXJlc1wiOjE1ODkwOTM5MTIsXCJsaWZlc3BhblwiOjI1OTIwMDAsXCJwcmluY2lwYWxcIjp7XCJmcmVlbWl1bVwiOjAsXCJleHRpZFwiOlwibWFjOmQ0OmNmOmY5OjFiOmZmOjEwXCIsXCJzdWJzY3JpYmVyXCI6e1wiaXNfZ3Vlc3RcIjpmYWxzZSxcInR5cGVcIjpcInN1YnNjcmliZXJcIixcImlkXCI6MTAxMTAzMjE4LFwiZ3JvdXBzXCI6W3tcImlkXCI6NDgyNTc0OTksXCJleHRpZFwiOlwiZXI6ZG9tYWluOmRldi10djMtcGVuemFcIn1dLFwiZXh0aWRcIjpcImRldi10djMtcGVuemE6NTgwMDExOTE0Njc5XCJ9LFwicGxhdGZvcm1cIjp7XCJvcGVyYXRvclwiOntcInRpdGxlXCI6XCJcIixcImlkXCI6MixcImV4dGlkXCI6XCJlclwifSxcInRpdGxlXCI6XCJcIixcImlkXCI6NDQsXCJleHRpZFwiOlwiYW5kcm9pZF9pcHR2XCJ9LFwiYXR0cnNcIjpudWxsLFwiZ3JvdXBzXCI6W3tcImlkXCI6MzQxOTcsXCJleHRpZFwiOlwiZXI6ZXZlcnlvbmVcIn1dLFwib3BlcmF0b3JcIjp7XCJ0aXRsZVwiOlwiXCIsXCJpZFwiOjIsXCJleHRpZFwiOlwiZXJcIn0sXCJ0eXBlXCI6XCJkZXZpY2VcIixcImlkXCI6OTc3MTk0NzB9fSIsInNpZ25hdHVyZSI6Ikk0WmNBVUdIQWRaSUxtb2UyTEpNU1JhTG5cL3A1b2pcL1ZBOXVPb1g4VWRqOD0ifQ==";

    @Test
    public void getInfoFromToken() throws IOException {
        TokenDecoderSimpleImpl tokenDecoder = new TokenDecoderSimpleImpl();
        Map<String, Object> info = tokenDecoder.getInfoFromToken(TOKEN);
        assertTrue(info.containsKey("device"));
        Map<String, Object> device = (Map<String, Object>) info.get("device");
        assertTrue(device.containsKey("id"));
        assertEquals(97719470, device.get("id"));
        assertTrue(device.containsKey("platform"));
        assertEquals("android_iptv",device.get("platform"));

        assertTrue(info.containsKey("subscriber"));
        Map<String, Object> subscriber = (Map<String, Object>) info.get("subscriber");
        assertTrue(subscriber.containsKey("id"));
        assertEquals("dev-tv3-penza:580011914679",subscriber.get("id"));
    }

    @Test
    public void deserilizeToken() throws IOException {
        TokenDecoderSimpleImpl tokenDecoder = new TokenDecoderSimpleImpl();
        Map<String, Object> tokenMap = tokenDecoder.deserilizeToken(TOKEN);
        System.out.println(tokenMap);
    }

}