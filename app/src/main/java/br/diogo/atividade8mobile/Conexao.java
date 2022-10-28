package br.diogo.atividade8mobile;

import android.content.*;
import android.database.sqlite.*;

public class Conexao extends SQLiteOpenHelper
{
    private static final String name="banco.db";
    private static final int version = 1;

    public Conexao(Context context)
    {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE pessoa(id integer primary key autoincrement,nome varchar(50),cpf varchar(50),telefone varchar(50))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
    }
}
