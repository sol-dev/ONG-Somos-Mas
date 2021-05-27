package com.team32.ong.component;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.team32.ong.constant.ConstantsPagination;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class PaginationComponent {

    public String changePaginationResponse(Page<?> page){
        Gson gson = new Gson();
        JsonObject json = new JsonObject();

        json.add(ConstantsPagination.CONTENT, gson.toJsonTree(page.getContent()));
        json.addProperty(ConstantsPagination.TOTAL_PAGES, page.getTotalPages());
        json.addProperty(ConstantsPagination.TOTAL_ELEMENTS, page.getTotalElements());
        json.addProperty(ConstantsPagination.NUMBER_OF_ELEMENTS, page.getNumberOfElements());
        json.addProperty(ConstantsPagination.SIZE, page.getSize());
        json.addProperty(ConstantsPagination.PAGE_NUMBER, page.getNumber());
        json.addProperty(ConstantsPagination.FIRST_PAGE, page.isFirst());
        json.addProperty(ConstantsPagination.LAST_PAGE, page.isLast());

        return gson.toJson(json);
    }
}
