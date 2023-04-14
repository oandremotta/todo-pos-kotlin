package br.com.tech.attom.todo_pos.ui

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.tech.attom.todo_pos.R
import br.com.tech.attom.todo_pos.ui.fragments.EmailInput
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var firebaseAnalytics: FirebaseAnalytics
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()
        firebaseAnalytics = Firebase.analytics
        auth = Firebase.auth

         val crashButton = Button(this)
        crashButton.text = "Test Crash"
        crashButton.setOnClickListener {
            throw RuntimeException("Test Crash") // Force a crash
        }

        addContentView(crashButton, ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT))

        val loginButton: Button = findViewById(R.id.btn_login)

        loginButton.setOnClickListener {
            performLogin()
        }

        findViewById<Button>(R.id.btn_register).setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        findViewById<Button>(R.id.btn_forget_password).setOnClickListener {
            val activity = Intent(this, ForgotPasswordActivity::class.java);
            startActivity(activity);
        }

        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.LOGIN) {
            param("login_button_clicked", "true")
            param("register_button_clicked", "true")
            param("forgot_password_button_clicked", "true")
        }
    }

    private fun performLogin() {
        val email = supportFragmentManager.findFragmentById(R.id.etEmail) as EmailInput
        val password = findViewById<EditText>(R.id.etPassword)

        if (email.text.isEmpty() || password.text.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT)
                .show()
            return
        }

        val inputEmail = email.text.toString()
        val inputPassword = password.text.toString()

        auth.signInWithEmailAndPassword(inputEmail, inputPassword)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    //val user = auth.currentUser
                    startActivity(Intent(this, HomeActivity::class.java))
                    Toast.makeText(
                        baseContext, "Autenticação feita com sucesso.",
                        Toast.LENGTH_SHORT
                    ).show()

                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(
                        baseContext, "Dados inválidos.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            .addOnFailureListener {
                Toast.makeText(
                    baseContext, "Autenticação falhou. ${it.localizedMessage}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

}
