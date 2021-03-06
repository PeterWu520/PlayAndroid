package com.df.playandroid.http.exception

import android.net.ParseException
import com.df.playandroid.R
import com.df.playandroid.application.MyApplication
import com.df.playandroid.utils.ToastUtil
import com.google.gson.JsonParseException
import org.json.JSONException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * 作者：PeterWu
 * 时间：2020/1/19
 * 描述：统一异常处理
 */
object ExceptionUtils {

    /**
     * Http状态码
     */
    // 错误请求
    private const val BAD_REQUEST = 400
    // 未授权（要求进行身份验证）
    private const val UNAUTHORIZED = 401
    // 服务器禁止相应请求
    private const val FORBIDDEN = 403
    // 服务器找不到请求的网页
    private const val NOT_FOUND = 404
    // 禁用相应请求中所指定的方法
    private const val METHOD_NOT_ALLOWED = 405
    // 请求超时
    private const val REQUEST_TIMEOUT = 408
    // 服务器遇到冲突
    private const val CONFLICT = 409
    // 服务器未满足请求者在请求中设置的其中一个前提条件
    private const val PRECONDITION_FAILED = 412
    // 服务器内部错误
    private const val INTERNAL_SERVER_ERROR = 500
    // 错误网关
    private const val BAD_GATEWAY = 502
    // 服务不可用
    private const val SERVICE_UNAVAILABLE = 503
    // 网关超时
    private const val GATEWAY_TIMEOUT = 504

    /**
     * 服务器定义的状态码，根据后端要求修改
     */
    /**
     * 完全成功
     */
    private const val CODE_SUCCESS = 0

    /**
     * Token 失效
     */
    private const val CODE_TOKEN_INVALID = 401

    /**
     * 缺少参数
     */
    private const val CODE_NO_MISSING_PARAMETER = 400400

    /**
     * 其他情况
     */
    private const val CODE_NO_OTHER = 403

    /**
     * 统一提示
     */
    private const val CODE_SHOW_TOAST = 400000

    /**
     * 这个可以处理服务器请求成功，但是业务逻辑失败，比如token失效需要重新登陆
     */
    fun serviceException(code: Int, content: String) {
        if (code != CODE_SUCCESS) {
            val serverException = ServerException(code, content)
            handleException(serverException)
        }
    }

    /**
     * 这个是处理网络异常，也可以处理业务中的异常
     */
    fun handleException(e: Throwable) {
        var ex: HttpException? = null
        //HTTP错误   网络请求异常 比如常见404 500之类的等
        if (e is retrofit2.HttpException) {
            ex = HttpException(e, ErrorCode.HTTP_ERROR)
            when (e.code()) {
                BAD_REQUEST,
                UNAUTHORIZED,
                FORBIDDEN,
                NOT_FOUND,
                METHOD_NOT_ALLOWED,
                REQUEST_TIMEOUT,
                CONFLICT,
                PRECONDITION_FAILED,
                GATEWAY_TIMEOUT,
                INTERNAL_SERVER_ERROR,
                BAD_GATEWAY,
                SERVICE_UNAVAILABLE -> {
                    // 均视为网络错误
                    ex.displayMessage = MyApplication.instance.getString(R.string.common_network_error)
                }
            }
        } else if (e is ServerException) {
            //服务器返回的错误（处理code不为成功的code的时候），code根据需要添加和删除
            val code = e.code
            val message = e.errorMessage
            val exception = HttpException(e, ErrorCode.SERVER_ERROR)
            when (code) {
                CODE_TOKEN_INVALID -> {
                    exception.displayMessage = MyApplication.instance.getString(R.string.common_token_invalid)
                    //下面这里可以统一处理跳转登录页面的操作逻辑
                }
                CODE_NO_OTHER -> exception.displayMessage = MyApplication.instance.getString(R.string.common_request_failed)
                else -> exception.displayMessage = message
            }
        } else if (e is JsonParseException || e is JSONException || e is ParseException) {
            ex = HttpException(e, ErrorCode.PARSE_ERROR)
            // 均视为解析错误
            ex.displayMessage = MyApplication.instance.getString(R.string.common_parse_error)
        } else if (e is ConnectException) {
            ex = HttpException(e, ErrorCode.NETWORK_ERROR)
            // 均视为网络错误
            ex.displayMessage = MyApplication.instance.getString(R.string.common_connection_failed)
        } else if (e is UnknownHostException) {
            ex = HttpException(e, ErrorCode.NETWORK_ERROR)
            // 网络未连接
            ex.displayMessage = MyApplication.instance.getString(R.string.common_network_dismiss)
        } else if (e is SocketTimeoutException) {
            ex = HttpException(e, ErrorCode.NETWORK_ERROR)
            // 网络未连接
            ex.displayMessage = MyApplication.instance.getString(R.string.common_response_timeout)
        } else {
            ex = HttpException(e, ErrorCode.UNKNOWN)
            // 未知错误
            ex.displayMessage = MyApplication.instance.getString(R.string.common_server_error)
        }

        ex?.displayMessage?.let { ToastUtil.showToast(it) }
    }

}