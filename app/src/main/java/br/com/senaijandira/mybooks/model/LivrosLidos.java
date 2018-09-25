package br.com.senaijandira.mybooks.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class LivrosLidos {

    @PrimaryKey(autoGenerate = true)
    private int idLivrosLidos;

    private int idLivros;

    public int getIdLivrosLidos() {
        return idLivrosLidos;
    }

    public void setIdLivrosLidos(int idLivrosLidos) {
        this.idLivrosLidos = idLivrosLidos;
    }

    public int getIdLivros() {
        return idLivros;
    }

    public void setIdLivros(int idLivros) {
        this.idLivros = idLivros;
    }
}
