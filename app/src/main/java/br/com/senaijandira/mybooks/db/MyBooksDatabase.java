package br.com.senaijandira.mybooks.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import br.com.senaijandira.mybooks.dao.LivroDao;
import br.com.senaijandira.mybooks.dao.LivrosLidosDao;
import br.com.senaijandira.mybooks.dao.LivrosParaLerDao;
import br.com.senaijandira.mybooks.model.Livro;
import br.com.senaijandira.mybooks.model.LivrosLidos;
import br.com.senaijandira.mybooks.model.LivrosParaLer;

@Database(entities = {Livro.class, LivrosParaLer.class, LivrosLidos.class}, version = 2)
public abstract class MyBooksDatabase extends RoomDatabase {

    public abstract LivroDao daoLivro();
    public abstract LivrosLidosDao daoLivrosLidos();
    public abstract LivrosParaLerDao daoLivrosParaLer();
}
