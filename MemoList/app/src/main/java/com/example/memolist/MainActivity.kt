package com.example.memolist

import android.app.Dialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import com.example.memolist.databinding.ActivityMainBinding
import com.example.memolist.databinding.DialogAddBinding
import com.example.memolist.db.TitleItem
import com.example.memolist.ui.main.TitleModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setSupportActionBar(binding.toolbar)

        binding.fragmentContainerView.getFragment<MainFragment>().recyclerSetup()

        setContentView(binding.root)
    }

    // toolbar
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menumain, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {   // addbtn 선택
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_add)
        val editdlg = dialog.findViewById<EditText>(R.id.dialog_add_editText)
        dialog.setTitle("")
        val positiveBtn = dialog.findViewById<Button>(R.id.dialog_add_yesButton)
        positiveBtn.setOnClickListener {
            if(editdlg.text.isEmpty()){
                Toast.makeText(this, "입력해주세요!", Toast.LENGTH_SHORT)
            }
            else {
                val title = editdlg.text.toString()
                val newTitle = TitleItem(title)
                val viewModel: TitleModel by viewModels()
                viewModel.insertTitle(newTitle)
                dialog.dismiss()
                binding.fragmentContainerView.getFragment<MainFragment>().recyclerSetup()
            }
        }
        val negativeBtn = dialog.findViewById<Button>(R.id.dialog_add_noButton)
        negativeBtn.setOnClickListener { dialog.dismiss() }
        dialog.create()
        dialog.show()
        return super.onOptionsItemSelected(item)
    }

}