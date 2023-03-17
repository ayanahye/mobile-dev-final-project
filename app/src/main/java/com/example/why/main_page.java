package com.example.why;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
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

    MyDatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        listView = findViewById(R.id.bookListView);
        button = findViewById(R.id.addBookButton);


        EditText title_input = findViewById(R.id.addBookEditText);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(main_page.this);
                myDB.addBook(title_input.getText().toString().trim());
                //addBook(view);
                displayData();

            }
        });

        myDB = new MyDatabaseHelper(main_page.this);
        books = new ArrayList<>();


        Button prev = findViewById(R.id.prevButton);
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(main_page.this, old.class);
                startActivity(intent);
            }
        });

        booksAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, books);
        listView.setAdapter(booksAdapter);
        setUplistViewListener();

        displayData();


    }

    private void setUplistViewListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Context context = getApplicationContext();
                Toast.makeText(context, "Item removed", Toast.LENGTH_LONG).show();

                // Remove the book from the database
                String title = books.get(i);
                Cursor cursor = myDB.readAllData();
                cursor.moveToPosition(i);
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
                myDB.deleteBook(id);

                books.remove(i);

                booksAdapter.notifyDataSetChanged();
            }
        });
    }

    public void deleteAll(View view) {
        myDB.deleteAllBooks();
        books.clear();
        booksAdapter.notifyDataSetChanged();
    }



    void displayData() {
        Cursor cursor = myDB.readAllData();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "no data", Toast.LENGTH_SHORT).show();
        } else {
            books.clear();
            while (cursor.moveToNext()) {
                String bookTitle = cursor.getString(1);
                books.add(bookTitle);
            }
            booksAdapter.notifyDataSetChanged(); // notify the adapter about the changes
        }
    }

}

