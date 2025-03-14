package com.explore.backend.service;

import com.explore.backend.config.RabbitMQConfig;
import com.explore.backend.dto.CommandResponseDTO;
import com.explore.backend.dto.CommandYTDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;

@Service
public class CommandService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${ai-service.url}")
    private String aiServiceUrl;

    public boolean processCommand(CommandYTDto command) {
        System.out.println("Going to service...");
        try {
            // Start a new thread to handle the AI service call asynchronously
            new Thread(() -> {
                try {
                    // Call external AI service
                    ResponseEntity<CommandResponseDTO[]> response = restTemplate.postForEntity(
                            aiServiceUrl + "/generate-youtube-reactions",
                            command,
                            CommandResponseDTO[].class);

                    // Publish each command response to RabbitMQ queue
                    CommandResponseDTO[] commandResponses = response.getBody();
                    if (commandResponses != null) {
                        for (CommandResponseDTO commandResponse : commandResponses) {
                            rabbitTemplate.convertAndSend(RabbitMQConfig.COMMAND_QUEUE, commandResponse);
                        }
                        System.out.println("Successfully processed AI service response and sent to RabbitMQ");
                    } else {
                        System.out.println("Received null response from AI service");
                    }
                } catch (Exception e) {
                    // Log the error here
                    System.out.println("Error in async processing: " + e.getMessage());
                }
            }).start();

            // Return true immediately after initiating the request
            return true;

        } catch (Exception e) {
            // This will only catch errors in starting the thread
            System.out.println("Error initiating request: " + e.getMessage());
            return false;
        }
    }
}