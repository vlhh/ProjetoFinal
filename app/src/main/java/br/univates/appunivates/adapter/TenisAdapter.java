package br.univates.appunivates.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import br.univates.appunivates.TenisActivity;
import br.univates.appunivates.R;
import br.univates.appunivates.model.Tenis;

public class TenisAdapter extends ArrayAdapter<Tenis> {

    private final Context context;
    private final ArrayList<Tenis> elementos;

    public TenisAdapter(Context context, ArrayList<Tenis> elementos){
        super(context, R.layout.item_lista_tenis, elementos);
        this.context = context;
        this.elementos = elementos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        try{
            Tenis objeto = elementos.get(position);

            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            //toda vez que passa por um item da lista, os elementos são associados
            View rowView = inflater.inflate(R.layout.item_lista_tenis, parent, false);

            TextView marca = rowView.findViewById(R.id.lblMarca);
            TextView valor = rowView.findViewById(R.id.lblValor);

            marca.setText(objeto.getMarca());
            valor.setText(objeto.getValor());

            rowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent tela = new Intent(context, TenisActivity.class);
                    tela.putExtra("id", objeto.getId());
                    context.startActivity(tela);
                }
            });

            return rowView;

        }catch (Exception ex){
            Log.e("ERRO_ADAPTER", ex.getMessage());
            return null;
        }

    }
}
