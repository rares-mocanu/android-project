package com.project.latino

import android.content.Context
import androidx.room.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.project.latino.models.ClubModel
import com.project.latino.models.EventModel
import com.project.latino.models.SchoolModel
import java.lang.reflect.Type
import java.util.Calendar

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

    @TypeConverter
    fun intListToJson(value: ArrayList<Int>) = Gson().toJson(value)


    val intlisttype = object : TypeToken<ArrayList<Int>>() {}.type
    @TypeConverter
    fun jsonToIntList(value: String) : ArrayList<Int> = Gson().fromJson(value,intlisttype)


    @TypeConverter
    fun calendarToString(value: Calendar) = Gson().toJson(value)

    val calendartype = object : TypeToken<Calendar>() {}.type

    @TypeConverter
    fun jsonToCalendar(value: String) : Calendar = Gson().fromJson(value,calendartype)
}

@Database(entities = [SchoolModel::class,ClubModel::class,EventModel::class],version=2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase(){
    abstract fun schoolDao(): SchoolDao
    abstract fun clubDao(): ClubDao

    abstract fun eventDao(): EventDao
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
    @Query("DELETE FROM school_item")
    suspend fun deleteAll()

    @Insert
    suspend fun insertSchool(school: SchoolModel)

    @Delete
    suspend fun deleteSchool(school: SchoolModel)

}

@Dao
interface ClubDao {
    @Query("SELECT * FROM club_item")
    suspend fun getAllClubs():List<ClubModel>
    @Query("DELETE FROM club_item")
    suspend fun deleteAll()

    @Query("SELECT * FROM club_item where clubID = :id")
    suspend fun getClubOfId(id :Int):List<ClubModel>
    @Insert
    suspend fun insertClub(club: ClubModel)

    @Delete
    suspend fun deleteClub(club: ClubModel)

}
@Dao
interface EventDao{
    @Query("SELECT * FROM event_item")
    suspend fun getAllEvents():List<EventModel>

    @Query("SELECT * FROM event_item WHERE club = :club")
    suspend fun getEventsOfClub(club: String):List<EventModel>
    @Query("DELETE FROM event_item")
    suspend fun deleteAll()

    @Insert
    suspend fun insertEvent(event:EventModel)

    @Delete
    suspend fun deleteEvent(event:EventModel)


}