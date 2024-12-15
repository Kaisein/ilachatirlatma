package com.example.ilachatirlatmasi;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

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

        // 3 saniye bekledikten sonra ilacekle sayfasına geçiş
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(MainActivity.this, ilacekle.class);
            startActivity(intent);
            finish(); // MainActivity'yi kapatır
        }, 1000); // 2000 milisaniye (2 saniye)

        // Kenarlık ayarlarını uygula
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
