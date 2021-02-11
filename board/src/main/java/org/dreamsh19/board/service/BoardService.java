package org.dreamsh19.board.service;

import org.dreamsh19.board.dto.BoardDTO;
import org.dreamsh19.board.entity.Board;
import org.dreamsh19.board.entity.Member;

public interface BoardService {

    Long register(BoardDTO dto);

    default Board dtoToEntity(BoardDTO dto) {
        Member writer = Member.builder()
                .email(dto.getWriterEmail())
                .build();

        Board board = Board.builder()
                .bno(dto.getBno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(writer)
                .build();
        return board;
    }
}
