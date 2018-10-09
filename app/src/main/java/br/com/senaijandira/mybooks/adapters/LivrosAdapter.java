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
import br.com.senaijandira.mybooks.Utils;
import br.com.senaijandira.mybooks.ViewHolder;
import br.com.senaijandira.mybooks.db.MyBooksDatabase;
import br.com.senaijandira.mybooks.model.Livro;
import br.com.senaijandira.mybooks.model.LivrosLidos;
import br.com.senaijandira.mybooks.model.LivrosParaLer;

public class LivrosAdapter extends RecyclerView.Adapter<ViewHolder> {

    private MyBooksDatabase myBooksDb;

    private List<Livro> livros;
    private Context ctx;

    public LivrosAdapter(Context ctx, List<Livro> livros, MyBooksDatabase myBooksDb ){

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
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final Livro livro = livros.get(position);

        //Setando os valores
        holder.imgLivroCapa.setImageBitmap(Utils.toBitmap(livro.getCapa()));
        holder.txtLivroTitulo.setText(livro.getTitulo());
        holder.txtLivroDescricao.setText(livro.getDescricao());

        holder.imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                popupMenu(v, livro, position);
            }
        });
    }

    @Override //total de itens da lista
    public int getItemCount() {

        //se livros não for nulo, retorne quantos livros tem, se for, retorne 0
        return livros != null ? livros.size() : 0;
    }

    //Médoto do popup menu de cada item da lista;
    private void popupMenu(View v,final Livro livro,final int position){

        final PopupMenu popup = new PopupMenu(v.getContext(), v);

        popup.getMenuInflater().inflate(R.menu.menu_list_item_livros, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()){
                    case R.id.menu_item_delete:
                        deletarLivro(livro, position);
                        break;
                    case R.id.menu_item_edit:
                        break;
                    case R.id.menu_item_ler:
                        paraLer(new LivrosParaLer(livro.getId()));
                        break;
                    case R.id.menu_item_lidos:
                        paraLidos(new LivrosLidos(livro.getId()));
                        break;
                }
                return false;
            }
        });
        popup.show();
    }

    //método para deletar um livro da lista principal
    private void deletarLivro(final Livro livro,final int position){

        AlertDialog.Builder alert = new AlertDialog.Builder(ctx);
        alert.setTitle("Aviso!");
        alert.setMessage("Tem certeza que deseja apagar este livro?");

        alert.setNegativeButton("Voltar", null);
        alert.setPositiveButton("Apagar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                myBooksDb.daoLivro().deletar(livro);

                //remove livro da lista
                livros.remove(livro);
                notifyItemRemoved(position);
            }
        });
        alert.create().show();
    }

    //Médoto para adicionar um livro na lista de livros lidos
    private void paraLidos(LivrosLidos lidos){
        myBooksDb.daoLivrosLidos().inserir(lidos);
    }

    //Médoto para adicionar um livro na lista de livros para ler
    private void paraLer(LivrosParaLer ler){
        myBooksDb.daoLivrosParaLer().inserir(ler);
    }
}