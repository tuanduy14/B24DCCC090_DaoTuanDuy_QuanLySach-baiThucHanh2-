package com.example.quanlysach;

import java.io.Serializable;

public class Book implements Serializable {
    public static final String UNREAD = "Chưa đọc";
    public static final String READING = "Đang đọc";
    public static final String READ = "Đã đọc";

    private String title;
    private String author;
    private String category;
    private String status;
    private float rating;
    private String note;

    public Book(String title, String author, String category,
                String status, float rating, String note) {
        this.title = title;
        this.author = author;
        this.category = category;
        this.status = status;
        this.rating = rating;
        this.note = note;
    }

    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getCategory() { return category; }
    public String getStatus() { return status; }
    public float getRating() { return rating; }
    public String getNote() { return note; }

    public void setStatus(String status) { this.status = status; }
    public void setRating(float rating) { this.rating = rating; }
}
