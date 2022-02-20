package com.ems.moussafirdima.ui.screens.main_app.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ems.moussafirdima.R
import com.ems.moussafirdima.ui.materials.SupportListItem
import com.ems.moussafirdima.ui.theme.LightGray
import com.ems.moussafirdima.ui.theme.MoussafirDimaTheme

@Composable
fun SupportScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 35.dp, vertical = 40.dp),
    ) {
        SupportScreenHeader(navController)
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            SupportScreenList(
                list = listOf(
                    SupportListItem(stringResource(id = R.string.contact_us), ""),
                    SupportListItem(stringResource(id = R.string.about_us), ""),
                    SupportListItem("Feedback", ""),
                    SupportListItem(stringResource(id = R.string.terms_of_service), ""),
                ),
                navController = navController
            )
        }
    }
}

@Composable
fun SupportScreenHeader(navController: NavController) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_back),
            contentDescription = null,
            tint = Color.Black,
            modifier = Modifier
                .size(30.dp)
                .clickable {
                    navController.navigateUp()
                }
        )
        Spacer(modifier = Modifier.width(dimensionResource(R.dimen.thirty_dp)))
        Text(
            text = stringResource(id = R.string.help_support),
            style = MaterialTheme.typography.h2,
            fontSize = dimensionResource(R.dimen.h0).value.sp,
            color = Color.Black
        )
    }
}

@Composable
fun SupportScreenList(list: List<SupportListItem>, navController: NavController) {
    LazyColumn {
        items(list.size) {
            SupportScreenListItem(item = list[it], navController)
        }
    }
}

@Composable
fun SupportScreenListItem(item: SupportListItem, navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
            .clickable {
                if (item.route.isNotEmpty()) {
                    navController.navigate(item.route)
                }
            },
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = item.title,
            style = MaterialTheme.typography.body1,
            color = Color.Black,
            fontSize = dimensionResource(R.dimen.h1).value.sp
        )
        Box(
            modifier = Modifier
                .size(35.dp)
                .clip(RoundedCornerShape(10))
                .background(LightGray)
                .padding(5.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_arrow_right),
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}
