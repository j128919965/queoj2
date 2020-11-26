package xyz.lizhaorong.queoj.controller.vo;

import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ProblemVO {

    @NotBlank
    private String name;

    @Min(0)
    private Integer point;

    @Min(1)
    @Max(3)
    private Byte level;

    @NotBlank
    private String defaultCode;

    @NotBlank
    private String pendingCode;

    @NotBlank
    private String description;

}
