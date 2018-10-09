package br.com.senaijandira.mybooks.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import br.com.senaijandira.mybooks.R;
import br.com.senaijandira.mybooks.utils.Utils;
import br.com.senaijandira.mybooks.ViewHolder;
import br.com.senaijandira.mybooks.db.MyBooksDatabase;
import br.com.senaijandira.mybooks.model.LivroELidos;

public class LivrosParaLerAdapter extends RecyclerView.Adapter<ViewHolder> {

    private MyBooksDatabase myBooksDb;

    private List<LivroELidos> livros;

    public LivrosParaLerAdapter(List<LivroELidos> livros, MyBooksDatabase myBooksDb){

        this.livros = livros;
        this.myBooksDb = myBooksDb;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.livro_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override //seta os valores de cada item
    public void onBindViewHolder(ViewHolder holder, final int position) {

        final LivroELidos livro = livros.get(position);

        holder.imgLivroCapa.setImageBitmap(Utils.toBitmap(livro.getCapa()));
        holder.txtLivroTitulo.setText(livro.getTitulo());
        holder.txtLivroDescricao.setText(livro.getDescricao());

    }

    @Override //total de itens da lista
    public int getItemCount() {

        //se livros não for nulo, retorne quantos livros tem, se for, retorne 0
        return livros != null ? livros.size() : 0;
    }

}