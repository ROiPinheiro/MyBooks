package br.com.senaijandira.mybooks.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class LivrosLidos {

    @PrimaryKey(autoGenerate = true)
    private int idPk;

    private int idLivros;

    @Ignore
    public LivrosLidos(){

    }

    public LivrosLidos(int idLivros){
        this.idLivros = idLivros;
    }

    public int getIdPk() {
        return idPk;
    }

    public void setIdPk(int idLivrosLidos) {
        this.idPk = idLivrosLidos;
    }

    public int getIdLivros() {
        return idLivros;
    }

    public void setIdLivros(int idLivros) {
        this.idLivros = idLivros;
    }
}
