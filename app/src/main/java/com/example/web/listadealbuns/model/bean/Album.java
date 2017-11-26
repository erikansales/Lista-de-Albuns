package com.example.web.listadealbuns.model.bean;

import android.support.annotation.NonNull;

import java.util.Date;

/**
 * Created by Erika_Douglas on 26/11/2017.
 */

public class Album implements Comparable<Album>{
    private Long id;
    private String banda;
    private String album;
    private String genero;
    private Date data;
    private boolean del;

    //Retorna o Objeto Album
    public Album (){    }

    //Retorna um Album especifico conforme o ID
    public Album(Long id) {
        this.id = id;
    }

    //Responsável por permitir fixar valores para albuns e iniciar o app com conteúdo
    public Album(Long id, String banda, String album, String genero, Date data) {
        this.id = id;
        this.banda = banda;
        this.album = album;
        this.genero = genero;
        this.data = data;
    }

    //Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBanda() {
        return banda;
    }

    public void setBanda(String banda) {
        this.banda = banda;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public boolean isDel() {
        return del;
    }

    public void setDel(boolean del) {
        this.del = del;
    }

    //Equals e Hash Code para utilizar Collections

    /**
     * Tabela Hash [ou Tabela de Dispersão ou Tabela de Espalhamento] é uma tabela onde as
     * informações são armazenadas conforme um “numero hash” calculado com base nas propriedades da informação.
     * Isso permite que seja muito rápido recuperar uma informação na tabela.
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // Mesmo endereço
        if (o == null || getClass() != o.getClass()) return false; // null ou tipos diferentes

        Album album = (Album) o;

        if (!id.equals(album.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public int compareTo(@NonNull Album outro) {
        return banda.toLowerCase().compareTo(outro.banda.toLowerCase());
    }
}
