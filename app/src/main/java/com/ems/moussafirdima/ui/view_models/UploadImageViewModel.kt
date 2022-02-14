package com.ems.moussafirdima.ui.view_models

import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ems.moussafirdima.domain.model.User
import com.ems.moussafirdima.domain.use_case.upload_image.UploadImageUseCase
import com.ems.moussafirdima.ui.view_models.states.ImageUploadState
import com.ems.moussafirdima.util.Resource
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UploadImageViewModel @Inject constructor(
    private val uploadImageUseCase: UploadImageUseCase
) : ViewModel() {

    private val _state = mutableStateOf(ImageUploadState())
    val state: State<ImageUploadState> = _state

    fun uploadImage(uri: Uri, user: User): Flow<Resource<String>> = flow {
        uploadImageUseCase(uri, user).onEach { result ->
            when(result) {
                is Resource.Success -> {
                    _state.value = ImageUploadState(url = result.data ?: "")
                }
                is Resource.Loading -> {
                    _state.value = ImageUploadState(isLoading = true)
                }
                is Resource.Error -> {
                    _state.value = ImageUploadState(error = result.message ?: "Unexpected Error")
                }
            }
        }.launchIn(viewModelScope)
    }

}