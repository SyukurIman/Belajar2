package com.example.belajar2;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.vstechlab.easyfonts.EasyFonts;

public class PindahDenganObjek extends AppCompatActivity implements View.OnClickListener {
    public static final String EXTRA_SELECTED_VALUE = "extra_selected_value" ;
    public static final int RESULT_CODE = 110;
    private RadioGroup KolomPertayaan2;
    public static final String EXTRA_PERSON = "extra_person";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pindah_dengan_objek);

        TextView Objek = findViewById(R.id.ObjekPenerima);
        Objek.setTypeface(EasyFonts.robotoBlack(this));
        Button pilih = findViewById(R.id.Pilih);
        pilih.setOnClickListener(this);

        KolomPertayaan2 = findViewById(R.id.kolomPertayaan);

        Person person = getIntent().getParcelableExtra(EXTRA_PERSON);
        assert person != null;
        String text = "Nama : " + person.getName() + ",\nEmail : " + person.getEmail() + ",\nUmur : " + person.getAge() + ",\nKota : " + person.getCity();
        Objek.setText(text);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.Pilih) {
            if (KolomPertayaan2.getCheckedRadioButtonId() != 0) {
                String value = "Anda Tidak Memilih";
                switch (KolomPertayaan2.getCheckedRadioButtonId()) {
                    case R.id.JawabanA:
                        value = "Bakso";
                        break;

                    case R.id.JawabanB:
                        value = "Mie Goreng";
                        break;

                    case R.id.JawabanC:
                        value = "Hisana";
                        break;

                    case R.id.JawabanD:
                        value = "Rawon Mawut";
                        break;
                }

                Intent resultIntent = new Intent();
                resultIntent.putExtra(EXTRA_SELECTED_VALUE, value);
                setResult(RESULT_CODE, resultIntent);
                finish();
            }
        }
    }
}
