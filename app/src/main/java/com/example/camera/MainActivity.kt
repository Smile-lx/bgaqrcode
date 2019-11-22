package com.example.camera


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Vibrator
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import cn.bingoogolapple.qrcode.core.QRCodeView
import cn.bingoogolapple.qrcode.zbar.ZBarView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), QRCodeView.Delegate {
    private var mZBarView: ZBarView? = null

    override fun onScanQRCodeSuccess(result: String?) {
        Log.i(TAG, "result:$result")
        title = "扫描结果为：$result"
        vibrate()
        mZBarView!!.startSpot() // 开始识别
    }

    override fun onScanQRCodeOpenCameraError() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate(state: Bundle?) {
        super.onCreate(state)
        setContentView(R.layout.activity_main)
//        setSupportActionBar(findViewById(R.id.toolbar) as Toolbar)

        mZBarView = zbarview as ZBarView
        mZBarView!!.setDelegate(this)
    }

    override fun onStart() {
        super.onStart()
        mZBarView!!.startCamera()
        //强制手机摄像头镜头朝向前边
        //mQRCodeView.startCamera(Camera.CameraInfo.CAMERA_FACING_FRONT);

//        mQRCodeView!!.showScanRect() //显示扫描方框
        mZBarView!!.startSpotAndShowRect() // 显示扫描框，并开始识别

    }

    override fun onStop() {
        mZBarView!!.stopCamera()
        super.onStop()
    }

    override fun onDestroy() {
        mZBarView!!.onDestroy()
        super.onDestroy()
    }

    //震动
    private fun vibrate() {
        val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        vibrator.vibrate(200)
    }

    companion object {
        private val TAG = MainActivity::class.java!!.getSimpleName()
        private val REQUEST_CODE_CHOOSE_QRCODE_FROM_GALLERY = 666
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        mZBarView!!.showScanRect()

        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_CHOOSE_QRCODE_FROM_GALLERY) {
//            val picturePath = BGAPhotoPickerActivity.getSelectedPhotos(data).get(0)
//            mZBarView.decodeQRCode(picturePath)

        }
    }

    override fun onCameraAmbientBrightnessChanged(isDark: Boolean) {
        // 这里是通过修改提示文案来展示环境是否过暗的状态，接入方也可以根据 isDark 的值来实现其他交互效果
        var tipText = mZBarView!!.getScanBoxView().tipText
        val ambientBrightnessTip = "\n环境过暗，请打开闪光灯"
        if (isDark) {
            if (!tipText.contains(ambientBrightnessTip)) {
                mZBarView!!.getScanBoxView().tipText = tipText + ambientBrightnessTip
            }
        } else {
            if (tipText.contains(ambientBrightnessTip)) {
                tipText = tipText.substring(0, tipText.indexOf(ambientBrightnessTip))
                mZBarView!!.getScanBoxView().tipText = tipText
            }
        }
    }


}
