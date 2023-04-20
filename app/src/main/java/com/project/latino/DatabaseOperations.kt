package com.project.latino

import android.content.Context
import androidx.room.*
import com.project.latino.models.ClubModel
import com.project.latino.models.SchoolModel


@Database(entities = [SchoolModel::class,ClubModel::class],version=1, exportSchema = false)
abstract class AppDatabase : RoomDatabase(){
    abstract fun schoolDao() :SchoolDao

    companion object{
        private var INSTANCE : AppDatabase?=null
        fun getInstance(context: Context) : AppDatabase?{
            if (INSTANCE==null){
                INSTANCE= Room.databaseBuilder(context.applicationContext,
                AppDatabase::class.java,"schools.db").build()
            }
            return INSTANCE
        }
    }

}

@Dao
interface SchoolDao {
    @Query("SELECT * FROM school_item")
    suspend fun getAllSchools():ArrayList<SchoolModel>
}