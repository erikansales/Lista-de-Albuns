package com.example.web.listadealbuns.model.dao;

import com.example.web.listadealbuns.model.bean.Album;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by Erika_Douglas on 26/11/2017.
 */

public class AlbumDao {
    public static AlbumDao manager = new AlbumDao();

    private List<Album> lista;

    private long id = 0;

    private AlbumDao (){
        lista = new ArrayList<>();

        lista.add(new Album (id++,"Spice Girls", "SpiceWorld", "Pop", new GregorianCalendar(1997, Calendar.MAY, 13).getTime()));

        lista.add(new Album (id++, "AlterBridge", "Blackbird", "Rock", new GregorianCalendar(2007,Calendar.SEPTEMBER,05).getTime()));
    }

    public List<Album> getLista (){
        Collections.sort(lista);
        return Collections.unmodifiableList(lista);
    }

    public Album getAlbum(final Long id){

        Album oAlbum = null;
        for(Album obj : lista) {
            if (obj.getId() == id) {
                oAlbum = obj;
                break;
            }
        }
        // Pesquisa utilizando o recurso de Collections
        // necessita que a classe tenha construtor especializado
        // alÃ©m da implementaÃ§Ã£o dos mÃ©todos equals e hashcode

        Album album = lista.get(lista.indexOf(new Album(id)));


        return oAlbum;
    }

    public void salvar(Album obj){

        if(obj.getId() == null){
            obj.setId(id++);
            lista.add(obj);

        }else{
            int posicao = lista.indexOf(new Album(obj.getId()));
            lista.set(posicao, obj);

        }
    }

    public void remover(Long id){
        lista.remove(new Album(id));

    }

    public void apagarOsSelecionados(){
        List<Album> albunsDel = new ArrayList<>();
        for(Album album : lista){
            if(album.isDel()){
                albunsDel.add(album);
            }
        }
        for(Album album : albunsDel){
            remover(album.getId());

        }
    }

    public boolean temAlbumPraApagar(){
        boolean temAlbumSelecionado = false;
        for(Album obj : lista){
            if(obj.isDel()){
                temAlbumSelecionado = true;
                break;
            }
        }
        return temAlbumSelecionado;
    }

    private Integer pegaPosicao(Long id){
        int i = 0;
        for(Album album : lista){
            if(album.getId() == id){
                return i;
            }
            i++;
        }
        return null;
    }
}
