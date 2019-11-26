package com.example.camera

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.os.Vibrator
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import cn.bingoogolapple.qrcode.core.QRCodeView
import cn.bingoogolapple.qrcode.zxing.QRCodeDecoder
import cn.bingoogolapple.qrcode.zxing.ZXingView
import com.luck.picture.lib.PictureSelector

class TestScanActivity : AppCompatActivity(), QRCodeView.Delegate {
    private var mZXingView: ZXingView? = null


    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_scan)
//        setSupportActionBar(findViewById<View>(R.id.toolbar) as Toolbar)

        mZXingView = findViewById(R.id.zxingview)
        mZXingView!!.setDelegate(this)
    }

    override fun onStart() {
        super.onStart()

        mZXingView!!.startCamera() // 打开后置摄像头开始预览，但是并未开始识别
        //        mZXingView.startCamera(Camera.CameraInfo.CAMERA_FACING_FRONT); // 打开前置摄像头开始预览，但是并未开始识别

        mZXingView!!.startSpotAndShowRect() // 显示扫描框，并开始识别
    }

    override fun onStop() {
        mZXingView!!.stopCamera() // 关闭摄像头预览，并且隐藏扫描框
        super.onStop()
    }

    override fun onDestroy() {
        mZXingView!!.onDestroy() // 销毁二维码扫描控件
        super.onDestroy()
    }

    private fun vibrate() {
        val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        vibrator.vibrate(200)
    }

    override fun onScanQRCodeSuccess(result: String) {

//        Log.i(TAG, "result:$result")
//        title = "扫描结果为：$result"
//        vibrate()
        if (result.isNotBlank()) {
            vibrate()
            val intent = Intent(this, ResultActivity::class.java)
            intent.putExtra("showResult", result)
            startActivity(intent)
        } else {
            mZXingView!!.startSpot() // 开始识别
        }
    }

    override fun onCameraAmbientBrightnessChanged(isDark: Boolean) {
        // 这里是通过修改提示文案来展示环境是否过暗的状态，接入方也可以根据 isDark 的值来实现其他交互效果
        var tipText = mZXingView!!.scanBoxView.tipText
        val ambientBrightnessTip = "\n环境过暗，请打开闪光灯"
        if (isDark) {
            if (!tipText.contains(ambientBrightnessTip)) {
                mZXingView!!.scanBoxView.tipText = tipText + ambientBrightnessTip
            }
        } else {
            if (tipText.contains(ambientBrightnessTip)) {
                tipText = tipText.substring(0, tipText.indexOf(ambientBrightnessTip))
                mZXingView!!.scanBoxView.tipText = tipText
            }
        }
    }

    override fun onScanQRCodeOpenCameraError() {
        Log.e(TAG, "打开相机出错")
    }

    fun onClick(v: View) {
        when (v.id) {
            R.id.open_flashlight -> mZXingView!!.openFlashlight() // 打开闪光灯
            R.id.close_flashlight -> mZXingView!!.closeFlashlight() // 关闭闪光灯
            R.id.choose_qrcde_from_gallery -> {
                /*
                从相册选取二维码图片，这里为了方便演示，使用的是Picture Selector
                 */
                startActivityForResult(
                    Intent(this, PictureActivity::class.java),
                    REQUEST_CODE_CHOOSE_QRCODE_FROM_GALLERY
                )
            }
//            R.id.start_preview -> mZXingView!!.startCamera() // 打开后置摄像头开始预览，但是并未开始识别
//            R.id.stop_preview -> mZXingView!!.stopCamera() // 关闭摄像头预览，并且隐藏扫描框
//            R.id.start_spot -> mZXingView!!.startSpot() // 开始识别
//            R.id.stop_spot -> mZXingView!!.stopSpot() // 停止识别
//            R.id.start_spot_showrect -> mZXingView!!.startSpotAndShowRect() // 显示扫描框，并且开始识别
//            R.id.stop_spot_hiddenrect -> mZXingView!!.stopSpotAndHiddenRect() // 停止识别，并且隐藏扫描框
//            R.id.show_scan_rect -> mZXingView!!.showScanRect() // 显示扫描框
//            R.id.hidden_scan_rect -> mZXingView!!.hiddenScanRect() // 隐藏扫描框
//            R.id.decode_scan_box_area -> mZXingView!!.scanBoxView.isOnlyDecodeScanBoxArea =
//                true // 仅识别扫描框中的码
//            R.id.decode_full_screen_area -> mZXingView!!.scanBoxView.isOnlyDecodeScanBoxArea =
//                false // 识别整个屏幕中的码
//            R.id.scan_one_dimension -> {
//                mZXingView!!.changeToScanBarcodeStyle() // 切换成扫描条码样式
//                mZXingView!!.setType(BarcodeType.ONE_DIMENSION, null) // 只识别一维条码
//                mZXingView!!.startSpotAndShowRect() // 显示扫描框，并开始识别
//            }
//            R.id.scan_two_dimension -> {
//                mZXingView!!.changeToScanQRCodeStyle() // 切换成扫描二维码样式
//                mZXingView!!.setType(BarcodeType.TWO_DIMENSION, null) // 只识别二维条码
//                mZXingView!!.startSpotAndShowRect() // 显示扫描框，并开始识别
//            }
//            R.id.scan_qr_code -> {
//                mZXingView!!.changeToScanQRCodeStyle() // 切换成扫描二维码样式
//                mZXingView!!.setType(BarcodeType.ONLY_QR_CODE, null) // 只识别 QR_CODE
//                mZXingView!!.startSpotAndShowRect() // 显示扫描框，并开始识别
//            }
//            R.id.scan_code128 -> {
//                mZXingView!!.changeToScanBarcodeStyle() // 切换成扫描条码样式
//                mZXingView!!.setType(BarcodeType.ONLY_CODE_128, null) // 只识别 CODE_128
//                mZXingView!!.startSpotAndShowRect() // 显示扫描框，并开始识别
//            }
//            R.id.scan_ean13 -> {
//                mZXingView!!.changeToScanBarcodeStyle() // 切换成扫描条码样式
//                mZXingView!!.setType(BarcodeType.ONLY_EAN_13, null) // 只识别 EAN_13
//                mZXingView!!.startSpotAndShowRect() // 显示扫描框，并开始识别
//            }
//            R.id.scan_high_frequency -> {
//                mZXingView!!.changeToScanQRCodeStyle() // 切换成扫描二维码样式
//                mZXingView!!.setType(
//                    BarcodeType.HIGH_FREQUENCY,
//                    null
//                ) // 只识别高频率格式，包括 QR_CODE、UPC_A、EAN_13、CODE_128
//                mZXingView!!.startSpotAndShowRect() // 显示扫描框，并开始识别
//            }
//            R.id.scan_all -> {
//                mZXingView!!.changeToScanQRCodeStyle() // 切换成扫描二维码样式
//                mZXingView!!.setType(BarcodeType.ALL, null) // 识别所有类型的码
//                mZXingView!!.startSpotAndShowRect() // 显示扫描框，并开始识别
//            }
//            R.id.scan_custom -> {
//                mZXingView!!.changeToScanQRCodeStyle() // 切换成扫描二维码样式
//
//                val hintMap = EnumMap<DecodeHintType, Any>(DecodeHintType::class.java)
//                val formatList = ArrayList<BarcodeFormat>()
//                formatList.add(BarcodeFormat.QR_CODE)
//                formatList.add(BarcodeFormat.UPC_A)
//                formatList.add(BarcodeFormat.EAN_13)
//                formatList.add(BarcodeFormat.CODE_128)
//                hintMap.put(DecodeHintType.POSSIBLE_FORMATS, formatList) // 可能的编码格式
//                hintMap.put(
//                    DecodeHintType.TRY_HARDER,
//                    java.lang.Boolean.TRUE
//                ) // 花更多的时间用于寻找图上的编码，优化准确性，但不优化速度
//                hintMap.put(DecodeHintType.CHARACTER_SET, "utf-8") // 编码字符集
//                mZXingView!!.setType(BarcodeType.CUSTOM, hintMap) // 自定义识别的类型
//
//                mZXingView!!.startSpotAndShowRect() // 显示扫描框，并开始识别
//
//            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        mZXingView!!.startSpotAndShowRect() // 显示扫描框，并开始识别

        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_CHOOSE_QRCODE_FROM_GALLERY) {
            val picture = PictureSelector.obtainMultipleResult(data)[0]
//            try {
//                Log.e("图片路径", picture.androidQToPath)
//                mZXingView!!.decodeQRCode(picture.androidQToPath)
//            } catch (e: Exception) {
//            }
            val execute = object : AsyncTask<Void, Void, String>() {
                override fun doInBackground(vararg params: Void): String {

                    return try {
                        QRCodeDecoder.syncDecodeQRCode(picture.androidQToPath)
                    } catch (e: Exception) {
                        SCAN_ORROR
                    }
                }

                override fun onPostExecute(result: String) {
                    //如果是识别错误的话就将不能识别打印一下否则就不打印
                    if (result.equals(SCAN_ORROR)) {
                        Toast.makeText(this@TestScanActivity, result, Toast.LENGTH_SHORT).show()
                    }
                }
            }.execute()
            if (execute.get() != SCAN_ORROR) {
                showResult(execute.get())
            }
        }
    }

    private fun showResult(showResult: String) {
        if (!showResult.isEmpty()) {
            vibrate()
            val intent = Intent(this, ResultActivity::class.java)
            intent.putExtra("showResult", showResult)
            startActivity(intent)
        }
    }

    companion object {
        private val TAG = TestScanActivity::class.java!!.getSimpleName()
        private val REQUEST_CODE_CHOOSE_QRCODE_FROM_GALLERY = 666
        private val SCAN_ORROR = "未识别到二维码/手机中的二维码识别度不高"
    }

}
