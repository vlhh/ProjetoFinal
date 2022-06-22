package br.univates.appunivates;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import br.univates.appunivates.adapter.TenisAdapter;
import br.univates.appunivates.controller.TenisController;
import br.univates.appunivates.model.*;
import br.univates.appunivates.tools.Globais;

public class ListaTenisActivity extends AppCompatActivity {

    Button btnNova;
    ListView ltvLista;
    ArrayList<Tenis> listagem;
    TenisAdapter adapter;
    Context context;
    TenisController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_tenis);
        context = ListaTenisActivity.this;

        btnNova = findViewById(R.id.btnAddLinguagem_linguagens);
        ltvLista = findViewById(R.id.ltvLista_linguagem);

        btnNova.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, TenisActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        atualizarLista();
    }

    private void atualizarLista(){
        try {

            controller = new TenisController(context);
            listagem = controller.lista();

            adapter = new TenisAdapter(context, listagem);
            ltvLista.setAdapter(adapter);

        }catch (Exception ex){
            Globais.exibirMensagem(context, ex.getMessage());
            Log.e("ERRO", ex.getMessage());
        }
    }
}