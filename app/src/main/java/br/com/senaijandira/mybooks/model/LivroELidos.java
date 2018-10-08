package br.com.senaijandira.mybooks.model;

public class LivroELidos {

    private int idPk;
    private int idLivros;

    private int id;
    private byte[] capa;
    private String descricao;
    private String titulo;

    public LivroELidos(){

    }

    public int getIdPk() {
        return idPk;
    }

    public void setIdPk(int idLivrosLidos) {
        this.idPk = idLivrosLidos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdLivros() {
        return idLivros;
    }

    public void setIdLivros(int idLivros) {
        this.idLivros = idLivros;
    }

    public byte[] getCapa() {
        return capa;
    }

    public void setCapa(byte[] capa) {
        this.capa = capa;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}
