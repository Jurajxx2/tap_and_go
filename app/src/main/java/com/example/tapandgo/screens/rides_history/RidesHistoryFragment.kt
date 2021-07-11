package com.example.tapandgo.screens.rides_history

import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.tapandgo.R
import com.example.tapandgo.base.BaseFragment
import com.example.tapandgo.databinding.FragmentRidesHistoryBinding
import com.example.tapandgo.screens.car_list.CarListFragmentDirections
import com.example.tapandgo.screens.car_list.SingleCarModel_
import com.example.tapandgo.screens.information.InformationFragmentDirections
import com.example.tapandgo.utils.getSendSupportEmailIntent
import com.example.tapandgo.utils.localDateToFormatted
import com.example.tapandgo.utils.stringToDate
import org.koin.androidx.viewmodel.ext.android.viewModel

class RidesHistoryFragment: BaseFragment<RidesHistoryViewModel, FragmentRidesHistoryBinding, RidesHistoryHandler>() {
    override val layout = R.layout.fragment_rides_history
    override val handler by lazy { requireActivity() as RidesHistoryHandler }
    override val viewModel: RidesHistoryViewModel by viewModel()

    override fun setup() {
        setNavigationViewListener()
        viewModel.error.observe(this, {
            Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
        })

        viewModel.loading.observe(this, {
            binding.loading.visibility = if (it) View.VISIBLE else View.GONE
        })

        viewModel.rentals.observe(this, { result ->
            binding.rentalList.withModels {
                result?.let {
                    it.forEachIndexed { index, pair ->
                        val rental = pair.first
                        val car = pair.second
                        val date1 = localDateToFormatted(stringToDate(rental.from).toLocalDate())
                        val date2 = localDateToFormatted(stringToDate(rental.to).toLocalDate())
                        SingleRentalModel_()
                            .id(index)
                            .image(car.imageURL)
                            .name("${car.brand} ${car.model}")
                            .status(rental.status)
                            .date("$date1 • $date2")
                            .addTo(this)
                    }
                }
            }

        })
    }

    private fun setNavigationViewListener() {
        binding.nvView.menu.getItem(1).isChecked = true
        binding.nvView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.car_rental -> {
                    handler.navigate(RidesHistoryFragmentDirections.openCarList())
                }
                R.id.history_rides -> {}//already here
                R.id.email_us -> {
                    val intent = getSendSupportEmailIntent()
                    if (intent.resolveActivity(requireActivity().packageManager) != null) {
                        requireActivity().startActivity(intent)
                    }
                }
                R.id.information -> {
                    handler.navigate(RidesHistoryFragmentDirections.openInformation())
                }
                R.id.logout -> {
                    handler.navigate(RidesHistoryFragmentDirections.openLogout())
                }
            }
            binding.drawerLayout.close()
            true
        }

        val name = binding.nvView.getHeaderView(0).findViewById<TextView>(R.id.profile_name)
        val email = binding.nvView.getHeaderView(0).findViewById<TextView>(R.id.profile_email)
        //val photo = binding.nvView.getHeaderView(0).findViewById<ImageView>(R.id.profile_image)

        viewModel.user.observe(this, {
            name.text = "${it?.firstName}'s account"
            email.text = it?.email
        })

        binding.toolbar.setNavigationOnClickListener {
            binding.drawerLayout.open()
        }
    }
}