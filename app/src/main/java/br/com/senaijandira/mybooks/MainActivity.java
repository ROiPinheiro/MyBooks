package br.com.senaijandira.mybooks;

import android.arch.persistence.room.Room;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import br.com.senaijandira.mybooks.db.MyBooksDatabase;
import br.com.senaijandira.mybooks.model.Livro;

public class MainActivity extends AppCompatActivity {

    private RecyclerView listaLivros;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    //variavel de acesso ao bd
    private MyBooksDatabase myBooksBD;

    public static Livro[] livros;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listaLivros =  (RecyclerView) findViewById(R.id.lstLivros);
        listaLivros.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);

        //instancia do banco de dados
        myBooksBD = Room.databaseBuilder(getApplicationContext(),MyBooksDatabase.class,Utils.DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();

        adapter = new LivrosAdapter(myBooksBD.daoLivro().selecionarTodos());

        listaLivros.setAdapter(adapter);
        listaLivros.setLayoutManager(layoutManager);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    public void deletarLivro(final Livro livro){

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
                //adapter.remove(livro);
            }
        });

        alert.show();
    }

    public void abrirCadastro(View v){
        startActivity(new Intent(this, CadastroActivity.class));
    }
}
