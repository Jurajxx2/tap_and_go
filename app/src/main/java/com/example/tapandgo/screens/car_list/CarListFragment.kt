package com.example.tapandgo.screens.car_list

import android.widget.Toast
import com.example.tapandgo.R
import com.example.tapandgo.base.BaseFragment
import com.example.tapandgo.databinding.FragmentCarListBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class CarListFragment : BaseFragment<CarListViewModel, FragmentCarListBinding, CarListHandler>() {
    override val layout = R.layout.fragment_car_list
    override val handler by lazy { requireActivity() as CarListHandler }
    override val viewModel: CarListViewModel by viewModel()

    override fun setup() {
        viewModel.error.observe(this, {
            Toast.makeText(context, "Error: $it", Toast.LENGTH_SHORT).show()
        })

        viewModel.cars.observe(this, { cars ->

            binding.carList.withModels {
                cars.forEach { cars ->
                    val car = cars.cars.firstOrNull()
                    if (car != null)
                    SingleCarModel_()
                        .id(car.id)
                        .image(car.imageURL)
                        .name("${car.brand} ${car.model}")
                        .year(car.year)
                        .data("${car.fuel} • ${car.type}")
                        .action {
                            handler.navigate(CarListFragmentDirections.openCarDetail(cars))
                        }
                        .addTo(this)
                }
            }

        })

        binding.carList.withModels {

        }
    }
}