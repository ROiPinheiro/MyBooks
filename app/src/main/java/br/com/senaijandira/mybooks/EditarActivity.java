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
import android.widget.TextView;

import java.io.InputStream;

import br.com.senaijandira.mybooks.db.MyBooksDatabase;
import br.com.senaijandira.mybooks.model.Livro;
import br.com.senaijandira.mybooks.utils.Utils;

public class EditarActivity extends AppCompatActivity {

    Bundle bundle;
    Livro livro;

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

        setContentView(R.layout.activity_editar);

        bundle = new Bundle();
        bundle = getIntent().getExtras();

        myBooksDB = Room.databaseBuilder(getApplicationContext(),MyBooksDatabase.class, Utils.DATABASE_NAME).fallbackToDestructiveMigration().allowMainThreadQueries().build();

        //pega os dados do livro que foi selecionado na tela principal
        livro = myBooksDB.daoLivro().selecioarUmLivro(bundle.getInt("ID"));

        livroCapa = Utils.toBitmap(livro.getCapa());
        imgLivroCapa = findViewById(R.id.imgLivroCapa);
        imgLivroCapa.setImageBitmap(Utils.toBitmap(livro.getCapa()));

        txtTitulo = findViewById(R.id.txtTitulo);
        txtTitulo.setText(livro.getTitulo());

        txtDescricacao = findViewById(R.id.txtDescricao);
        txtDescricacao.setText(livro.getDescricao());
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

    public void editarLivro(View view) {

        //validação, caso senhum valor for nulo...
        if(livroCapa != null && !txtDescricacao.getText().toString().equals("") && !txtTitulo.getText().toString().equals("")) {

            //seta no livro os novos valores
            livro.setTitulo(txtTitulo.getText().toString());
            livro.setDescricao(txtDescricacao.getText().toString());
            livro.setCapa(Utils.toByteArray(livroCapa));

            myBooksDB.daoLivro().atualizar(livro);

            alert("Sucesso", "Livro editado com sucesso", 0);

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
