package br.com.senaijandira.mybooks.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import br.com.senaijandira.mybooks.model.Livro;

@Dao
public interface LivrosParaLerDao {

    @Insert
    void inserir(Livro l);

    @Update
    void atualizar(Livro l);

    @Delete
    void deletar(Livro l);

    @Query("SELECT * FROM livro")
    List<Livro> selecionarTodos();

}
