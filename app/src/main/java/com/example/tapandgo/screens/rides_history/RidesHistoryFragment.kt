package com.example.tapandgo.screens.rides_history

import com.example.tapandgo.R
import com.example.tapandgo.base.BaseFragment
import com.example.tapandgo.databinding.FragmentPickerLocationBinding
import com.example.tapandgo.databinding.FragmentRidesHistoryBinding
import com.example.tapandgo.screens.picker_location.PickerLocationHandler
import com.example.tapandgo.screens.picker_location.PickerLocationViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class RidesHistoryFragment: BaseFragment<RidesHistoryViewModel, FragmentRidesHistoryBinding, RidesHistoryHandler>() {
    override val layout = R.layout.fragment_rides_history
    override val handler by lazy { requireActivity() as RidesHistoryHandler }
    override val viewModel: RidesHistoryViewModel by viewModel()

    override fun setup() {

    }
}