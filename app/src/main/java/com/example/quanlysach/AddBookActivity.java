package com.example.quanlysach;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddBookActivity extends AppCompatActivity {

    private EditText edtTitle, edtAuthor, edtNote;
    private Spinner spCategory;
    private RadioGroup rgStatus;
    private RatingBar ratingBar;
    private TextView tvRatingValue;
    private Button btnSave, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        edtTitle = findViewById(R.id.edtTitle);
        edtAuthor = findViewById(R.id.edtAuthor);
        edtNote = findViewById(R.id.edtNote);
        spCategory = findViewById(R.id.spCategory);
        rgStatus = findViewById(R.id.rgStatus);
        ratingBar = findViewById(R.id.ratingBar);
        tvRatingValue = findViewById(R.id.tvRatingValue);
        btnSave = findViewById(R.id.btnSave);
        btnBack = findViewById(R.id.btnBack);

        String[] categories = {"Văn học", "Khoa học", "Công nghệ", "Kỹ năng sống", "Khác"};
        ArrayAdapter<String> spAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, categories);
        spCategory.setAdapter(spAdapter);

        ratingBar.setOnRatingBarChangeListener((bar, rating, fromUser) ->
                tvRatingValue.setText((int) rating + " sao"));

        btnSave.setOnClickListener(v -> saveBook());
        btnBack.setOnClickListener(v -> finish());
    }

    private void saveBook() {
        String title = edtTitle.getText().toString().trim();
        String author = edtAuthor.getText().toString().trim();

        if (title.isEmpty()) {
            edtTitle.setError("Vui lòng nhập tên sách");
            edtTitle.requestFocus();
            return;
        }
        if (author.isEmpty()) {
            edtAuthor.setError("Vui lòng nhập tác giả");
            edtAuthor.requestFocus();
            return;
        }

        String category = spCategory.getSelectedItem().toString();

        String status;
        int checkedId = rgStatus.getCheckedRadioButtonId();
        if (checkedId == R.id.rbReading) {
            status = Book.READING;
        } else if (checkedId == R.id.rbRead) {
            status = Book.READ;
        } else {
            status = Book.UNREAD;
        }

        float rating = ratingBar.getRating();
        String note = edtNote.getText().toString().trim();

        Book book = new Book(title, author, category, status, rating, note);

        Intent data = new Intent();
        data.putExtra("book", book);
        setResult(RESULT_OK, data);
        Toast.makeText(this, "Đã thêm sách", Toast.LENGTH_SHORT).show();
        finish();
    }
}