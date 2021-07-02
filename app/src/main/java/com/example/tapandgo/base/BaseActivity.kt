package com.example.tapandgo.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<B: ViewDataBinding>: AppCompatActivity() {

    protected abstract val layout: Int
    private lateinit var binding: B

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBinding()
        setupActivity()
    }

    private fun setupBinding() {
        val binding = DataBindingUtil.setContentView<B>(this, layout)
        this.binding = binding
        binding.executePendingBindings()
    }

    abstract fun setupActivity()
}