package br.com.senaijandira.mybooks.fragments;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.senaijandira.mybooks.R;
import br.com.senaijandira.mybooks.Utils;
import br.com.senaijandira.mybooks.adapters.LivrosParaLerAdapter;
import br.com.senaijandira.mybooks.db.MyBooksDatabase;

public class LivrosParaLerFragment extends Fragment{

    //variavel de acesso ao bd
    private MyBooksDatabase myBooksBD;
    private RecyclerView listaLivros;
    private RecyclerView.Adapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_livros_para_ler, container, false);

        listaLivros = view.findViewById(R.id.lstLivros);
        listaLivros.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

        //instancia do banco de dados
        myBooksBD = Room.databaseBuilder(getContext(),MyBooksDatabase.class, Utils.DATABASE_NAME).fallbackToDestructiveMigration().allowMainThreadQueries().build();

        adapter = new LivrosParaLerAdapter(myBooksBD.daoLivrosParaLer().selecionarLivro(), myBooksBD);

        listaLivros.setAdapter(adapter);
        listaLivros.setLayoutManager(layoutManager);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        adapter = new LivrosParaLerAdapter(myBooksBD.daoLivrosParaLer().selecionarLivro(), myBooksBD);
        listaLivros.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        myBooksBD.close();
    }
}
