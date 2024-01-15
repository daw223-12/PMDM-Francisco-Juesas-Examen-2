package com.example.franciscojuesasexamen2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.os.bundleOf
import kotlin.random.Random

class MainActivity : AppCompatActivity(), Comunicador {
    private lateinit var dbHandler: DatabaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializa el controlador de la base de datos.
        dbHandler = DatabaseHelper(this)

        if (savedInstanceState == null) {
            var fragment1Class = fragment1()
            val bundle = bundleOf("ListaTareas" to dbHandler.getAllTareas())
            fragment1Class.arguments = bundle
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
        //BOTON AGREGAR AGREGA COSAS A LA BBDD DE FORMA MEDIO ALEATORIA PARA TENER CON QUÉ COMPARAR
        findViewById<Button>(R.id.btnAgregar).setOnClickListener({
            val nombre = Random.nextInt(1, 250000).toString()
            val fecha = Random.nextInt(1,  250000).toString()
            val estado = Random.nextInt(1,  250000)

            dbHandler.addTarea(DataClassTarea(nombre, fecha, estado))

            supportFragmentManager.beginTransaction()
                // Reemplaza el contenido del contenedor (identificado por R.id.containerFragmentoA) con una instancia de FragmentoA.
                // Este es el proceso de agregar el fragmento a la actividad.
                .replace(R.id.fragment1Layout, fragment1())
                // Confirma la transacción. Hasta que no se llama a commit, los cambios no son efectivos.
                .commit()
        })


    }
    override fun enviarDatos(datos: String) {
        val fragmentoB = supportFragmentManager.findFragmentById(R.id.fragment2Layout) as? fragment2
        fragmentoB?.actualizarTexto(datos)
    }

}