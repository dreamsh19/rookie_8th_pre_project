package org.dreamsh19.board.service;

import org.dreamsh19.board.dto.BoardDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BoardServiceTests {

    @Autowired
    private BoardService boardService;

    @Test
    public void registerTest(){
        BoardDTO dto = BoardDTO.builder()
                .title("test title")
                .content("test content")
                .writerEmail("user1@test.io")
                .build();
        Long bno = boardService.register(dto);
    }
}
