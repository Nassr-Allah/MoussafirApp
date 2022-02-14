package com.ems.moussafirdima.util

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.ui.text.toLowerCase
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import com.ems.moussafirdima.domain.model.User
import com.ems.moussafirdima.domain.model.toUserDto
import com.ems.moussafirdima.ui.navigation.AuthScreens
import com.ems.moussafirdima.ui.view_models.client_view_models.AddClientViewModel
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ImageUpload() {

    private val storageReference = FirebaseStorage.getInstance().getReference("profile_pictures")

    fun uploadImage(imgUri: Uri, user: User, navController: NavController) {
        val fileReference = storageReference.child(user.phoneNumber)
        fileReference.putFile(imgUri).apply {
            addOnCompleteListener {
                getImageUrl(it.result!!, user, navController)
            }
            addOnProgressListener {
                Log.d("Upload Progress", it.bytesTransferred.toString())
            }
            addOnFailureListener {
                Log.d("Upload Progress", it.localizedMessage ?: "Unexpected Firebase Error")
            }
        }
    }

    private fun getImageUrl(task: UploadTask.TaskSnapshot, user: User, navController: NavController) {
        task.storage.downloadUrl.apply {
            addOnCompleteListener {
                user.profilePicture = it.result.toString()
                navController.currentBackStackEntry?.arguments?.putParcelable("user", user)
                navController.navigate(AuthScreens.SignUpScreen.route)
                Log.d("ImageUrl", user.toString())
            }
            addOnFailureListener {
                Log.d("ImageUrl", it.localizedMessage ?: "Unexpected Error")
            }
        }
    }
}