package com.example.why;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;
import com.example.why.SettingsActivity;

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

        setUplistViewListener();

        booksAdapter = new ArrayAdapter<String>(this, R.layout.list_item, R.id.textView, books) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                Button ratingButton = view.findViewById(R.id.rateButton);
                ratingButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showRatingDialog(books.get(position));
                    }
                });

                Button deleteButton = view.findViewById(R.id.deleteButton);
                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Delete the book from the database
                        Cursor cursor = myDB.readAllData();
                        cursor.moveToPosition(position);
                        int id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
                        myDB.deleteBook(id);

                        // Remove the book from the list and notify the adapter
                        books.remove(position);
                        booksAdapter.notifyDataSetChanged();
                    }
                });

                return view;
            }
        };

        listView.setAdapter(booksAdapter);


    //listView.setAdapter(booksAdapter);
        // setUplistViewListener();

        displayData();




    }

    private void showRatingDialog(String bookTitle) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Rate " + bookTitle);

        // Add a rating bar to the dialog
        RatingBar ratingBar = new RatingBar(this);
        ratingBar.setNumStars(5);
        //ratingBar.setStepSize(1);
        ratingBar.setProgressTintList(ColorStateList.valueOf(Color.YELLOW));
        builder.setView(ratingBar);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Save the rating to your database or wherever you store your book ratings
                // You can also update the rating display in your list item layout here
                float rating = ratingBar.getRating();
                Toast.makeText(main_page.this, "Rating saved: " + rating, Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel", null);

        builder.create().show();
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


