package com.example.realtimeapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun registrarEmpleado(view: View){
        //abrir la activity registrar
        val intent = Intent(this, RegistrarEmpleado::class.java)
        startActivity(intent)
    }
    fun listarEmpleados(view: View){
        //abrir la activity listar
        var intentListado = Intent(this,ListadoActivity::class.java)
        startActivity(intentListado)
    }
    fun salir(view: View){
        //terminar la app
        finishAffinity()
    }
}
