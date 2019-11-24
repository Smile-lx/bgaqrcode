package com.example.camera

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia
import java.util.*

class PictureActivity : AppCompatActivity() {

    private var selectList: List<LocalMedia> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        PictureSelector.create(this@PictureActivity)
            .openGallery(PictureMimeType.ofAll())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
            .loadImageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
            //                .theme(R.style.picture_default_style)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
            .maxSelectNum(9)// 最大图片选择数量
            .minSelectNum(1)// 最小选择数量
            .imageSpanCount(3)// 每行显示个数
            //                .cameraFileName("")// 使用相机时保存至本地的文件名称,注意这个只在拍照时可以使用，选图时不要用
            //                .isSingleDirectReturn(false)// 单选模式下是否直接返回
            .previewImage(true)// 是否可预览图片
            //.querySpecifiedFormatSuffix(PictureMimeType.ofPNG())// 查询指定后缀格式资源
            .isCamera(true)// 是否显示拍照按钮
            .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
            //.imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
            //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径
            //                .enableCrop(true)// 是否裁剪
            .compressQuality(80)// 图片压缩后输出质量 0~ 100
            .synOrAsy(false)//同步true或异步false 压缩 默认同步
            //.compressSavePath(getPath())//压缩图片保存地址
            //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
            .selectionMedia(selectList)// 是否传入已选图片
            .cutOutQuality(90)// 裁剪输出质量 默认100
            .minimumCompressSize(100)// 小于100kb的图片不压缩
            .forResult(PictureConfig.CHOOSE_REQUEST)//结果回调onAct

