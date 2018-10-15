package br.com.senaijandira.mybooks.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import br.com.senaijandira.mybooks.model.Livro;
import br.com.senaijandira.mybooks.model.LivroELidos;
import br.com.senaijandira.mybooks.model.LivrosLidos;

@Dao
public interface LivrosLidosDao {

    @Insert
    void inserir(LivrosLidos l);

    @Delete
    void deletar(LivrosLidos l);

    @Query("SELECT * FROM LivrosLidos INNER JOIN Livro ON LivrosLidos.idLivros = Livro.id")
    List<LivroELidos> selecionarTodos();

    @Query("SELECT * FROM LivrosLidos WHERE idLivros = :id")
    boolean selecioarUmLivro(int id);

    @Query("SELECT * FROM LivrosLidos WHERE idLivros = :id")
    LivrosLidos selecionarPorId(int id);
}