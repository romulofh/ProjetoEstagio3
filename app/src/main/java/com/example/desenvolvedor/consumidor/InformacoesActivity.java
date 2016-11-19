package com.example.desenvolvedor.consumidor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class InformacoesActivity extends AppCompatActivity {

    private TextView txtNome;
    private TextView txtLocal;
    private TextView txtData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacoes);

        Festa festa = (Festa) getIntent().getSerializableExtra("festa");

        txtNome = (TextView) findViewById(R.id.txtNome);
        txtLocal = (TextView) findViewById(R.id.txtLocal);
        txtData = (TextView) findViewById(R.id.txtData);

        txtNome.setText(festa.getNome());
        txtLocal.setText(festa.getLocal());
        txtData.setText(festa.getData());
    }
}
