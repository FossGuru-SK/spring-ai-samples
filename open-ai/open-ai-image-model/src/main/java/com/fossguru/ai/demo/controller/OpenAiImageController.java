package com.fossguru.ai.demo.controller;

import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.openai.OpenAiImageModel;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OpenAiImageController {

    private final OpenAiImageModel imageModel;

    @Autowired
    public OpenAiImageController(OpenAiImageModel imageModel) {
        this.imageModel = imageModel;
    }

    @GetMapping("/ask/image")
    public ImageResponse generateImage(@RequestParam(value = "message", defaultValue = "Horse running behind tiger") String messgae) {
        return imageModel.call(
                new ImagePrompt(messgae,
                        OpenAiImageOptions.builder()
                                .quality("hd")
                                .N(4)
                                .height(1024)
                                .width(1024).build())

        );
    }

    @GetMapping("/ask/image/withUrl")
    public String generateImageUrl(@RequestParam(value = "message", defaultValue = "Horse running behind tiger") String messgae) {
        ImageResponse imageResponse = imageModel.call(new ImagePrompt(messgae, OpenAiImageOptions.builder().quality("hd").N(4).height(1024).width(1024).build()));
        String imageUrl = imageResponse.getResult().getOutput().getUrl();
        return "Redirect to:" + imageUrl;
    }
}
