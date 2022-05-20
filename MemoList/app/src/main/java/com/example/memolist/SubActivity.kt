package com.example.memolist

import android.icu.text.CaseMap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import com.example.memolist.databinding.ActivitySubBinding
import com.example.memolist.db.ListItem
import com.example.memolist.db.TitleItem
import com.example.memolist.ui.main.ListModel
import com.example.memolist.ui.main.TitleModel

class SubActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySubBinding

    lateinit var titleid: Int
    lateinit var title: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySubBinding.inflate(layoutInflater)

        titleid = intent.getIntExtra("titldid", 0)
        title = intent.getStringExtra("title")!!
        binding.toolbar2.title = title

        setSupportActionBar(binding.toolbar2)
        setContentView(binding.root)
    }

    // toolbar
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menusub, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.editTitle -> {  // title edit
                val editTitleDlg = AlertDialog.Builder(this)
                val TitleEditText = EditText(this)
                editTitleDlg.setTitle("목록 수정")
                editTitleDlg.setView(TitleEditText)
                TitleEditText.setText(title)
                TitleEditText.addTextChangedListener{
                    if(TitleEditText.text.toString().length>20){
                        TitleEditText.setText(
                            TitleEditText.text.substring(0, TitleEditText.length() -1)
                        )
                        TitleEditText.setSelection(TitleEditText.length())
                    }
                }
                editTitleDlg.setPositiveButton("수정"){ dialog, which ->
                    val updateTitle = TitleItem(TitleEditText.text.toString())
                    val viewModel: TitleModel by viewModels()
                    viewModel.updateTitle(updateTitle)
                }
                editTitleDlg.setNegativeButton("취소"){ dialog, which ->
                }
            }
            R.id.addNewList -> {    // 새로운 list 추가
                binding.listLayoutView.radioButton.isChecked = false
                binding.listLayoutView.listText.setText("")
                binding.newlist.visibility = View.VISIBLE
                binding.listLayoutView.listText.addTextChangedListener{
                    if(binding.listLayoutView.listText.text.length > 100){
                        binding.listLayoutView.listText.text.substring(0, binding.listLayoutView.listText.length() -1)
                    }
                }
                binding.subActivityLayout.setOnClickListener {  // 다른 곳 클릭 시 저장
                    if(binding.listLayoutView.listText.text.isNotEmpty()){
                        val newList = ListItem(titleid, binding.listLayoutView.listText.text.toString(), false)
                        val viewModel: ListModel by viewModels()
                        viewModel.insertList(newList)
                    }
                    else{
                        binding.newlist.visibility = View.GONE
                    }
                }

            }
        }

        return super.onOptionsItemSelected(item)
    }
}