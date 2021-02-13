package org.dreamsh19.board.service;

import lombok.RequiredArgsConstructor;
import org.dreamsh19.board.dto.BoardDTO;
import org.dreamsh19.board.dto.PageRequestDTO;
import org.dreamsh19.board.dto.PageResultDTO;
import org.dreamsh19.board.entity.Board;
import org.dreamsh19.board.entity.Member;
import org.dreamsh19.board.repository.BoardRepository;
import org.dreamsh19.board.repository.ReplyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    private final ReplyRepository replyRepository;

    @Override
    public Long register(BoardDTO dto) {

        Board board = dtoToEntity(dto); // 이때 board.bno == null
        boardRepository.save(board);
        // 이때 board.bno != null
        return board.getBno();
    }

    @Override
    public PageResultDTO<Object[], BoardDTO> getList(PageRequestDTO pageRequestDTO) {
        Page<Object[]> result = boardRepository.searchPage(
                pageRequestDTO.getType(),
                pageRequestDTO.getKeyword(),
                pageRequestDTO.getPageable(Sort.by("bno").descending()));

        Function<Object[], BoardDTO> fn = (row ->
                entityToDTO((Board) row[0], (Member) row[1], (Long) row[2]));

        return new PageResultDTO<>(result, fn);
    }

    @Override
    public BoardDTO getBoardDTO(Long bno) {

        Object result = boardRepository.getBoardByBnoWithReplyCount(bno);
        Object[] row = (Object[]) result;
        return entityToDTO((Board) row[0], (Member) row[1], (Long) row[2]);
    }

    @Transactional
    @Override
    public void deleteByBnoWithReplies(Long bno) {
        replyRepository.deleteByBno(bno);
        boardRepository.deleteById(bno);
    }

    @Transactional // getBno()가 지연로딩이기 때문에 필요. 혹은 @Transactional 없이 findById로 대체 가능
    @Override
    public void update(BoardDTO boardDTO) {
        Board board = boardRepository.getOne(boardDTO.getBno());
        if (board != null) {
            board.changeTitle(boardDTO.getTitle()); // @Transactional 없으면 여기서 no session LazyInitializationException 발생
            board.changeContent(boardDTO.getContent());
            boardRepository.save(board);
        }
    }


}
