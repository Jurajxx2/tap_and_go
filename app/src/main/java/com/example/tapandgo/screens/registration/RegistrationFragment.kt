package com.example.tapandgo.screens.registration

import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import com.example.tapandgo.R
import com.example.tapandgo.base.BaseFragment
import com.example.tapandgo.databinding.FragmentRegistrationBinding
import com.example.tapandgo.screens.splash.SplashViewModel
import com.example.tapandgo.utils.isNameOK
import com.example.tapandgo.utils.isPasswordValid
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegistrationFragment: BaseFragment<RegistrationViewModel, FragmentRegistrationBinding, RegistrationHandler>() {
    override val layout = R.layout.fragment_registration
    override val handler by lazy { requireActivity() as RegistrationHandler }
    override val viewModel: RegistrationViewModel by viewModel()

    override fun setup() {
        binding.viewmodel = viewModel

        /*viewModel.name.value = "Jožko Mrkvička"
        viewModel.email.value = "jozko1@mrkvicka.sk"
        viewModel.password.value = "jozkoMrkvicka1"*/

        viewModel.loading.observe(this, {
            binding.loading.visibility = if (it) View.VISIBLE else View.GONE
        })

        binding.buttonLogin.setOnClickListener {
            handler.navigate(RegistrationFragmentDirections.openLogin())
        }

        binding.buttonRegister.setOnClickListener {
            if (isOK()) {
                viewModel.register()
            }
        }

        viewModel.success.observe(this, {
            if (it) {
                handler.navigate(RegistrationFragmentDirections.openCarList())
                viewModel.success.value = false
            }
        })

        viewModel.error.observe(this, {
            Toast.makeText(context, "Error: $it", Toast.LENGTH_SHORT).show()
        })

        binding.toolbar.setNavigationOnClickListener {
            handler.navigateUp()
        }

        viewModel.name.observe(this, {
            handleRegisterButton()
        })

        viewModel.email.observe(this, {
            handleRegisterButton()
        })

        viewModel.password.observe(this, {
            handleRegisterButton()
        })
    }

    private fun handleRegisterButton() {
        if (viewModel.name.value.isNullOrEmpty() || viewModel.email.value.isNullOrEmpty() || viewModel.password.value.isNullOrEmpty()) {
            binding.buttonRegister.setBackgroundResource(R.drawable.rectangle_rounded_background_grey)
        } else {
            binding.buttonRegister.setBackgroundResource(R.drawable.rectangle_rounded_background_blue)
        }
    }

    private fun isOK(): Boolean {
        var isOK = true
        if (!viewModel.name.value.toString().isNameOK()) {
            isOK = false
            binding.nameInputHolder.error = "Enter your first name and last name at least 3 characters long."
        } else {
            binding.nameInputHolder.error = null
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(viewModel.email.value.toString()).matches()) {
            isOK = false
            binding.mailInputHolder.error = "Enter valid email."
        } else {
            binding.mailInputHolder.error = null
        }

        if (!viewModel.password.value.toString().isPasswordValid()) {
            isOK = false
            binding.passwordInputHolder.error = "Password has to be at least 6 characters long and has to contain at least one upper case letter, one lowercase letter and one number."
        } else {
            binding.passwordInputHolder.error = null
        }

        return isOK
    }
}