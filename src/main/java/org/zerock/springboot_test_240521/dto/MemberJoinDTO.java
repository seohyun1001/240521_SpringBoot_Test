package org.zerock.springboot_test_240521.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    private boolean del;
    private boolean social;

}
