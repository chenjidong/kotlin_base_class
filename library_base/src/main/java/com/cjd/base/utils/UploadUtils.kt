package com.cjd.base.utils

import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

/**
 * @Author chenjidong
 * @email 374122600@qq.com
 * created 2019/7/16
 * description
 */
object UploadUtils {

    fun getMultiPartBody(files: List<File>): List<MultipartBody.Part> {
        val builder = MultipartBody.Builder().setType(MultipartBody.FORM)
        for (file: File in files) {
            val body = RequestBody.create(MediaType.parse("multipart/form-data"), file)
            builder.addPart(body)//如果是参数，则通过addFormDataPart添加  如果是文件，则通过addPart添加
        }
        return builder.build().parts()
    }

    fun getSingleMultiPartBody(file: File): MultipartBody.Part {
        val body = RequestBody.create(MediaType.parse("multipart/form-data"), file)

        return MultipartBody.Part.createFormData("file", file.name, body)
    }
}