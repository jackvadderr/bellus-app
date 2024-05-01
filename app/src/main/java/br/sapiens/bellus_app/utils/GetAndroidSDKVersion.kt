package br.sapiens.bellus_app.utils

import android.os.Build

fun getAndroidSDKVersion(): Int {
    return Build.VERSION.SDK_INT
}