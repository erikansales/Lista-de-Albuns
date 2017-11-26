package com.example.web.listadealbuns.view;

import android.app.Activity;
import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.web.listadealbuns.R;
import com.example.web.listadealbuns.model.bean.Album;
import com.example.web.listadealbuns.model.dao.AlbumDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Erika_Douglas on 26/11/2017.
 */

enum TipoDeDetalhe {
    EDICAO,
    EXCLUSAO;
}

public class AlbumAdapter extends BaseAdapter implements View.OnClickListener {

    private AlbumDao dao = AlbumDao.manager;
    private Map<Integer, Long> mapa;
    private boolean trocouLayout = false;
    private boolean apagar = false;
    private Long idApagar;
    private Activity activity;

    public AlbumAdapter() {
        criaMapa();
    }

    @Override
    public void notifyDataSetChanged() {
        criaMapa();
        super.notifyDataSetChanged();
    }

    private void criaMapa() {
        mapa = new HashMap<>();

        List<Album> lista = dao.getLista();
        for (int linha = 0; linha < lista.size(); linha++) {
            Album album = lista.get(linha);
            mapa.put(linha, album.getId());

        }
    }

    public void trocouOLayout(TipoDeDetalhe tipo) {
        if (tipo == TipoDeDetalhe.EDICAO) {
            trocouLayout = true;
            apagar = false;
        } else {
            trocouLayout = true;
            apagar = true;
        }
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return mapa.size();
    }

    @Override
    public Object getItem(int id) {
        return dao.getAlbum((long) id);
    }

    @Override
    public long getItemId(int linha) {
        return mapa.get(linha);
    }

    @Override
    public View getView(int linha, View view, ViewGroup viewGroup) {
        ConstraintLayout layout;

        if (view == null || trocouLayout) {

            Context context = viewGroup.getContext();

            LayoutInflater inflater =
                    (LayoutInflater) context
                            .getSystemService(
                                    Context.LAYOUT_INFLATER_SERVICE);

            layout = new ConstraintLayout(context);
            if (!apagar) {
                inflater.inflate(R.layout.activity_item, layout);
            } else {
                inflater.inflate(R.layout.activity_item_seleciona, layout);
            }
        } else {
            layout = (ConstraintLayout) view;
        }

        TextView tvBanda = layout.findViewById(R.id.txt_banda);
        TextView tvAlbum = layout.findViewById(R.id.txt_album);
        TextView tvGenero = layout.findViewById(R.id.txt_genero);
        TextView tvData = layout.findViewById(R.id.txt_data);

        Long id = mapa.get(linha);

        Album album = dao.getAlbum(id);

        tvBanda.setText(album.getBanda());
        tvAlbum.setText(album.getAlbum());
        tvGenero.setText(album.getGenero());
        tvData.setText(album.getData().toString());

        if (apagar) {
            CheckBox checkBox = layout.findViewById(R.id.checkBox);
            checkBox.setTag(album.getId());
            checkBox.setOnClickListener(this);
        }


        return layout;
    }


    @Override
    public void onClick(View view) {
        Long id = (Long) view.getTag();

        Album album = dao.getAlbum(id);

        if (album == null) {
            return;
        }
        album.setDel(!album.isDel());
        Log.d("AlbumAdapter", "Album marcado para exclusão [" +
                album.isDel() +
                "] id: " + album.getBanda());

        dao.salvar(album);
    }
}