package br.com.tech.attom.todo_pos.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import br.com.tech.attom.todo_pos.R
import br.com.tech.attom.todo_pos.ui.fragments.EmailInput
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance();

        findViewById<View>(R.id.textEntrar).setOnClickListener{
            returnToLogin()
        }

        findViewById<View>(R.id.btnRecuperar).setOnClickListener{
            val emailAddress = supportFragmentManager.findFragmentById(R.id.etEmail) as EmailInput

            firebaseAuth.sendPasswordResetEmail(emailAddress.text.toString()).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Email enviado com sucesso!", Toast.LENGTH_SHORT).show()
                    returnToLogin()
                }
            }
        }
    }

    fun returnToLogin(){
        val activity = Intent(this, LoginActivity::class.java);
        startActivity(activity)
        finish()
    }
}