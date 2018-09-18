package br.com.senaijandira.mybooks;

import android.arch.persistence.room.Room;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import br.com.senaijandira.mybooks.db.MyBooksDatabase;
import br.com.senaijandira.mybooks.model.Livro;

public class MainActivity extends AppCompatActivity {

    LinearLayout listaLivros;

    public static Livro[] livros;

    //variavel de acesso ao bd
    private MyBooksDatabase myBooksBD;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listaLivros = findViewById(R.id.listaLivros);


        //instancia do banco de dados
        myBooksBD = Room.databaseBuilder(
                getApplicationContext(),
                MyBooksDatabase.class,
                Utils.DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();

        //Criando cadastros fake
        //livros = new Livro[]{};
    }

    @Override
    protected void onResume() {
        super.onResume();

        //Faz o select no banco
        livros = myBooksBD.daoLivro().selecionarTodos();

        //remove tudo e cria novamente
        listaLivros.removeAllViews();

        //cada livro dentro da lista de livros
        for (Livro l : livros){
            criarLivro(l, listaLivros);
        }
    }

    public void deletarLivro(final Livro livro,final View v){

        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Deletar");
        alert.setMessage("Tem certeza que deseja deletar?");

        alert.setNegativeButton("Não", null);
        alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                //deletar livro do banco
                myBooksBD.daoLivro().deletar(livro);

                //deletar livro da tela
                listaLivros.removeView(v);
            }
        });

        alert.show();
    }

    public void criarLivro(final Livro livro, ViewGroup root){

        //carrega um xml para o código
        final View v = LayoutInflater.from(this).inflate(R.layout.livro_layout, root , false);

        //Achando os elementos dentro o V
        ImageView imgLivroCapa = v.findViewById(R.id.imgLivroCapa);
        TextView txtLivroTitulo = v.findViewById(R.id.txtLivroTitulo);
        TextView txtLivroDescricao = v.findViewById(R.id.txtLivroDescricao);

        //Setando os valores
        imgLivroCapa.setImageBitmap(Utils.toBitmap(livro.getCapa()));
        txtLivroTitulo.setText(livro.getTitulo());
        txtLivroDescricao.setText(livro.getDescricao());

        ImageView imgDeleteLivro = v.findViewById(R.id.imgDeleteLivro);

        imgDeleteLivro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deletarLivro(livro, v);
            }
        });

        //Exibindo na tela
        root.addView(v);
    }

    public void abrirCadastro(View v){
        startActivity(new Intent(this, CadastroActivity.class));
    }
}
