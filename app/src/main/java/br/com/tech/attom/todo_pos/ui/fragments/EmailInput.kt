package br.com.tech.attom.todo_pos.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import br.com.tech.attom.todo_pos.R
import java.util.regex.Pattern

class EmailInput : Fragment() {
    private lateinit var textEmailValidateValue: TextView
    lateinit var etEmail: EditText
    lateinit var text: Editable;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_email_input, container, false)

        textEmailValidateValue = view.findViewById<TextView>(R.id.textEmailValidateValue)
        etEmail = view.findViewById<EditText>(R.id.etEmail)

        etEmail.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (s != null) {
                    text = s
                }

                if (isValidEmail(text.toString())) {
                    textEmailValidateValue.text = "Válido"
                }
                else {
                    textEmailValidateValue.text = "Inválido"
                }
            }
        })

        return view
    }
    private fun isValidEmail(email: String): Boolean {
        val emailPattern = Pattern.compile("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")
        return emailPattern.matcher(email).matches()
    }
}

