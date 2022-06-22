package br.univates.appunivates;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

import br.univates.appunivates.controller.TenisController;
import br.univates.appunivates.model.Tenis;
import br.univates.appunivates.model.Size;
import br.univates.appunivates.tools.Globais;

public class TenisActivity extends AppCompatActivity {

    Button btnExcluir;
    CheckBox chbFavorito;
    EditText txtMarca,txtValor,txtModelo;
    Spinner spiSize;
    Tenis objeto;
    TenisController controller;
    Context context;
    int id_tenis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenis);

        txtMarca = findViewById(R.id.txtMarca);
        txtValor = findViewById(R.id.txtValor);
        txtModelo = findViewById(R.id.txtModelo);
        spiSize = findViewById(R.id.spiSize);
        btnExcluir = findViewById(R.id.btnExcluir);

        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller = new TenisController(context);
                boolean retorno = controller.excluir(id_tenis);
                if(retorno){
                    finish();
                }else{
                    Globais.exibirMensagem(context, "Erro ao excluir");
                }
            }
        });

        context = TenisActivity.this;

        ArrayList<Size> lista = new ArrayList<>();
        lista.add(new Size(1, "tam 36"));
        lista.add(new Size(2, "tam 37"));
        lista.add(new Size(3, "tam 38"));
        lista.add(new Size(4, "tam 39"));
        lista.add(new Size(5, "tam 40"));
        lista.add(new Size(6, "tam 41"));
        lista.add(new Size(7, "tam 42"));

        ArrayAdapter<Size> adapter_size = new ArrayAdapter<>(context,
                android.R.layout.simple_list_item_1, lista);

        spiSize.setAdapter(adapter_size);

        //Verificar se veio algum EXTRA da tela anterior
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            id_tenis = extras.getInt("id", 0);
            //buscar através desta chave
            controller = new TenisController(context);
            objeto = controller.buscarTenis(id_tenis);
            if(objeto != null){
                txtMarca.setText(objeto.getMarca());
                txtValor.setText(objeto.getValor());
                txtModelo.setText(objeto.getModelo());

                for(int i = 0; i < spiSize.getAdapter().getCount(); i++){
                    Size size = (Size) spiSize.getItemAtPosition(i);
                    if(size.getId() == objeto.getSize()){
                        spiSize.setSelection(i);
                        break;
                    }
                }
            }

        }else{
            id_tenis = 0;
            btnExcluir.setVisibility(View.GONE);
        }

    }

    //Funcao para inflar o menu na tela
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_cad, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){

            case R.id.action_ok:

                //SALVAR
                salvar();

            case R.id.action_cancel:

                finish();

        }

        return super.onOptionsItemSelected(item);
    }

    private void salvar(){
        try{

            String marca = txtMarca.getText().toString().trim();
            String valor = txtValor.getText().toString().trim();
            String modelo = txtModelo.getText().toString().trim();
            Size size = (Size) spiSize.getSelectedItem();

            if(marca.equals("")) {
                Globais.exibirMensagem(context, "Informe uma marca");
                return;
            }

            if(valor.equals("")) {
                Globais.exibirMensagem(context, "Informe um valor");
                return;
            }

            if(size.getId() <= 0) {
                Globais.exibirMensagem(context, "Informe um size");
                return;
            }

            if(marca.length() > 30){
                Globais.exibirMensagem(context,
                        "A marca é muito grande, credo.");
                return;
            }

            objeto = new Tenis();
            objeto.setMarca(marca);
            objeto.setValor(valor);
            objeto.setModelo(modelo);
            objeto.setSize(size.getId());

            controller = new TenisController(context);

            boolean retorno = false;
            if(id_tenis == 0){
                retorno = controller.incluir(objeto);
            }else{
                objeto.setId(id_tenis);
                retorno = controller.alterar(objeto);
            }

            if(retorno) {
                Globais.exibirMensagem(context, "Sucesso");
                finish();
            }

        }catch (Exception ex){
            Globais.exibirMensagem(context, ex.getMessage());
            Log.e("ERRO", ex.getMessage());
        }
    }
}