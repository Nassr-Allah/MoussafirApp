package com.ems.moussafirdima.ui.materials

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.ems.moussafirdima.R
import com.google.android.libraries.maps.MapView

@Composable
fun rememberMapViewWithLifeCycle(): MapView {
    val context = LocalContext.current
    val mapView = remember {
        MapView(context).apply {
            id = R.id.map
        }
    }
    val lifecycleObserver = rememberMapLifeCycleObserver(mapView = mapView)
    val lifeCycle = LocalLifecycleOwner.current.lifecycle
    DisposableEffect(lifeCycle) {
        lifeCycle.addObserver(lifecycleObserver)
        onDispose {
            lifeCycle.removeObserver(lifecycleObserver)
        }
    }
    return mapView
}

@Composable
fun rememberMapLifeCycleObserver(mapView: MapView): LifecycleEventObserver =
    remember(mapView) {
        LifecycleEventObserver { source, event ->
            when(event) {
                Lifecycle.Event.ON_CREATE -> mapView.onCreate(Bundle())
                Lifecycle.Event.ON_START -> mapView.onStart()
                Lifecycle.Event.ON_RESUME -> mapView.onResume()
                Lifecycle.Event.ON_PAUSE -> mapView.onPause()
                Lifecycle.Event.ON_STOP -> mapView.onStop()
                Lifecycle.Event.ON_DESTROY -> mapView.onDestroy()
                else -> throw IllegalStateException()
            }
        }
    }