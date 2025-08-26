package com.vaasudev.androidcomposestructure.presentation.home

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vaasudev.androidcomposestructure.data.data_store.MyDataStore
import com.vaasudev.androidcomposestructure.domain.dto.ErrorDto
import com.vaasudev.androidcomposestructure.domain.repository.AuthRepository
import com.vaasudev.androidcomposestructure.domain.utility.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.vaasudev.androidcomposestructure.domain.utility.Result
import com.vaasudev.androidcomposestructure.domain.utility.error_handle.asNetworkErrorString
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: AuthRepository,
    private val dataStore: MyDataStore
) : ViewModel() {

    private val eventChannel = Channel<Status>()
    val event = eventChannel.receiveAsFlow()

    fun restGetAPI(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            eventChannel.send(Status.Loading)
            repository.userDetails().collect { response ->
                when (response) {
                    is Result.Error -> {
                        eventChannel.send(Status.Error(
                            ErrorDto(
                                statusCode = response.error.status,
                                message = response.error.asNetworkErrorString(context = context)
                            )
                        ))
                    }

                    is Result.Success -> {
                        if (response.data.status == "success") {
                            eventChannel.send(Status.Success(response.data))
                        } else {
                            eventChannel.send(Status.Error(ErrorDto(message = response.data.message)))
                        }
                    }
                }
            }
        }
    }

}
  