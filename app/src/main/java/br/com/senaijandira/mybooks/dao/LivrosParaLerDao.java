package br.com.senaijandira.mybooks.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import br.com.senaijandira.mybooks.model.LivroELidos;
import br.com.senaijandira.mybooks.model.LivrosParaLer;

@Dao
public interface LivrosParaLerDao {

    @Insert
    void inserir(LivrosParaLer l);

    @Update
    void atualizar(LivrosParaLer l);

    @Delete
    void deletar(LivrosParaLer l);

    @Query("SELECT * FROM LivrosParaLer")
    List<LivrosParaLer> selecionarTodos();

    @Query("SELECT * FROM LivrosParaLer INNER JOIN Livro ON LivrosParaler.idLivros = Livro.id")
    List<LivroELidos> selecionarLivro();
}