package com.stackblitz.OnlineIDE.dto;

import lombok.Data;

@Data
public class FolderDTO {
    private String name;
    private Long projectId;
    private Long parentFolderId;
}
