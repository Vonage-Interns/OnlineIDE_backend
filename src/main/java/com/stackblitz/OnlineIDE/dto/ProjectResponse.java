package com.stackblitz.OnlineIDE.dto;

import lombok.Data;
import java.util.List;

//CREATION OF PROJECT RESPONSE
@Data
public class ProjectResponse {

    private List<ProjectDTO> projects;

}
