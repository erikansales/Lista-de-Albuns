package com.example.web.listadealbuns.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.web.listadealbuns.R;
import com.example.web.listadealbuns.model.dao.AlbumDao;

public class MainActivity extends AppCompatActivity
        implements DialogInterface.OnClickListener, AdapterView.OnItemClickListener {
    private AlbumDao dao = AlbumDao.manager;
    private ListView listView;
    private MenuItem itEditar;
    private MenuItem itApagar;
    private AlbumAdapter itemLista;

    private final int EDITA_ALBUM = 0;
    private final int NOVO_ALBUM = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        itemLista = new AlbumAdapter();

        listView = (ListView) findViewById(R.id.listaAlbuns);

        listView.setAdapter(itemLista);

        listView.setOnItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_menu_alterar, menu);
        itEditar = (MenuItem) menu.findItem(R.id.acao_editar);
        itApagar = (MenuItem) menu.findItem(R.id.acao_apagar);

        itApagar.setVisible(false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.acao_editar:
                itemLista.trocouOLayout(TipoDeDetalhe.EXCLUSAO);
                itEditar.setVisible(false);
                itApagar.setVisible(true);
                break;

            case R.id.acao_apagar:
                if(dao.temAlbumPraApagar()){
                    AlertDialog.Builder alerta = new AlertDialog.Builder(this);
                    alerta.setMessage("Confirma a exclusão dos Albuns?");
                    alerta.setPositiveButton("Sim", this);
                    alerta.setNegativeButton("Não", null);
                    alerta.create();
                    alerta.show();
                }else{
                    mudaLayout();
                }
                break;
        }

        return true;
    }

    private void mudaLayout() {
        itemLista.trocouOLayout(TipoDeDetalhe.EDICAO);
        itEditar.setVisible(true);
        itApagar.setVisible(false);
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        dao.apagarOsSelecionados();
        mudaLayout();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int linha, long id) {
        Intent tela = new Intent(getBaseContext(), EditarCadastrar.class);
        tela.putExtra("id", id);
        startActivityForResult(tela, EDITA_ALBUM);
    }


    public void adicionaAlbum(View view) {
        Intent intent = new Intent(getBaseContext(), EditarCadastrar.class);

        startActivityForResult(intent, NOVO_ALBUM);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String msg = "A " + (requestCode == EDITA_ALBUM ? "alteração" : "inclusão") +
                " do Album foi ";

        if(resultCode == RESULT_OK){
            itemLista.notifyDataSetChanged();
            msg += "um sucesso";
        }else{
            msg += "cancelada";
        }
        Snackbar.make(listView, msg, Snackbar.LENGTH_LONG).show();
    }
}
