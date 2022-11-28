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
public class UpdateDepartmentDto {
    @NotBlank(message = "Department name can't be blank")
    @NotNull(message = "Department name can't be null")
    @NoHtml
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    private String name;
}
