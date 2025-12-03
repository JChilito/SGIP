package dev.chilito.backend.shared.infrastructure.input.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/api")
public class PingController {

    /**
     * Public ping endpoint
     * Function to test if the server is running
     * 
     * @return "pong public"
     */
    @GetMapping("/public/ping")
    public String publicPing() {
        return "pong public";
    }

    /**
     * Private ping endpoint
     * Function to test if the server is validating the token
     * 
     * @return "pong private"
     */
    @GetMapping("/private/ping")
    public String privatePing() {
        return "pong private";
    }

}
