package com.example.camera


import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Vibrator
import android.util.Log
import android.view.View
import android.widget.Toast

import cn.bingoogolapple.qrcode.core.QRCodeView
import cn.bingoogolapple.qrcode.zxing.ZXingView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : Activity() {
    private var mQRCodeView: QRCodeView? = null
    private var activity: Activity? = null

    override fun onCreate(state: Bundle?) {
        super.onCreate(state)
        //单纯测试git
        setContentView(R.layout.activity_main)
        activity = this
        val zxingview = zxingview
        mQRCodeView = zxingview as ZXingView
        mQRCodeView!!.changeToScanQRCodeStyle() //扫二维码
        mQRCodeView!!.startSpot()  //开启全局一直扫描
        mQRCodeView!!.setDelegate(object : QRCodeView.Delegate {
            override fun onCameraAmbientBrightnessChanged(isDark: Boolean) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onScanQRCodeSuccess(result: String) {
                Log.d("二维码扫描结果", "result:$result")

                Toast.makeText(activity, result, Toast.LENGTH_LONG).show()

                //扫描得到结果震动一下表示
                vibrate()

                //获取结果后三秒后，重新开始扫描
                Handler().postDelayed(Runnable { mQRCodeView!!.startSpot() }, 1000)
            }

            override fun onScanQRCodeOpenCameraError() {
                Toast.makeText(activity, "打开相机错误！", Toast.LENGTH_SHORT).show()
            }
        })
        val startSpot = start_spot
        startSpot.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                mQRCodeView!!.startSpot()
                Toast.makeText(activity, "startSpot", Toast.LENGTH_SHORT).show()
            }
        })
        val stopSpot = stop_spot
        stopSpot.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                mQRCodeView!!.stopSpot()
                Toast.makeText(activity, "stopSpot", Toast.LENGTH_SHORT).show()
            }
        })
        val openFlashlight = open_flashlight
        openFlashlight.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                mQRCodeView!!.openFlashlight()
                Toast.makeText(activity, "openFlashlight", Toast.LENGTH_SHORT).show()
            }
        })
        val closeFlashlight = close_flashlight
        closeFlashlight.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                mQRCodeView!!.closeFlashlight()
                Toast.makeText(activity, "closeFlashlight", Toast.LENGTH_SHORT).show()
            }
        })

        //        findViewById(R.id.scan_qrcode).setOnClickListener(new View.OnClickListener(){
        //            @Override
        //            public void onClick(View view) {
        //                mQRCodeView.changeToScanQRCodeStyle();
        //                Toast.makeText(activity,"changeToScanQRCodeStyle",Toast.LENGTH_SHORT).show();
        //            }
        //        });
    }

    override fun onStart() {
        super.onStart()
        mQRCodeView!!.startCamera()
        //强制手机摄像头镜头朝向前边
        //mQRCodeView.startCamera(Camera.CameraInfo.CAMERA_FACING_FRONT);

        mQRCodeView!!.showScanRect() //显示扫描方框
    }

    override fun onStop() {
        mQRCodeView!!.stopCamera()
        super.onStop()
    }

    override fun onDestroy() {
        mQRCodeView!!.onDestroy()
        super.onDestroy()
    }

    //震动
    private fun vibrate() {
        val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        vibrator.vibrate(200)
    }
}
