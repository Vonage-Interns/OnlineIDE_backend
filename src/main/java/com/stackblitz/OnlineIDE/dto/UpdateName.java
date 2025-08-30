package com.stackblitz.OnlineIDE.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class UpdateName {
    private Long folderId;
    private String updateName;


}
