package com.example.tapandgo.screens.car_list

import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.tapandgo.R
import com.example.tapandgo.base.BaseFragment
import com.example.tapandgo.databinding.FragmentCarListBinding
import com.example.tapandgo.screens.rides_history.RidesHistoryFragmentDirections
import com.example.tapandgo.utils.getSendSupportEmailIntent
import com.google.android.material.navigation.NavigationView
import org.koin.androidx.viewmodel.ext.android.viewModel


class CarListFragment : BaseFragment<CarListViewModel, FragmentCarListBinding, CarListHandler>() {
    override val layout = R.layout.fragment_car_list
    override val handler by lazy { requireActivity() as CarListHandler }
    override val viewModel: CarListViewModel by viewModel()

    override fun setup() {
        setNavigationViewListener()
        viewModel.error.observe(this, {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        })

        viewModel.loading.observe(this, {
            binding.loading.visibility = if (it) View.VISIBLE else View.GONE
        })

        viewModel.cars.observe(this, { result ->
            binding.carList.withModels {
                result?.let {
                    it.forEach { car ->
                        SingleCarModel_()
                            .id(car.id)
                            .image(car.imageURL)
                            .name("${car.brand} ${car.model}")
                            .year(car.year)
                            .data("${car.fuel} • ${car.type}")
                            .action {
                                handler.navigate(CarListFragmentDirections.openCarDetail(car))
                            }
                            .addTo(this)
                    }
                }
            }

        })
    }

    private fun setNavigationViewListener() {
        binding.nvView.menu.getItem(0).isChecked = true
        binding.nvView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.car_rental -> {
                } //already here
                R.id.history_rides -> {
                    handler.navigate(CarListFragmentDirections.openRidesHistory())
                }
                R.id.email_us -> {
                    val intent = getSendSupportEmailIntent()
                    if (intent.resolveActivity(requireActivity().packageManager) != null) {
                        requireActivity().startActivity(intent)
                    }
                }
                R.id.information -> {
                    handler.navigate(CarListFragmentDirections.openInformation())
                }
                R.id.logout -> {
                    handler.navigate(CarListFragmentDirections.openLogout())
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