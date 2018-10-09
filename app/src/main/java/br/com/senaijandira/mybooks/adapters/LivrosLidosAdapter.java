package br.com.senaijandira.mybooks.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import br.com.senaijandira.mybooks.R;
import br.com.senaijandira.mybooks.ViewHolder;
import br.com.senaijandira.mybooks.db.MyBooksDatabase;
import br.com.senaijandira.mybooks.model.LivroELidos;
import br.com.senaijandira.mybooks.model.LivrosLidos;
import br.com.senaijandira.mybooks.utils.Utils;

public class LivrosLidosAdapter extends RecyclerView.Adapter<ViewHolder> {

    private MyBooksDatabase myBooksDb;

    private List<LivroELidos> livros;
    private Context ctx;

    public LivrosLidosAdapter(Context ctx, List<LivroELidos> livros, MyBooksDatabase myBooksDb){

        this.livros = livros;
        this.myBooksDb = myBooksDb;
        this.ctx = ctx;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.livro_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override //seta os valores de cada item
    public void onBindViewHolder(ViewHolder holder, final int position) {

        final LivroELidos livroELidos = livros.get(position);

        //cria e converte um LivrosELidos para um LivrosLidos
        //resultado da um consulta de duas tabelas em um model Livros lidos
        //para poder ser deletado
        final LivrosLidos lidos = new LivrosLidos();
        lidos.setIdPk(livroELidos.getIdPk());
        lidos.setIdLivros(livroELidos.getIdLivros());

        holder.imgLivroCapa.setImageBitmap(Utils.toBitmap(livroELidos.getCapa()));
        holder.txtLivroTitulo.setText(livroELidos.getTitulo());
        holder.txtLivroDescricao.setText(livroELidos.getDescricao());

        holder.imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupMenu(v, lidos, position);
            }
        });
    }

    @Override //total de itens da lista
    public int getItemCount() {

        //se livros não for nulo, retorne quantos livros tem, se for, retorne 0
        return livros != null ? livros.size() : 0;
    }

    //Médoto do popup menu de cada item da lista;
    private void popupMenu(View v, final LivrosLidos livro, final int position){

        final PopupMenu popup = new PopupMenu(v.getContext(), v);

        popup.getMenuInflater().inflate(R.menu.menu_ler_lidos, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()){

                    case R.id.menu_ler_lidos_item_edit:
                        break;

                    case R.id.menu_ler_lidos_item_delete:
                        deletarLivro(livro, position);
                        break;
                }
                return false;
            }
        });
        popup.show();
    }

    private void deletarLivro(final LivrosLidos livro, final int position){

        AlertDialog.Builder alert = new AlertDialog.Builder(ctx);
        alert.setTitle("Aviso!");
        alert.setMessage("Tem certeza que deseja apagar este livro?");

        alert.setNegativeButton("Voltar", null);
        alert.setPositiveButton("Apagar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                myBooksDb.daoLivrosLidos().deletar(livro);

                //remove livro da lista
                livros.remove(livro);
                notifyItemRemoved(position);
            }
        });
        alert.create().show();
    }

}