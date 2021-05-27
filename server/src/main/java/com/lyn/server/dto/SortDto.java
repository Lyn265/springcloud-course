package com.lyn.server.dto;

public class SortDto {

    private String id;

    private Integer oldSort;

    private Integer newSort;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getOldSort() {
        return oldSort;
    }

    public void setOldSort(Integer oldSort) {
        this.oldSort = oldSort;
    }

    public Integer getNewSort() {
        return newSort;
    }

    public void setNewSort(Integer newSort) {
        this.newSort = newSort;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("SortDto{");
        sb.append("id='").append(id).append('\'');
        sb.append(", oldSort='").append(oldSort).append('\'');
        sb.append(", newSort='").append(newSort).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
