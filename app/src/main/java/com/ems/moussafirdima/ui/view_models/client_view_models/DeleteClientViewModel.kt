package com.ems.moussafirdima.ui.view_models.client_view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ems.moussafirdima.domain.use_case.client.DeleteClientUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeleteClientViewModel @Inject constructor(
    private val deleteClientUseCase: DeleteClientUseCase
) : ViewModel() {

    fun deleteClient() {
        viewModelScope.launch {
            deleteClientUseCase()
        }
    }

}