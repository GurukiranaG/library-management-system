package com.library.library_management_system.dto;

public class BookResponseDTO {
    private Long id;
    private String title;
    private String author;
    private String isbn;
    private String category;
    //private Boolean available;

    public BookResponseDTO() {
    }

    public BookResponseDTO(Long id, String title, String author,
                           String isbn, String category) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

//    public Boolean getAvailable() {
//        return available;
//    }
//
//    public void setAvailable(Boolean available) {
//        this.available = available;
//    }
}
