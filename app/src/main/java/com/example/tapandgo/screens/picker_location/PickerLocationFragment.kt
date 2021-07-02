package com.example.tapandgo.screens.picker_location

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.activityViewModels
import com.example.tapandgo.R
import com.example.tapandgo.base.BaseFragment
import com.example.tapandgo.databinding.FragmentPickerLocationBinding
import com.example.tapandgo.screens.car_detail.CarDetailViewModel
import com.example.tapandgo.screens.picker_location.adapters.PositionsAdapter
import com.pixplicity.easyprefs.library.Prefs
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.osmdroid.config.Configuration.getInstance
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.overlay.ItemizedIconOverlay
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus
import org.osmdroid.views.overlay.OverlayItem


class PickerLocationFragment: BaseFragment<PickerLocationViewModel, FragmentPickerLocationBinding, PickerLocationHandler>() {
    override val layout = R.layout.fragment_picker_location
    override val handler by lazy { requireActivity() as PickerLocationHandler }
    override val viewModel: PickerLocationViewModel by viewModel()
    private val carDetailViewModel: CarDetailViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        getInstance().load(context, Prefs.getPreferences());
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun setup() {
        binding.map.setTileSource(TileSourceFactory.MAPNIK)
        val startPoint = GeoPoint(50.0819889,14.4128241)
        binding.map.controller.animateTo(startPoint, 13.0, 10)

        val adapter = PositionsAdapter()
        adapter.setupLocations(emptyList())

        viewModel.locations.observe(this, {
            adapter.setupLocations(it.map { it.first })

            val items = ArrayList<OverlayItem>()
            it.forEach {
                items.add(OverlayItem(it.first, "${it.second.pricePerDay} eur per day", GeoPoint(it.second.location.latitude, it.second.location.longitude)))
            }

            val overlay = ItemizedOverlayWithFocus<OverlayItem>(items, object:
                ItemizedIconOverlay.OnItemGestureListener<OverlayItem> {
                override fun onItemSingleTapUp(index:Int, item:OverlayItem):Boolean {
                    return true
                }
                override fun onItemLongPress(index:Int, item:OverlayItem):Boolean {
                    return false
                }
            }, context)
            overlay.setFocusItemsOnTap(true)

            binding.map.overlays.add(overlay)
            binding.map.invalidate()
        })

        carDetailViewModel.cars.observe(this, {
            viewModel.getLocationFromCoordinates(it)
        })

        binding.map.zoomController.setVisibility(CustomZoomButtonsController.Visibility.NEVER)
        binding.map.setMultiTouchControls(true)

        binding.positionsDropdown.adapter = adapter
        binding.positionsDropdown.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {}

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (position != 0) {
                        val selectedItem = carDetailViewModel.cars.value?.get(position - 1)
                        if (selectedItem != null) {
                            viewModel.selectedCar.value = selectedItem
                            viewModel.selectedLocation.value = adapter.getItems()[position]
                        }
                    } else {
                        viewModel.selectedCar.value = null
                        viewModel.selectedLocation.value = null
                    }
                }
            }

        viewModel.selectedCar.observe(this, { car ->
            if (car != null) {
                binding.buttonSave.visibility = View.VISIBLE
                val newPoint = GeoPoint(car.location.latitude, car.location.longitude)
                binding.map.controller.animateTo(newPoint, 14.0, 10)
            } else {
                binding.buttonSave.visibility = View.GONE
            }
        })

        binding.buttonSave.setOnClickListener {
            carDetailViewModel.selectedCar.value = viewModel.selectedCar.value
            carDetailViewModel.selectedLocation.value = viewModel.selectedLocation.value
            carDetailViewModel.selectedRange.value = null
            handler.navigateUp()
        }
    }

    override fun onPause() {
        super.onPause()
        binding.map.onPause()
    }

    override fun onResume() {
        super.onResume()
        binding.map.onResume()
    }
}