package com.stackblitz.OnlineIDE.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RunCodeRequest {
    private Long fileId;
    private String fileName;
}
