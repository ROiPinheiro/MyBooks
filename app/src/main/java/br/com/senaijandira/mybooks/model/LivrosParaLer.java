package br.com.senaijandira.mybooks.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class LivrosParaLer {

    @PrimaryKey(autoGenerate = true)
    private int idLivrosParaLer;

    private int idLivros;


    public int getIdLivrosParaLer() {
        return idLivrosParaLer;
    }

    public void setIdLivrosParaLer(int idLivrosParaLer) {
        this.idLivrosParaLer = idLivrosParaLer;
    }

    public int getIdLivros() {
        return idLivros;
    }

    public void setIdLivros(int idLivros) {
        this.idLivros = idLivros;
    }
}
