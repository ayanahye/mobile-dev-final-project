package com.example.why;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class main_page extends AppCompatActivity {

    private ArrayList<String> books;
    private ArrayAdapter<String> booksAdapter;
    private Button button;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        listView = findViewById(R.id.bookListView);
        button = findViewById(R.id.addBookButton);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addBook(view);
            }
        });

        Button prev = findViewById(R.id.prevButton);
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(main_page.this, old.class);
                startActivity(intent);
            }
        });

        books = new ArrayList<>();
        booksAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, books);
        listView.setAdapter(booksAdapter);
        setUplistViewListener();

    }

    private void setUplistViewListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Context context = getApplicationContext();
                Toast.makeText(context, "Item removed", Toast.LENGTH_LONG).show();

                books.remove(i);
                booksAdapter.notifyDataSetChanged();
                return;
            }
        });
    }

    private void addBook(View view) {
        EditText input = findViewById(R.id.addBookEditText);
        String bookText = input.getText().toString();

        if(!(bookText.equals(""))) {
            booksAdapter.add(bookText);
            input.setText("");
        } else {
            Toast.makeText(getApplicationContext(), "Please enter text", Toast.LENGTH_LONG).show();
        }
    }

    public void deleteAll(View view) {
        books.clear();
        booksAdapter.notifyDataSetChanged();

    }
}