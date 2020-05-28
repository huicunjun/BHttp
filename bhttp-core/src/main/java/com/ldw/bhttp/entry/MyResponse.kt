package com.ldw.bhttp.entry

/**
 * @date 2020/5/28 12:14
 * @user 威威君
 */
class MyResponse<T> {
    var msg: String? = null
    var code: Int? = null
    var data: T? = null

    override fun toString(): String {
        return "MyResponse(msg=$msg, code=$code, data=$data)"
    }


}