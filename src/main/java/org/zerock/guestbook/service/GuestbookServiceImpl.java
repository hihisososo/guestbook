package org.zerock.guestbook.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.zerock.guestbook.dto.GuestbookDTO;
import org.zerock.guestbook.dto.PageRequestDTO;
import org.zerock.guestbook.dto.PageResultDTO;
import org.zerock.guestbook.entity.Guestbook;
import org.zerock.guestbook.entity.QGuestbook;
import org.zerock.guestbook.repository.GuestbookRepository;

import java.util.Optional;
import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor
public class GuestbookServiceImpl implements GuestbookService {

    private final GuestbookRepository repository;

    @Override
    public Long register(GuestbookDTO dto) {

        log.info("DTO------------------------");
        log.info(dto);

        Guestbook entity = dtoToEntity(dto);

        log.info(entity);

        repository.save(entity);

        return entity.getGno();
    }

    @Override
    public PageResultDTO<GuestbookDTO, Guestbook> getList(PageRequestDTO requestDTO) {

        //정렬은 gno 내림차순이고, PageRequestDTO 내부의 페이지 번호와 출력 갯수를 통해 Pageable 객체를 생성한다
        Pageable pageable = requestDTO.getPageable(Sort.by("gno").descending());

        //requestDTO 의 파라미터와 QueryDSL 을 통해, 검색 조건을 생성한다
        BooleanBuilder booleanBuilder = getSearch(requestDTO);

        //repository 에처 해당 조건 및 페이징 정보를 통해 결과를 가져온다
        Page<Guestbook> result = repository.findAll(booleanBuilder, pageable);

        //entity 를 DTO 로 매칭시키는 함수를 정의
        Function<Guestbook, GuestbookDTO> fn = (entity -> entityToDto(entity));

        //페이징 결과와 함께 리턴
        return new PageResultDTO<>(result, fn );
    }

    @Override
    public GuestbookDTO read(Long gno) {

        Optional<Guestbook> result = repository.findById(gno);

        return result.isPresent()? entityToDto(result.get()): null;
    }

    @Override
    public void remove(Long gno) {

        repository.deleteById(gno);

    }

    @Override
    public void modify(GuestbookDTO dto) {

        //먼저 해당 id 가 존재하는지 확인
        Optional<Guestbook> result = repository.findById(dto.getGno());

        //수정할 목록이 있을 경우 수정한다
        if(result.isPresent()){

            Guestbook entity = result.get();

            entity.changeTitle(dto.getTitle());
            entity.changeContent(dto.getContent());

            repository.save(entity);

        }
    }

    private BooleanBuilder getSearch(PageRequestDTO requestDTO){
        //페이지에서 요청한 검색 타입을 가져온다
        String type = requestDTO.getType();

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        //QueryDSL 사용을 위한 qGuestbook 객체를 가져온다
        QGuestbook qGuestbook = QGuestbook.guestbook;

        String keyword = requestDTO.getKeyword();

        BooleanExpression expression = qGuestbook.gno.gt(0L); // gno > 0 조건만 생성

        booleanBuilder.and(expression);

        if(type == null || type.trim().length() == 0){ //검색 조건이 없는 경우
            return booleanBuilder;
        }


        //검색 조건을 작성
        BooleanBuilder conditionBuilder = new BooleanBuilder();

        //제목
        if(type.contains("t")){
            conditionBuilder.or(qGuestbook.title.contains(keyword));
        }
        //본문
        if(type.contains("c")){
            conditionBuilder.or(qGuestbook.content.contains(keyword));
        }
        //작성자
        if(type.contains("w")){
            conditionBuilder.or(qGuestbook.writer.contains(keyword));
        }

        //모든 조건 통합
        booleanBuilder.and(conditionBuilder);

        return booleanBuilder;
    }
}