        //        adapter = new GridImageAdapter(MainActivity.this);
        //        adapter.setList(selectList);
        //        adapter.setSelectMax(9);
        //        recyclerView.setAdapter(adapter);
        //        adapter.setOnItemClickListener((position, v) -> {
        //            if (selectList.size() > 0) {
        //                LocalMedia media = selectList.get(position);
        //                String mimeType = media.getMimeType();
        //                int mediaType = PictureMimeType.getMimeType(mimeType);
        //                switch (mediaType) {
        //                    case PictureConfig.TYPE_VIDEO:
        //                        // 预览视频
        //                        PictureSelector.create(MainActivity.this).externalPictureVideo(media.getPath());
        //                        break;
        //                    case PictureConfig.TYPE_AUDIO:
        //                        // 预览音频
        //                        PictureSelector.create(MainActivity.this).externalPictureAudio(media.getPath());
        //                        break;
        //                    default:
        //                        // 预览图片 可自定长按保存路径
        //                        PictureSelector.create(MainActivity.this)
        //                                .themeStyle(R.style.picture_default_style)
        //                                .isNotPreviewDownload(true)// 预览图片长按是否可以下载
        //                                .loadImageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
        //                                .openExternalPreview(position, selectList);
        //                        break;
        //                }
        //            }
        //        });
    }

    //    private GridImageAdapter.onAddPicClickListener onAddPicClickListener = new GridImageAdapter.onAddPicClickListener() {
    //        @Override
    //        public void onAddPicClick() {
    //            boolean mode = cb_mode.isChecked();
    //            if (mode) {
    //                // 进入相册 以下是例子：不需要的api可以不写
    //                PictureSelector.create(MainActivity.this)
    //                        .openGallery(ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
    //                        .loadImageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
    //                        .theme(R.style.picture_default_style)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
    //                        .maxSelectNum(9)// 最大图片选择数量
    //                        .minSelectNum(1)// 最小选择数量
    //                        .imageSpanCount(3)// 每行显示个数
    //                        .cameraFileName("")// 使用相机时保存至本地的文件名称,注意这个只在拍照时可以使用，选图时不要用
    //                        .isSingleDirectReturn(false)// 单选模式下是否直接返回
    //                        .previewImage(true)// 是否可预览图片
    //                        //.querySpecifiedFormatSuffix(PictureMimeType.ofPNG())// 查询指定后缀格式资源
    //                        .isCamera(true)// 是否显示拍照按钮
    //                        .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
    //                        //.imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
    //                        //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径
    //                        .enableCrop(true)// 是否裁剪
    //                        .compressQuality(80)// 图片压缩后输出质量 0~ 100
    //                        .synOrAsy(false)//同步true或异步false 压缩 默认同步
    //                        //.compressSavePath(getPath())//压缩图片保存地址
    //                        //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
    //                        .selectionMedia(selectList)// 是否传入已选图片
    //                        .cutOutQuality(90)// 裁剪输出质量 默认100
    //                        .minimumCompressSize(100)// 小于100kb的图片不压缩
    //                        .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    //            } else {
    //                // 单独拍照
    //                PictureSelector.create(MainActivity.this)
    //                        .openCamera(PictureMimeType.ofAll())// 单独拍照，也可录像或也可音频 看你传入的类型是图片or视频
    //                        .loadImageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
    //                        .maxSelectNum(9)// 最大图片选择数量
    //                        .minSelectNum(1)// 最小选择数量
    //                        //.querySpecifiedFormatSuffix(PictureMimeType.ofPNG())// 查询指定后缀格式资源
    //                        .selectionMedia(selectList)// 是否传入已选图片
    //                        .previewEggs(false)//预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
    //                        //.previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
    //                        //.cropCompressQuality(90)// 废弃 改用cutOutQuality()
    //                        .cutOutQuality(90)// 裁剪输出质量 默认100
    //                        .minimumCompressSize(100)// 小于100kb的图片不压缩
    //                        .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    //            }
    //        }
    //    };

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.i("tag", "回调")
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                PictureConfig.CHOOSE_REQUEST -> {
                    // 图片选择结果回调
                    selectList = PictureSelector.obtainMultipleResult(data)
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，已取压缩路径为准，因为是先裁剪后压缩的
                    // 4.media.getAndroidQToPath();为Android Q版本特有返回的字段，此字段有值就用来做上传使用
                    for (media in selectList) {
                        Log.i("tagtest", "压缩---->" + media.compressPath)
                        Log.i("tagtest", "原图---->" + media.path)
                        Log.i("tagtest", "裁剪---->" + media.cutPath)
                        Log.i("tagtest", "Android Q 特有Path---->" + media.androidQToPath)
                        Log.i("tagtest", "Android Q 特有Path---->$media")
                        val bundle = intent.extras
                        // 设置要回传的数据
                        data!!.putExtra("path", media.path)
                        // 设置回传码和Intent
                        setResult(Activity.RESULT_OK, data)
                        finish()

                        //                        File file = new File("/storage/emulated/0/PictureSelector/CameraImage/1574317765602.JPEG");
                        //                        File newfile=new File(file.getPath()+"111"+".JPEG");//创建新抽象文件
                        //                        if(!file.exists()||!file.isFile()) {
                        //                            System.out.println("复制文件莫得¿¿¿");
                        //                            return;
                        //                        }
                        //                        if(newfile.exists()) {//新文件路径下有同名文件
                        //                            System.out.println("是否覆盖原有文件¿（y不覆盖|n覆盖）");
                        //                                try {
                        //                                    newfile.createNewFile();
                        //                                } catch (IOException e) {
                        //                                    // TODO Auto-generated catch block
                        //                                    e.printStackTrace();
                        //                                }
                        ////                            Scanner scanner=new Scanner(System.in);
                        ////                            String string=scanner.nextLine();
                        ////                            if(string=="n") {
                        ////                                newfile.delete();
                        ////                                try {
                        ////                                    newfile.createNewFile();
                        ////                                } catch (IOException e) {
                        ////                                    // TODO Auto-generated catch block
                        ////                                    e.printStackTrace();
                        ////                                }
                        ////                            }
                        ////                            else {
                        ////                                newfile=new    File(newpath+File.separator+"(1)"+newfile.getName());
                        ////                                try {
                        ////                                    newfile.createNewFile();
                        ////                                } catch (IOException e) {
                        ////                                    // TODO Auto-generated catch block
                        ////                                    e.printStackTrace();
                        ////                                }
                        ////                            }
                        ////                        }
                        ////                        else {
                        ////                            try {
                        ////                                newfile.createNewFile();
                        ////                            } catch (IOException e) {
                        ////                                // TODO Auto-generated catch block
                        ////                                e.printStackTrace();
                        ////                            }
                        ////                        }
                        //                            }
                        //                        try {
                        //                            FileInputStream fin=new FileInputStream(file);//输入流
                        //                            try {
                        //                                FileOutputStream fout=new FileOutputStream(newfile,true);//输出流
                        //                                byte[]b=new byte[1024];
                        //                                try {
                        //                                    while((fin.read(b))!=-1) {//读取到末尾 返回-1 否则返回读取的字节个数
                        //                                        fout.write(b);
                        //                                    }
                        //                                    fin.close();
                        //                                    fout.close();
                        //                                } catch (IOException e) {
                        //                                    // TODO Auto-generated catch block
                        //                                    e.printStackTrace();
                        //                                }
                        //
                        //                            } catch (FileNotFoundException e) {
                        //                                // TODO Auto-generated catch block
                        //                                e.printStackTrace();
                        //                            }
                        //
                        //                        } catch (FileNotFoundException e) {
                        //                            // TODO Auto-generated catch block
                        //                            e.printStackTrace();
                        //                        }


                        //                        InputStreamReader reader= null;
                        //                        try {
                        //                            reader = new InputStreamReader(new FileInputStream(file),"GBK");
                        //                        } catch (UnsupportedEncodingException e) {
                        //                            e.printStackTrace();
                        //                        } catch (FileNotFoundException e) {
                        //                            e.printStackTrace();
                        //                        }
                        //                        BufferedReader bfreader=new BufferedReader(reader);
                        //                        String line = "";
                        //                        while(true) {
                        //                            try {
                        //                                if (!((line=bfreader.readLine())!=null)) break;
                        //                            } catch (IOException e) {
                        //                                e.printStackTrace();
                        //                            }//包含该行内容的字符串，不包含任何行终止符，如果已到达流末尾，则返回 null
                        //                            System.out.println(line);
                        //
                        //                        }
                    }
                }
            }//                    adapter.setList(selectList);
            //                    adapter.notifyDataSetChanged();
            //                    break;
        }
    }
}
