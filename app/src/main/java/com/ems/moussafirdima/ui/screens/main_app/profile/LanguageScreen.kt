package com.ems.moussafirdima.ui.screens.main_app.profile


import android.preference.PreferenceManager
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ems.moussafirdima.R
import com.ems.moussafirdima.ui.materials.LanguageItem
import com.ems.moussafirdima.ui.theme.LightGray
import com.ems.moussafirdima.ui.theme.MoussafirDimaTheme
import com.ems.moussafirdima.ui.theme.Orange
import com.ems.moussafirdima.util.LanguageHelper
import java.util.*

@Composable
fun LanguageScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 35.dp, vertical = 40.dp),
    ) {
        LanguageScreenHeader(navController)
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            LanguageScreenList(list = listOf(
                LanguageItem("English", "en"),
                LanguageItem("Francais", "fr")
            ))
        }
    }
}

@Composable
fun LanguageScreenHeader(navController: NavController) {
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
            text = "Help & Support",
            style = MaterialTheme.typography.h2,
            fontSize = dimensionResource(R.dimen.h0).value.sp,
            color = Color.Black
        )
    }
}

@Composable
fun LanguageScreenList(list: List<LanguageItem>) {
    val context = LocalContext.current
    val language = LanguageHelper.getUserLanguage(context)
    var selectedLanguage by remember {
        mutableStateOf(language)
    }
    Column {
        list.forEach {
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = selectedLanguage == it.code,
                    onClick = {
                        selectedLanguage = it.code
                        LanguageHelper.storeLanguage(context, it.code)
                        LanguageHelper.updateLanguage(context, it.code)
                    },
                    enabled = true,
                    colors = RadioButtonDefaults.colors(selectedColor = Orange)
                )
                Spacer(modifier = Modifier.width(dimensionResource(R.dimen.twenty_dp)))
                Text(
                    text = it.language,
                    style = MaterialTheme.typography.body1,
                    fontSize = dimensionResource(R.dimen.h2).value.sp,
                    color = Color.Black
                )
            }
        }
    }
}

@Composable
fun LanguageScreenListItem(name: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = name,
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

