package com.team32.ong.dto;

import com.team32.ong.model.Category;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
public class ListCategoryNameDTO {
    List<String> categoriesNames = new ArrayList<>();
}
