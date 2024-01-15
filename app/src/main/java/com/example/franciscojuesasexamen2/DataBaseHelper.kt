package com.example.franciscojuesasexamen2

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

// Clase DatabaseHelper que extiende SQLiteOpenHelper para manejar la base de datos de la aplicación.
class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    // Bloque companion object para definir constantes que serán usadas en toda la clase.
    // Son como los valores estáticos en Java
    companion object {
        // Nombre de la base de datos.
        private const val DATABASE_NAME = "TareasBBDD"
        // Versión de la base de datos, útil para manejar actualizaciones esquemáticas.
        private const val DATABASE_VERSION = 1
        // Nombre de la tabla donde se almacenarán los discos.
        private const val TABLE_DISCOS = "tareasPendientes"
        // Nombres de las columnas de la tabla.
        private const val KEY_NOMBRE = "nombre"
        private const val KEY_FECHA = "fecha"
        private const val KEY_ESTADO = "completada"
    }

    // Método llamado cuando la base de datos se crea por primera vez.
    override fun onCreate(db: SQLiteDatabase) {
        // Define la sentencia SQL para crear la tabla de discos.
        val createDiscosTable = ("CREATE TABLE " + TABLE_DISCOS + "("
                + KEY_NOMBRE + " TEXT PRIMARY KEY," + KEY_FECHA + " TEXT,"
                + KEY_ESTADO + " INTEGER" + ")")
        // Ejecuta la sentencia SQL para crear la tabla.
        db.execSQL(createDiscosTable)
    }

    // Método llamado cuando se necesita actualizar la base de datos, por ejemplo, cuando se incrementa DATABASE_VERSION.
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Elimina la tabla existente y crea una nueva.
        db.execSQL("DROP TABLE IF EXISTS $TABLE_DISCOS")
        onCreate(db)
    }

    fun getAllDiscos(): ArrayList<DataClassTarea> {
        // Lista para almacenar y retornar los discos.
        val tareasList = ArrayList<DataClassTarea>()
        // Consulta SQL para seleccionar todos los discos.
        val selectQuery = "SELECT  * FROM $TABLE_DISCOS"

        val db = this.readableDatabase
        var cursor: Cursor? = null
        try {
            // Ejecuta la consulta SQL.
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            // Maneja la excepción en caso de error al ejecutar la consulta.
            db.execSQL(selectQuery)
            return ArrayList()
        }

        // Variables para almacenar los valores de las columnas.
        var nombre: String
        var fecha: String
        var estado: Int

        // Itera a través del cursor para leer los datos de la base de datos.
        if (cursor.moveToFirst()) {
            do {
                // Obtiene los índices de las columnas.
                val nombreIndex = cursor.getColumnIndex(KEY_NOMBRE)
                val fechaIndex = cursor.getColumnIndex(KEY_FECHA)
                val estadoIndex = cursor.getColumnIndex(KEY_ESTADO)

                // Verifica que los índices sean válidos.
                if (nombreIndex != -1 && fechaIndex != -1 && estadoIndex != -1) {
                    // Lee los valores y los añade a la lista de discos.
                    nombre = cursor.getString(nombreIndex)
                    fecha = cursor.getString(fechaIndex)
                    estado = cursor.getInt(estadoIndex)

                    val tarea = DataClassTarea(nombre = nombre, fecha = fecha, estado = estado)
                    tareasList.add(tarea)
                }
            } while (cursor.moveToNext())
        }

        // Cierra el cursor para liberar recursos.
        cursor.close()
        return tareasList
    }
}