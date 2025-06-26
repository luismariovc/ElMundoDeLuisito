package com.example.appsplashlogin.home

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.appsplashlogin.R
import com.google.firebase.auth.FirebaseAuth

class HomeActivity : AppCompatActivity() {

    private lateinit var tvUserInfo: TextView
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        tvUserInfo = findViewById(R.id.tvUserInfo)
        auth = FirebaseAuth.getInstance()

        val currentUser = auth.currentUser

        if (currentUser != null) {
            // Mostrar correo registrado
            tvUserInfo.text = "Bienvenido, ${currentUser.email}"
            // Si tienes el nombre guardado en el perfil, puedes usar:
            // tvUserInfo.text = "Bienvenido, ${currentUser.displayName}"
        } else {
            tvUserInfo.text = "Usuario no autenticado"
        }
    }
}