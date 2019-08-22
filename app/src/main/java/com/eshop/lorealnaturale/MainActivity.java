package com.eshop.lorealnaturale;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView HairDresser, Naturalista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HairDresser = findViewById(R.id.tvhairdresser);
        Naturalista = findViewById(R.id.tvnaturalista);

        HairDresser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(MainActivity.this, Hairdresser.class));
                        finish();
                    }
                }, 300);
            }
        });

        Naturalista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(MainActivity.this, Naturalista.class));
                        finish();
                    }
                }, 300);
            }
        });
    }
}
