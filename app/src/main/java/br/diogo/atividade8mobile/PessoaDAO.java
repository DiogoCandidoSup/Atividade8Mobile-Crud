package br.diogo.atividade8mobile;

import android.content.*;
import android.database.*;
import android.database.sqlite.*;

import java.util.*;

public class PessoaDAO
{
    private Conexao conexao;
    private SQLiteDatabase banco;

    public PessoaDAO(Context context)
    {
        conexao = new Conexao(context);
        banco = conexao.getWritableDatabase();
    }

    public long inserir(Pessoa pessoa)
    {
        ContentValues values = new ContentValues();
        values.put("nome", pessoa.getNome());
        values.put("cpf", pessoa.getCpf());
        values.put("telefone", pessoa.getTelefone());

        return banco.insert("pessoa", null,values);
    }

    public List<Pessoa> obterTodos()
    {
        List<Pessoa> pessoas = new ArrayList<>();

        Cursor cursor = banco.query("pessoa", new String[]{"id", "nome", "cpf", "telefone"},null, null, null, null, null);
        while (cursor.moveToNext())
        {
            Pessoa p = new Pessoa();

            p.setId(cursor.getInt(0));
            p.setNome(cursor.getString(1));
            p.setCpf(cursor.getString(2));
            p.setTelefone(cursor.getString(3));

            pessoas.add(p);
        }
        return pessoas;
    }

    public void atualizar(Pessoa pessoa)
    {
        ContentValues values = new ContentValues();

        values.put("nome", pessoa.getNome());
        values.put("cpf", pessoa.getCpf());
        values.put("telefone", pessoa.getTelefone());

        banco.update("pessoa", values, "id = ?",new String[]{pessoa.getId().toString()});
    }

    public void excluir(Pessoa pessoa)
    {
        banco.delete("pessoa", "id = ?",new String[]{pessoa.getId().toString()});
    }
}