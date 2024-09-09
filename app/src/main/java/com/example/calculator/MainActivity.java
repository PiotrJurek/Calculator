package com.example.calculator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    public void simpleButtonOnClick(View view) {
        startActivity(new Intent(MainActivity.this, SimpleActivity.class));
    }

    public void advancedButtonOnClick(View view) {
        startActivity(new Intent(MainActivity.this, AdvancedActivity.class));
    }

    public void aboutButtonOnClick(View view) {
        startActivity(new Intent(MainActivity.this, AboutActivity.class));
    }

    public void exitButtonOnClick(View view) {
        finish();
        System.exit(0);
    }
}