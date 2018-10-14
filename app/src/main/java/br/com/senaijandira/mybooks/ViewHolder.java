package br.com.senaijandira.mybooks;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

//classe que tem o modelo de cada linha da lista
public class ViewHolder extends RecyclerView.ViewHolder {

    public ImageView imgLivroCapa;
    public ImageView imgMenu;
    public TextView txtLivroTitulo;
    public TextView txtLivroDescricao;

    public ViewHolder(View v){
        super(v);

        imgLivroCapa = v.findViewById(R.id.imgLivroCapa);
        imgMenu = v.findViewById(R.id.imgMenu);
        txtLivroTitulo = v.findViewById(R.id.txtLivroTitulo);
        txtLivroDescricao = v.findViewById(R.id.txtLivroDescricao);
    }
}
