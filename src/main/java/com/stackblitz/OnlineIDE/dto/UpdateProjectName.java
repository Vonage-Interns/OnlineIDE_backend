package com.stackblitz.OnlineIDE.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UpdateProjectName {

    private Long id;
    private String updateName;

}
