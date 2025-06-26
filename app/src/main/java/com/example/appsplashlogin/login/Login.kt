package com.example.appsplashlogin.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.appsplashlogin.R
import com.example.appsplashlogin.home.HomeActivity
import com.example.appsplashlogin.register.Register
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {

    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Verificar sesión activa
        val sharedPref = getSharedPreferences("MyAppPrefs", MODE_PRIVATE)
        val isLoggedIn = sharedPref.getBoolean("isLoggedIn", false)
        if (isLoggedIn) {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
            return
        }

        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        etUsername = findViewById(R.id.etUsername)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        val btnGoToRegister = findViewById<Button>(R.id.btnGoToRegister)

        auth = FirebaseAuth.getInstance()

        btnGoToRegister.setOnClickListener {
            startActivity(Intent(this, Register::class.java))
        }

        btnLogin.setOnClickListener {
            val email = etUsername.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            } else {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "¡Entraste con éxito!", Toast.LENGTH_SHORT).show()

                            // Guardar estado de sesión
                            saveLoginState(true)

                            startActivity(Intent(this, HomeActivity::class.java))
                            finish()
                        } else {
                            Toast.makeText(this, "Error: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
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
