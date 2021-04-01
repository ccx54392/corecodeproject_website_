package io.corecode.mywebsite.model;

import java.util.List;

public class Writer {

    private Integer writerId;

    private String name;

    private List<Book> books;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getWriterId() {
        return writerId;
    }

    public void setWriterId(Integer writerId) {
        this.writerId = writerId;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "Writer{" +
                "writerId=" + writerId +
                ", name='" + name + '\'' +
                ", books=" + books +
                '}';
    }
}


