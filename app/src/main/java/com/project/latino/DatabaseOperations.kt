package com.project.latino

import android.content.Context
import androidx.room.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.project.latino.models.ClubModel
import com.project.latino.models.SchoolModel
import java.lang.reflect.Type

class Converters{
    @TypeConverter
    fun listToJson(value: ArrayList<String>) = Gson().toJson(value)


    val stringlisttype = object : TypeToken<ArrayList<String>>() {}.type
    @TypeConverter
    fun jsonToList(value: String) : ArrayList<String> = Gson().fromJson(value,stringlisttype)

    @TypeConverter
    fun schoollistToJson(value: ArrayList<SchoolModel>) = Gson().toJson(value)


    val schoollisttype = object : TypeToken<ArrayList<SchoolModel>>() {}.type
    @TypeConverter
    fun schooljsonToList(value: String) : ArrayList<SchoolModel> = Gson().fromJson(value,schoollisttype)


}

@Database(entities = [SchoolModel::class,ClubModel::class],version=1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase(){
    abstract fun schoolDao(): SchoolDao
    abstract fun clubDao(): ClubDao
    companion object{
        private var INSTANCE : AppDatabase?=null
        fun getInstance(context: Context) : AppDatabase?{
            if (INSTANCE==null){
                INSTANCE= Room.databaseBuilder(context.applicationContext,
                AppDatabase::class.java,"database.db").build()
            }
            return INSTANCE
        }
    }

}

@Dao
interface SchoolDao {
    @Query("SELECT * FROM school_item")
    suspend fun getAllSchools():List<SchoolModel>

    @Query("SELECT * FROM school_item WHERE name LIKE '%' || :name || '%'")
    suspend fun getSchoolsLikeName(name: String):List<SchoolModel>

    @Insert
    suspend fun insertSchool(school: SchoolModel)

    @Delete
    suspend fun deleteSchool(school: SchoolModel)

}

@Dao
interface ClubDao {
    @Query("SELECT * FROM club_item")
    suspend fun getAllClubs():List<ClubModel>

    @Insert
    suspend fun insertClub(club: ClubModel)

    @Delete
    suspend fun deleteClub(club: ClubModel)

}