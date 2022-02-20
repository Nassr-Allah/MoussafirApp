package com.ems.moussafirdima.ui.screens.login

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ems.moussafirdima.R
import com.ems.moussafirdima.TransportationActivity
import com.ems.moussafirdima.domain.model.User
import com.ems.moussafirdima.domain.model.toUserDto
import com.ems.moussafirdima.ui.view_models.UploadImageViewModel
import com.ems.moussafirdima.ui.view_models.client_view_models.AddClientViewModel
import com.ems.moussafirdima.ui.view_models.client_view_models.DeleteClientViewModel
import com.ems.moussafirdima.ui.view_models.client_view_models.GetClientViewModel
import com.ems.moussafirdima.ui.view_models.client_view_models.InsertClientViewModel
import com.ems.moussafirdima.util.ImageUpload
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun SignUpScreen(
    navController: NavController,
    user: User?,
    addClientViewModel: AddClientViewModel = hiltViewModel(),
    insertClientViewModel: InsertClientViewModel = hiltViewModel(),
    deleteClientViewModel: DeleteClientViewModel = hiltViewModel()
) {
    val state = addClientViewModel.state.value
    Log.d("SignUpScreen", state.toString())
    val context = LocalContext.current.applicationContext
    var progressBarAlpha by remember {
        mutableStateOf(0f)
    }
    var textAlpha by remember {
        mutableStateOf(1f)
    }
    var isUploading by remember {
        mutableStateOf(false)
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 35.dp, vertical = 20.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = null,
                modifier = Modifier
                    .size(30.dp)
                    .clickable {
                        navController.navigateUp()
                    }
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                SignUpHeader()
                ProfilePicture(user)
            }
            ProfileInfo(
                name = user?.firstName ?: "" + " " + user?.lastName,
                address = user?.address ?: "",
                phone = user?.phoneNumber ?: ""
            )
            ProfileGender(gender = user?.gender ?: "male")
            LaunchedEffect(key1 = state, key2 = user) {
                if (!state.isLoading && state.client != null) {
                    deleteClientViewModel.deleteClient()
                    insertClientViewModel.insertClient(state.client)
                    Toast.makeText(context, context.getString(R.string.account_created), Toast.LENGTH_SHORT).show()
                    val toTransportation = Intent(context, TransportationActivity::class.java)
                    toTransportation.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(toTransportation)
                }
                if (state.isLoading) {
                    progressBarAlpha = 1f
                    textAlpha = 0f
                    isUploading = false
                }
                if (!state.isLoading && state.error.isNotEmpty()) {
                    progressBarAlpha = 0f
                    textAlpha = 1f
                    Toast.makeText(context, state.error, Toast.LENGTH_SHORT).show()
                }
            }
            Button(
                onClick = {
                    addClientViewModel.addClient(user!!.toUserDto())
                    Log.d("SignUpScreen", user.toString())
                    Log.d("SignUp", addClientViewModel.state.toString())
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .clip(RoundedCornerShape(15)),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black)
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(id = R.string.create),
                        style = MaterialTheme.typography.body1,
                        fontSize = dimensionResource(id = R.dimen.body1).value.sp,
                        color = Color.White,
                        modifier = Modifier.alpha(textAlpha)
                    )
                    CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier.alpha(progressBarAlpha)
                    )
                }
            }
        }
    }
}

@Composable
fun SignUpHeader() {
    Column {
        Text(
            text = stringResource(id = R.string.gender),
            style = MaterialTheme.typography.h1,
            color = Color.Black,
            fontSize = dimensionResource(R.dimen.h0).value.sp
        )
        Text(
            text = "Profile",
            style = MaterialTheme.typography.h1,
            color = Color.Black,
            fontSize = dimensionResource(R.dimen.h0).value.sp
        )
    }
}

@Composable
fun ProfilePicture(user: User?) {
    Box(modifier = Modifier
        .size(dimensionResource(R.dimen.seventy_five_dp))
        .clip(RoundedCornerShape(15))) {
        Card(elevation = 20.dp) {
            Log.d("UserPic", user.toString())
            GlideImage(
                imageModel = user!!.profilePicture,
                contentDescription = null,
                contentScale = ContentScale.Crop,
            )
        }
    }
}

@Composable
fun ProfileInfo(name: String, address: String, phone: String) {
    Column(
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.body1,
            color = Color.Black,
            fontSize = dimensionResource(R.dimen.body1).value.sp,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .border(
                    width = 1.dp, color = Color.Black,
                    shape = RoundedCornerShape(15)
                )
                .padding(start = 15.dp, top = 15.dp),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.fifteen_dp)))
        Text(
            text = address,
            style = MaterialTheme.typography.body1,
            color = Color.Black,
            fontSize = dimensionResource(R.dimen.body1).value.sp,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .border(
                    width = 1.dp, color = Color.Black,
                    shape = RoundedCornerShape(15)
                )
                .padding(start = 15.dp, top = 15.dp),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.fifteen_dp)))
        Text(
            text = phone,
            style = MaterialTheme.typography.body1,
            color = Color.Black,
            fontSize = dimensionResource(R.dimen.body1).value.sp,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .border(
                    width = 1.dp, color = Color.Black,
                    shape = RoundedCornerShape(15)
                )
                .padding(start = 15.dp, top = 15.dp),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun ProfileGender(gender: String) {

    val maleBackgroundColor = if (gender == "male") {
        Color.Gray
    } else {
        Color.Transparent
    }
    val femaleBackgroundColor = if (gender == "female") {
        Color.Gray
    } else {
        Color.Transparent
    }
    val maleTint = if (gender == "male") {
        Color.White
    } else {
        Color.Gray
    }
    val femaleTint = if (gender == "female") {
        Color.White
    } else {
        Color.Gray
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = R.string.gender),
            style = MaterialTheme.typography.body1,
            color = Color.Black,
            fontSize = dimensionResource(R.dimen.body1).value.sp
        )
        Row {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(color = maleBackgroundColor)
                    .border(width = 1.dp, color = Color.Gray, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.mars),
                    contentDescription = null,
                    tint = maleTint,
                    modifier = Modifier.size(dimensionResource(R.dimen.twenty_five_dp))
                )
            }
            Spacer(modifier = Modifier.width(15.dp))
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(color = femaleBackgroundColor)
                    .border(width = 1.dp, color = Color.Gray, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.femenine),
                    contentDescription = null,
                    tint = femaleTint,
                    modifier = Modifier.size(dimensionResource(R.dimen.twenty_five_dp))
                )
            }
        }
    }
}
