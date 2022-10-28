package br.diogo.atividade8mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.app.*;
import android.content.*;
import android.os.Bundle;
import android.view.*;
import android.widget.*;

import java.util.*;

public class ListarPessoaActivity extends AppCompatActivity
{
    private ListView listview;

    private PessoaDAO dao;
    private List<Pessoa> pessoas;
    private List<Pessoa> pessoasFiltrados = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_pessoa_activity);

        listview = findViewById(R.id.lvPessoas);
        dao = new PessoaDAO(this);
        pessoas = dao.obterTodos();
        pessoasFiltrados.addAll(pessoas);
        ArrayAdapter<Pessoa> adaptador = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,pessoasFiltrados);
        listview.setAdapter(adaptador);
        registerForContextMenu(listview);
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.menu_principal, menu);

        SearchView sv = (SearchView)menu.findItem(R.id.app_bar_search).getActionView();
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s)
            {
                procuraPessoa(s);
                return false;
            }
        });
        return true;
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu,v,menuInfo);
        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.menu_contexto,menu);
    }

    public void procuraPessoa(String nome)
    {
        pessoasFiltrados.clear();

        for(Pessoa p : pessoas)
        {
            if(p.getNome().toLowerCase().contains(nome.toLowerCase()))
            {
                pessoasFiltrados.add(a);
            }
        }
        listview.invalidateViews();
    }

    public void cadastrar(MenuItem item)
    {
        Intent it = new Intent(this, MainActivity.class);
        startActivity(it);
    }

    public void excluir(MenuItem item)
    {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final Pessoa pessoaExcluir = pessoasFiltrados.get(menuInfo.position);
        AlertDialog dialog = new AlertDialog.Builder(this).setTitle("Atenção").setMessage("Tem certeza que deseja excluir" + pessoaExcluir.getNome()+"?")
                .setNegativeButton("Nao", null).setPositiveButton("Sim", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        pessoasFiltrados.remove(pessoaExcluir);
                        pessoas.remove(pessoaExcluir);
                        dao.excluir(pessoaExcluir);
                        listview.invalidateViews();
                    }
                }).create();
        dialog.show();
    }

    public void atualizar(MenuItem item)
    {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final Pessoa pessoaAtualizar = pessoasFiltrados.get(menuInfo.position);

        Intent it = new Intent(this, MainActivity.class);
        it.putExtra("pessoa", pessoaAtualizar);
        startActivity(it);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        pessoas = dao.obterTodos();
        pessoasFiltrados.clear();
        pessoasFiltrados.addAll(pessoas);
        listview.invalidateViews();
    }
}