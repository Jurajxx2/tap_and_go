package com.example.tapandgo.screens.login

import android.util.Patterns
import android.widget.Toast
import com.example.tapandgo.R
import com.example.tapandgo.base.BaseFragment
import com.example.tapandgo.databinding.FragmentLoginBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment: BaseFragment<LoginViewModel, FragmentLoginBinding, LoginHandler>() {
    override val layout = R.layout.fragment_login
    override val handler by lazy { requireActivity() as LoginHandler }
    override val viewModel: LoginViewModel by viewModel()

    override fun setup() {
        binding.viewmodel = viewModel

        viewModel.email.value = "jozko1@mrkvicka.sk"
        viewModel.password.value = "jozkoMrkvicka1"
        viewModel.keepSignedIn.value = true

        binding.buttonLogin.setOnClickListener {
            if (isOK()) {
                viewModel.login()
            }
        }

        binding.buttonSignup.setOnClickListener {
            handler.navigate(LoginFragmentDirections.openRegistration())
        }

        viewModel.success.observe(this, {
            if (it) {
                handler.navigate(LoginFragmentDirections.openCarList())
                viewModel.success.value = false
            }
        })

        viewModel.error.observe(this, {
            Toast.makeText(context, "Error: $it", Toast.LENGTH_SHORT).show()
        })

        binding.toolbar.setNavigationOnClickListener {
            handler.navigateUp()
        }

        viewModel.email.observe(this, {
            handleButton()
        })

        viewModel.password.observe(this, {
            handleButton()
        })
    }

    private fun handleButton() {
        if (viewModel.email.value.isNullOrEmpty() || viewModel.password.value.isNullOrEmpty()) {
            binding.buttonLogin.setBackgroundResource(R.drawable.rectangle_rounded_background_grey)
        } else {
            binding.buttonLogin.setBackgroundResource(R.drawable.rectangle_rounded_background_blue)
        }
    }

    private fun isOK(): Boolean {
        var isOK = true

        if (!Patterns.EMAIL_ADDRESS.matcher(viewModel.email.value.toString()).matches()) {
            isOK = false
            binding.emailInputHolder.error = "Enter valid email."
        } else {
            binding.emailInputHolder.error = null
        }

        return isOK
    }
}