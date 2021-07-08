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
import com.pixplicity.easyprefs.library.Prefs
import org.osmdroid.config.Configuration.getInstance
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.overlay.ItemizedIconOverlay
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus
import org.osmdroid.views.overlay.OverlayItem


class PickerLocationFragment: BaseFragment<CarDetailViewModel, FragmentPickerLocationBinding, PickerLocationHandler>() {
    override val layout = R.layout.fragment_picker_location
    override val handler by lazy { requireActivity() as PickerLocationHandler }
    override val viewModel: CarDetailViewModel by activityViewModels()

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

        viewModel.selectedLocation.observe(this, {
            binding.name.text = it
        })

        viewModel.car.observe(this, { car ->
            val newPoint = GeoPoint(car.location.latitude, car.location.longitude)
            binding.map.controller.animateTo(newPoint, 14.0, 10)


            val items = ArrayList<OverlayItem>()
            items.add(OverlayItem(car.registrationPlate, "${car.pricePerDay} eur per day", GeoPoint(car.location.latitude, car.location.longitude)))

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

        binding.map.zoomController.setVisibility(CustomZoomButtonsController.Visibility.NEVER)
        binding.map.setMultiTouchControls(true)
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