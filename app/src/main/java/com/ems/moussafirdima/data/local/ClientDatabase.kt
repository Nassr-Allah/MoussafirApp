package com.ems.moussafirdima.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ems.moussafirdima.domain.model.MapRoute
import com.ems.moussafirdima.domain.model.RoomTicket
import com.ems.moussafirdima.domain.model.Ticket
import com.ems.moussafirdima.domain.model.User

@Database(entities = [User::class, RoomTicket::class, MapRoute::class], version = 9)
abstract class ClientDatabase : RoomDatabase() {

    abstract val clientDao: ClientDao
    abstract val routeDao: RouteDao

    companion object {
        const val DATABASE_NAME = "clients_db"
    }

}