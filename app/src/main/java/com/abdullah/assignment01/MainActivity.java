package com.abdullah.assignment01;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    ImageButton menuButton;
    ImageButton menuButton2;
    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        menuButton = findViewById(R.id.menuButton);
        menuButton2 = findViewById(R.id.menuButton2);

        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu obj = new PopupMenu(MainActivity.this, menuButton);
                obj.getMenuInflater().inflate(R.menu.menu_items, obj.getMenu());
                obj.show();
            }
        });

        menuButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu obj = new PopupMenu(MainActivity.this, menuButton2);
                obj.getMenuInflater().inflate(R.menu.menu_items, obj.getMenu());
                obj.show();
            }
        });

    }
}