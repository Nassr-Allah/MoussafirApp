package com.ems.moussafirdima

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.ems.moussafirdima.ui.materials.CustomSnackBar
import com.ems.moussafirdima.ui.navigation.MapNavigation
import com.ems.moussafirdima.ui.screens.main_app.MapScreen
import com.ems.moussafirdima.ui.theme.MoussafirDimaTheme
import com.ems.moussafirdima.util.ConnectionLiveData
import com.ems.moussafirdima.util.LanguageHelper
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransportationActivity : ComponentActivity() {

    lateinit var connectionLiveData: ConnectionLiveData

    @ExperimentalPermissionsApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val language = LanguageHelper.getUserLanguage(this)
        LanguageHelper.updateLanguage(this, language)
        connectionLiveData = ConnectionLiveData(this)
        setContent {
            MoussafirDimaTheme {
                val isNetworkAvailable = connectionLiveData.observeAsState(initial = false).value
                val scaffoldState = rememberScaffoldState()
                Surface(color = Color.White) {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        scaffoldState = scaffoldState,
                        snackbarHost = {
                            CustomSnackBar(snackbarHostState = it) {

                            }
                        }
                    ) {
                        MapNavigation()
                    }
                    LaunchedEffect(key1 = isNetworkAvailable) {
                        if (!isNetworkAvailable) {
                            scaffoldState.snackbarHostState.showSnackbar(
                                message = "No Internet Connection",
                                duration = SnackbarDuration.Indefinite,
                            )
                        } else {
                            scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
                        }
                    }
                }
            }
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        val language = LanguageHelper.getUserLanguage(this)
        LanguageHelper.updateLanguage(this, language)
    }
}
