package br.univates.appunivates;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import br.univates.appunivates.controller.UsuarioController;
import br.univates.appunivates.model.Usuario;
import br.univates.appunivates.tools.Globais;

public class LoginActivity extends AppCompatActivity {

    Context context;
    Button btnEntrar_login;
    LinearLayout linearMain;
    EditText txtUsuario, txtSenha;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        context = LoginActivity.this;
        txtUsuario = findViewById(R.id.txtUsuario_login);
        txtSenha = findViewById(R.id.txtSenha_login);
        btnEntrar_login = findViewById(R.id.btnEntrar_login);

        btnEntrar_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String senha = "123";
                String senha_md5= Globais.md5(senha);

                UsuarioController objController = new UsuarioController(context);
                Usuario login = objController.buscar("vitoria",senha);

                if(txtUsuario.getText().toString().equals("vitoria") &&
                        txtSenha.getText().toString().equals("123")) {

                    Intent intent = new Intent(context, ListaTenisActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Globais.exibirMensagem(context, "Nome de usuário ou senha incorreta");
                }


            }
        });


    }


}