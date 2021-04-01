package io.corecode.mywebsite.model;


import java.util.List;

public class Book {


    private Integer bookId;

    private String title;

    private Integer writerId;

    private Integer publisherId;

    private String description;

    private Publisher publisher;

    private Writer writer;

    private List<Review> reviews;

    private String cover;

    private String picture;



    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public Integer getWriterId() {
        return writerId;
    }

    public void setWriterId(Integer writerId) {
        this.writerId = writerId;
    }

    public Integer getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(Integer publisherId) {
        this.publisherId = publisherId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public Writer getWriter() {
        return writer;
    }

    public void setWriter(Writer writer) {
        this.writer = writer;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                ", title='" + title + '\'' +
                ", writerId=" + writerId +
                ", publisherId=" + publisherId +
                ", description='" + description + '\'' +
                ", publisher=" + publisher +
                ", writer=" + writer +
                ", reviews=" + reviews +
                ", cover='" + cover + '\'' +
                ", picture='" + picture + '\'' +
                '}';
    }
}


