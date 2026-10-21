package com.example.quanlysach;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final ArrayList<Book> books = new ArrayList<>();
    private BookAdapter adapter;
    private TextView tvStats;
    private ListView lvBooks;
    private Button btnAddBook;

    // Nhận sách mới từ AddBookActivity
    private final ActivityResultLauncher<Intent> addLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    @SuppressWarnings("deprecation")
                    Book newBook = (Book) result.getData().getSerializableExtra("book");
                    if (newBook != null) {
                        books.add(newBook);
                        refresh();
                    }
                }
            });

    // Nhận kết quả (cập nhật / xóa) từ BookDetailActivity
    private final ActivityResultLauncher<Intent> detailLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                Intent data = result.getData();
                if (result.getResultCode() != RESULT_OK || data == null) return;

                int position = data.getIntExtra("position", -1);
                if (position < 0 || position >= books.size()) return;

                if (data.getBooleanExtra("deleted", false)) {
                    books.remove(position);
                } else {
                    @SuppressWarnings("deprecation")
                    Book updated = (Book) data.getSerializableExtra("book");
                    if (updated != null) books.set(position, updated);
                }
                refresh();
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvStats = findViewById(R.id.tvStats);
        lvBooks = findViewById(R.id.lvBooks);
        btnAddBook = findViewById(R.id.btnAddBook);

        // Dữ liệu mẫu ban đầu
        books.add(new Book("Lập trình Android cơ bản", "Nguyễn Văn A", "Công nghệ", Book.READING, 4, ""));
        books.add(new Book("Đắc nhân tâm", "Dale Carnegie", "Kỹ năng sống", Book.READ, 5, ""));
        books.add(new Book("Dế Mèn phiêu lưu ký", "Tô Hoài", "Văn học", Book.UNREAD, 0, ""));

        adapter = new BookAdapter(this, books);
        lvBooks.setAdapter(adapter);
        updateStats();

        btnAddBook.setOnClickListener(v ->
                addLauncher.launch(new Intent(this, AddBookActivity.class)));

        lvBooks.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(this, BookDetailActivity.class);
            intent.putExtra("book", books.get(position));
            intent.putExtra("position", position);
            detailLauncher.launch(intent);
        });
    }

    private void refresh() {
        adapter.notifyDataSetChanged();
        updateStats();
    }

    private void updateStats() {
        int total = books.size();
        int read = 0;
        for (Book b : books) {
            if (Book.READ.equals(b.getStatus())) read++;
        }
        int unfinished = total - read; // Chưa đọc + Đang đọc
        tvStats.setText("Tổng số sách: " + total
                + "\nĐã đọc: " + read
                + "\nChưa đọc xong: " + unfinished);
    }
}
