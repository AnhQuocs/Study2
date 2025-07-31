package com.example.test

import com.example.test.multiple_lang.data.repository.FirebaseUserRepository
import com.example.test.multiple_lang.domain.repository.UserRepository
import com.example.test.multiple_lang.domain.usecase.RegisterUserUseCase
import com.example.test.weather.data.remote.api.IApiService
import com.example.test.weather.data.repository.WeatherRepositoryImpl
import com.example.test.weather.domain.repository.WeatherRepository
import com.example.test.weather.domain.usecase.GetWeatherByLocationUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

    // MULTIPLE_LANG
    @Provides
    fun provideUserRepository(): UserRepository = FirebaseUserRepository()

    @Provides
    fun provideRegisterUserUseCase(repo: UserRepository) = RegisterUserUseCase(repo)

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
}