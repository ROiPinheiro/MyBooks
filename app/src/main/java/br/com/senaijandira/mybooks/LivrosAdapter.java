package br.com.senaijandira.mybooks;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.senaijandira.mybooks.model.Livro;

public class LivrosAdapter extends RecyclerView.Adapter<LivrosAdapter.ViewHolder> {

    /*
    public LivrosAdapter(Context ctx){
        super(ctx, 0, new ArrayList<Livro>());
    }
    */

    private Livro[] livros;

    public LivrosAdapter(Livro[] livrosArr){
        this.livros = livrosArr;
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{

        public ImageView imgLivroCapa;
        public TextView txtLivroTitulo;
        public TextView txtLivroDescricao;

        public ViewHolder(View v){
            super(v);

            imgLivroCapa = v.findViewById(R.id.imgLivroCapa);
            txtLivroTitulo = v.findViewById(R.id.txtLivroTitulo);
            txtLivroDescricao = v.findViewById(R.id.txtLivroDescricao);

        }
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

        Context context = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.livro_layout,parent,false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Livro livro = livros[position];

        //Setando os valores
        holder.imgLivroCapa.setImageBitmap(Utils.toBitmap(livros[position].getCapa()));
        holder.txtLivroTitulo.setText(livros[position].getTitulo());
        holder.txtLivroDescricao.setText(livros[position].getDescricao());
    }

    @Override
    public int getItemCount() {
        return livros.length;
    }

    /*

        ImageView imgDeleteLivro = view.findViewById(R.id.imgDeleteLivro);

        imgDeleteLivro.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                //deletarLivro(livro);
            }
        });

        return view;
    }
    */

}
