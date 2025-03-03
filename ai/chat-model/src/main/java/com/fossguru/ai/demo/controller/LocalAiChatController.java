package com.fossguru.ai.demo.controller;

import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LocalAiChatController {

    @Autowired
    private final OllamaChatModel chatModel;


    public LocalAiChatController(OllamaChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @GetMapping("/ai/local/prompt")
    public String promptResponse(@RequestParam(value = "message") String message) {
        return chatModel.call(new Prompt(message)).getResult().getOutput().getText();
    }
}
