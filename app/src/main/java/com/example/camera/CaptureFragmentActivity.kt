package com.example.camera

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toolbar
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.camera.util.StatusBarUtils
import com.king.zxing.CaptureFragment


/**
 * Fragment扫码
 * @author [Jenly](mailto:jenly1314@gmail.com)
 */
class CaptureFragmentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_activity)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        StatusBarUtils.immersiveStatusBar(this, toolbar, 0.2f)
        val tvTitle = findViewById<TextView>(R.id.tvTitle)
        tvTitle.text = intent.getStringExtra(MainActivity.KEY_TITLE)

        replaceFragment(CaptureFragment.newInstance())
    }

    fun replaceFragment(fragment: Fragment) {
        replaceFragment(R.id.fragmentContent, fragment)
    }

    fun replaceFragment(@IdRes id: Int, fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(id, fragment).commit()
    }

    fun onClick(v: View) {
        when (v.id) {
            R.id.ivLeft -> onBackPressed()
        }
    }
}
