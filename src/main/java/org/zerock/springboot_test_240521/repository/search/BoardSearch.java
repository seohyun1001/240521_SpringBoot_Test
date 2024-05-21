package org.zerock.springboot_test_240521.repository.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.zerock.springboot_test_240521.domain.Board;
import org.zerock.springboot_test_240521.dto.BoardListAllDTO;
import org.zerock.springboot_test_240521.dto.BoardListReplyCountDTO;

public interface BoardSearch {
    Page<Board> search1(Pageable pageable);

    Page<Board> searchAll(String[] types, String keyword, Pageable pageable);

    // 댓글 표시해서 목록 출력
    Page<BoardListReplyCountDTO> searchWithReplyCount(String[] types, String keyword, Pageable pageable);

    // N + 1 문제
    Page<BoardListAllDTO> searchWithAll(String[] types, String keyword, Pageable pageable);
}
