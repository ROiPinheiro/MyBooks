package br.com.senaijandira.mybooks.fragments;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.senaijandira.mybooks.CadastroActivity;
import br.com.senaijandira.mybooks.adapters.LivrosAdapter;
import br.com.senaijandira.mybooks.R;
import br.com.senaijandira.mybooks.utils.Utils;
import br.com.senaijandira.mybooks.db.MyBooksDatabase;

public class LivrosFragment extends Fragment {

    //variavel de acesso ao bd
    private MyBooksDatabase myBooksBD;
    private RecyclerView listaLivros;
    private RecyclerView.Adapter adapter;

    private FloatingActionButton btnFloat;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_livros, container, false);

        listaLivros = view.findViewById(R.id.lstLivros);
        listaLivros.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

        //instancia do banco de dados
        myBooksBD = Room.databaseBuilder(getContext(),MyBooksDatabase.class, Utils.DATABASE_NAME).fallbackToDestructiveMigration().allowMainThreadQueries().build();

        adapter = new LivrosAdapter(getContext(), myBooksBD.daoLivro().selecionarTodos(), myBooksBD);

        listaLivros.setAdapter(adapter);
        listaLivros.setLayoutManager(layoutManager);

        btnFloat = view.findViewById(R.id.abrirCadastro);

        btnFloat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), CadastroActivity.class));
            }
        });

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();

        adapter = new LivrosAdapter(getContext(), myBooksBD.daoLivro().selecionarTodos(), myBooksBD);
        listaLivros.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        myBooksBD.close();
    }
}
