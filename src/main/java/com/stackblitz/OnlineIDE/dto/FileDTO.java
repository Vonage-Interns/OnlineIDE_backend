package com.stackblitz.OnlineIDE.dto;


import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class FileDTO {
    private String fileName;
//    private String content;
    private Map<String, String> content;
    private String type;
    private Long projectID;
    private Long folderID;
}
