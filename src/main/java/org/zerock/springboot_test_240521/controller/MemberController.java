package org.zerock.springboot_test_240521.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.springboot_test_240521.dto.BoardDTO;
import org.zerock.springboot_test_240521.dto.MemberJoinDTO;
import org.zerock.springboot_test_240521.dto.PageRequestDTO;
import org.zerock.springboot_test_240521.repository.MemberRepository;
import org.zerock.springboot_test_240521.service.MemberService;

import java.security.Principal;
import java.util.List;


@Controller
@RequestMapping("/member")
@Log4j2
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @GetMapping("/login")
    public void loginGET(String error, String logout) {
        log.info("--------------- login GET ---------------");
        log.info("--------------- error : " + error + "---------------");
        if (logout != null){
            log.info("user logout --------------");
        }
    }

    @GetMapping("/join")
    public void joinGET(){
        log.info("join get ---------------------");
    }

    @PostMapping("/join")
    public String joinPOST(MemberJoinDTO memberJoinDTO, RedirectAttributes redirectAttributes) {
        log.info("join post --------------------");
        log.info(memberJoinDTO);

        try {
            // 회원가입 서비스 실행
            memberService.join(memberJoinDTO);
            // 기존에 아이디가 존재할 경우 에러 발생
        } catch (MemberService.MIdExistException e) {
            // 에러 발생시 redirect 페이지에 error:mid 값을 가지고 ->
            redirectAttributes.addFlashAttribute("error", "mid");
            // -> member join 페이지로 이동
            return "redirect:/member/join";
        }
        redirectAttributes.addFlashAttribute("result", "success");
        return "redirect:/member/login";
    }

    @PostMapping("/confirm")
    public String confirmPOST(MemberJoinDTO memberJoinDTO, RedirectAttributes redirectAttributes) {
        log.info("confirm post -------------------");
        try {
            memberService.confirmMid(memberJoinDTO);
            // 기존에 아이디가 존재할 경우 에러 발생
        } catch (MemberService.MIdExistException e) {
            // 에러 발생시 redirect 페이지에 error:mid 값을 가지고 ->
            redirectAttributes.addFlashAttribute("error", "mid");
            // -> member join 페이지로 이동
            return "redirect:/member/join";
        }
        redirectAttributes.addFlashAttribute("result", "success");
        redirectAttributes.addFlashAttribute("mid", memberJoinDTO.getMid());
        return "redirect:/member/join";
    }

    @GetMapping( {"modify", "myPage"})
    public void modify(Principal principal, PageRequestDTO pageRequestDTO, Model model) {
        MemberJoinDTO memberJoinDTO = memberService.myPage(principal.getName());
        model.addAttribute("memberDTO", memberJoinDTO);
    }

    @PostMapping("/modify")
    public String modify( PageRequestDTO pageRequestDTO, MemberJoinDTO memberJoinDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()){
            log.info("has error----------------");
            String link = pageRequestDTO.getLink();
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            redirectAttributes.addAttribute("mid", memberJoinDTO.getMid());
            return "redirect:/member/modify?" + link;
        }

        memberService.modify(memberJoinDTO);
        redirectAttributes.addFlashAttribute("result", "modified");
        redirectAttributes.addAttribute("mid", memberJoinDTO.getMid());
        return "redirect:/member/myPage";
    }

//    @PreAuthorize("principal.username == #memberJoinDTO.mid")
    @PostMapping("/remove")
    public String remove(MemberJoinDTO memberJoinDTO, RedirectAttributes redirectAttributes) {
        String mid = memberJoinDTO.getMid();
        log.info("remove post ----- " + mid);

        memberService.remove(mid);

        redirectAttributes.addFlashAttribute("result", "removed");
        return "redirect:/board/list";
    }

}

