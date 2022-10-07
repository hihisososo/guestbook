package org.zerock.guestbook.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Optional;

@Builder
@AllArgsConstructor
@Data
public class PageRequestDTO {

    private int page;
    private int size;
    private String type;
    private String keyword;
    private String sort;


    public PageRequestDTO(){
        this.page = 1;
        this.size = 10;
    }

    public Pageable getPageable(){
        //Pageable : 페이징 처리를 쉽게 하기 위해서 JPA 에서 지원해주는 인터페이스 
        if(Optional.ofNullable(sort).isPresent()){
            return PageRequest.of(page -1, size, Sort.by(sort).descending());
        }
        return PageRequest.of(page -1, size, Sort.by("gno").descending());

    }
}
