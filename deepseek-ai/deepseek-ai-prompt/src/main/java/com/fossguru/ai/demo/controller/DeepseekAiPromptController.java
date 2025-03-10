package com.fossguru.ai.demo.controller;

import org.antlr.v4.runtime.misc.Pair;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.converter.ListOutputConverter;
import org.springframework.ai.converter.MapOutputConverter;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
public class DeepseekAiPromptController {

    private final OllamaChatModel chatModel;

    @Value("classpath:/prompts/user-message.st")
    private Resource promptUserMessageTemplate;

    @Value("classpath:/prompts/system-message.st")
    private Resource promptSystemMessageTemplate;

    @Autowired
    public DeepseekAiPromptController(OllamaChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @GetMapping("/tourist/guide")
    public String touristGuide(@RequestParam(value = "country", defaultValue = "India") String countryName) {
        PromptTemplate pt = new PromptTemplate(promptUserMessageTemplate);
        Prompt prompt = pt.create(Map.of("location", countryName));
        return chatModel.call(prompt).getResult().getOutput().getText();
    }

    @GetMapping("/tourist/guide/system")
    public String touristGuideSystem(@RequestParam(value = "country", defaultValue = "India") String countryName) {
        //Create Message with User messageType
        PromptTemplate userPromptTemplate = new PromptTemplate(promptUserMessageTemplate);
        Message userMessage = userPromptTemplate.createMessage(Map.of("location", countryName));

        //Create Message with System messageType
        SystemPromptTemplate systemPromptTemplate = new SystemPromptTemplate(promptSystemMessageTemplate);
        Message systemMessage = systemPromptTemplate.createMessage(Map.of("name", "Siri"));

        //Create Prompt with both user and message
        Prompt prompt = new Prompt(List.of(userMessage, systemMessage));
        return chatModel.call(prompt).getResult().getOutput().getText();
    }

    //Request Structured output response
    @GetMapping("/country/capitals")
    public Map<String, Object> getCountryCapitals(@RequestParam(value = "countryNames", defaultValue = "India,Germany,Canada") String countryNames) {

        if(countryNames == null || countryNames.isEmpty()) {
            throw new IllegalArgumentException("Comma Separated Country names cannot be null or empty");
        }

        MapOutputConverter mapOutputConverter = new MapOutputConverter();
        String format = mapOutputConverter.getFormat();

        PromptTemplate pt = new PromptTemplate("For these list of countries {countryNamesCsv}, return the list of capitals. {format}");
        Prompt prompt = pt.create(Map.of("countryNamesCsv", countryNames, "format", format));
        Generation generation = chatModel.call(prompt).getResult();
        return mapOutputConverter.convert(generation.getOutput().getText());
    }

    //Request Structured output response
    @GetMapping("/country/capitals/list")
    public List<String> getCountryCapitalsList(@RequestParam(value = "countryNames", defaultValue = "India,Germany,Canada") String countryNames) {

        if(countryNames == null || countryNames.isEmpty()) {
            throw new IllegalArgumentException("Comma Separated Country names cannot be null or empty");
        }

        ListOutputConverter listOutputConverter = new ListOutputConverter(new DefaultConversionService());
        String format = listOutputConverter.getFormat();

        PromptTemplate pt = new PromptTemplate("For these list of countries {countryNamesCsv}, return the list of capitals. {format}");
        Prompt prompt = pt.create(Map.of("countryNamesCsv", countryNames, "format", format));
        Generation generation = chatModel.call(prompt).getResult();
        return listOutputConverter.convert(generation.getOutput().getText());
    }

    //Request Structured output response
    @GetMapping("/country/capitals/bean")
    public Object getCountryCapitalsBean(@RequestParam(value = "countryNames", defaultValue = "India,Germany,Canada") String countryNames) {

        if(countryNames == null || countryNames.isEmpty()) {
            throw new IllegalArgumentException("Comma Separated Country names cannot be null or empty");
        }

        BeanOutputConverter beanOutputConverter = new BeanOutputConverter(Object.class);
        String format = beanOutputConverter.getFormat();

        PromptTemplate pt = new PromptTemplate("For these list of countries {countryNamesCsv}, return the list of top cities. {format}");
        Prompt prompt = pt.create(Map.of("countryNamesCsv", countryNames, "format", format));
        Generation generation = chatModel.call(prompt).getResult();
        return beanOutputConverter.convert(generation.getOutput().getText());
    }


}
