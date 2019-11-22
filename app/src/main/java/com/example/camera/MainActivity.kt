package com.example.camera


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        setSupportActionBar(findViewById(R.id.toolbar) as Toolbar)

//        BGAQRCodeUtil.setDebug(true)
        val button3 = go
        button3.setOnClickListener {
            startActivity(Intent(this, TestScanActivity::class.java))
        }
    }


}
