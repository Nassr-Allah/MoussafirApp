package com.ems.moussafirdima.di

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ems.moussafirdima.data.local.ClientDao
import com.ems.moussafirdima.data.local.ClientDatabase
import com.ems.moussafirdima.data.local.RouteDao
import com.ems.moussafirdima.data.remote.DirectionsApi
import com.ems.moussafirdima.data.remote.MoussafirApi
import com.ems.moussafirdima.data.respository.*
import com.ems.moussafirdima.domain.repository.*
import com.ems.moussafirdima.util.Constants
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMoussafirApi(): MoussafirApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MoussafirApi::class.java)
    }

    @Provides
    @Singleton
    fun provideDirectionsApi(): DirectionsApi {
        return Retrofit.Builder()
            .baseUrl(Constants.DIRECTIONS_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DirectionsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideClientDatabase(app: Application): ClientDatabase {
        return Room.databaseBuilder(
            app,
            ClientDatabase::class.java,
            ClientDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideStorageReference(): StorageReference {
        return FirebaseStorage.getInstance().getReference("profile_pictures")
    }

    @Provides
    @Singleton
    fun provideTripRepository(api: MoussafirApi): TripsRepository {
        return TripRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideTicketRepository(api: MoussafirApi): TicketsRepository {
        return TicketRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideStationRepository(api: MoussafirApi): StationsRepository {
        return StationRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideCodeRepository(api: MoussafirApi): CodeRepository {
        return CodeRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideClientRepository(api: MoussafirApi, database: ClientDatabase): ClientRepository {
        return ClientRepositoryImpl(api, database.clientDao)
    }

    @Provides
    @Singleton
    fun provideRouteRepository(database: ClientDatabase): RouteRepository {
        return RouteRepositoryImpl(database.routeDao)
    }

}