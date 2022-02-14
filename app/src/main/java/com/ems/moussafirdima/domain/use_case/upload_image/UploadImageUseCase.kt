package com.ems.moussafirdima.domain.use_case.upload_image

import android.net.Uri
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.ems.moussafirdima.domain.model.User
import com.ems.moussafirdima.util.Resource
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

class UploadImageUseCase @Inject constructor(
    private val storageRef: StorageReference
) {

    suspend operator fun invoke(uri: Uri, user: User): Flow<Resource<String>> = flow {
        val fileRef = storageRef.child(user.phoneNumber)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                Log.d("ImgUpload", "Started Uploading")
                emit(Resource.Loading<String>())
                val task = async { fileRef.putFile(uri) }
                val result = task.await()
                if (result.isSuccessful) {
                    Log.d("ImgUpload", "Uploaded")
                    val urlTask = async { result.result.storage.downloadUrl }
                    val urlResult = urlTask.await()
                    if (urlResult.isSuccessful) {
                        val url = urlResult.result.toString()
                        emit(Resource.Success<String>(url))
                    } else {
                        emit(Resource.Error<String>(urlResult.exception?.localizedMessage ?: "Unexpected Error"))
                    }
                } else {
                    emit(Resource.Error<String>(result.exception?.localizedMessage ?: "Unexpected Error"))
                }
            } catch (e: Exception) {
                emit(Resource.Error<String>(e.localizedMessage ?: "Unexpected Error"))
            }
        }
    }

}