package com.example.sleeptracker.SleepTracker

import android.app.Application
import android.arch.lifecycle.Transformations
import android.provider.SyncStateContract.Helpers.insert
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.sleeptracker.Database.SleepDatabaseDao
import com.example.sleeptracker.Database.SleepNight
import com.example.sleeptracker.formatNights
import kotlinx.coroutines.*

class SleepTrackerViewModel(
    val database: SleepDatabaseDao,
    application: Application,
) : AndroidViewModel(application) {
    private var viewModelJob = Job()

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private var uiscope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private var tonight = MutableLiveData<SleepNight?>()
    private var nights = database.getAllNights()
    val nightsString = Transformations.map(nights){
        formatNights(it,application.resources)

    }

    init {
        initializeTonight()
    }

    private fun initializeTonight() {
        uiscope.launch {
            tonight.value = getTonightFromDatabase()
        }
    }

    private suspend fun getTonightFromDatabase() : SleepNight?{
        return withContext(Dispatchers.IO) {
            var night = database.getTonight()
            if (night?.endTimeMilli != night?.startTimeMilli) {
            }
            night
        }
    }
    fun onStartTracking(){
        uiscope.launch {
            val newNight = SleepNight()
            insert(newNight)
            tonight.value = getTonightFromDatabase()
        }
    }
    private suspend fun insert(night: SleepNight){
        withContext(Dispatchers.IO){
            database.insert(night)
        }
    }
    fun onStopTraking(){
        uiscope.launch {
            val oldNight = tonight.value ?: return@launch
            oldNight?.endTimeMilli = System.currentTimeMillis()
            update(oldNight)
        }
    }
    private suspend fun update(night: SleepNight){
        withContext(Dispatchers.IO){
            database.update(night)
        }
    }
    fun onClear(){
        uiscope.launch {
            clear()
            tonight.value = null
        }
    }
    suspend fun clear(){
        withContext(Dispatchers.IO){
            database.clear()
        }

    }
}