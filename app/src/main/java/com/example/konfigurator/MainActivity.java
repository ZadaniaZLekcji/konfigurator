package com.example.konfigurator;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button btnSalon, btnKuchnia, btnSypialnia;
    ImageView imgSalon, imgKuchnia, imgSypialnia;
    RadioGroup radioGroup;
    ListView listView;
    final String SALON = "Salon";
    final String KUCHNIA = "Kuchnia";
    final String SYPIALNIA = "Sypialnia";
    String[] kolory={"Biały","Żółty","Niebieski"};
    int[] koloryTla={
            Color.WHITE,
            Color.YELLOW,
            Color.BLUE
    };
    int sKStan,kKStan,syKStan = 0;
    String tryb = "Dzienny";
    ArrayList<String>sceny=new ArrayList<>();
    ArrayAdapter<String>adapter;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        btnSalon = findViewById(R.id.btn_salon);
        btnKuchnia = findViewById(R.id.btn_kuchnia);
        btnSypialnia = findViewById(R.id.btn_sypialnia);
        imgSalon = findViewById(R.id.img_salon);
        imgKuchnia = findViewById(R.id.img_kuchnia);
        imgSypialnia = findViewById(R.id.img_sypialnia);
        radioGroup = findViewById(R.id.radioGroup);
        listView = findViewById(R.id.listView);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,sceny);
        listView.setAdapter(adapter);

        preferences = getSharedPreferences("Sceny",MODE_PRIVATE);
        String ostatniaScena = preferences.getString("ostatniaScena", "");
        if (!ostatniaScena.isEmpty()){
            adapter.insert(ostatniaScena,0);
            Toast.makeText(this,"Ostatnia scena: "+ ostatniaScena, Toast.LENGTH_LONG).show();
        }
        radioGroup.setOnCheckedChangeListener(
            (group, checkedId) -> {
                if (checkedId == R.id.rb_dzienny){
                    tryb = "Dzienny";
                    ustawTryb(0);
                } else if (checkedId==R.id.rb_wieczorny) {
                    tryb = "Wieczorny";
                    ustawTryb(1);
                } else if (checkedId == R.id.rb_nocny) {
                    tryb = "Nocny";
                    ustawTryb(2);
                }
            });
        aktualizujKolory();
    }
    public void zmianaKoloru(View view){
        Button kliknietyPrzycisk = (Button) view;
        int indeksKoloru;
        String nazwaPomieszczenia;
        if (view.getId()==R.id.btn_salon){
            sKStan=(sKStan+1)%3;
            indeksKoloru = sKStan;
            nazwaPomieszczenia = SALON;
            imgSalon.setBackgroundColor(koloryTla[indeksKoloru]);
        } else if(view.getId()==R.id.btn_kuchnia){
            kKStan=(kKStan+1)%3;
            indeksKoloru = kKStan;
            nazwaPomieszczenia = KUCHNIA;
            imgKuchnia.setBackgroundColor(koloryTla[indeksKoloru]);
        } else if(view.getId()==R.id.btn_sypialnia){
            syKStan=(syKStan+1)%3;
            indeksKoloru = syKStan;
            nazwaPomieszczenia = SYPIALNIA;
            imgSypialnia.setBackgroundColor(koloryTla[indeksKoloru]);
        } else {
            return;
        }
        kliknietyPrzycisk.setText(nazwaPomieszczenia + ": " + kolory[indeksKoloru]);
    }
    public void ustawTryb(int indeksKoloru){
        sKStan = indeksKoloru;
        kKStan = indeksKoloru;
        syKStan = indeksKoloru;
        aktualizujKolory();
    }
    public void aktualizujKolory(){
        btnSalon.setText(SALON + ": " + kolory[sKStan]);
        btnKuchnia.setText(KUCHNIA + ": " + kolory[kKStan]);
        btnSypialnia.setText(SYPIALNIA + ": " + kolory[syKStan]);
        imgSalon.setBackgroundColor(koloryTla[sKStan]);
        imgKuchnia.setBackgroundColor(koloryTla[kKStan]);
        imgSypialnia.setBackgroundColor(koloryTla[syKStan]);
    }
    public void zapiszScene(View view){
        String opis = "["+tryb+"] "+ "S:"+kolory[sKStan]+", K:"+kolory[kKStan]+", Sy:"+kolory[syKStan];
        adapter.insert(opis, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("ostatniaScena", opis);
        editor.apply();
        Toast.makeText(this, "Zapisano scenę",Toast.LENGTH_SHORT).show();
    }
}