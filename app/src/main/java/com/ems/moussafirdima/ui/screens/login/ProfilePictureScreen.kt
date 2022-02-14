package com.ems.moussafirdima.ui.screens.login

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ems.moussafirdima.R
import com.ems.moussafirdima.domain.model.User
import com.ems.moussafirdima.ui.navigation.AuthScreens
import com.ems.moussafirdima.ui.theme.MoussafirDimaTheme
import com.ems.moussafirdima.ui.theme.Orange
import com.ems.moussafirdima.util.ImageUpload

var uri: Uri? = Uri.EMPTY

@Composable
fun ProfilePictureScreen(
    navController: NavController,
    user: User?
) {
    var alpha by remember {
        mutableStateOf(0f)
    }
    Box(
        modifier = Modifier.fillMaxSize(),
    )
    {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    text = "Choose a profile picture",
                    style = MaterialTheme.typography.h1,
                    color = Color.Black,
                    fontSize = dimensionResource(R.dimen.h2).value.sp
                )
                ProfilePicturePicker(user = user)
                CircularProgressIndicator(color = Orange, modifier = Modifier.alpha(alpha))
            }
            Button(
                onClick = {
                    alpha = 1f
                    val imgUpload = ImageUpload()
                    imgUpload.uploadImage(uri!!, user!!, navController)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .clip(RoundedCornerShape(15)),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black)
            ) {
                Text(
                    text = "next",
                    style = MaterialTheme.typography.body1,
                    fontSize = dimensionResource(id = R.dimen.body1).value.sp,
                    color = Color.White
                )
            }

        }
    }
}

@Composable
fun ProfilePicturePicker(user: User?) {
    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    val context = LocalContext.current.applicationContext
    val bitmap = remember {
        mutableStateOf<Bitmap?>(null)
    }
    var isPicked by remember {
        mutableStateOf(false)
    }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
    ) {
        imageUri = it
        uri = it
        isPicked = true
        user?.profilePicture
    }
    Box(
        modifier = Modifier
            .size(200.dp)
            .clip(RoundedCornerShape(15)),
        contentAlignment = Alignment.Center
    ) {
        Card(elevation = 5.dp) {
            if (isPicked) {
                imageUri?.let {
                    if (Build.VERSION.SDK_INT < 28) {
                        bitmap.value = MediaStore.Images
                            .Media.getBitmap(context.contentResolver, it)

                    } else {
                        val source = ImageDecoder
                            .createSource(context.contentResolver, it)
                        bitmap.value = ImageDecoder.decodeBitmap(source)
                    }

                    bitmap.value?.let { btm ->
                        Image(
                            bitmap = btm.asImageBitmap(),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.clickable {
                                launcher.launch("image/*")
                            }
                        )
                    }
                }
            } else {
                Image(
                    painter = painterResource(R.drawable.man),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.clickable {
                        launcher.launch("image/*")
                    },
                )
            }
        }
    }
}

@Preview(device = Devices.NEXUS_5)
@Composable
fun ProfilePicturePreview() {
    val navController = rememberNavController()
    MoussafirDimaTheme {
        Surface(color = MaterialTheme.colors.background) {
            ProfilePictureScreen(navController = navController, user = null)
        }
    }
}