package com.DDN.login.dto.filter;

import com.DDN.login.model.categories.GenderCategory;

public class GenderCategoryDTO {
    private Integer id;
    private String type;

    public GenderCategoryDTO(GenderCategory genderCategory) {
        this.id = genderCategory.getId();
        this.type = genderCategory.getType();
    }

    public GenderCategoryDTO() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
