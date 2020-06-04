package com.ldw.test.ui.default

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * @date 2020/6/3 23:43
 * @user 威威君
 */
class DefaultViewModel : ViewModel() {
    var mutableLiveData = MutableLiveData<String>()
    var mediatorLiveData = MediatorLiveData<String>()

}