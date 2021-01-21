package org.dreamsh19.guestbook.service;

import org.dreamsh19.guestbook.dto.GuestbookDTO;
import org.dreamsh19.guestbook.dto.PageRequestDTO;
import org.dreamsh19.guestbook.dto.PageResultDTO;
import org.dreamsh19.guestbook.entity.Guestbook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GuestbookServiceTests {

    @Autowired
    private GuestbookService service;

    @Test
    public void testRegister() {

        GuestbookDTO guestbookDTO = GuestbookDTO.builder()
                .title("Sample Title...")
                .content("Sample Content...")
                .writer("user0")
                .build();

        System.out.println(service.register(guestbookDTO));
    }

    @Test
    public void testList() {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(1)
                .size(10)
                .build();

        PageResultDTO<GuestbookDTO, Guestbook> pageResultDTO = service.getList(pageRequestDTO);
        for (GuestbookDTO guestbookDTO : pageResultDTO.getDtoList()) {
            System.out.println(guestbookDTO);
        }
        System.out.println("=======================");
        pageResultDTO.getPageList().forEach(i -> System.out.println(i));
    }

    @Test
    public void testSearch() {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(1)
                .size(10)
                .type("tc")
                .keyword("9")
                .build();

        PageResultDTO<GuestbookDTO, Guestbook> resultDTO = service.getList(pageRequestDTO);
        System.out.println("TOTAL PAGES: " + resultDTO.getTotalPage());
        System.out.println("========================");
        for (GuestbookDTO guestbookDTO : resultDTO.getDtoList()) System.out.println(guestbookDTO);

        System.out.println("========================");
        resultDTO.getPageList().forEach(i -> System.out.println(i));
    }
}
