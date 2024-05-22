package org.zerock.springboot_test_240521.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
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
    public void join(MemberJoinDTO memberJoinDTO) {
        Member member = modelMapper.map(memberJoinDTO, Member.class);
        member.changePassword(passwordEncoder.encode(memberJoinDTO.getMpw()));
        member.addRole(MemberRole.USER);

        log.info("==========================================");
        log.info(member);
        log.info(member.getRoleSet());

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
        String mid = memberJoinDTO.getMid();
        boolean exist = memberRepository.existsById(mid);
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
    public String remove(String mid) {
        memberRepository.deleteById(mid);

        return "redirect:/logout";
    }


}
