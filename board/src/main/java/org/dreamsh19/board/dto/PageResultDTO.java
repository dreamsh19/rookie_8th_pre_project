package org.dreamsh19.board.dto;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Data
public class PageResultDTO<EN, DTO> {

    List<DTO> dtoList;

    int size;

    int page;

    boolean next;

    int start, end;

    public PageResultDTO(Page<EN> result, Function<EN, DTO> entityToDTO) {
        dtoList = result.get().map(entityToDTO).collect(Collectors.toList());
        int totalPages = result.getTotalPages();
        size = result.getSize();
        Pageable pageable = result.getPageable();
        page = pageable.getPageNumber() + 1;
        start = ((page - 1) / 10) * 10 + 1;
        end = Math.min(start + 9, totalPages);
        next = totalPages > end;

    }

}
