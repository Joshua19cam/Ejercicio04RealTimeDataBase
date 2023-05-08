package com.example.realtimeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue

class ListadoActivity : AppCompatActivity() {
    //Declaraciones
    lateinit var refBaseDatos: DatabaseReference //Real Time DataBase
    var listaEmpleados = ArrayList<String>()// Listado de empleados
    lateinit var arrayAdapter: ArrayAdapter<String>// Mostrar - items
    lateinit var lvEmpleados: ListView //Donde se van a mostrar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listado)

        //Referencias
        lvEmpleados = findViewById(R.id.lvEmpleadosV)
        refBaseDatos = FirebaseDatabase.getInstance().getReference("empleados")
        arrayAdapter = ArrayAdapter(this,
            android.R.layout.simple_list_item_1,
            listaEmpleados)

        lvEmpleados.adapter = arrayAdapter

        //Proceso - función
        leerDatosRTD()
    }

    private fun leerDatosRTD() {
        //Leer la primera vez
        refBaseDatos.get().addOnSuccessListener {
            listaEmpleados.clear()
            it.children.forEach{empleado->
                val empleadoHM = empleado.getValue<HashMap<String,Any>>()
                val empleadoNombre = empleadoHM?.get("nombre")
                listaEmpleados.add(empleadoNombre.toString())
            }
            arrayAdapter.notifyDataSetChanged()
        }
        //Escuchar los cambios en las coleccion empleados
        refBaseDatos.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                //Escuchando - Agregar, Actualizar o Eliminar
                listaEmpleados.clear()
                snapshot.children.forEach{
                    val empleadoHM = it.getValue<HashMap<String,Any>>()
                    val empleadoNombre = empleadoHM?.get("nombre")
                    listaEmpleados.add(empleadoNombre.toString())
                }
                arrayAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }
}