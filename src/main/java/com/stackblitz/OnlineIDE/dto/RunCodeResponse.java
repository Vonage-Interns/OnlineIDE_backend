package com.stackblitz.OnlineIDE.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RunCodeResponse {
    private String output;
    private String error;
    private int exitCode;

}
