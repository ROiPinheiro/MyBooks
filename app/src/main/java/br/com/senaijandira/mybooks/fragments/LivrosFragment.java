package br.com.senaijandira.mybooks.fragments;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.senaijandira.mybooks.LivrosAdapter;
import br.com.senaijandira.mybooks.R;
import br.com.senaijandira.mybooks.Utils;
import br.com.senaijandira.mybooks.db.MyBooksDatabase;

public class LivrosFragment extends Fragment {

    //variavel de acesso ao bd
    private MyBooksDatabase myBooksBD;
    private RecyclerView listaLivros;
    private RecyclerView.Adapter adapter;

    public LivrosFragment() {
        // Required empty public constructor
    }

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

        adapter = new LivrosAdapter(myBooksBD.daoLivro().selecionarTodos(), myBooksBD);

        listaLivros.setAdapter(adapter);
        listaLivros.setLayoutManager(layoutManager);

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        adapter = new LivrosAdapter(myBooksBD.daoLivro().selecionarTodos(), myBooksBD);
        listaLivros.setAdapter(adapter);
    }

}
