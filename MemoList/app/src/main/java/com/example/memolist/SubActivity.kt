package com.example.memolist

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.get
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


        binding.subActLayout.apply{
            setOnClickListener {
                binding.fragmentContainerView2.getFragment<SubFragment>().setSwipeAll()
                if(binding.newlist.visibility == View.VISIBLE){
                    if(binding.newEditText.text.isNotEmpty()) {
                        val newList = ListItem(titleid, binding.newEditText.text.toString(), false)
                        val viewModel: ListModel by viewModels()
                        viewModel.insertList(newList)
                        binding.fragmentContainerView2.getFragment<SubFragment>().recyclerSetup()
                    }
                    binding.newlist.visibility = View.GONE
                    // 키보드 숨기기
                    var imm: InputMethodManager =
                        applicationContext.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
                    binding.newEditText.setText("")
                }

            }
        }

        binding.newListAddBtn.setOnClickListener {
            if(binding.newlist.visibility == View.VISIBLE){
                if(binding.newEditText.text.isNotEmpty()) {
                    val newList = ListItem(titleid, binding.newEditText.text.toString(), false)
                    val viewModel: ListModel by viewModels()
                    viewModel.insertList(newList)
                    binding.fragmentContainerView2.getFragment<SubFragment>().recyclerSetup()
                    binding.newlist.visibility = View.GONE
                    // 키보드 숨기기
                    var imm: InputMethodManager =
                        applicationContext.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
                    binding.newEditText.setText("")
                }
                else{
                    Toast.makeText(this, "내용을 입력해주세요!", Toast.LENGTH_SHORT).show()
                }
            }
        }

        setContentView(binding.root)
    }

    // toolbar
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menusub, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {
                val intenthome = Intent(this, MainActivity::class.java)
                intenthome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intenthome)
            }
            R.id.editTitle -> {  // title edit
                val dialog = Dialog(this)
                dialog.setContentView(R.layout.dialog_edit)
                val editdlg = dialog.findViewById<EditText>(R.id.dialog_edit_editText)
                editdlg.setText(title)
                editdlg.addTextChangedListener {
                    if (editdlg.text.length > 20) {
                        editdlg.setText(
                            editdlg.text.substring(0, editdlg.length() - 1)
                        )
                        editdlg.setSelection(editdlg.length())
                    }
                }
                dialog.setTitle("")
                val positiveBtn = dialog.findViewById<Button>(R.id.dialog_edit_yesButton)
                positiveBtn.setOnClickListener {
                    if (editdlg.text.isEmpty()) {
                        Toast.makeText(this, "입력해주세요!", Toast.LENGTH_SHORT).show()
                    } else {
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
                    Toast.makeText(applicationContext, "삭제되었습니다!", Toast.LENGTH_SHORT).show()
                }
                dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog.create()
                dialog.show()
            }
            R.id.addNewList -> {    // 새로운 list 추가
                binding.newlist.visibility = View.VISIBLE
                binding.newEditText.setText("")
                binding.newEditText.addTextChangedListener {
                    if (binding.newEditText.text.length > 100) {
                        binding.newEditText.text.substring(
                            0,
                            binding.newEditText.length() - 1
                        )
                    }
                }
                binding.subActLayout.isClickable = true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}