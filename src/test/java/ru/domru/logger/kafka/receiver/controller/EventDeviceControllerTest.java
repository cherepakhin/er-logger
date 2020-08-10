package ru.domru.logger.kafka.receiver.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.domru.logger.kafka.receiver.service.EventDeviceProducer;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class EventDeviceControllerTest {

    final String TOKEN = "eyJkYXRhIjoie1wiZXhwaXJlc1wiOjE1ODkwOTM5MTIsXCJsaWZlc3BhblwiOjI1OTIwMDAsXCJwcmluY2lwYWxcIjp7XCJmcmVlbWl1bVwiOjAsXCJleHRpZFwiOlwibWFjOmQ0OmNmOmY5OjFiOmZmOjEwXCIsXCJzdWJzY3JpYmVyXCI6e1wiaXNfZ3Vlc3RcIjpmYWxzZSxcInR5cGVcIjpcInN1YnNjcmliZXJcIixcImlkXCI6MTAxMTAzMjE4LFwiZ3JvdXBzXCI6W3tcImlkXCI6NDgyNTc0OTksXCJleHRpZFwiOlwiZXI6ZG9tYWluOmRldi10djMtcGVuemFcIn1dLFwiZXh0aWRcIjpcImRldi10djMtcGVuemE6NTgwMDExOTE0Njc5XCJ9LFwicGxhdGZvcm1cIjp7XCJvcGVyYXRvclwiOntcInRpdGxlXCI6XCJcIixcImlkXCI6MixcImV4dGlkXCI6XCJlclwifSxcInRpdGxlXCI6XCJcIixcImlkXCI6NDQsXCJleHRpZFwiOlwiYW5kcm9pZF9pcHR2XCJ9LFwiYXR0cnNcIjpudWxsLFwiZ3JvdXBzXCI6W3tcImlkXCI6MzQxOTcsXCJleHRpZFwiOlwiZXI6ZXZlcnlvbmVcIn1dLFwib3BlcmF0b3JcIjp7XCJ0aXRsZVwiOlwiXCIsXCJpZFwiOjIsXCJleHRpZFwiOlwiZXJcIn0sXCJ0eXBlXCI6XCJkZXZpY2VcIixcImlkXCI6OTc3MTk0NzB9fSIsInNpZ25hdHVyZSI6Ikk0WmNBVUdIQWRaSUxtb2UyTEpNU1JhTG5cL3A1b2pcL1ZBOXVPb1g4VWRqOD0ifQ==";
    final String X_AUTH_TOKEN_HEADER_NAME = "X-Auth-Token";
    final String VIEW = "View";
    final String STB3 = "stb3";
    final String body = "{\"events\":[{\"data\":{\"identifier\":1838387,\"tag\":\"poster_channel_grid_blueprint\",\"appversion\":\"1.32.0\",\"subtype\":\"image.resource.missing.error\",\"connectionType\":\"eth\",\"fwbuildver\":\"202004081158\",\"serialnumber\":\"UHD300X2GR000063\"},\"name\":\"app.mon\",\"timestamp\":1586501905,\"uuid\":\"fa49ee71-2549-4737-8032-1b2cd0a15618\"}]}";
    String BODY = "events=%7B%22events%22%3A%5B%7B%22data%22%3A%7B%22identifier%22%3A1838387%2C%22tag%22%3A%22poster_channel_grid_blueprint%22%2C%22appversion%22%3A%221.32.0%22%2C%22subtype%22%3A%22image.resource.missing.error%22%2C%22connectionType%22%3A%22eth%22%2C%22fwbuildver%22%3A%22202004081158%22%2C%22serialnumber%22%3A%22UHD300X2GR000063%22%7D%2C%22name%22%3A%22app.mon%22%2C%22timestamp%22%3A1586501905%2C%22uuid%22%3A%22fa49ee71-2549-4737-8032-1b2cd0a15618%22%7D%5D%7D";
    EventDeviceController eventDeviceController;

    @Mock
    EventDeviceProducer eventDeviceProducer;

    @Mock
    ITokenDecoder tokenDecoder;

    private MockMvc mockMvc;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        eventDeviceController = new EventDeviceController(eventDeviceProducer, tokenDecoder);

        mockMvc = MockMvcBuilders
                .standaloneSetup(eventDeviceController)
                .build();
    }

    @Test
    public void receiveEvent() throws Exception {
        RequestBuilder requestBuilder = post("/logging/device/events")
                .content(getEncodedBody())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .header(VIEW, STB3)
                .header(X_AUTH_TOKEN_HEADER_NAME, TOKEN);
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void receiveEvent_AccessDenied() throws Exception {
        RequestBuilder requestBuilder = post("/logging/device/events")
                .content(getEncodedBody())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .header(VIEW, STB3);
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    public String getEncodedBody() throws UnsupportedEncodingException {
        return "events=" + URLEncoder.encode(body, StandardCharsets.UTF_8.toString());
    }

    @Test
    public void assertHeaderToken() {
        MockHttpServletRequest httpServletRequest = new MockHttpServletRequest();
        assertFalse(eventDeviceController.existTokenInHeader(httpServletRequest));

        httpServletRequest.addHeader(X_AUTH_TOKEN_HEADER_NAME, TOKEN);
        assertTrue(eventDeviceController.existTokenInHeader(httpServletRequest));
    }

    @Test
    public void echo() throws Exception {
        String OK = "ok";
        String MESSAGE = "MESSAGE";
        mockMvc.perform(get("/echo/" + MESSAGE)
                .contentType(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(content().string(OK + ":" + MESSAGE))
        ;
    }

    @Test
    public void echoEmpty() throws Exception {
        mockMvc.perform(get("/echo")
                .contentType(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(content().string("ok:-"))
        ;
    }
}