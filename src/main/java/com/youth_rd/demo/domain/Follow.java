package com.youth_rd.demo.domain;

import java.io.Serializable;

public class Follow implements Serializable {
    private Integer id;

    private Integer focusId;

    private Integer focusedId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFocusId() {
        return focusId;
    }

    public void setFocusId(Integer focusId) {
        this.focusId = focusId;
    }

    public Integer getFocusedId() {
        return focusedId;
    }

    public void setFocusedId(Integer focusedId) {
        this.focusedId = focusedId;
    }
}
