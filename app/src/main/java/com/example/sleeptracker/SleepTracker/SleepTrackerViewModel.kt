package com.example.sleeptracker.SleepTracker

import android.app.Application
import android.arch.lifecycle.Transformations
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.sleeptracker.Database.SleepDatabaseDao
import com.example.sleeptracker.Database.SleepNight

class SleepTrackerViewModel(
    val database: SleepDatabaseDao,
    application: Application,
) : AndroidViewModel(application) {
   /* private var tonight = MutableLiveData<SleepNight?>()
    private val night = database.getAllNights()

    val nightsString = Transformations.map(night){
        nights -> formatNights(nights,application.resources)*/
    //}
}