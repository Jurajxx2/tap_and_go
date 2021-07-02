package com.example.tapandgo.base

import androidx.navigation.NavDirections

interface BaseHandler {
    fun navigate(nav: NavDirections)
    fun navigateUp()
}