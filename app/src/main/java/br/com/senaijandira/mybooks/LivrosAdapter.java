package br.com.senaijandira.mybooks;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import br.com.senaijandira.mybooks.db.MyBooksDatabase;
import br.com.senaijandira.mybooks.model.LinhaHolder;
import br.com.senaijandira.mybooks.model.Livro;

public class LivrosAdapter extends RecyclerView.Adapter<LinhaHolder> {

    private MyBooksDatabase myBooksDb;

    private List<Livro> livros;

    public LivrosAdapter(List<Livro> livros, MyBooksDatabase myBooksDb){

        this.livros = livros;
        this.myBooksDb = myBooksDb;
    }

    @Override //utiliza a classe LinhaHolder e infla o layout
    public LinhaHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View v = LayoutInflater.from(parent.getContext())
                .inflate(
                        R.layout.livro_layout,
                        parent,
                        false);

        return new LinhaHolder(v);
    }

    @Override //seta os valores de cada item
    public void onBindViewHolder(LinhaHolder holder, final int position) {

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
    }

    //total de itens da lista
    @Override
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
}
