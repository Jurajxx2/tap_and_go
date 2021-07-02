package com.example.tapandgo.screens.car_detail

import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.tapandgo.R
import com.example.tapandgo.base.BaseFragment
import com.example.tapandgo.databinding.FragmentCarDetailBinding
import com.example.tapandgo.screens.picker_date.PickerDateViewModel
import com.example.tapandgo.screens.picker_location.PickerLocationViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CarDetailFragment: BaseFragment<CarDetailViewModel, FragmentCarDetailBinding, CarDetailHandler>() {
    override val layout = R.layout.fragment_car_detail
    override val handler by lazy { requireActivity() as CarDetailHandler }
    override val viewModel: CarDetailViewModel by activityViewModels()

    private val args: CarDetailFragmentArgs by navArgs()

    override fun setup() {
        viewModel.cars.value = args.cars.cars

        viewModel.selectedRange.observe(this, {
            handleSelection()
        })

        viewModel.selectedCar.observe(this, {
            handleSelection()
        })

        viewModel.selectedLocation.observe(this, {
            if (it != null) {
                binding.selectedLocation.text = it
            } else {
                handleSelection()
            }
        })

        viewModel.cars.observe(this, {
            val car = it.first()
            Glide.with(binding.carImage)
                .load(car.imageURL)
                .placeholder(R.drawable.splash_hero)
                .into(binding.carImage)

            binding.carName.text = "${car.brand} ${car.model}"
            binding.carData.text = "${car.fuel} • ${car.type}"
        })

        viewModel.car.observe(this, { car ->
            Glide.with(binding.carImage)
                .load(car.imageURL)
                .placeholder(R.drawable.splash_hero)
                .into(binding.carImage)
            binding.carName.text = "${car.brand} ${car.model}"
            binding.carRegistration.text = car.registrationPlate
            binding.carData.text = "${car.fuel} • ${car.type}"
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
    }

    private fun handleSelection() {
        when {
            viewModel.selectedRange.value == null && viewModel.selectedCar.value == null -> {
                binding.selectLocation.visibility = View.VISIBLE
                binding.selectDate.visibility = View.GONE
                binding.selectedDateHolder.visibility = View.GONE
                binding.selectedLocationHolder.visibility = View.GONE

                binding.orderButton.setBackgroundResource(R.drawable.rectangle_rounded_background_grey)
                binding.orderButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.strong_grey))
            }
            viewModel.selectedCar.value == null -> {
                viewModel.selectedRange.value = null
                binding.orderButton.setBackgroundResource(R.drawable.rectangle_rounded_background_grey)
                binding.orderButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.strong_grey))
            }
            viewModel.selectedRange.value == null -> {
                binding.selectLocation.visibility = View.GONE
                binding.selectDate.visibility = View.VISIBLE
                binding.selectedDateHolder.visibility = View.GONE
                binding.selectedLocationHolder.visibility = View.VISIBLE
                binding.orderButton.setBackgroundResource(R.drawable.rectangle_rounded_background_grey)
                binding.orderButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.strong_grey))
            }
            else -> {
                binding.selectLocation.visibility = View.GONE
                binding.selectDate.visibility = View.GONE
                binding.selectedDateHolder.visibility = View.VISIBLE
                binding.selectedLocationHolder.visibility = View.VISIBLE
                binding.orderButton.setBackgroundResource(R.drawable.rectangle_rounded_background_blue)
                binding.orderButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            }
        }
    }
}