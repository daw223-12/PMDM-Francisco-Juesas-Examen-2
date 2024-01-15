package com.example.franciscojuesasexamen2

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment


class fragment1:Fragment() {
    // Declaramos una variable comunicador de tipo Comunicador, que será utilizada para comunicarse con otras partes de la aplicación.
    // Inicialmente es null porque aún no se ha establecido la comunicación.
    private var comunicador: Comunicador? = null

    // El método onAttach se llama cuando el fragmento se asocia con su contexto, generalmente la actividad que lo contiene.
    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Se intenta convertir el contexto a tipo Comunicador. Si el contexto implementa la interfaz Comunicador, se asigna a la variable comunicador.
        comunicador = context as? Comunicador
    }

    // onCreateView se llama para que el fragmento cree su vista.
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Se infla el layout del fragmento. R.layout.fragment1_layout es un archivo XML que define la interfaz de usuario del fragmento.
        val view = inflater.inflate(R.layout.fragment1_layout, container, false)

        // Se busca el botón dentro de la vista inflada utilizando su ID.
        val listViewTareas = view.findViewById<LinearLayout>(R.id.listaTareasPendientes)
        for (i in 0 until 5)
        {
            var textView = TextView(context)
            textView.text = "Hijo "
            listViewTareas.addView(textView)



            textView.setOnClickListener( {
                comunicador?.enviarDatos("Datos desde Hijo"+i.toString())
            })
        }

        // Se devuelve la vista inflada.
        return view
    }
}