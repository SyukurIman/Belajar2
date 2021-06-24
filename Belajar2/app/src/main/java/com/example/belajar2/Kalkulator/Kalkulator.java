package com.example.belajar2.Kalkulator;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.belajar2.R;

public class Kalkulator extends AppCompatActivity implements View.OnClickListener{

    private EditText Panjang, Panjang2, Lebar, Lebar2, Tinggi;
    private Button Volume, Hitung2, Save, Keliling, LuasArea;
    private TextView Hasil, Hasil2;

    private MainViewModel mainViewModel;
    private static final String STATE_RESULT =  "state_result";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kalkulator);

        mainViewModel = new MainViewModel(new CuboidModel());

        Panjang = findViewById(R.id.Panjang);
        Panjang2 = findViewById(R.id.Panjang2);
        Lebar = findViewById(R.id.Lebar);
        Lebar2 = findViewById(R.id.Lebar2);
        Tinggi = findViewById(R.id.Tinggi);
        Volume = findViewById(R.id.Hitung);
        Volume.setOnClickListener(this);
        Save = findViewById(R.id.Save);
        Save.setOnClickListener(this);
        Keliling = findViewById(R.id.Keliling);
        Keliling.setOnClickListener(this);
        LuasArea = findViewById(R.id.LuasArea);
        LuasArea.setOnClickListener(this);
        Hitung2 = findViewById(R.id.Hitung2);
        Hitung2.setOnClickListener(this);
        Hasil = findViewById(R.id.Hasil);
        Hasil2 = findViewById(R.id.Hasil2);

        if (savedInstanceState != null) {
            String result = savedInstanceState.getString(STATE_RESULT);
            Hasil.setText(result);
            Hasil2.setText(result);
        }
    }

    @Override
    public void onClick(View v) {
            String InPanjang = Panjang.getText().toString().trim();
            String InTinggi = Tinggi.getText().toString().trim();
            String InLebar = Lebar.getText().toString().trim();

            if (TextUtils.isEmpty(InPanjang)) {
                Panjang.setError("Kolom Ini Tidak Boleh Kosong");
            } else if (TextUtils.isEmpty(InTinggi)) {
                Tinggi.setError("Kolom Ini Tidak Boleh Kosong");
            } else if (TextUtils.isEmpty(InLebar)) {
                Lebar.setError("Kolom Ini Tidak Boleh Kosong");
            } else {
                double l = Double.parseDouble(InLebar);
                double p = Double.parseDouble(InPanjang);
                double t = Double.parseDouble(InTinggi);

                if (v.getId() == R.id.Save) {
                    mainViewModel.save(l, p, t);
                    visible();
                } else if (v.getId() == R.id.Hitung) {
                    Hasil.setText(String.valueOf(mainViewModel.getVolume()));
                    gone();
                } else if (v.getId() == R.id.Keliling) {
                    Hasil.setText(String.valueOf(mainViewModel.getKeliling()));
                    gone();
                } else if (v.getId() == R.id.LuasArea) {
                    Hasil.setText(String.valueOf(mainViewModel.getLuasArea()));
                    gone();
                }
            }
        if (v.getId() == R.id.Hitung2) {
            String InPanjang2 = Panjang2.getText().toString().trim();
            String InLebar2 = Lebar2.getText().toString().trim();

            boolean isEmptyFields = false;
            boolean isInvailidDouble2 = false;

            if (TextUtils.isEmpty(InPanjang2)) {
                isEmptyFields = true;
                Panjang2.setError("Kolom Ini Tidak Boleh Kosong");
            }

            if (TextUtils.isEmpty(InLebar2)) {
                isEmptyFields = true;
                Lebar2.setError("Kolom Ini Tidak Boleh Kosong");
            }

            if (!isEmptyFields) {
                double PersegiPanjang = Double.valueOf(InPanjang2) * Double.valueOf(InLebar2);
                Hasil2.setText(String.valueOf(PersegiPanjang));
            }

        }

    }

    private void visible() {
        Volume.setVisibility(View.VISIBLE);
        Keliling.setVisibility(View.VISIBLE);
        LuasArea.setVisibility(View.VISIBLE);
        Save.setVisibility(View.GONE);
        Hasil.setVisibility(View.GONE);
    }

    private void gone() {
        Volume.setVisibility(View.GONE);
        Keliling.setVisibility(View.GONE);
        LuasArea.setVisibility(View.GONE);
        Save.setVisibility(View.VISIBLE);
        Hasil.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(STATE_RESULT, Hasil.getText().toString());
        outState.putString(STATE_RESULT, Hasil2.getText().toString());
    }
}
