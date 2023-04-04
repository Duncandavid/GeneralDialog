package com.primary.generaldialog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext

class MainActivity : AppCompatActivity() {
    private lateinit var openDialog: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        openDialog = findViewById(R.id.openDialog)
        showDialog()
    }

    private fun showDialog(){
        openDialog.setOnClickListener {
            val builder = GeneralDialog.Builder(this)
                .setTitle("想要访问你的位置信息")
                .setMessage("该应用程序将会使用你的位置信息，以提升我们的服务")
                .setPositiveButton("去设置")
                .setNegativeButton("暂不设置")
                .setCancelable(true)

            builder.setButtonClickListener(object : GeneralDialog.ClickButtonListener {
                override fun onPositiveButtonClick() {

                }

                override fun onNegativeButtonClick() {

                }

                override fun onCancelTextClick() {
                }
            })

            builder.show()
        }
    }
}