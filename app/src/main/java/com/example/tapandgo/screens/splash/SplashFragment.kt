package com.example.tapandgo.screens.splash

import com.example.tapandgo.R
import com.example.tapandgo.base.BaseFragment
import com.example.tapandgo.databinding.FragmentSplashBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashFragment: BaseFragment<SplashViewModel, FragmentSplashBinding, SplashHandler>() {
    override val layout = R.layout.fragment_splash
    override val handler by lazy { requireActivity() as SplashHandler }
    override val viewModel: SplashViewModel by viewModel()

    override fun setup() {
        binding.buttonLogin.setOnClickListener {
            handler.navigate(SplashFragmentDirections.openLogin())
        }

        binding.buttonSignup.setOnClickListener {
            handler.navigate(SplashFragmentDirections.openRegistration())
        }

        binding.toolbar.setNavigationOnClickListener {
            binding.drawerLayout.open()
        }
    }
}