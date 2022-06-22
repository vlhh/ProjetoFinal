package br.univates.appunivates.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import br.univates.appunivates.database.DadosOpenHelper;
import br.univates.appunivates.database.Tabelas;
import br.univates.appunivates.model.Tenis;
import br.univates.appunivates.tools.Globais;

public class TenisController {

    private SQLiteDatabase conexao;
    private Context context;

    public TenisController(Context context){
        DadosOpenHelper banco = new DadosOpenHelper(context);
        this.conexao = banco.getWritableDatabase();
        this.context = context;
    }

    public Tenis buscarTenis(int id){
        Tenis objeto = null;

        try{

            StringBuilder sql = new StringBuilder();
            sql.append("SELECT * FROM ");
            sql.append(Tabelas.TB_LINGUAGENS);
            sql.append(" WHERE id = '"+ id +"'");

            Cursor resultado = conexao.rawQuery(sql.toString(), null);
            if(resultado.moveToNext()){
                objeto = new Tenis();

                objeto.setId(resultado.getInt(resultado.getColumnIndexOrThrow("id")));
                objeto.setMarca(resultado.getString(resultado.getColumnIndexOrThrow("marca")));
                objeto.setValor(resultado.getString(resultado.getColumnIndexOrThrow("valor")));
                objeto.setModelo(resultado.getString(resultado.getColumnIndexOrThrow("modelo")));
                objeto.setSize(resultado.getInt(resultado.getColumnIndexOrThrow("size")));
            }

            return objeto;

        }catch (Exception ex){
            Globais.exibirMensagem(context, ex.getMessage());
            return objeto;
        }
    }

    public boolean incluir(Tenis objeto){
        try{

            ContentValues valores = new ContentValues();
            valores.put("marca", objeto.getMarca());
            valores.put("valor", objeto.getValor());
            valores.put("modelo", objeto.getModelo());
            valores.put("size", objeto.getSize());

            conexao.insertOrThrow(Tabelas.TB_LINGUAGENS, null,
                    valores);

            return true;

        }catch (Exception ex){
            Globais.exibirMensagem(context, ex.getMessage());
            return false;
        }
    }

    public boolean alterar(Tenis objeto){
        try{

            ContentValues valores = new ContentValues();
            valores.put("marca", objeto.getMarca());
            valores.put("valor", objeto.getValor());
            valores.put("modelo", objeto.getModelo());
            valores.put("size", objeto.getSize());

            String[] parametros = new String[1];
            parametros[0] = String.valueOf(objeto.getId());

            conexao.update(Tabelas.TB_LINGUAGENS, valores, "id = ?" , parametros);

            return true;

        }catch (Exception ex){
            Globais.exibirMensagem(context, ex.getMessage());
            return false;
        }
    }

    public boolean excluir(int id){
        try{

            String[] parametros = new String[1];
            parametros[0] = String.valueOf(id);

            conexao.delete(Tabelas.TB_LINGUAGENS, "id = ?", parametros);

            return true;

        }catch (Exception ex){
            Globais.exibirMensagem(context, ex.getMessage());
            return false;
        }
    }

    public ArrayList<Tenis> lista(){

        ArrayList<Tenis> listagem = new ArrayList<>();
        try{

            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT * FROM ");
            sql.append(Tabelas.TB_LINGUAGENS);
            sql.append(" ORDER BY marca ");

            Cursor resultado = conexao.rawQuery(sql.toString(), null);
            if(resultado.moveToFirst()){

                Tenis objeto;
                do{
                    objeto = new Tenis();

                    objeto.setId(resultado.getInt(resultado.getColumnIndexOrThrow("id")));
                    objeto.setMarca(resultado.getString(resultado.getColumnIndexOrThrow("marca")));
                    objeto.setValor(resultado.getString(resultado.getColumnIndexOrThrow("valor")));
                    objeto.setModelo(resultado.getString(resultado.getColumnIndexOrThrow("modelo")));
                    objeto.setSize(resultado.getInt(resultado.getColumnIndexOrThrow("size")));

                    listagem.add(objeto);

                }while (resultado.moveToNext());

            }

            return listagem;

        }catch (Exception ex){
            Globais.exibirMensagem(context, ex.getMessage());
            return listagem;
        }
    }

}
