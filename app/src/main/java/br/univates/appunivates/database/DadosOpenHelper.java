package br.univates.appunivates.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import br.univates.appunivates.tools.Globais;

public class DadosOpenHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1; //versão do banco de dados
    private static final String NM_BANCO = "banco";
    private Context context;

    public DadosOpenHelper(Context context) {
        super(context, NM_BANCO, null, VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try{
            StringBuilder sql = new StringBuilder();
            sql.append(" CREATE TABLE IF NOT EXISTS ");
            sql.append(Tabelas.TB_LINGUAGENS);
            sql.append(" ( ");
            sql.append(" id INTEGER PRIMARY KEY AUTOINCREMENT, ");
            sql.append(" id_categoria INTEGER, ");
            sql.append(" marca VARCHAR(30) NOT NULL, ");
            sql.append(" valor VARCHAR(15), ");
            sql.append(" modelo VARCHAR(15), ");
            sql.append(" size INTEGER ");
            sql.append(" ) ");
            sqLiteDatabase.execSQL(sql.toString());

            String senha = "123";//senha em md5

            sql = new StringBuilder();
            sql.append(" CREATE TABLE IF NOT EXISTS ");
            sql.append(Tabelas.TB_USUARIOS);
            sql.append(" ( ");
            sql.append(" id INTEGER PRIMARY KEY AUTOINCREMENT, ");
            sql.append(" nome VARCHAR(30) NOT NULL, ");
            sql.append(" login VARCHAR(20) NOT NULL, ");
            sql.append(" senha VARCHAR(50) NOT NULL ");
            sql.append(" ) ");
            sqLiteDatabase.execSQL(sql.toString());

            sql= new StringBuilder();
            sql.append(" INSERT INTO ");
            sql.append(Tabelas.TB_USUARIOS);
            sql.append(" VALUES( 'Vitoria', 'vitoria', '" + senha + "') ");

        }catch (Exception ex){
            Globais.exibirMensagem(context, ex.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try{
            StringBuilder sql;

            if(newVersion >= 2){
                try {
                    sql = new StringBuilder();
                    sql.append(" ALTER TABLE ");
                    sql.append(Tabelas.TB_LINGUAGENS);
                    sql.append(" ADD COLUMN ");
                    sql.append(" modelo VARCHAR(15) ");
                    db.execSQL(sql.toString());
                }catch (Exception ex){
                    Log.e("ALTER_TABLE", ex.getMessage());
                }


            }

        }catch (Exception ex){
            Log.e("ONUPGRADE", ex.getMessage());
        }
    }
}
