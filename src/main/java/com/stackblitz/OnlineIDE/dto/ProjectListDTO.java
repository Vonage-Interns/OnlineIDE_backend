package com.stackblitz.OnlineIDE.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
//TO GET ALL PROJECTS
public class ProjectListDTO {
    private Long projectId;
    private String projectName;

}
