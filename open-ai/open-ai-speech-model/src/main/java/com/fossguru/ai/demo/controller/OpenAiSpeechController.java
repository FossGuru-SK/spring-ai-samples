package com.fossguru.ai.demo.controller;

import org.springframework.ai.openai.OpenAiAudioSpeechModel;
import org.springframework.ai.openai.OpenAiAudioSpeechOptions;
import org.springframework.ai.openai.api.OpenAiAudioApi;
import org.springframework.ai.openai.audio.speech.SpeechPrompt;
import org.springframework.ai.openai.audio.speech.SpeechResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class OpenAiSpeechController {

    private final OpenAiAudioSpeechModel speechModel;

    @Autowired
    public OpenAiSpeechController(OpenAiAudioSpeechModel speechModel) {
        this.speechModel = speechModel;
    }

    @GetMapping("/ask/speech")
    public byte[] generateSpeechFromText(@RequestParam(value = "message", defaultValue = "I am cool AI assitantce") String message) {
        return speechModel.call(message);
    }

    /*@GetMapping("/stream-speech")
    Flux<SpeechResponse> streamingSpeech(@RequestParam String message) {
        var openAiAudioApi = new OpenAiAudioApi(System.getenv("OPENAI_API_KEY"));

        var openAiAudioSpeechModel = new OpenAiAudioSpeechModel(openAiAudioApi);

        OpenAiAudioSpeechOptions speechOptions = OpenAiAudioSpeechOptions.builder()
                .voice(OpenAiAudioApi.SpeechRequest.Voice.ALLOY)
                .speed(1.0f)
                .responseFormat(OpenAiAudioApi.SpeechRequest.AudioResponseFormat.MP3)
                .model(OpenAiAudioApi.TtsModel.TTS_1.value)
                .build();

        SpeechPrompt speechPrompt = new SpeechPrompt("Today is a wonderful day to build something people love!", speechOptions);

        Flux<SpeechResponse> responseStream = openAiAudioSpeechModel.stream(speechPrompt);
        return responseStream;
    }*/

}
