package br.com.senaijandira.mybooks.model;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.senaijandira.mybooks.R;

//classe que tem o modelo de cada linha da lista
public class LinhaHolder extends RecyclerView.ViewHolder {

    public ImageView imgLivroCapa;
    public TextView txtLivroTitulo;
    public TextView txtLivroDescricao;

    public LinhaHolder(View v){
        super(v);

        imgLivroCapa = (ImageView) v.findViewById(R.id.imgLivroCapa);
        txtLivroTitulo = (TextView) v.findViewById(R.id.txtLivroTitulo);
        txtLivroDescricao = (TextView) v.findViewById(R.id.txtLivroDescricao);
    }
}
