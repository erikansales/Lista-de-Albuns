package com.example.web.listadealbuns.view;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.web.listadealbuns.R;
import com.example.web.listadealbuns.model.bean.Album;
import com.example.web.listadealbuns.model.dao.AlbumDao;
import com.example.web.listadealbuns.utils.DateDialog;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Erika_Douglas on 26/11/2017.
 */

public class EditarCadastrar  extends AppCompatActivity {
    private AlbumDao dao = AlbumDao.manager;
    private EditText edBanda;
    private EditText edAlbum;
    private EditText edGenero;
    private EditText edData;
    private Album album;
    private Calendar calendar = Calendar.getInstance();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_edicao);

        edBanda = (EditText) findViewById(R.id.edt_banda);
        edAlbum = (EditText) findViewById(R.id.edt_album);
        edGenero = (EditText) findViewById(R.id.edt_genero);
        edData = (EditText) findViewById(R.id.edt_lancamento);

        Intent intent = getIntent();

        if (intent != null) {
            Bundle dados = intent.getExtras();
            if (dados != null) {
                long id = dados.getLong("id");
                album = dao.getAlbum(id);
                if (album != null) {
                    edBanda.setText(album.getBanda());
                    edAlbum.setText(album.getAlbum());
                    edGenero.setText(album.getGenero());
                    edData.setText(album.getData().toString());
                }
            }
        }

        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_menu_salvar, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                setResult(Activity.RESULT_CANCELED);
                break;
            case R.id.acao_salvar:
                if (album == null) {
                    album = new Album();
                }

                album.setBanda(edBanda.getText().toString());
                album.setAlbum(edAlbum.getText().toString());
                album.setGenero(edGenero.getText().toString());
                album.setData(new Date(edData.getText().toString()));

                dao.salvar(album);

                setResult(Activity.RESULT_OK);
                break;

        }
        finish();

        return true;

    }

    public void abreCalendario(View view) {
        selecionaData(view);
    }


    public void selecionaData(View view){
        DateDialog.makeDialog(calendar, edData)
                .show(getFragmentManager(), "Data de Lançamento");

    }

}
