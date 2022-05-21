package com.example.memolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import com.example.memolist.databinding.ActivityMainBinding
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
        val newListDlg = AlertDialog.Builder(this)
        val ListEditText = EditText(this)
        newListDlg.setTitle("새로운 목록")
        newListDlg.setView(ListEditText)
        ListEditText.addTextChangedListener{
            if(ListEditText.text.toString().length > 20){   // 글자수 제한
                ListEditText.setText(
                    ListEditText.text.substring(0, ListEditText.length() -1)
                )
                ListEditText.setSelection(ListEditText.length())
            }
        }

        newListDlg.setPositiveButton("생성"){ dialog, which ->
            if(ListEditText.text.isEmpty()){
                Toast.makeText(this, "입력해주세요!", Toast.LENGTH_SHORT)
            }
            else {
                val title = ListEditText.text.toString()
                // title 추가
                val newTitle = TitleItem(title)
                val viewModel: TitleModel by viewModels()
                viewModel.insertTitle(newTitle)
                binding.fragmentContainerView.getFragment<MainFragment>().recyclerSetup()
            }
        }
        newListDlg.setNegativeButton("취소"){ dialog, which ->
        }

        val dlg = newListDlg.create()
        dlg.show()
        return super.onOptionsItemSelected(item)
    }

}