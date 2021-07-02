package com.example.tapandgo.utils

fun String.isPasswordValid(): Boolean {
    var capitalFlag = false
    var lowerCaseFlag = false
    var number = false
    for (ch in this) {
        if (Character.isUpperCase(ch)) {
            capitalFlag = true
        } else if (Character.isLowerCase(ch)) {
            lowerCaseFlag = true
        } else if (Character.isDigit(ch)) {
            number = true
        }
        if (capitalFlag && lowerCaseFlag && number && this.length >= 6) return true
    }
    return false
}

fun String.isNameOK(): Boolean {
    var isOK = true
    val words = this.trim()
    val wordsList = words.split("\\s+".toRegex())
    val wordsCount = wordsList.size
    if (wordsCount < 2) {
        isOK = false
    }
    for (word in wordsList) {
        if (word.length < 3) {
            isOK = false
        }
    }
    return isOK
}