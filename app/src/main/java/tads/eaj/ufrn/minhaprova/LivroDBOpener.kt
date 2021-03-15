package tads.eaj.ufrn.minhaprova

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import java.sql.Array

class LivroDBOpener(context: Context) : SQLiteOpenHelper(context, LivroContrato.DATABASE_NAME, null, LivroContrato.DATA_BASE_VERSION) {
    val SQL_CREATE_TABLE =
            "CREATE TABLE ${LivroContrato.LivroEntry.TABLE_NAME}" +
                    " (${BaseColumns._ID} INTEGER PRIMARY KEY," +
                    " ${LivroContrato.LivroEntry.NOME} TEXT," +
                    " ${LivroContrato.LivroEntry.AUTOR} TEXT," +
                    " ${LivroContrato.LivroEntry.ANO} INTEGER," +
                    " ${LivroContrato.LivroEntry.NOTA} REAL" +
                    ")"

    val SQL_DROP_TABLE =
            "DROP TABLE ${LivroContrato.LivroEntry.TABLE_NAME}"

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (oldVersion != newVersion){
            db?.execSQL(SQL_DROP_TABLE)
            db?.execSQL(SQL_CREATE_TABLE)
        }
    }

    fun insert(livro: Livro){
        var bd: SQLiteDatabase = writableDatabase

        try {
            var values = ContentValues()

            values.put(LivroContrato.LivroEntry.NOME, livro.nome)
            values.put(LivroContrato.LivroEntry.AUTOR, livro.autor)
            values.put(LivroContrato.LivroEntry.ANO, livro.ano)
            values.put(LivroContrato.LivroEntry.NOTA, livro.nota)

            bd.insert(LivroContrato.LivroEntry.TABLE_NAME, null, values)
        } finally {
            bd.close()
        }
    }

    fun findById(id: Int): Livro {
        var bd: SQLiteDatabase = readableDatabase

        try {
            var selection = "${BaseColumns._ID} = ?"
            var whereArgs = arrayOf("${id}")

            val cursor: Cursor = bd.query(LivroContrato.LivroEntry.TABLE_NAME, null, selection, whereArgs, null, null, null, null)
            cursor.moveToFirst()

            val idLivro = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID))
            val nome = cursor.getString(cursor.getColumnIndex(LivroContrato.LivroEntry.NOME))
            val autor = cursor.getString(cursor.getColumnIndex(LivroContrato.LivroEntry.AUTOR))
            val ano = cursor.getInt(cursor.getColumnIndex(LivroContrato.LivroEntry.ANO))
            val nota = cursor.getFloat(cursor.getColumnIndex(LivroContrato.LivroEntry.NOTA))

            var livro = Livro(idLivro, nome, autor, ano, nota)

            return livro
        } finally {
            bd.close()
        }
    }

    fun findAll(): ArrayList<Livro>{
        var bd: SQLiteDatabase = readableDatabase

        try {
            val cursor: Cursor = bd.query(LivroContrato.LivroEntry.TABLE_NAME, null, null, null, null, null, null,null)
            var listaLivros = ArrayList<Livro>()

            while (cursor.moveToNext()){
                val idLivro = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID))
                val nome = cursor.getString(cursor.getColumnIndex(LivroContrato.LivroEntry.NOME))
                val autor = cursor.getString(cursor.getColumnIndex(LivroContrato.LivroEntry.AUTOR))
                val ano = cursor.getInt(cursor.getColumnIndex(LivroContrato.LivroEntry.ANO))
                val nota = cursor.getFloat(cursor.getColumnIndex(LivroContrato.LivroEntry.NOTA))

                var livro = Livro(idLivro, nome, autor, ano, nota)
                listaLivros.add(livro)
            }

            return listaLivros
        } finally {
            bd.close()
        }
    }
}