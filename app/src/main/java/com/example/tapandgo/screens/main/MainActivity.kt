package com.example.tapandgo.screens.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import com.example.tapandgo.R
import com.example.tapandgo.base.BaseActivity
import com.example.tapandgo.databinding.ActivityMainBinding
import com.example.tapandgo.screens.car_detail.CarDetailHandler
import com.example.tapandgo.screens.car_list.CarListHandler
import com.example.tapandgo.screens.information.InformationHandler
import com.example.tapandgo.screens.login.LoginHandler
import com.example.tapandgo.screens.picker_date.PickerDateHandler
import com.example.tapandgo.screens.picker_location.PickerLocationHandler
import com.example.tapandgo.screens.registration.RegistrationHandler
import com.example.tapandgo.screens.rides_history.RidesHistoryHandler
import com.example.tapandgo.screens.splash.SplashHandler

class MainActivity : BaseActivity<ActivityMainBinding>(), SplashHandler, LoginHandler, RegistrationHandler, CarListHandler, CarDetailHandler, PickerDateHandler, PickerLocationHandler, RidesHistoryHandler, InformationHandler{

    override val layout = R.layout.activity_main

    override fun setupActivity() {

    }

    override fun navigate(nav: NavDirections) {
        findNavController(R.id.nav_host_fragment_main).navigate(nav)
    }

    override fun navigateUp() {
        findNavController(R.id.nav_host_fragment_main).navigateUp()
    }
}