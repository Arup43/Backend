package com.explore.backend.service;

import com.explore.backend.config.RabbitMQConfig;
import com.explore.backend.dto.CommandDTO;
import com.explore.backend.dto.CommandResponseDTO;
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
    
    public boolean processCommand(CommandDTO command) {
        try {
            // Call external AI service
            ResponseEntity<CommandResponseDTO[]> response = restTemplate.postForEntity(
                aiServiceUrl + "/generate-reactions",
                command,
                CommandResponseDTO[].class
            );
            
            // Publish each command response to RabbitMQ queue
            CommandResponseDTO[] commandResponses = response.getBody();
            if (commandResponses != null) {
                for (CommandResponseDTO commandResponse : commandResponses) {
                    rabbitTemplate.convertAndSend(RabbitMQConfig.COMMAND_QUEUE, commandResponse);
                }
                return true;
            }
            return false;
            
        } catch (Exception e) {
            // Log the error here
            return false;
        }
    }
}