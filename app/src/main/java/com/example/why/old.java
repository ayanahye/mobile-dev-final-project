package com.example.why;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class old extends AppCompatActivity {

    private String[] bookTitles = {
            "The Great Gatsby",
            "To Kill a Mockingbird",
            "1984",
            "Brave New World",
            "The Catcher in the Rye",
            "Lord of the Flies",
            "Animal Farm",
            "Pride and Prejudice",
            "Jane Eyre",
            "Wuthering Heights",
            "The Picture of Dorian Gray",
            "Dracula",
            "Frankenstein",
            "The Adventures of Tom Sawyer",
            "The Adventures of Huckleberry Finn",
            "Alice's Adventures in Wonderland",
            "Through the Looking-Glass",
            "The War of the Worlds",
            "The Time Machine",
            "The Invisible Man",
            "The Hitchhiker's Guide to the Galaxy",
            "The Lord of the Rings",
            "The Hobbit",
            "The Silmarillion",
            "A Song of Ice and Fire",
            "The Name of the Wind",
            "The Wise Man's Fear",
            "The Way of Kings",
            "Words of Radiance"
    };

    // Define a method to generate a random book title
    public String generateBookTitle() {
        Random random = new Random();
        int index = random.nextInt(bookTitles.length);
        return bookTitles[index];
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_old);

        Button next = findViewById(R.id.nextButton);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(old.this, main_page.class);
                startActivity(intent);
            }
        });

        Button generateButton = findViewById(R.id.generateButton);
        generateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Generate a random book title
                String bookTitle = generateBookTitle();

                // Display the book title in the text view
                TextView bookTitleTextView = findViewById(R.id.bookTitleTextView);
                bookTitleTextView.setText(bookTitle);
            }
        });
    }
}