package org.zerock.springboot_test_240521.service;

import org.zerock.springboot_test_240521.domain.Member;
import org.zerock.springboot_test_240521.dto.MemberJoinDTO;

public interface MemberService {

    static class MIdExistException extends Exception {
    }

    // 회원가입
    void join(MemberJoinDTO memberJoinDTO) throws MIdExistException;

    // 마이 페이지
    MemberJoinDTO myPage(String mid);

    // 중복확인
    MemberJoinDTO confirmMid(MemberJoinDTO memberJoinDTO) throws MIdExistException;

    // 정보 수정
    void modify(MemberJoinDTO memberJoinDTO);

    // domain -> entity
    default  MemberJoinDTO entityToDto(Member member) {
        MemberJoinDTO memberJoinDTO = MemberJoinDTO.builder()
                .mid(member.getMid())
                .mpw(member.getMpw())
                .name(member.getName())
                .email(member.getEmail())
                .address(member.getAddress())
                .regDate(member.getRegDate())
                .modDate(member.getModDate())
                .build();

        return memberJoinDTO;
    }

    // 회원 삭제
    String remove(String mid);

}
