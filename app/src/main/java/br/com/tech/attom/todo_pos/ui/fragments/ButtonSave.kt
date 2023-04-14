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

class ButtonSave() : Fragment() {
    private var listener: OnConfirmClickListener? = null

    interface OnConfirmClickListener {
        fun onConfirmClicked(activityType: String)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_button, container, false)

        val confirmButton = view.findViewById<Button>(R.id.btn_confirm)
        confirmButton.setOnClickListener {
            listener?.onConfirmClicked("activity1") // Substitua "activity1" pelo nome da sua primeira atividade
        }

        return view
    }

    fun setOnConfirmClickListener(listener: OnConfirmClickListener) {
        this.listener = listener
    }
}

