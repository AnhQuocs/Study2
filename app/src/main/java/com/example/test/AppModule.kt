package com.example.test

import android.content.Context
import com.example.test.flights.data.repository.FlightRepositoryImpl
import com.example.test.flights.data.source.FirebaseFlightDataSource
import com.example.test.flights.domain.repository.FlightRepository
import com.example.test.flights.domain.usecase.GetAllFlightsUseCase
import com.example.test.language.data.prefecences.LanguagePreferenceManager
import com.example.test.location.data.repository.LocationRepositoryImpl
import com.example.test.location.domain.repository.LocationRepository
import com.example.test.location.domain.usecase.GetUserLocationUseCase
import com.example.test.weather.data.remote.api.IApiService
import com.example.test.weather.data.repository.WeatherRepositoryImpl
import com.example.test.weather.domain.repository.WeatherRepository
import com.example.test.weather.domain.usecase.GetWeatherByLocationUseCase
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

    @Provides
    @Singleton
    fun provideFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun provideLanguagePreferenceManager(@ApplicationContext context: Context): LanguagePreferenceManager {
        return LanguagePreferenceManager(context)
    }
//
//    // MULTIPLE_LANG
//    @Provides
//    fun provideUserRepository(): UserRepository = FirebaseUserRepository()
//
//    @Provides
//    fun provideRegisterUserUseCase(repo: UserRepository) = RegisterUserUseCase(repo)
//
//    @Provides
//    @Singleton
//    fun provideLanguagePreferenceManager(
//        @ApplicationContext context: Context
//    ): LanguagePreferenceManager = LanguagePreferenceManager(context)

    @Provides
    fun provideApiService(): IApiService {
        return Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(IApiService::class.java)
    }

    @Provides
    fun provideWeatherRepository(api: IApiService): WeatherRepository {
        return WeatherRepositoryImpl(api)
    }

    @Provides
    fun provideWeatherUseCase(repository: WeatherRepository): GetWeatherByLocationUseCase {
        return GetWeatherByLocationUseCase(repository)
    }

    @Provides
    fun provideLocationProvider(@ApplicationContext context: Context): LocationRepository =
        LocationRepositoryImpl(context)

    @Provides
    fun provideGetUserLocationUseCase(locationProvider: LocationRepository): GetUserLocationUseCase =
        GetUserLocationUseCase(locationProvider)

    // FLIGHTS
    @Provides
    @Singleton
    fun provideFlightRepository(
        dataSource: FirebaseFlightDataSource
    ): FlightRepository {
        return FlightRepositoryImpl(dataSource)
    }

    @Provides
    @Singleton
    fun provideGetAllFlightsUseCase(
        flightRepository: FlightRepository
    ): GetAllFlightsUseCase {
        return GetAllFlightsUseCase(flightRepository)
    }

    @Provides
    @Singleton
    fun provideFirebaseFlightDataSource(): FirebaseFlightDataSource {
        return FirebaseFlightDataSource()
    }
}