package com.fossguru.ai.demo.chat.controller;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OpenAiChatController {

    private final ChatModel chatModel;

    @Autowired
    public OpenAiChatController(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @GetMapping("/ask")
    public String tellSimpleJoke(@RequestParam("message") String message) {
         return chatModel.call(new Prompt(message)).getResult().getOutput().getText();
    }
}
