package com.dicoding.githubuserapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favorite: Favorite)

    @Update
    fun update(favorite: Favorite)

    @Delete
    fun delete(favorite: Favorite)

    @Query("SELECT * FROM Favorite")
    fun getFavoriteUser(): LiveData<List<Favorite>>

    @Query("SELECT * FROM Favorite WHERE username = :username")
    fun getFavoriteUserByUsername(username: String): LiveData<Favorite>

//    @Query("SELECT EXISTS(SELECT * FROM Favorite WHERE username = :username AND favorite = 1)")
//    fun isUserFavorite(username: String): Boolean
}