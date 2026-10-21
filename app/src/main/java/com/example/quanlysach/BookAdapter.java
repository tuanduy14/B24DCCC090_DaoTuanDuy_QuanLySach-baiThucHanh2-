package com.example.quanlysach;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class BookAdapter extends ArrayAdapter<Book> {

    public BookAdapter(@NonNull Context context, @NonNull ArrayList<Book> books) {
        super(context, 0, books);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_book, parent, false);
        }

        Book book = getItem(position);

        TextView tvInitial = convertView.findViewById(R.id.tvItemInitial);
        TextView tvTitle = convertView.findViewById(R.id.tvItemTitle);
        TextView tvAuthor = convertView.findViewById(R.id.tvItemAuthor);
        TextView tvCategory = convertView.findViewById(R.id.tvItemCategory);
        TextView tvStatus = convertView.findViewById(R.id.tvItemStatus);
        RatingBar ratingBar = convertView.findViewById(R.id.ratingItem);

        if (book != null) {
            String title = book.getTitle();
            tvInitial.setText(title.isEmpty() ? "?" : title.substring(0, 1).toUpperCase());
            tvTitle.setText(title);
            tvAuthor.setText("Tác giả: " + book.getAuthor());
            tvCategory.setText("Thể loại: " + book.getCategory());
            tvStatus.setText(book.getStatus());
            ratingBar.setRating(book.getRating());

            // Màu chữ và màu nền của nhãn tình trạng
            int textColor;
            int bgColor;
            switch (book.getStatus()) {
                case Book.READ:
                    textColor = Color.parseColor("#2E7D32");
                    bgColor = Color.parseColor("#E8F5E9");
                    break;
                case Book.READING:
                    textColor = Color.parseColor("#E65100");
                    bgColor = Color.parseColor("#FFF3E0");
                    break;
                default:
                    textColor = Color.parseColor("#616161");
                    bgColor = Color.parseColor("#EEEEEE");
            }
            tvStatus.setTextColor(textColor);
            tvStatus.setBackgroundTintList(ColorStateList.valueOf(bgColor));
        }
        return convertView;
    }
}
