package org.zerock.springboot_test_240521.service;

import org.zerock.springboot_test_240521.dto.PageRequestDTO;
import org.zerock.springboot_test_240521.dto.PageResponseDTO;
import org.zerock.springboot_test_240521.dto.ReplyDTO;

public interface ReplyService {

    Long register(ReplyDTO replyDTO);

    ReplyDTO read(Long rno);

    void modify(ReplyDTO replyDTO);

    void remove(Long rno);

    PageResponseDTO<ReplyDTO> getListOfBoard(Long bno, PageRequestDTO pageRequestDTO);

}
