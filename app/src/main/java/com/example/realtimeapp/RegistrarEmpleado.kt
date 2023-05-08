package com.example.realtimeapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegistrarEmpleado : AppCompatActivity() {
    lateinit var etNombre: EditText
    lateinit var etRFC: EditText
    lateinit var etEmail: EditText
    lateinit var btnAgregar: Button
    lateinit var btnRegresar: Button

    lateinit var refBaseDatos: DatabaseReference

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar_empleado)

        //Referencias
        etNombre = findViewById(R.id.editTextNombre)
        etRFC = findViewById(R.id.editTextRFC)
        etEmail = findViewById(R.id.editTextEmail)
        btnAgregar = findViewById(R.id.buttonGuardar)
        btnRegresar = findViewById(R.id.buttonRegresar)

        refBaseDatos= FirebaseDatabase.getInstance().getReference("empleados")

        btnAgregar.setOnClickListener {
            guardarEmpleadoRTD()
        }

    }
    fun regresar(view: View){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
    private fun guardarEmpleadoRTD(){
        val nombre = etNombre.text.toString()
        val rfc = etRFC.text.toString()
        val email = etEmail.text.toString()
        //Esto crea hijos xd en la nube
        val idEmpleado = refBaseDatos.push().key!!
        // creamos una instancia de un empleado
        val empleado = Empleado(idEmpleado,nombre,rfc,email)

        refBaseDatos.child(idEmpleado).setValue(empleado)
            .addOnCompleteListener {
                Toast.makeText(this,"El registro se guardo en la nube",Toast.LENGTH_LONG).show()
            }
    }
    data class Empleado (val id: String?=null, val nombre: String?=null, val rfc: String?=null, val email: String?=null)

}