package br.univates.appunivates.controller;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import br.univates.appunivates.database.DadosOpenHelper;
import br.univates.appunivates.database.Tabelas;
import br.univates.appunivates.model.Usuario;
import br.univates.appunivates.tools.Globais;

public class UsuarioController {

    private SQLiteDatabase conexao;
    Context context;

    public UsuarioController(Context context){
        DadosOpenHelper banco = new DadosOpenHelper(context);
        this.conexao = banco.getWritableDatabase();
        this.context = context;
    }

    public Usuario buscar(String login, String senha){
        Usuario objeto = null;

        try{

            StringBuilder sql = new StringBuilder();
            sql.append("SELECT * FROM ");
            sql.append(Tabelas.TB_USUARIOS);
            sql.append(" WHERE login = '"+ login +"'");
            sql.append(" AND senha = '"+ senha +"' ");

            Cursor resultado = conexao.rawQuery(sql.toString(), null);
            if(resultado.moveToNext()){
                objeto = new Usuario();

                objeto.setId(resultado.getInt(resultado.getColumnIndexOrThrow("id")));
                objeto.setLogin(resultado.getString(resultado.getColumnIndexOrThrow("login")));
                objeto.setSenha(resultado.getString(resultado.getColumnIndexOrThrow("senha")));
            }

            return objeto;

        }catch (Exception ex){
            Globais.exibirMensagem(context, ex.getMessage());
            return objeto;
        }
    }

}
