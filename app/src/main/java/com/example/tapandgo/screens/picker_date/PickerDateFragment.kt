package com.example.tapandgo.screens.picker_date

import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import com.example.tapandgo.R
import com.example.tapandgo.base.BaseFragment
import com.example.tapandgo.databinding.FragmentPickerDateBinding
import com.example.tapandgo.databinding.ListItemCalendarDayBinding
import com.example.tapandgo.databinding.ListItemCalendarMonthBinding
import com.example.tapandgo.screens.car_detail.CarDetailViewModel
import com.example.tapandgo.utils.dateToLocalDateTime
import com.example.tapandgo.utils.dateToString
import com.example.tapandgo.utils.localDateToLocalDate
import com.example.tapandgo.utils.stringToDate
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.CalendarMonth
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.MonthHeaderFooterBinder
import com.kizitonwose.calendarview.ui.ViewContainer
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.threeten.bp.*
import java.time.YearMonth
import java.time.temporal.WeekFields
import java.util.*

class PickerDateFragment: BaseFragment<PickerDateViewModel, FragmentPickerDateBinding, PickerDateHandler>() {
    override val layout = R.layout.fragment_picker_date
    override val handler by lazy { requireActivity() as PickerDateHandler }
    override val viewModel: PickerDateViewModel by viewModel()
    private val carDetailViewModel: CarDetailViewModel by activityViewModels()

    override fun setup() {

        val startDate = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault())
        val endDate = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault())
        endDate.add(Calendar.MONTH, 6)

        binding.calendar.apply {
            showDayOfWeekTitle(true)
            setRangeDate(startDate.time, endDate.time)
            setOnRangeSelectedListener { startDate, endDate, startLabel, endLabel ->
                viewModel.selectedRange.value = startLabel to endLabel
                viewModel.selectedRangeLocalDate.value = dateToLocalDateTime(startDate) to dateToLocalDateTime(endDate)
            }
            setOnStartSelectedListener { startDate, label ->
                viewModel.selectedRange.value = label to label
                viewModel.selectedRangeLocalDate.value = dateToLocalDateTime(startDate) to dateToLocalDateTime(startDate)
            }
        }


        binding.calendarView.dayBinder = object : DayBinder<DayViewContainer> {
            override fun create(view: View) = DayViewContainer(view)
            override fun bind(container: DayViewContainer, day: CalendarDay) {
                container.textView.text = day.day.toString()
            }
        }

        binding.calendarView.monthHeaderBinder = object :
            MonthHeaderFooterBinder<MonthViewContainer> {
            override fun create(view: View) = MonthViewContainer(view)
            override fun bind(container: MonthViewContainer, month: CalendarMonth) {
                container.textView.text = "${month.yearMonth.month.name.lowercase()
                    .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }} ${month.year}"
            }
        }

        val currentMonth = YearMonth.now()
        val firstMonth = currentMonth.minusMonths(10)
        val lastMonth = currentMonth.plusMonths(10)
        val firstDayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek
        binding.calendarView.setup(firstMonth, lastMonth, firstDayOfWeek)
        binding.calendarView.scrollToMonth(currentMonth)
        binding.calendarView.hasBoundaries = true

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
            val unavailableDays = viewModel.unavailableDays.value
            if (unavailableDays != null && range != null) {
                unavailableDays.forEach {
                    if (range.first.minusDays(1).isBefore(it) && range.second.plusDays(1).isAfter(it)) {
                        val dialog = AlertDialog.Builder(requireContext())
                            .setTitle("Please, choose another date. Your selection is already reserved. Reservations:")
                            .setMessage(viewModel.unavailableDays.value?.map { "${it.dayOfMonth}.${it.monthValue}.${it.year}" }?.joinToString())
                            .setPositiveButton("") { dialog, _ -> dialog?.dismiss() }
                            .create()

                        dialog.show()
                    }
                }
            }
        })

        carDetailViewModel.selectedCar.observe(this, {
            if (it != null) {
                val unavailable = it.carReservations.flatMap {
                    val dates = mutableListOf<LocalDateTime>()
                    val dateTo = stringToDate(it.to)
                    var date = stringToDate(it.from)
                    dates.add(date)
                    while (date.isBefore(dateTo)) {
                        date = date.plusDays(1)
                        dates.add(date)
                    }
                    dates
                }

                viewModel.unavailableDays.postValue(unavailable)
            }
        })

        viewModel.unavailableDays.observe(this, {
            Log.d("TAPX", "result: ${it.map { dateToString(it) }.joinToString("\n")}")
        })

        binding.buttonSave.setOnClickListener {
            carDetailViewModel.selectedRange.value = viewModel.selectedRange.value
            carDetailViewModel.selectedRangeDate.value = viewModel.selectedRangeLocalDate.value
            handler.navigateUp()
        }
    }

    private fun getLocalDate(createdAt: String, clock: Clock): LocalDate {
        val zonedDateTime = ZonedDateTime.parse(createdAt)
        return zonedDateTime.withZoneSameInstant(clock.zone).toLocalDate()
    }



    class DayViewContainer(view: View) : ViewContainer(view) {
        val textView = ListItemCalendarDayBinding.bind(view).day
        lateinit var day: CalendarDay
        lateinit var binding: FragmentPickerDateBinding
        private var selectedDate: LocalDate? = null

        init {

        }
    }

    class MonthViewContainer(view: View) : ViewContainer(view) {
        val textView = ListItemCalendarMonthBinding.bind(view).month
    }

}