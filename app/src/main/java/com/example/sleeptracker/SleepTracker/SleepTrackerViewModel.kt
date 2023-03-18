package com.example.sleeptracker.SleepTracker

import android.app.Application
import android.arch.lifecycle.Transformations
import android.provider.SyncStateContract.Helpers.insert
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
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

    private val uiscope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private var tonight = MutableLiveData<SleepNight?>()
    private val nights = database.getAllNights()
    val nightsString = androidx.lifecycle.Transformations.map(nights){
        formatNights(it,application.resources)

    }
private val _navigateToSleepQuality = MutableLiveData<SleepNight>()
    val navigationToSleepQuality : LiveData<SleepNight>
    get() = _navigateToSleepQuality

    fun doneNavigating(){
        _navigateToSleepQuality.value = null
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
            _navigateToSleepQuality.value = oldNight
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