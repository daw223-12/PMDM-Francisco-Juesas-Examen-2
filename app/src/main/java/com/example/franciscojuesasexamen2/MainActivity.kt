package com.example.franciscojuesasexamen2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity(), Comunicador {
    private lateinit var dbHandler: DatabaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializa el controlador de la base de datos.
        dbHandler = DatabaseHelper(this)

        if (savedInstanceState == null) {
            // Comienza una transacción de fragmentos. Las transacciones se utilizan para añadir, reemplazar o realizar otras operaciones con fragmentos.
            supportFragmentManager.beginTransaction()
                // Reemplaza el contenido del contenedor (identificado por R.id.containerFragmentoA) con una instancia de FragmentoA.
                // Este es el proceso de agregar el fragmento a la actividad.
                .replace(R.id.fragment1Layout, fragment1())
                // Confirma la transacción. Hasta que no se llama a commit, los cambios no son efectivos.
                .commit()
        }

        // Añadir FragmentoB
        // Repite el proceso para el FragmentoB. Esta vez se coloca en un contenedor diferente dentro del layout de la actividad.
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment2Layout, fragment2())
                .commit()
        }
        dbHandler.getAllDiscos();
    }

    override fun enviarDatos(datos: String) {
        val fragmentoB = supportFragmentManager.findFragmentById(R.id.fragment2Layout) as? fragment2
        fragmentoB?.actualizarTexto(datos)
    }

}