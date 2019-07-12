package com.cjd.base;

import okhttp3.*;
import okio.Buffer;

import java.io.IOException;

/**
 * @Author chenjidong
 * @email 374122600@qq.com
 * created 2019/7/12
 * description 进行 aes rsa md5 加解密
 */
public abstract class ParseInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        // 加密请求
        Request encrypt = encrypt(chain.request());

        Response response = chain.proceed(encrypt);

        // 解密响应
        Response decrypt = decrypt(response);
        return decrypt;
    }

    /**
     * 加密
     */

    private Request encrypt(Request originalRequest) {
        try {
            // 1、原 请求体,获取请求体中的具体值
            RequestBody originalBody = originalRequest.body();
            Buffer buffer = new Buffer();
            originalBody.writeTo(buffer);
            String originalStr = buffer.readUtf8();

            // 2、实现具体的请求体几加密
            String encryptStr = encrypt(originalStr);
            RequestBody newRequestBody = RequestBody.create(originalBody.contentType(), encryptStr);

            // 3、返回新的请求携带新的请求体
            return originalRequest.newBuilder()
                    .method(originalRequest.method(), newRequestBody)
                    .build();

        } catch (Exception e) {

        }
        return originalRequest;
    }

    /**
     * 解密
     */
    private Response decrypt(Response originalResponse) {
        try {

            ResponseBody originalResponseBody = originalResponse.body();
            if (originalResponseBody == null || originalResponseBody.contentType() == null) {
                return originalResponse;
            }

            String decrypt = decrypt(originalResponseBody.string());

            ResponseBody newResponseBody = ResponseBody.create(originalResponseBody.contentType(), decrypt);

            return originalResponse.newBuilder()
                    .body(newResponseBody)
                    .build();
        } catch (Exception e) {

        }

        return originalResponse;

    }

    /**
     * 加密请求
     * 省略具体加密细节，例如MD5、RSA、DES对字符串进行加密
     */
    public abstract String encrypt(String response);

    /**
     * 解密响应
     * 省略具体解密细节，例如MD5、RSA、DES对字符串进行解密
     */
    public abstract String decrypt(String response);
}
