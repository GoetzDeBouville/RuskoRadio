package com.hellcorp.extensions

import android.util.Log

/**
 * This method returns string without expression in brackets
 */
fun String.formatExpression(): String {
    if (this.contains('(')) {
        val openBracketIndex = this.indexOf('(')
        Log.i("MyLog", "formatExpression = $this")
        val formatedStr = this.subSequence(0..openBracketIndex).toString()
        Log.i("MyLog", "formatedStr = $formatedStr")
        return formatedStr
    }
    return this
}

/**
 * This method returns translated artist name if it's written with cyrillick chars
 */
fun String.songArtistTranslite(): String {
    val dashIndex = this.indexOf('-')
    if (dashIndex != -1) {
        val partToTransliterate = this.substring(0, dashIndex + 1)
        val restOfTheString = this.substring(dashIndex + 1)

        Log.i("MyLog", "track artist name $partToTransliterate")
        val transliteratedPart = partToTransliterate.map { char ->
            TranslateConst.TRANSLATE_MAP[char] ?: char
        }.joinToString("")

        Log.i("MyLog", "track artist translated $transliteratedPart")
        return transliteratedPart + restOfTheString
    }
    return this
}


private object TranslateConst {
    val TRANSLATE_MAP = mapOf(
        'А' to "A",
        'Б' to "B",
        'В' to "V",
        'Г' to "G",
        'Д' to "D",
        'Е' to "E",
        'Ё' to "E",
        'Ж' to "ZH",
        'З' to "Z",
        'И' to "I",
        'Й' to "I",
        'К' to "K",
        'Л' to "L",
        'М' to "M",
        'Н' to "N",
        'О' to "O",
        'П' to "P",
        'Р' to "R",
        'С' to "S",
        'Т' to "T",
        'У' to "U",
        'Ф' to "F",
        'Х' to "KH",
        'Ц' to "TS",
        'Ч' to "CH",
        'Ш' to "SH",
        'Щ' to "SHCH",
        'Ъ' to "IE",
        'Ы' to "Y",
        'Э' to "E",
        'Ю' to "IU",
        'Я' to "IA"
    )
}