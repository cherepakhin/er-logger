package ru.domru.logger.kafka.receiver.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import ru.domru.logger.kafka.receiver.service.EventDeviceProducer;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@CrossOrigin(origins = "*")
public class EventDeviceController {

    public static final String X_AUTH_TOKEN_HEADER_NAME = "x-auth-token";

    final Logger logger = LoggerFactory.getLogger(EventDeviceController.class);
    final EventDeviceProducer eventDeviceProducer;
    final ITokenDecoder tokenDecoder;
    EventsBuilder eventsBuilder = new EventsBuilder();
    ExecutorService executorService;

    public EventDeviceController(@Autowired EventDeviceProducer eventDeviceProducer,
                                 @Autowired ITokenDecoder tokenDecoder) {
        this.eventDeviceProducer = eventDeviceProducer;
        this.tokenDecoder = tokenDecoder;
        this.executorService = Executors.newFixedThreadPool(100);
    }

    @GetMapping(value = {"/", "/{msg}"})
    public ResponseEntity<String> echo(@PathVariable(name = "msg", required = false) String msg) {
        if (msg == null) {
            msg = "-";
        }
        return new ResponseEntity<>("ok:" + msg, HttpStatus.OK);
    }

    @PostMapping(consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    @RequestMapping("/logging/device/events/async")
    public CompletableFuture<ResponseEntity<String>> receiveEventAsync(HttpServletRequest httpServletRequest,
                                                                       @RequestBody MultiValueMap<String, Object> body) {
        return CompletableFuture.supplyAsync(() -> {
            String tokenString = httpServletRequest.getHeader(X_AUTH_TOKEN_HEADER_NAME);
            if (!existTokenInHeader(httpServletRequest)) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            try {
                produceData(tokenString, body);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        }, executorService);
    }

    @PostMapping(consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    @RequestMapping("/logging/device/events")
    public ResponseEntity<String> receiveEvent(HttpServletRequest httpServletRequest,
                                               @RequestBody MultiValueMap<String, Object> body) {
        String tokenString = httpServletRequest.getHeader(X_AUTH_TOKEN_HEADER_NAME);
        if (!existTokenInHeader(httpServletRequest)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        try {
            produceData(tokenString, body);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    @RequestMapping("/logging/device/events/stub")
    public ResponseEntity<String> receiveEventStub(HttpServletRequest httpServletRequest,
                                                   @RequestBody MultiValueMap<String, Object> body) {
        if (!existTokenInHeader(httpServletRequest)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    void produceData(String tokenString, MultiValueMap<String, Object> body) throws Exception {
        Map<String, Object> info = tokenDecoder.getInfoFromToken(tokenString);
        String strEvents = (String) body.get("events").get(0);
        List<Map<String, Object>> events = eventsBuilder.prepareEvents(info, strEvents);
        eventDeviceProducer.send(events);
    }

    boolean existTokenInHeader(HttpServletRequest httpServletRequest) {
        if (httpServletRequest.getHeader(X_AUTH_TOKEN_HEADER_NAME) == null) {
            return false;
        }
        return !httpServletRequest.getHeader(X_AUTH_TOKEN_HEADER_NAME).isEmpty();
    }
}
