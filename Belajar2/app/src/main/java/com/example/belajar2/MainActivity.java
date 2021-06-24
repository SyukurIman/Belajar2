package com.example.belajar2;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.belajar2.Kalkulator.Kalkulator;
import com.example.belajar2.MenuMain2.Main2Activity;
import com.example.belajar2.MenuMain3.Main3Activity;
import com.example.belajar2.Quote.InspirasiActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView Hasil;
    private int REQUEST_CODE = 110;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FrameLayout kalkulator = findViewById(R.id.kalkulator);
        kalkulator.setOnClickListener(this);

        FrameLayout PindahObjek = findViewById(R.id.PindahObjek);
        PindahObjek.setOnClickListener(this);

        FrameLayout Main2 = findViewById(R.id.main2);
        Main2.setOnClickListener(this);

        FrameLayout Main3 = findViewById(R.id.main3);
        Main3.setOnClickListener(this);

        FrameLayout Corona = findViewById(R.id.coronaMenu);
        Corona.setOnClickListener(this);

        FrameLayout Inspirasi = findViewById(R.id.Inspiracion);
        Inspirasi.setOnClickListener(this);

        FrameLayout cuaca = findViewById(R.id.weather);
        cuaca.setOnClickListener(this);

        FrameLayout pemandangan = findViewById(R.id.DetailPemandangan);
        pemandangan.setOnClickListener(this);

        Hasil = findViewById(R.id.result2);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.kalkulator:
                Intent calculator = new Intent(MainActivity.this, Kalkulator.class);
                startActivity(calculator);
                break;
            case R.id.main2:
                Intent Main2 = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(Main2);
                break;
            case R.id.main3:
                Intent main3 = new Intent(MainActivity.this, Main3Activity.class);
                startActivity(main3);
                break;
            case R.id.coronaMenu:
                Intent corona = new Intent(MainActivity.this, CoronaActivity.class);
                startActivity(corona);
                break;
            case R.id.DetailPemandangan:
                Intent pemandangan = new Intent(MainActivity.this, MenuPemandanganActivity.class);
                startActivity(pemandangan);
                break;
            case R.id.Inspiracion:
                Intent Inspirasi = new Intent(MainActivity.this, InspirasiActivity.class);
                startActivity(Inspirasi);
                break;
            case R.id.PindahObjek:
                Person person1 = new Person();
                person1.setName();
                person1.setAge();
                person1.setEmail();
                person1.setCity();

                Intent pindahObjek = new Intent(MainActivity.this, PindahDenganObjek.class);
                pindahObjek.putExtra(PindahDenganObjek.EXTRA_PERSON, person1);
                startActivityIfNeeded(pindahObjek, REQUEST_CODE );
                break;
            case R.id.weather:
                Intent cuaca = new Intent(MainActivity.this, WeatherActivity.class);
                startActivity(cuaca);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE) {
            if (requestCode == PindahDenganObjek.RESULT_CODE) {
                String selectedValue = data.getStringExtra(PindahDenganObjek.EXTRA_SELECTED_VALUE);
                Hasil.setText(String.format("Hasil : %s", selectedValue));
            }
        }
    }
}
