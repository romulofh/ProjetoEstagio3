package com.example.desenvolvedor.consumidor;

import java.io.Serializable;

/**
 * Created by Desenvolvedor on 31/10/2016.
 */
public class Festa implements Serializable {

    private static final long serialVersionUID = 1L;
    private String nome;
    private String data;
    private String local;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String toString() {
        return  nome;
    }

}
