package org.zerock.springboot_test_240521.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.zerock.springboot_test_240521.domain.Board;
import org.zerock.springboot_test_240521.domain.Member;
import org.zerock.springboot_test_240521.domain.MemberRole;
import org.zerock.springboot_test_240521.dto.MemberJoinDTO;
import org.zerock.springboot_test_240521.repository.MemberRepository;

import java.util.Optional;


@Log4j2
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final ModelMapper modelMapper;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void join(MemberJoinDTO memberJoinDTO) throws MIdExistException {

        // ID가 존재하지 않으면 Member 객체로 변환
        Member member = modelMapper.map(memberJoinDTO, Member.class);
        // 비밀번호 암호화
        member.changePassword(passwordEncoder.encode(memberJoinDTO.getMpw()));
        // 권한 설정
        member.addRole(MemberRole.USER);

        log.info("==========================================");
        log.info(member);
        log.info(member.getRoleSet());

        // 데이터베이스에 저장
        memberRepository.save(member);
    }

    @Override
    public MemberJoinDTO myPage(String mid) {
        Optional<Member> result = memberRepository.findById(mid);
        Member member = result.orElseThrow();
        MemberJoinDTO memberJoinDTO = entityToDto(member);
        return memberJoinDTO;
    }

    @Override
    public MemberJoinDTO confirmMid(MemberJoinDTO memberJoinDTO) throws MIdExistException {
        // 화면헤서 가지고 온 ID를 저장
        String mid = memberJoinDTO.getMid();
        // JPA에서 지원하는 ID 존재 여부 확인 메서드 실행
        boolean exist = memberRepository.existsById(mid);
        // ID가 이미 존재하면 에러를 발생시킴
        if (exist) {
            throw new MIdExistException();
        }
        return memberJoinDTO;
    }

    @Override
    public void modify(MemberJoinDTO memberJoinDTO) {
        Optional<Member> result = memberRepository.findById(memberJoinDTO.getMid());
        Member member = result.orElseThrow();
        member.changeAll(memberJoinDTO.getMid(),
                memberJoinDTO.getMpw(),
                memberJoinDTO.getName(),
                memberJoinDTO.getEmail(),
                memberJoinDTO.getAddress());
        member.changePassword(passwordEncoder.encode(memberJoinDTO.getMpw()));
        memberRepository.save(member);
    }

    @Override
    public void remove(String mid) {
        memberRepository.deleteById(mid);
    }


}
