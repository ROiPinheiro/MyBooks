package br.com.senaijandira.mybooks;

import android.app.Activity;
import android.arch.persistence.room.Room;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import br.com.senaijandira.mybooks.db.MyBooksDatabase;
import br.com.senaijandira.mybooks.model.Livro;
import br.com.senaijandira.mybooks.utils.Utils;

public class CadastroActivity extends AppCompatActivity {

    //objeto img
    Bitmap livroCapa;

    //elemento para aparecer na tela
    ImageView imgLivroCapa;
    EditText txtTitulo, txtDescricacao;

    //define uma constante/ imutavel
    private final int COD_REQ_GALRERIA = 101;
    private MyBooksDatabase myBooksDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cadastro);

        imgLivroCapa = findViewById(R.id.imgLivroCapa);
        txtTitulo = findViewById(R.id.txtTitulo);
        txtDescricacao = findViewById(R.id.txtDescricao);

        myBooksDB = Room.databaseBuilder(getApplicationContext(),MyBooksDatabase.class, Utils.DATABASE_NAME).fallbackToDestructiveMigration().allowMainThreadQueries().build();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //se o retorno foi o COD E se o usuario selecionou alguma coisa
        if(requestCode == COD_REQ_GALRERIA && resultCode == Activity.RESULT_OK){
            //o retorno vem como inputStream

            try{
                //getContentResolver() - transforma em binario  //data.getData - conteudo selecionado
                InputStream input = getContentResolver().openInputStream(data.getData());

                //converte para bitmap
                livroCapa = BitmapFactory.decodeStream(input);

                //exibindo na tela
                imgLivroCapa.setImageBitmap(livroCapa);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void abrirGaleria(View view) {
        //abrir documentos
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);

        //só imagens - abre galeria ou app que gerencia imagens do celular
        intent.setType("image/*");

        //retorna o resultado
        startActivityForResult(Intent.createChooser(intent,"Selecione uma imagem"), COD_REQ_GALRERIA);
    }

    public void salvarLivro(View view) {

        if(livroCapa != null && !txtDescricacao.getText().toString().equals("") && !txtTitulo.getText().toString().equals("")) {

            String titulo = txtTitulo.getText().toString();
            String descricao = txtDescricacao.getText().toString();

            //obj para comprimir a img
            ByteArrayOutputStream stream = new ByteArrayOutputStream();

            //comprimir a img para reduzir o tamanho
            livroCapa.compress(Bitmap.CompressFormat.JPEG, 50, stream);

            byte[] capa = stream.toByteArray();

            Livro livro = new Livro(capa, titulo, descricao);

            myBooksDB.daoLivro().inserir(livro);

            alert("Sucesso", "Livro adicionado com sucesso", 0);

        }else{

            String erroCapa = "", erroTitulo = "", erroDescricao = "";

            if(livroCapa == null){
                erroCapa = "Falta a imagem da capa\n";
            }
            if(txtTitulo.getText().toString().equals("")){
                erroTitulo = "Falta um título\n";
            }
            if(txtDescricacao.getText().toString().equals("")){
                erroDescricao = "Falta uma descrição";
            }

            alert("Erro", erroCapa + erroTitulo + erroDescricao, 1);
        }
    }

    private void alert(String titulo, String msg, int tipo){

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(titulo);
        alert.setMessage(msg);

        alert.setCancelable(false);

        //tipo 0 = sucesso, 1 = erro
        if(tipo == 0){
            alert.setPositiveButton("Adicionar outro", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    //volta o icone para a ImageView
                    imgLivroCapa.setImageResource(android.R.drawable.ic_menu_camera);

                    livroCapa = null;
                    txtTitulo.getText().clear();
                    txtDescricacao.getText().clear();
                }
            });

            alert.setNegativeButton("Voltar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });

        }else {

            alert.setPositiveButton("Tentar novamente", null);
            alert.setNegativeButton("Voltar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
        }
        alert.create().show();
    }
}
