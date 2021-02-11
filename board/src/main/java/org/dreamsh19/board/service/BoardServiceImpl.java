package org.dreamsh19.board.service;

import lombok.RequiredArgsConstructor;
import org.dreamsh19.board.dto.BoardDTO;
import org.dreamsh19.board.dto.PageRequestDTO;
import org.dreamsh19.board.dto.PageResultDTO;
import org.dreamsh19.board.entity.Board;
import org.dreamsh19.board.entity.Member;
import org.dreamsh19.board.repository.BoardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    @Override
    public Long register(BoardDTO dto) {

        Board board = dtoToEntity(dto); // 이때 board.bno == null
        boardRepository.save(board);
        // 이때 board.bno != null
        return board.getBno();
    }

    @Override
    public PageResultDTO<Object[], BoardDTO> getList(PageRequestDTO pageRequestDTO) {
        Pageable pageable = pageRequestDTO.getPageable(Sort.by("bno").descending());
        Page<Object[]> result = boardRepository.getBoardListWithReplyCount(pageable);
        Function<Object[], BoardDTO> fn = (row ->
                entityToDTO((Board) row[0], (Member) row[1], (Long) row[2]));

        return new PageResultDTO<>(result, fn);
    }


}
