package org.dreamsh19.guestbook.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.dreamsh19.guestbook.dto.GuestbookDTO;
import org.dreamsh19.guestbook.dto.PageRequestDTO;
import org.dreamsh19.guestbook.dto.PageResultDTO;
import org.dreamsh19.guestbook.entity.Guestbook;
import org.dreamsh19.guestbook.entity.QGuestbook;
import org.dreamsh19.guestbook.repository.GuestbookRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor
public class GuestbookServiceImpl implements GuestbookService {

    private final GuestbookRepository repository;

    @Override
    public Long register(GuestbookDTO dto) {

        log.info("DTO------------------");
        log.info(dto);

        Guestbook entity = dtoToEntity(dto);

        log.info(entity);

        repository.save(entity);

        return entity.getGno();
    }

    @Override
    public PageResultDTO<GuestbookDTO, Guestbook> getList(PageRequestDTO pageRequestDTO) {
        Pageable pageable = pageRequestDTO.getPageable(Sort.by("gno").descending());

        BooleanBuilder booleanBuilder = getSearch(pageRequestDTO);
        Page<Guestbook> result = repository.findAll(booleanBuilder, pageable);

        Function<Guestbook, GuestbookDTO> fn = (entity ->
                entityToDto(entity));

        return new PageResultDTO<>(result, fn);

    }

    @Override
    public GuestbookDTO read(Long gno) {
        Optional<Guestbook> result = repository.findById(gno);

        return result.isPresent() ? entityToDto(result.get()) : null;
    }

    @Override
    public void modify(GuestbookDTO dto) {

        Optional<Guestbook> result = repository.findById(dto.getGno());
        if (result.isPresent()) {
            Guestbook entity = result.get();
            entity.changeTitle(dto.getTitle());
            entity.changeContent(dto.getContent());
            repository.save(entity);
        }
    }

    @Override
    public void remove(Long gno) {
        repository.deleteById(gno);
    }

    private BooleanBuilder getSearch(PageRequestDTO requestDTO) {
        String type = requestDTO.getType();

        QGuestbook qGuestbook = QGuestbook.guestbook;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        BooleanExpression expression = qGuestbook.gno.gt(0L);
        booleanBuilder.and(expression);

        if(type==null || type.trim().length()==0) return booleanBuilder;

        String keyword = requestDTO.getKeyword();
        BooleanBuilder conditionBuilder = new BooleanBuilder();
        if(type.contains("t")){
            conditionBuilder.or(qGuestbook.title.contains(keyword));
        }
        if(type.contains("c")){
            conditionBuilder.or(qGuestbook.content.contains(keyword));
        }
        if(type.contains("w")){
            conditionBuilder.or(qGuestbook.writer.contains(keyword));
        }
        booleanBuilder.and(conditionBuilder);
        return booleanBuilder;
    }

}
