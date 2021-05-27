package com.team32.ong.component;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class Pagination {

    public String changePaginationResponse(Page<Object> page){
        Gson gson = new Gson();
        JsonObject json = new JsonObject();

        json.add("Content", gson.toJsonTree(page.getContent()));
        json.addProperty("TotalPages", page.getTotalPages());
        json.addProperty("NumberOfElements", page.getNumberOfElements());
        json.addProperty("Size", page.getSize());
        json.addProperty("FirstPage", page.isFirst());
        json.addProperty("LastPage", page.isLast());

        return gson.toJson(json);
    }
}
