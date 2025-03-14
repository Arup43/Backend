package com.explore.backend.controller;

import com.explore.backend.dto.CommandResponseDTO;
import com.explore.backend.dto.CommandYTDto;
import com.explore.backend.service.CommandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/commands/yt")
@Tag(name = "Command", description = "Command processing APIs")
public class CommandYTController {
    @Autowired
    private CommandService commandService;

    @Operation(
            summary = "Process command",
            description = "Processes a command by calling external AI service"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Command processed successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = CommandResponseDTO.class))
    )
    @PostMapping
    public ResponseEntity<Boolean> processCommand(@RequestBody CommandYTDto command) {
        System.out.println("Getting request......");
        boolean result = commandService.processCommand(command);
        return ResponseEntity.ok(result);
    }
}
