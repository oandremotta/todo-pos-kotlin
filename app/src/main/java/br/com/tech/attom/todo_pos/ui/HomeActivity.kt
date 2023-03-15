package br.com.tech.attom.todo_pos.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.SparseBooleanArray
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import br.com.tech.attom.todo_pos.R
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson

class HomeActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    private var gson = Gson()
    lateinit var mGoogleSignClient: GoogleSignInClient;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        supportActionBar?.hide()

        sharedPreferences = getSharedPreferences("lista_de_tarefas", Context.MODE_PRIVATE)

        val listViewTasks = findViewById<android.widget.ListView>(R.id.listViewTasks);
        val createTask = findViewById<android.widget.EditText>(R.id.createTask);

        val itemList = getData();

        val adapter =
            ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, itemList);

        val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance();

        listViewTasks.adapter = adapter;
        adapter.notifyDataSetChanged()

        findViewById<View>(R.id.add).setOnClickListener {
            itemList.add(createTask.text.toString());
            listViewTasks.adapter = adapter;
            adapter.notifyDataSetChanged()

            saveData(itemList)
            createTask.text.clear()
        }

        findViewById<View>(R.id.delete).setOnClickListener {
            val position: SparseBooleanArray = listViewTasks.checkedItemPositions;
            val count = listViewTasks.count;
            var item = count - 1;
            while (item >= 0) {
                if (position.get(item)) {
                    adapter.remove(itemList.get(item))
                }
                item--;
            }

            saveData(itemList)
            position.clear();
            adapter.notifyDataSetChanged()
        }

        findViewById<View>(R.id.clear).setOnClickListener {
            itemList.clear()
            saveData(itemList)
            adapter.notifyDataSetChanged()
        }

    }

    private fun getData(): ArrayList<String> {
        val arrayJson = sharedPreferences.getString("lista", null);
        return if (arrayJson.isNullOrEmpty()) {
            arrayListOf();
        } else {
            gson.fromJson(arrayJson, object : TypeToken<ArrayList<String>>() {}.type)
        }
    }

    private fun saveData(array: ArrayList<String>) {
        val arrayJson = gson.toJson(array);
        val editor = sharedPreferences.edit();
        editor.putString("lista", arrayJson);
        editor.apply();
    }
}