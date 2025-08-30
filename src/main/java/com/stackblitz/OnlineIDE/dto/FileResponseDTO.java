package com.stackblitz.OnlineIDE.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FileResponseDTO {
    private Long id;
    private String name;
    private  String type;
    private String content;

}
