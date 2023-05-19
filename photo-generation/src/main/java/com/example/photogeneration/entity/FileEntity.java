package com.example.photogeneration.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "file")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class FileEntity {
    @Id
    @Column(name = "id", columnDefinition = "uuid")
    private UUID id;

    @Column(name = "root_id")
    private String root_id;

    @Column(name = "code")
    private String code;

    @Column(name = "prompt")
    private String prompt;

    @Column(name = "url")
    private String url;
}
