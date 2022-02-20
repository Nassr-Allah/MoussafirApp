package com.ems.moussafirdima.ui.screens.main_app.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.DefaultTintColor
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ems.moussafirdima.R
import com.ems.moussafirdima.ui.theme.MoussafirDimaTheme

@Composable
fun AboutUsScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxHeight()
        ) {
            Text(
                text = "About Us",
                style = MaterialTheme.typography.h1,
                fontSize = dimensionResource(R.dimen.h0).value.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(30.dp))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.fillMaxHeight(0.7f)
            ) {
                InfoItem(title = "Version", name = "1.0.0")
                InfoItem(title = stringResource(id = R.string.founder), name = "Fethi Nouar")
                Spacer(modifier = Modifier.height(15.dp))
                InfoItem(title = "Designer", name = "Yahia Anas")
                Spacer(modifier = Modifier.height(15.dp))
                InfoItem(title = stringResource(id = R.string.developer), name = "Nassr-Allah Guetatlia")
                Spacer(modifier = Modifier.height(15.dp))
                InfoItem(title = stringResource(id = R.string.website), name = "moussafir.com")
            }
            Spacer(modifier = Modifier.height(35.dp))
            Image(
                painter = painterResource(R.drawable.ic_moussafir),
                contentDescription = null,
                modifier = Modifier.size(150.dp)
            )
        }
    }
}

@Composable
fun InfoItem(title: String, name: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.h2,
        fontSize = dimensionResource(R.dimen.h2).value.sp,
        color = Color.Black
    )
    Text(
        text = name,
        style = MaterialTheme.typography.body1,
        fontSize = dimensionResource(R.dimen.body1).value.sp,
        color = Color.Black,
    )
}
