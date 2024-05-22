package org.zerock.springboot_test_240521.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.zerock.springboot_test_240521.repository.MemberRepository;
import org.zerock.springboot_test_240521.security.dto.MemberSecurityDTO;
import org.zerock.springboot_test_240521.domain.Member;

import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Load User By Username : " + username);

        Optional<Member> result = memberRepository.getWithRoles(username);

        if (result.isEmpty()) {
            throw  new UsernameNotFoundException(("user name not found------------"));
        }

        Member member = result.get();

        MemberSecurityDTO memberSecurityDTO =
                new MemberSecurityDTO(
                        member.getMid(),
                        member.getMpw(),
                        member.getName(),
                        member.getEmail(),
                        member.getAddress(),
                        member.isDel(),
                        false,
                        member.getRoleSet()
                                .stream()
                                .map(memberRole -> new SimpleGrantedAuthority("ROLE_" + memberRole.name()))
                                .collect(Collectors.toList())
                );


        return  memberSecurityDTO;
    }
}
