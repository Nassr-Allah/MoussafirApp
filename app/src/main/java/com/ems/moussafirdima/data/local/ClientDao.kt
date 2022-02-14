package com.ems.moussafirdima.data.local

import androidx.room.*
import com.ems.moussafirdima.domain.model.User
import kotlinx.coroutines.flow.Flow

@Dao
interface ClientDao {

    @Query("SELECT * FROM user")
    suspend fun getUser(): User?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Query("DELETE FROM user")
    suspend fun deleteUser()

}