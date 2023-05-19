package com.example.photogeneration.dto;

import com.example.photogeneration.entity.FileEntity;
import lombok.Data;

import java.util.UUID;

@Data
public class FileResponse {
    private UUID id;

    private String root_id;

    private String code;

    private String prompt;

    private String url;

    public FileResponse(FileEntity entity) {
        this.id = entity.getId();
        this.root_id = entity.getRoot_id();
        this.code = entity.getCode();
        this.prompt = entity.getPrompt();
        this.url = entity.getUrl();
    }
}
