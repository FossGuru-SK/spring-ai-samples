package com.fossguru.ai.demo.controller;

import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeeseekAiChatController {

    private final OllamaChatModel chatModel;

    @Autowired
    public DeeseekAiChatController(OllamaChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @GetMapping("/ask")
    public String askMeAnything(@RequestParam(value = "question", defaultValue = "Who are you?") String question) {
        return chatModel.call(new Prompt(question)).getResult().getOutput().getText();

    }
}
