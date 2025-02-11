package br.iftm.edu.tspi.pmvc.projeto_crud.domain;

public class Genero {
    private Integer codigo;
    private String descricao;
    private String data;
    private String pais;

    public Genero() {
    }

    public Genero(Integer codigo){
        this.codigo = codigo;
    }

    public Genero(Integer codigo, String descricao, String data, String pais) {
        this.codigo = codigo;
        this.descricao = descricao;
        this.data = data;
        this.pais = pais;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Genero other = (Genero) obj;
        if (codigo == null) {
            if (other.codigo != null)
                return false;
        } else if (!codigo.equals(other.codigo))
            return false;
        return true;
    }
}

