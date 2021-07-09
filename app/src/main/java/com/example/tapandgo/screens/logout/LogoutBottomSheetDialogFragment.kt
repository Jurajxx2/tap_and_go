package com.example.tapandgo.screens.logout

import com.example.tapandgo.R
import com.example.tapandgo.base.BaseBottomSheetDialogFragment
import com.example.tapandgo.databinding.FragmentLogoutBottomSheetDialogBinding
import com.example.tapandgo.screens.login.LoginHandler
import com.example.tapandgo.screens.login.LoginViewModel
import com.example.tapandgo.screens.rides_history.RidesHistoryFragmentDirections
import org.koin.androidx.viewmodel.ext.android.viewModel

class LogoutBottomSheetDialogFragment: BaseBottomSheetDialogFragment<LogoutBottomSheetDialogViewModel, FragmentLogoutBottomSheetDialogBinding, LogoutBottomSheetDialogHandler>() {
    override val layout = R.layout.fragment_logout_bottom_sheet_dialog
    override val handler by lazy { requireActivity() as LogoutBottomSheetDialogHandler }
    override val viewModel: LogoutBottomSheetDialogViewModel by viewModel()

    override fun setup() {
        viewModel.success.observe(this, {
            if (it) {
                viewModel.success.value = false
                handler.navigate(LogoutBottomSheetDialogFragmentDirections.openSplash())
            }
        })

        binding.logout.setOnClickListener {
            viewModel.logout()
        }

        binding.cancel.setOnClickListener {
            dismiss()
        }
    }
}