package com.example.tapandgo.screens.picker_date

import android.view.View
import androidx.fragment.app.activityViewModels
import com.example.tapandgo.R
import com.example.tapandgo.base.BaseFragment
import com.example.tapandgo.databinding.FragmentPickerDateBinding
import com.example.tapandgo.screens.car_detail.CarDetailViewModel
import com.example.tapandgo.utils.dateToLocalDate
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.threeten.bp.Clock
import org.threeten.bp.LocalDate
import org.threeten.bp.Period
import org.threeten.bp.ZonedDateTime
import java.util.*

class PickerDateFragment: BaseFragment<PickerDateViewModel, FragmentPickerDateBinding, PickerDateHandler>() {
    override val layout = R.layout.fragment_picker_date
    override val handler by lazy { requireActivity() as PickerDateHandler }
    override val viewModel: PickerDateViewModel by viewModel()
    private val carDetailViewModel: CarDetailViewModel by sharedViewModel()

    override fun setup() {

        val startDate = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault())
        val endDate = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault())
        endDate.add(Calendar.MONTH, 6)

        binding.calendar.apply {
            showDayOfWeekTitle(true)
            setRangeDate(startDate.time, endDate.time)
            setOnRangeSelectedListener { startDate, endDate, startLabel, endLabel ->
                viewModel.selectedRange.value = startLabel to endLabel
                viewModel.selectedRangeLocalDate.value = dateToLocalDate(startDate) to dateToLocalDate(endDate)
            }
            setOnStartSelectedListener { startDate, label ->
                viewModel.selectedRange.value = label to label
                viewModel.selectedRangeLocalDate.value = dateToLocalDate(startDate) to dateToLocalDate(startDate)
            }
        }

        viewModel.selectedRange.observe(this, {
            if (it != null) {
                binding.buttonSave.visibility = View.VISIBLE
                binding.fromPlaceholder.visibility = View.GONE
                binding.fromPlaceholderUnderline.visibility = View.GONE
                binding.toPlaceholder.visibility = View.GONE
                binding.toPlaceholderUnderline.visibility = View.GONE
                binding.title.text = "SELECTED RANGE"
                binding.selectedRange.visibility = View.VISIBLE
                binding.selectedRange.text = "${it.first} - ${it.second}"
            } else {
                binding.buttonSave.visibility = View.GONE
                binding.fromPlaceholder.visibility = View.VISIBLE
                binding.fromPlaceholderUnderline.visibility = View.VISIBLE
                binding.toPlaceholder.visibility = View.VISIBLE
                binding.toPlaceholderUnderline.visibility = View.VISIBLE
                binding.title.text = "SELECT RANGE"
                binding.selectedRange.visibility = View.GONE
            }
        })

        viewModel.selectedRangeLocalDate.observe(this, { range ->
            if (range != null) {
                val period = Period.between(range.first, range.second)
                val pricePerDay = carDetailViewModel.car.value?.pricePerDay ?: 0.0
                val currency = carDetailViewModel.car.value?.currency ?: ""
                val result = (period.days + 1) * pricePerDay
                binding.price.text = "$result $currency"
            } else {
                binding.price.text = "0"
            }
        })

        binding.buttonSave.setOnClickListener {
            carDetailViewModel.selectedRange.value = viewModel.selectedRange.value
            carDetailViewModel.selectedRangeLocalDate.value = viewModel.selectedRangeLocalDate.value
            handler.navigateUp()
        }
    }
}