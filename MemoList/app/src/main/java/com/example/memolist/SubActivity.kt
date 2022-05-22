package com.example.memolist

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
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

    private var titleid: Int = 0
    private lateinit var title: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySubBinding.inflate(layoutInflater)

        titleid = intent.getIntExtra("titleId", 0)
        title = intent.getStringExtra("title")!!
        binding.toolbar2.title = title
        setSupportActionBar(binding.toolbar2)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_arrow_back)

        val bundle = Bundle()
        bundle.putInt("titleId", titleid)
        binding.fragmentContainerView2.getFragment<SubFragment>().arguments = bundle

        binding.subActivityLayout.setOnClickListener{
            if(currentFocus != null)
                currentFocus!!.clearFocus()
        }

        setContentView(binding.root)
    }

    // toolbar
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menusub, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home ->{
                val intenthome = Intent(this,MainActivity::class.java)
                startActivity(intenthome)
            }
            R.id.editTitle -> {  // title edit
                val dialog = Dialog(this)
                dialog.setContentView(R.layout.dialog_edit)
                val editdlg = dialog.findViewById<EditText>(R.id.dialog_edit_editText)
                editdlg.addTextChangedListener{
                    if(editdlg.text.length > 20){
                        editdlg.setText(
                            editdlg.text.substring(0, editdlg.length()-1)
                        )
                        editdlg.setSelection(editdlg.length())
                    }
                }
                dialog.setTitle("")
                val positiveBtn = dialog.findViewById<Button>(R.id.dialog_edit_yesButton)
                positiveBtn.setOnClickListener {
                    if(editdlg.text.isEmpty()){
                        Toast.makeText(this, "입력해주세요!", Toast.LENGTH_SHORT)
                    }
                    else{
                        val updateTitle = TitleItem(editdlg.text.toString())
                        updateTitle.id = titleid
                        val viewModel: TitleModel by viewModels()
                        viewModel.updateTitle(updateTitle)
                        binding.toolbar2.title = editdlg.text.toString()
                        dialog.dismiss()
                    }
                }
                val negativeBtn = dialog.findViewById<Button>(R.id.dialog_edit_noButton)
                negativeBtn.setOnClickListener {
                    dialog.dismiss()
                }
                val deleteBtn = dialog.findViewById<Button>(R.id.dialog_edit_deleteButton)
                deleteBtn.setOnClickListener {
                    val listViewModel: ListModel by viewModels()
                    listViewModel.deleteAllList(titleid)
                    val titleViewModel: TitleModel by viewModels()
                    titleViewModel.deleteTitle(titleid)
                    val mainintent = Intent(this, MainActivity::class.java)
                    startActivity(mainintent)
                    Toast.makeText(applicationContext, "삭제되었습니다!", Toast.LENGTH_SHORT)
                }
                dialog.create()
                dialog.show()
            }
            R.id.addNewList -> {    // 새로운 list 추가
                binding.listLayoutView.checkbox.isChecked = false
                binding.listLayoutView.listTextEdit.setText("")
                binding.listLayoutView.listTextEdit.requestFocus()
                binding.listLayoutView.listTextEdit.addTextChangedListener{
                    if(binding.listLayoutView.listTextEdit.text.length > 100){
                        binding.listLayoutView.listTextEdit.text.substring(0, binding.listLayoutView.listTextEdit.length() -1)
                    }
                }
                binding.subActivityLayout.setOnClickListener {  // 다른 곳 클릭 시 저장
                    if(binding.listLayoutView.listTextEdit.text.isNotEmpty() && binding.listLayoutView.listTextEdit.hasFocus()){
                        val newList = ListItem(titleid, binding.listLayoutView.listTextEdit.text.toString(), false)
                        val viewModel: ListModel by viewModels()
                        viewModel.insertList(newList)
                        binding.newlist.visibility = View.GONE
                        binding.listLayoutView.listTextEdit.clearFocus()
                        // recycler view 갱신
                        binding.fragmentContainerView2.getFragment<SubFragment>().recyclerSetup()
                    }
                    else{
                        binding.newlist.visibility = View.GONE
                    }
                    // 키보드 숨기기
                    var imm: InputMethodManager = applicationContext.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
                }

            }
        }

        return super.onOptionsItemSelected(item)
    }
}