package br.com.senaijandira.mybooks;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.senaijandira.mybooks.R;

//classe que tem o modelo de cada linha da lista
public class ViewHolder extends RecyclerView.ViewHolder {

    public ImageView imgLivroCapa;
    public ImageView imgDeletarLivro;
    public TextView txtLivroTitulo;
    public TextView txtLivroDescricao;

    public ViewHolder(View v){
        super(v);

        imgLivroCapa = (ImageView) v.findViewById(R.id.imgLivroCapa);
        imgDeletarLivro = (ImageView) v.findViewById(R.id.imgDeletarLivro);
        txtLivroTitulo = (TextView) v.findViewById(R.id.txtLivroTitulo);
        txtLivroDescricao = (TextView) v.findViewById(R.id.txtLivroDescricao);
    }
}
