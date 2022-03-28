package com.example.androidgiuaki


import android.app.AlertDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    //In Kotlin `var` is used to declare a mutable variable. On the other hand
    //`internal` means a variable is visible within a given module.
    internal var dbHelper = DatabaseHelper(this)


    fun showToast(text: String){
        Toast.makeText(this@MainActivity, text, Toast.LENGTH_LONG).show()
    }

    fun showDialog(title : String,Message : String){
        val builder = AlertDialog.Builder(this)
        builder.setCancelable(true)
        builder.setTitle(title)
        builder.setMessage(Message)
        builder.show()
    }


    fun clearEditTexts(){
        tenTxt.setText("")
        emailTxt.setText("")
        hpTxt.setText("")

        mssvTxt.setText("")
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        handleThem()
        handleSua()
        handleXoa()
        handleView()
    }


    fun handleThem() {
        insertBtn.setOnClickListener {
            try {
                dbHelper.insertData(mssvTxt.text.toString(), tenTxt.text.toString(),emailTxt.text.toString(),
                    hpTxt.text.toString())
                clearEditTexts()
                showToast("Data Insert Successfully")
            }catch (e: Exception){
                e.printStackTrace()
                showToast(e.message.toString())
            }
        }
    }


    fun handleSua() {
        updateBtn.setOnClickListener {
            try {
                val isUpdate = dbHelper.updateData(mssvTxt.text.toString(),
                    tenTxt.text.toString(), emailTxt.text.toString(), hpTxt.text.toString())
                if (isUpdate == true)
                    showToast("Data Updated Successfully")
                else
                    showToast("Data Not Updated")
            }catch (e: Exception){
                e.printStackTrace()
                showToast(e.message.toString())
            }
        }
    }

    fun handleXoa(){
        deleteBtn.setOnClickListener {
            try {
                dbHelper.deleteData(mssvTxt.text.toString())
                clearEditTexts()
            }catch (e: Exception){
                e.printStackTrace()
                showToast(e.message.toString())
            }
        }
    }


    fun handleView() {
        viewBtn.setOnClickListener(
            View.OnClickListener {
                val res = dbHelper.allData
                if (res.count == 0) {
                    showDialog("Error", "No Data Found")
                    return@OnClickListener
                }

                val buffer = StringBuffer()
                while (res.moveToNext()) {
                    buffer.append("MSSV : " + res.getString(0) + "\n")
                    buffer.append("HỌ&TÊN : " + res.getString(1) + "\n")
                    buffer.append("EMAIL : " + res.getString(2) + "\n")
                    buffer.append("HỌC PHẦN : " + res.getString(3) + "\n\n")
                }
                showDialog("Data Listing", buffer.toString())
            }
        )
    }
}