package com.hyc.libs.http.callback;

import com.hyc.libs.utils.rxtool.RxFileTool;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.convert.FileConvert;

import java.io.File;

import okhttp3.Response;

/**
 * @author zhxumao
 *         Created on 2017/12/29 0029 11:35.
 */

public abstract class DefaultFileCallback extends AbsCallback<File> {

    public static final String IMAGE_DIR = "imageDir";
    public static final String File_DIR = "fileDir";
    public static final String CACHE_DIR = "cacheDir";
    public static final String DEFAULT_DIR_NAME = "HycLib";

    private static final String cacheDestination = RxFileTool.getRootPath().getAbsolutePath();

    private FileConvert convert;    //文件转换类

    public DefaultFileCallback() {
        this(null);
    }

    /**
     * 默认地址 ：/sdCard/HycLib
     */
    public DefaultFileCallback(String fileName) {
        this(cacheDestination + File.separator + DEFAULT_DIR_NAME, fileName);
    }

    public DefaultFileCallback(String destFileDir, String destFileName) {
        convert = new FileConvert(destFileDir, destFileName);
        convert.setCallback(this);
    }

    @Override
    public File convertResponse(Response response) throws Throwable {
        File file = convert.convertResponse(response);
        response.close();
        return file;
    }
}
