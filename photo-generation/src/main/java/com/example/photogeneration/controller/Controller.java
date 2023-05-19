package com.example.photogeneration.controller;

import com.example.photogeneration.WebClient.WebClient;
import com.example.photogeneration.dto.FileResponse;
import com.example.photogeneration.dto.ImageResponse;
import com.example.photogeneration.dto.ServiceResponse;
import com.example.photogeneration.entity.FileEntity;
import com.example.photogeneration.repository.FileRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/image-generation")

public class Controller {

    @Value("${client.deep-ai.url}")
    private String url;

    @Value("${client.deep-ai.api-key}")
    private String apiKey;

    private final WebClient webClient;

    private final FileRepository fileRepository;

    public Controller(WebClient webClient, FileRepository fileRepository) {
        this.webClient = webClient;
        this.fileRepository = fileRepository;
    }

    @GetMapping
    ServiceResponse<FileResponse> generateImage(
            @RequestParam(value = "code", defaultValue = "") String code,
            @RequestParam(value = "prompt", defaultValue = "") String prompt) {
        URI uri = UriComponentsBuilder.fromHttpUrl(url)
                .build().toUri();
        Map<String, String> headers = new HashMap<>();
        headers.put("api-key", apiKey);
        Map<String, String> body = new HashMap<>();
        body.put("text", prompt);
        ImageResponse response = webClient.postForObject(uri, ImageResponse.class, headers, body);
        FileEntity file = FileEntity.builder()
                .id(UUID.randomUUID())
                .root_id(response.getId())
                .code(code)
                .prompt(prompt)
                .url(response.getOutput_url())
                .build();
        fileRepository.save(file);
        return ServiceResponse.succeed(HttpStatus.OK, new FileResponse(file));
    }

    @GetMapping("/{code}")
    ServiceResponse<List<FileResponse>> findImage(@PathVariable @Valid String code) {
        List<FileEntity> files = fileRepository.findByCodeLike("%" + code + "%");
        List<FileResponse> responses = files.stream().map(item -> new FileResponse(item)).collect(Collectors.toList());
        return ServiceResponse.succeed(HttpStatus.OK, responses);
    }

}
