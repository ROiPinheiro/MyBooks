package br.com.senaijandira.mybooks.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import br.com.senaijandira.mybooks.R;
import br.com.senaijandira.mybooks.Utils;
import br.com.senaijandira.mybooks.db.MyBooksDatabase;
import br.com.senaijandira.mybooks.ViewHolder;
import br.com.senaijandira.mybooks.model.Livro;
import br.com.senaijandira.mybooks.model.LivrosLidos;
import br.com.senaijandira.mybooks.model.LivrosParaLer;

public class LivrosAdapter extends RecyclerView.Adapter<ViewHolder> {

    private MyBooksDatabase myBooksDb;

    private List<Livro> livros;
    private LivrosLidos lidos;
    private LivrosParaLer ler;

    public LivrosAdapter(List<Livro> livros, MyBooksDatabase myBooksDb){

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

        final Livro livro = livros.get(position);

        //Setando os valores
        holder.imgLivroCapa.setImageBitmap(Utils.toBitmap(livro.getCapa()));
        holder.txtLivroTitulo.setText(livro.getTitulo());
        holder.txtLivroDescricao.setText(livro.getDescricao());

        holder.imgDeletarLivro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletarLivro(livro, position);
            }
        });

        holder.imgLidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paraLidos(new LivrosLidos(livro.getId()));
            }
        });

        holder.imgLer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paraLer(new LivrosParaLer(livro.getId()));
            }
        });
    }

    @Override //total de itens da lista
    public int getItemCount() {

        //se livros não for nulo, retorne quantos livros tem, se for, retorne 0
        return livros != null ? livros.size() : 0;
    }

    private void deletarLivro(Livro livro, int position){

        myBooksDb.daoLivro().deletar(livro);

        //remove livro da lista
        livros.remove(livro);
        notifyItemRemoved(position);
    }

    private void paraLidos(LivrosLidos lidos){
        myBooksDb.daoLivrosLidos().inserir(lidos);
    }

    private void paraLer(LivrosParaLer ler){
        myBooksDb.daoLivrosParaLer().inserir(ler);
    }
}