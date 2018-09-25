package br.com.senaijandira.mybooks;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import br.com.senaijandira.mybooks.model.LinhaHolder;
import br.com.senaijandira.mybooks.model.Livro;

public class LivrosAdapter extends RecyclerView.Adapter<LinhaHolder> {

    private List<Livro> livros;

    public LivrosAdapter(List<Livro> livros){
        this.livros = livros;
    }

    @Override //utiliza a classe LinhaHolder e infla o layout
    public LinhaHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.livro_layout,parent,false);

        return new LinhaHolder(v);
    }

    @Override
    public void onBindViewHolder(LinhaHolder holder, int position) {

        Livro livro = livros.get(position);

        //Setando os valores
        holder.imgLivroCapa.setImageBitmap(Utils.toBitmap(livro.getCapa()));
        holder.txtLivroTitulo.setText(livro.getTitulo());
        holder.txtLivroDescricao.setText(livro.getDescricao());
    }

    //total de itens da lista
    @Override
    public int getItemCount() {

        //se livros não for nulo, retorne quantos livros tem, se for, retorne 0
        return livros != null ? livros.size() : 0;
    }
}
