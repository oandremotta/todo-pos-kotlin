package br.com.tech.attom.todo_pos.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.tech.attom.todo_pos.R
import br.com.tech.attom.todo_pos.ui.fragments.EmailInput
import br.com.tech.attom.todo_pos.ui.fragments.PasswordLevel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        supportActionBar?.hide()

        auth = Firebase.auth

        val registerButton: Button = findViewById<Button>(R.id.btn_registrar)
        val backButton: Button = findViewById<Button>(R.id.btn_voltar)
        registerButton.setOnClickListener { performSignUp() }
        backButton.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

    }

    private fun performSignUp() {
        val email = supportFragmentManager.findFragmentById(R.id.etEmail) as EmailInput
        val password = supportFragmentManager.findFragmentById(R.id.etPassword) as PasswordLevel

        if (email.text.isEmpty() || password.text.isEmpty()) {
            Toast.makeText(this, "Por favor preencha todos os campos.", Toast.LENGTH_SHORT)
                .show()
            return
        }
        val inputEmail = email.text.toString()
        val inputPassword = password.text.toString()

        auth.createUserWithEmailAndPassword(inputEmail, inputPassword)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    startActivity(Intent(this, HomeActivity::class.java))
                    Toast.makeText(
                        baseContext, "Autenticação feita com successo.",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        baseContext, "Dados inválidos.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Ocorreu um erro ${it.localizedMessage}", Toast.LENGTH_SHORT)
                    .show()
            }
    }
}