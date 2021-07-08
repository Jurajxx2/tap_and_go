package com.example.tapandgo

import com.example.tapandgo.api.CarService
import com.example.tapandgo.api.RentalService
import com.example.tapandgo.api.UserService
import com.example.tapandgo.helpers.AppDatabase
import com.example.tapandgo.helpers.FirebaseObject
import com.example.tapandgo.helpers.RetrofitFactory
import com.example.tapandgo.repository.CarRepository
import com.example.tapandgo.repository.RentalRepository
import com.example.tapandgo.repository.UserRepository
import com.example.tapandgo.repository.impl.CarRepositoryImpl
import com.example.tapandgo.repository.impl.RentalRepositoryImpl
import com.example.tapandgo.repository.impl.UserRepositoryImpl
import com.example.tapandgo.screens.car_detail.CarDetailViewModel
import com.example.tapandgo.screens.car_list.CarListViewModel
import com.example.tapandgo.screens.information.InformationViewModel
import com.example.tapandgo.screens.login.LoginViewModel
import com.example.tapandgo.screens.logout.LogoutBottomSheetDialogViewModel
import com.example.tapandgo.screens.picker_date.PickerDateViewModel
import com.example.tapandgo.screens.registration.RegistrationViewModel
import com.example.tapandgo.screens.rides_history.RidesHistoryViewModel
import com.example.tapandgo.screens.splash.SplashViewModel
import com.google.firebase.database.FirebaseDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single<FirebaseDatabase> { FirebaseObject.getInstance() }

    single { AppDatabase.getInstance(androidContext()).userDao() }
    single { AppDatabase.getInstance(androidContext()).carDao() }
    single { AppDatabase.getInstance(androidContext()).rentalDao() }

    single { RetrofitFactory.getApi(CarService::class.java) }
    single { RetrofitFactory.getApi(UserService::class.java) }
    single { RetrofitFactory.getApi(RentalService::class.java) }

    single<UserRepository> { UserRepositoryImpl(get(), get(), get(), get()) }
    single<CarRepository> { CarRepositoryImpl(get(), get(), get()) }
    single<RentalRepository> { RentalRepositoryImpl(get(), get(), get()) }

    viewModel { SplashViewModel(get()) }
    viewModel { RegistrationViewModel(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { CarListViewModel(get(), get()) }
    viewModel { CarDetailViewModel(get(), get()) }
    viewModel { PickerDateViewModel() }
    viewModel { InformationViewModel(get()) }
    viewModel { RidesHistoryViewModel(get(), get(), get()) }
    viewModel { LogoutBottomSheetDialogViewModel(get(), get(), get()) }
}