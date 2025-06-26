package com.example.appsplashlogin.register

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.appsplashlogin.R
import com.example.appsplashlogin.home.HomeActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.userProfileChangeRequest

class Register : AppCompatActivity() {

    private lateinit var etFirstName: EditText
    private lateinit var etLastName: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnRegister: Button

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        etFirstName = findViewById(R.id.etFirstName)
        etLastName = findViewById(R.id.etLastName)
        etPassword = findViewById(R.id.etPassword)
        btnRegister = findViewById(R.id.btnRegister)

        auth = FirebaseAuth.getInstance()

        btnRegister.setOnClickListener {
            val firstName = etFirstName.text.toString().trim()
            val lastName = etLastName.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (firstName.isEmpty() || lastName.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Generar email basado en nombre y apellido
            val email = "${firstName.lowercase()}.${lastName.lowercase()}@example.com"

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Actualizar perfil con nombre completo
                        val user = auth.currentUser
                        val profileUpdates = userProfileChangeRequest {
                            displayName = "$firstName $lastName"
                        }

                        user?.updateProfile(profileUpdates)
                            ?.addOnCompleteListener { updateTask ->
                                val message = if (updateTask.isSuccessful) {
                                    "Perfil actualizado con éxito"
                                } else {
                                    "Registro exitoso, pero error al actualizar nombre"
                                }
                                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

                                // Guardar estado de login en SharedPreferences
                                saveLoginState(true)

                                // Redirigir a HomeActivity
                                startActivity(Intent(this, HomeActivity::class.java))
                                finish()
                            }
                    } else {
                        Toast.makeText(this, "Error: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                    }
                }
        }
    }

    private fun saveLoginState(isLoggedIn: Boolean) {
        val sharedPref = getSharedPreferences("MyAppPrefs", MODE_PRIVATE)
        with(sharedPref.edit()) {
            putBoolean("isLoggedIn", isLoggedIn)
            apply()
        }
    }
}

