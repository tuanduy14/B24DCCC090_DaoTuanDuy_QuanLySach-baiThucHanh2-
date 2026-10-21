package com.example.quanlysach;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class BookDetailActivity extends AppCompatActivity {

    private Book book;
    private int position;

    private TextView tvTitle, tvAuthorCategory, tvStatus, tvNote;
    private RatingBar ratingBar;
    private RadioGroup rgDetailStatus;
    private Button btnBack, btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        tvTitle = findViewById(R.id.tvDetailTitle);
        tvAuthorCategory = findViewById(R.id.tvDetailAuthorCategory);
        tvStatus = findViewById(R.id.tvDetailStatus);
        tvNote = findViewById(R.id.tvDetailNote);
        ratingBar = findViewById(R.id.ratingDetail);
        rgDetailStatus = findViewById(R.id.rgDetailStatus);
        btnBack = findViewById(R.id.btnBack);
        btnDelete = findViewById(R.id.btnDelete);

        // Nhận dữ liệu qua Intent
        Intent intent = getIntent();
        @SuppressWarnings("deprecation")
        Book received = (Book) intent.getSerializableExtra("book");
        position = intent.getIntExtra("position", -1);

        if (received == null || position < 0) {
            finish();
            return;
        }
        book = received;

        showBook();
        sendResult(); // để nút Back của hệ thống cũng trả về dữ liệu

        ratingBar.setOnRatingBarChangeListener((bar, rating, fromUser) -> {
            if (fromUser) {
                book.setRating(rating);
                sendResult();
            }
        });

        // Chọn sẵn trạng thái hiện tại (3 lựa chọn luôn hiển thị)
        checkCurrentStatus();

        rgDetailStatus.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rbDetailReading) {
                book.setStatus(Book.READING);
            } else if (checkedId == R.id.rbDetailRead) {
                book.setStatus(Book.READ);
            } else {
                book.setStatus(Book.UNREAD);
            }
            tvStatus.setText("Tình trạng: " + book.getStatus());
            sendResult();
        });

        btnBack.setOnClickListener(v -> finish());

        btnDelete.setOnClickListener(v -> {
            Intent data = new Intent();
            data.putExtra("position", position);
            data.putExtra("deleted", true);
            setResult(RESULT_OK, data);
            finish();
        });
    }

    private void showBook() {
        tvTitle.setText(book.getTitle());
        tvAuthorCategory.setText(book.getAuthor() + " • " + book.getCategory());
        tvStatus.setText("Tình trạng: " + book.getStatus());
        ratingBar.setRating(book.getRating());
        tvNote.setText(book.getNote().isEmpty() ? "(Không có ghi chú)" : book.getNote());
    }

    private void checkCurrentStatus() {
        if (Book.READING.equals(book.getStatus())) {
            rgDetailStatus.check(R.id.rbDetailReading);
        } else if (Book.READ.equals(book.getStatus())) {
            rgDetailStatus.check(R.id.rbDetailRead);
        } else {
            rgDetailStatus.check(R.id.rbDetailUnread);
        }
    }

    // Trả sách (đã cập nhật) về MainActivity
    private void sendResult() {
        Intent data = new Intent();
        data.putExtra("book", book);
        data.putExtra("position", position);
        data.putExtra("deleted", false);
        setResult(RESULT_OK, data);
    }
}