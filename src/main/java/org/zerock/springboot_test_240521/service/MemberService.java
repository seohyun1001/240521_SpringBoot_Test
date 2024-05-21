package org.zerock.springboot_test_240521.service;


import org.zerock.springboot_test_240521.domain.Board;
import org.zerock.springboot_test_240521.domain.Member;
import org.zerock.springboot_test_240521.dto.BoardDTO;
import org.zerock.springboot_test_240521.dto.MemberJoinDTO;

import java.util.List;
import java.util.stream.Collectors;

public interface MemberService {

    static class MIdExistException extends Exception {
    }

    void join(MemberJoinDTO memberJoinDTO) throws MIdExistException;

    // select
    MemberJoinDTO myPage(String mid);

    // select
    MemberJoinDTO confirmMid(MemberJoinDTO memberJoinDTO) throws MIdExistException;

    // update
    void modify(MemberJoinDTO memberJoinDTO);


    default  MemberJoinDTO entityToDto(Member member) {

        // 단순 데이터 설정
        MemberJoinDTO memberJoinDTO = MemberJoinDTO.builder()
                .mid(member.getMid())
                .mpw(member.getMpw())
                .name(member.getName())
                .email(member.getEmail())
                .address(member.getAddress())
                .build();

        return memberJoinDTO;

    }

    void remove(String mid);

}
