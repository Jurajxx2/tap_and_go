package com.example.tapandgo.screens.car_detail

import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.tapandgo.R
import com.example.tapandgo.base.BaseFragment
import com.example.tapandgo.databinding.FragmentCarDetailBinding
import com.example.tapandgo.screens.car_list.CarListFragmentDirections
import com.example.tapandgo.screens.rides_history.RidesHistoryFragmentDirections
import com.example.tapandgo.utils.getSendSupportEmailIntent
import com.google.android.material.navigation.NavigationView
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CarDetailFragment: BaseFragment<CarDetailViewModel, FragmentCarDetailBinding, CarDetailHandler>() {
    override val layout = R.layout.fragment_car_detail
    override val handler by lazy { requireActivity() as CarDetailHandler }
    override val viewModel: CarDetailViewModel by sharedViewModel()

    private val args: CarDetailFragmentArgs by navArgs()

    override fun setup() {
        setNavigationViewListener()
        viewModel.car.value = args.car

        viewModel.selectedRange.observe(this, {
            when (it) {
                null -> {
                    binding.selectDate.visibility = View.VISIBLE
                    binding.selectedDateHolder.visibility = View.GONE
                    binding.orderButton.setBackgroundResource(R.drawable.rectangle_rounded_background_grey)
                    binding.orderButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.strong_grey))
                }
                else -> {
                    binding.selectedDate.text = "${it.first} - ${it.second}"
                    binding.selectDate.visibility = View.GONE
                    binding.selectedDateHolder.visibility = View.VISIBLE
                    binding.orderButton.setBackgroundResource(R.drawable.rectangle_rounded_background_blue)
                    binding.orderButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                }
            }
        })

        viewModel.selectedLocation.observe(this, {
            binding.selectedLocation.text = it
        })

        viewModel.success.observe(this, {
            if (it) {
                viewModel.success.value = false
                handler.navigateUp()
            }
        })

        viewModel.car.observe(this, { car ->
            Glide.with(binding.carImage)
                .load(car.imageURL)
                .placeholder(R.drawable.splash_hero)
                .into(binding.carImage)
            binding.carName.text = "${car.brand} ${car.model}"
            binding.carRegistration.text = car.registrationPlate
            binding.carData.text = "${car.fuel} • ${car.type}"

            viewModel.getLocationFromCoordinates(car)
        })

        binding.selectDate.setOnClickListener {
            handler.navigate(CarDetailFragmentDirections.openPickerDate())
        }

        binding.selectLocation.setOnClickListener {
            handler.navigate(CarDetailFragmentDirections.openPickerLocation())
        }

        binding.selectedLocationHolder.setOnClickListener {
            handler.navigate(CarDetailFragmentDirections.openPickerLocation())
        }

        binding.selectedDateHolder.setOnClickListener {
            handler.navigate(CarDetailFragmentDirections.openPickerDate())
        }

        binding.orderButton.setOnClickListener {
            viewModel.sendOrder()
        }
    }

    private fun setNavigationViewListener() {
        binding.nvView.menu.getItem(0).isChecked = true
        binding.nvView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.car_rental -> {
                    handler.navigate(CarDetailFragmentDirections.openCarList())
                }
                R.id.history_rides -> {
                    handler.navigate(CarDetailFragmentDirections.openRidesHistory())
                }
                R.id.email_us -> {
                    val intent = getSendSupportEmailIntent()
                    if (intent.resolveActivity(requireActivity().packageManager) != null) {
                        requireActivity().startActivity(intent)
                    }
                }
                R.id.information -> {
                    handler.navigate(CarDetailFragmentDirections.openInformation())
                }
                R.id.logout -> {
                    handler.navigate(CarDetailFragmentDirections.openLogout())
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