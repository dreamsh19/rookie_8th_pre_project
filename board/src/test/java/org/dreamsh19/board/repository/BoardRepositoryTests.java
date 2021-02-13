package org.dreamsh19.board.repository;

import org.dreamsh19.board.entity.Board;
import org.dreamsh19.board.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class BoardRepositoryTests {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void insertBoards() {

        IntStream.rangeClosed(1, 100).forEach(i -> {
            Member member = Member.builder().email("user" + i + "@test.io").build();

            Board board = Board.builder()
                    .title("Title..." + i)
                    .content("Content..." + i)
                    .writer(member) // Member Table에 없는 member 추가 시 Error
                    .build();

            boardRepository.save(board);
        });

    }

    @Transactional
    @Test
    public void testRead1() {

        Optional<Board> result = boardRepository.findById(1L);

        Board board = result.get();
        System.out.println(board);
        System.out.println(board.getWriter());
    }

    @Test
    public void testReadWriterWithJPQL() {
        Object result = boardRepository.getBoardWithWriter(1L);
        Object[] arr = (Object[]) result;
        System.out.println(Arrays.toString(arr));
    }

    @Test
    public void testReadReplyWithJPQL() {

        List<Object[]> result = boardRepository.getBoardWithReply(1L);
        for (Object[] arr : result) {
            System.out.println(Arrays.toString(arr));
        }
    }

    @Test
    public void testBoardListWithReplyCount() {

        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());
        Page<Object[]> result = boardRepository.getBoardListWithReplyCount(pageable);
        result.get().forEach(board -> {
            System.out.println(Arrays.toString(board));
        });

    }

    @Test
    public void testBoardByBnoWithReplyCount() {
        Object result = boardRepository.getBoardByBnoWithReplyCount(100L);
        Object[] arr = (Object[]) result;
        System.out.println(Arrays.toString(arr));
    }

    @Test
    public void testSearchPage() {
        Pageable pageable = PageRequest.of(0, 10,
                Sort.by("bno").descending().and(Sort.by("title").ascending()));
        boardRepository.searchPage("", "", pageable);
    }
}
