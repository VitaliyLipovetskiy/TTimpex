package com.lvv.ttimpex2.dto;

import com.lvv.ttimpex2.validation.NoHtml;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UpdateSCodeDto {
    @NotBlank(message = "Id scode can't be blank")
    @NotNull(message = "Id scode name can't be null")
    @NoHtml
    @Size(min = 4, max = 4, message = "Id scode must be 4 characters")
    private String id;
    @NoHtml
    @Size(min = 8, max = 8, message = "Scode must be 8 characters")
    private String sCode;
}
