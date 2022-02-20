package com.ems.moussafirdima.ui.screens.main_app.profile

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.ems.moussafirdima.R
import com.ems.moussafirdima.domain.model.User
import com.ems.moussafirdima.ui.materials.AccountListItem
import com.ems.moussafirdima.ui.navigation.AuthScreens
import com.ems.moussafirdima.ui.navigation.MapScreens
import com.ems.moussafirdima.ui.theme.LightGray
import com.ems.moussafirdima.ui.theme.MoussafirDimaTheme
import com.ems.moussafirdima.ui.theme.Orange
import com.ems.moussafirdima.ui.view_models.client_view_models.DeleteClientViewModel
import com.ems.moussafirdima.ui.view_models.client_view_models.GetClientFromDbViewModel
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun AccountScreen(
    navController: NavController,
    getClientFromDbViewModel: GetClientFromDbViewModel = hiltViewModel(),
    deleteClientViewModel: DeleteClientViewModel = hiltViewModel()
) {
    val state = getClientFromDbViewModel.state.value
    val context = LocalContext.current.applicationContext
    Log.d("AccountScreen", state.client.toString())
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 40.dp, start = 15.dp, end = 15.dp, bottom = 100.dp)
            .clip(RoundedCornerShape(15))
            .background(Color.White)
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxHeight()
        ) {
            AccountScreenHeader(state.client)
            Spacer(modifier = Modifier.height(20.dp))
            Box(modifier = Modifier.fillMaxHeight(), contentAlignment = Alignment.Center) {
                if (!state.isLoading && state.client != null) {
                    AccountList(
                        list = listOf(
                            AccountListItem(
                                R.drawable.global, stringResource(R.string.language),
                                MapScreens.LanguageScreen.route
                            ),
                            AccountListItem(
                                R.drawable.bus, stringResource(R.string.tickets),
                                MapScreens.TripsScreen.withArgs(state.client.token)
                            ),
                            AccountListItem(
                                R.drawable.information,
                                stringResource(R.string.help_support), MapScreens.HelpScreen.route
                            ),
                            AccountListItem(
                                R.drawable.logout,
                                stringResource(R.string.logout), "null"
                            )
                        ),
                        navController = navController,
                        deleteClientViewModel
                    )
                } else if (state.isLoading) {
                    CircularProgressIndicator(color = Orange)
                } else {
                    Toast.makeText(context, state.error, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

@Composable
fun AccountScreenHeader(client: User?) {
    Log.d("AccountScreen", client.toString())
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Card(
            modifier = Modifier
                .clip(CircleShape)
                .size(55.dp)
        ) {
            GlideImage(
                imageModel = client?.profilePicture,
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
        }
        Spacer(modifier = Modifier.width(15.dp))
        Column(verticalArrangement = Arrangement.SpaceAround) {
            Text(
                text = "${client?.firstName} ${client?.lastName}",
                style = MaterialTheme.typography.h2,
                color = Color.Black,
                fontSize = dimensionResource(R.dimen.h2).value.sp
            )
            Text(
                text = client?.address ?: "",
                style = MaterialTheme.typography.body1,
                color = Color.Black,
                fontSize = dimensionResource(R.dimen.h2).value.sp,
                modifier = Modifier.alpha(0.6f)
            )
        }
    }
}

@Composable
fun AccountList(
    list: List<AccountListItem>,
    navController: NavController,
    viewModel: DeleteClientViewModel
) {
    val activity = LocalContext.current as Activity
    val logout = stringResource(R.string.logout)
    LazyColumn(contentPadding = PaddingValues(horizontal = 20.dp)) {
        items(list.size) {
            ListItem(item = list[it], navController) {
                if (list[it].title == logout) {
                    viewModel.deleteClient()
                    activity.finish()
                }
            }
        }
    }
}

@Composable
fun ListItem(item: AccountListItem, navController: NavController, onLogout: () -> Unit) {
    val alpha = if (item.title == stringResource(R.string.logout)) 0f else 1f
    val logout = stringResource(R.string.logout)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
            .clickable {
                onLogout()
                if (item.title != logout) {
                    navController.navigate(item.route)
                }
            },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Card(elevation = 2.dp) {
                Icon(
                    painter = painterResource(item.icon),
                    contentDescription = null,
                    tint = Orange,
                    modifier = Modifier
                        .size(35.dp)
                        .padding(8.dp)
                )
            }
            Spacer(modifier = Modifier.width(15.dp))
            Text(
                text = item.title,
                style = MaterialTheme.typography.body1,
                color = Color.Black,
                fontSize = dimensionResource(R.dimen.h2).value.sp
            )
        }
        Box(
            modifier = Modifier
                .alpha(alpha)
                .size(35.dp)
                .clip(RoundedCornerShape(10))
                .background(LightGray)
                .padding(5.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_arrowa_head_right),
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}