package br.com.senaijandira.mybooks.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class LivrosParaLer {

    @PrimaryKey(autoGenerate = true)
    private int idPk;

    private int idLivros;

    @Ignore
    public LivrosParaLer(){

    }

    public LivrosParaLer(int idLivros){
        this.idLivros = idLivros;
    }

    public int getIdPk() {
        return idPk;
    }

    public void setIdPk(int idLivrosParaLer) {
        this.idPk = idLivrosParaLer;
    }

    public int getIdLivros() {
        return idLivros;
    }

    public void setIdLivros(int idLivros) {
        this.idLivros = idLivros;
    }
}
