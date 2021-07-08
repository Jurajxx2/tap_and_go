package com.example.tapandgo.screens.splash

import com.example.tapandgo.R
import com.example.tapandgo.base.BaseFragment
import com.example.tapandgo.databinding.FragmentSplashBinding
import com.example.tapandgo.screens.rides_history.RidesHistoryFragmentDirections
import com.example.tapandgo.utils.getSendSupportEmailIntent
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashFragment: BaseFragment<SplashViewModel, FragmentSplashBinding, SplashHandler>() {
    override val layout = R.layout.fragment_splash
    override val handler by lazy { requireActivity() as SplashHandler }
    override val viewModel: SplashViewModel by viewModel()

    override fun setup() {
        setNavigationViewListener()

        binding.buttonLogin.setOnClickListener {
            handler.navigate(SplashFragmentDirections.openLogin())
        }

        binding.buttonSignup.setOnClickListener {
            handler.navigate(SplashFragmentDirections.openRegistration())
        }

        binding.toolbar.setNavigationOnClickListener {
            binding.drawerLayout.open()
        }

        viewModel.loggedIn.observe(this, {
            if (it) {
                viewModel.loggedIn.value = false
                handler.navigate(SplashFragmentDirections.openCarList())
            }
        })
    }

    private fun setNavigationViewListener() {
        binding.nvView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.login -> {
                    handler.navigate(SplashFragmentDirections.openLogin())
                }
                R.id.signup -> {
                    handler.navigate(SplashFragmentDirections.openRegistration())
                }
            }
            binding.drawerLayout.close()
            true
        }

        binding.toolbar.setNavigationOnClickListener {
            binding.drawerLayout.open()
        }
    }
}