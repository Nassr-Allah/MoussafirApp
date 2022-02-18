package com.ems.moussafirdima

import android.os.Bundle
import android.view.WindowManager
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
import androidx.compose.ui.tooling.preview.Preview
import com.ems.moussafirdima.ui.materials.CustomSnackBar
import com.ems.moussafirdima.ui.navigation.LoginNavigation
import com.ems.moussafirdima.ui.screens.login.ResetPasswordScreen
import com.ems.moussafirdima.ui.theme.MoussafirDimaTheme
import com.ems.moussafirdima.util.ConnectionLiveData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    lateinit var connectionLiveData: ConnectionLiveData

    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        connectionLiveData = ConnectionLiveData(this)
        setContent {
            MoussafirDimaTheme {
                val isNetworkAvailable = connectionLiveData.observeAsState(initial = false).value
                val scaffoldState = rememberScaffoldState()
                Surface(color = Color.White) {
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
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        scaffoldState = scaffoldState,
                        snackbarHost = {
                            CustomSnackBar(snackbarHostState = it) {

                            }
                        }
                    ) {
                        LoginNavigation(isNetworkAvailable)
                    }
                }
            }
        }
    }
}