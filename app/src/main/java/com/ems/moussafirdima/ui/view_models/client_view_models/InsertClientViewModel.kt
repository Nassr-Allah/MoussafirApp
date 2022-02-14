package com.ems.moussafirdima.ui.view_models.client_view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ems.moussafirdima.domain.model.User
import com.ems.moussafirdima.domain.use_case.client.InsertClientUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InsertClientViewModel @Inject constructor(
    private val insertClientUseCase: InsertClientUseCase
) : ViewModel() {

    fun insertClient(client: User) {
        viewModelScope.launch {
            insertClientUseCase(client)
        }
    }

}