package com.fossguru.ai.demo.controller;

import org.springframework.ai.audio.transcription.AudioTranscriptionPrompt;
import org.springframework.ai.openai.OpenAiAudioTranscriptionModel;
import org.springframework.ai.openai.OpenAiAudioTranscriptionOptions;
import org.springframework.ai.openai.api.OpenAiAudioApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OpenAiSpeechToTextController {

    private final OpenAiAudioTranscriptionModel transcriptionModel;

    @Autowired
    public OpenAiSpeechToTextController(OpenAiAudioTranscriptionModel transcriptionModel) {
        this.transcriptionModel = transcriptionModel;
    }

    @GetMapping("/transcription")
    public String getSpeechToText(@Value("classpath:speech.mp3") Resource audioFile) {
        return transcriptionModel.call(new AudioTranscriptionPrompt(audioFile))
                .getResult()
                .getOutput();
    }

    @GetMapping("/transcription_v2")
    public String getSpeechToTextDetails(@Value("classpath:speech.mp3") Resource audioFile) {

        var transcriptionOptions = OpenAiAudioTranscriptionOptions.builder()
                .responseFormat(OpenAiAudioApi.TranscriptResponseFormat.TEXT)
                .temperature(0f)
                .build();

        var transcriptionResponse = transcriptionModel
                .call(new AudioTranscriptionPrompt(audioFile, transcriptionOptions));
        return transcriptionResponse.getResult().getOutput();
    }

}
