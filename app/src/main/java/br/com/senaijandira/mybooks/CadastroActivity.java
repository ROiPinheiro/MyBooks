package br.com.senaijandira.mybooks;

import android.app.Activity;
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

import java.io.InputStream;
import java.util.Arrays;

import br.com.senaijandira.mybooks.model.Livro;

public class CadastroActivity extends AppCompatActivity {

    //objeto img
    Bitmap livroCapa;

    //elemento para aparecer na tela
    ImageView imgLivroCapa;
    EditText txtTitulo, txtDescricacao;

    //define uma constante/ imutavel
    private final int COD_REQ_GALRERIA = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cadastro);

        imgLivroCapa = findViewById(R.id.imgLivroCapa);
        txtTitulo = findViewById(R.id.txtTitulo);
        txtDescricacao = findViewById(R.id.txtDescricao);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //se o retorno foi o COD E se o usuario selecionou alguma coisa
        if(requestCode == COD_REQ_GALRERIA && resultCode == Activity.RESULT_OK){
            //o retorno vem como inputStream

            try{
                //getContentResolver() - transforma em binario
                //data.getData - conteudo selecionado
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
            byte[] capa = Utils.toByteArray(livroCapa);

            Livro livro = new Livro(0, capa, titulo, descricao);

            //Inserir na variável estática da MainActivity
            //tamanho atual do array
            int tamanhoArray = MainActivity.livros.length;

            MainActivity.livros = Arrays.copyOf(MainActivity.livros, tamanhoArray + 1);

            MainActivity.livros[tamanhoArray] = livro;

            alert("Contato salvo", "Contato salvado");

        }else{

            String erroCapa = "", erroTitulo = "", erroDescricao = "";

            if(livroCapa == null){
                erroCapa = "Falta a imagem da capa";
            }

            if(txtDescricacao.getText().toString().equals("")){
                erroTitulo = "Falta um título";
            }

            if(txtTitulo.getText().toString().equals("")){
                erroDescricao = "Falta uma descrição";
            }

            alert("Erro", erroCapa + "\n" + erroTitulo + "\n" + erroDescricao);
        }
    }

    private void alert(String titulo, String msg){

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(titulo);
        alert.setMessage(msg);

        alert.setPositiveButton("Adicionar outro", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                finish();
                startActivity(new Intent(getBaseContext(), CadastroActivity.class));
            }
        });

        alert.setNegativeButton("Voltar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        alert.create().show();

    }
}
