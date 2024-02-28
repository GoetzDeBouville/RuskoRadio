package com.hellcorp.extensions

import android.util.Log

fun String.formatExpression(): String {
    var openBracketIndex = -1
    if (this.contains('(')) {
        for (i in indices) {
            if (this[i] == '(') openBracketIndex = i
            break
        }
        Log.i("MyLog", "formatExpression = $this")
        return this.subSequence(0..openBracketIndex).toString()
    }
    Log.i("MyLog", "formatExpression = $this")
    return this
}
