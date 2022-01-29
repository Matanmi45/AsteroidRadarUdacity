package com.udacity.asteroidradar.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.asteroidradar.Asteroid

class DetailViewModel: ViewModel() {

    private val _displayExplanationDialog = MutableLiveData<Boolean>()
    val displayExplanationDialog: LiveData<Boolean>
            get() = _displayExplanationDialog

    fun onExplanationButtonClicked(){
        _displayExplanationDialog.value = true
    }

    fun onExplanationButtonDialogueGone(){
        _displayExplanationDialog.value = false
    }


}