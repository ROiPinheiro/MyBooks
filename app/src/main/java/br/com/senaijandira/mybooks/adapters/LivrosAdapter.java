package br.com.senaijandira.mybooks.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import br.com.senaijandira.mybooks.EditarActivity;
import br.com.senaijandira.mybooks.R;
import br.com.senaijandira.mybooks.ViewHolder;
import br.com.senaijandira.mybooks.db.MyBooksDatabase;
import br.com.senaijandira.mybooks.model.Livro;
import br.com.senaijandira.mybooks.model.LivrosLidos;
import br.com.senaijandira.mybooks.model.LivrosParaLer;
import br.com.senaijandira.mybooks.utils.Utils;

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
    private void popupMenu(final View v, final Livro livro, final int position){

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
                        editarLivro(v, position);
                        break;
                    case R.id.menu_item_ler:
                        paraLer(livro , new LivrosParaLer(livro.getId()));
                        break;
                    case R.id.menu_item_lidos:
                        paraLidos(livro, new LivrosLidos(livro.getId()));
                        break;
                }
                return false;
            }
        });
        popup.show();
    }

    private void editarLivro(View v, int position){

        Bundle bundle = new Bundle();
        bundle.putInt("ID", livros.get(position).getId());

        Intent i = new Intent(v.getContext(), EditarActivity.class);
        i.putExtras(bundle);

        ctx.startActivity(i);
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

                //se o livro não estiver em nenhuma lista, pode ser apagado
                if(!verificarLivro(livro)){

                    myBooksDb.daoLivro().deletar(livro);

                    //remove livro da lista
                    livros.remove(position);
                    notifyItemRemoved(position);
                }
                else{
                    Toast.makeText(ctx,"Este livro está em 'PARA LER' ou 'LIDOS', não pode ser apagado",Toast.LENGTH_LONG).show();
                }
            }
        });
        alert.create().show();
    }

    private boolean verificarLivro(Livro livro){

        //se alguma das consultas retornar true quer dizer que esse livro está em outra lista
        return (myBooksDb.daoLivrosParaLer().selecioarUmLivro(livro.getId()) || myBooksDb.daoLivrosLidos().selecioarUmLivro(livro.getId()));
    }

    //Médoto para adicionar um livro na lista de livros lidos
    private void paraLidos(final Livro livro, final LivrosLidos lidos){

        //se o livro não estiver em livros lidos, pode ser adicionado
        if(!myBooksDb.daoLivrosLidos().selecioarUmLivro(livro.getId())){

            if(!myBooksDb.daoLivrosParaLer().selecioarUmLivro(livro.getId())){
                myBooksDb.daoLivrosLidos().inserir(lidos);

                Toast.makeText(ctx,"Livro adicionado em 'Lidos' com sucesso",Toast.LENGTH_SHORT).show();
            }
            else{
                String msg = "Este livro já está em 'Para ler', caso realmente queira adicionar em 'Lidos' ele será retirado de 'Para Ler'";

                AlertDialog.Builder alert = new AlertDialog.Builder(ctx);
                alert.setTitle("Aviso!");
                alert.setMessage(msg);

                alert.setNegativeButton("Voltar", null);
                alert.setPositiveButton("Confirmar mudança", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //após a confirmação o livro é inserido na tabela lidos
                        myBooksDb.daoLivrosLidos().inserir(lidos);

                        //recebe o mesmo livro da tabela para ler
                        LivrosParaLer ler = myBooksDb.daoLivrosParaLer().selecionarPorId(livro.getId());

                        //e retira da tabela para ler
                        myBooksDb.daoLivrosParaLer().deletar(ler);
                    }
                });

                alert.create().show();
            }
        }
        else{
            Toast.makeText(ctx,"Este livro já está em 'Lidos', não pode ser adicionado novamente",Toast.LENGTH_LONG).show();
        }
    }

    //Médoto para adicionar um livro na lista de livros para ler
    private void paraLer(final Livro livro, final LivrosParaLer ler){

        //se o livro não estiver em livros para ler, pode ser adicionado
        if(!myBooksDb.daoLivrosParaLer().selecioarUmLivro(livro.getId())){

            if(!myBooksDb.daoLivrosLidos().selecioarUmLivro(livro.getId())){
                myBooksDb.daoLivrosParaLer().inserir(ler);

                Toast.makeText(ctx,"Livro adicionado em 'Para Ler' com sucesso",Toast.LENGTH_SHORT).show();
            }
            else{

                String msg = "Este livro já está em 'Lidos', caso realmente queira adicionar em 'Para Ler' ele será retirado de 'Lidos'";

                AlertDialog.Builder alert = new AlertDialog.Builder(ctx);
                alert.setTitle("Aviso!");
                alert.setMessage(msg);

                alert.setNegativeButton("Voltar", null);
                alert.setPositiveButton("Confirmar mudança", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //após a confirmação o livro é inserido na tabela lidos
                        myBooksDb.daoLivrosParaLer().inserir(ler);

                        //recebe o mesmo livro da tabela para ler
                        LivrosLidos lidos = myBooksDb.daoLivrosLidos().selecionarPorId(livro.getId());

                        //e retira da tabela para ler
                        myBooksDb.daoLivrosLidos().deletar(lidos);
                    }
                });

                alert.create().show();
            }
        }
        else{
            Toast.makeText(ctx,"Este livro já está em 'Para ler', não pode ser adicionado novamente",Toast.LENGTH_LONG).show();
        }
    }
}