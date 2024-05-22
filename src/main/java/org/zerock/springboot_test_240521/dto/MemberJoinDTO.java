package org.zerock.springboot_test_240521.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberJoinDTO {

    private String mid;
    private String mpw;
    private String name;
    private String email;
    private String address;

    @NotEmpty
    private LocalDateTime regDate;
    private LocalDateTime modDate;

    private boolean del;
    private boolean social;

}
