package com.ems.moussafirdima.util

import android.content.Context
import android.hardware.camera2.params.Capability
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.util.Log
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.HashSet

class ConnectionLiveData(context: Context) : LiveData<Boolean>() {

    private lateinit var networkCallback: ConnectivityManager.NetworkCallback
    private val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private val validNetworks: MutableSet<Network> = HashSet()
    private var netStrength = 0

    private fun checkValidNetworks() {
        postValue(validNetworks.size > 0)
    }

    override fun onActive() {
        networkCallback = createNetworkCallback()
        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()
        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
    }

    override fun onInactive() {
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }

    private fun createNetworkCallback() = object : ConnectivityManager.NetworkCallback() {

        override fun onAvailable(network: Network) {
            val netCapabilities = connectivityManager.getNetworkCapabilities(network)
            val hasInternetCapabilities = netCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            if (hasInternetCapabilities == true) {
                CoroutineScope(Dispatchers.IO).launch {
                    val hasInternet = DoesNetworkHaveInternet.execute(network.socketFactory)
                    if (hasInternet) {
                        withContext(Dispatchers.Main) {
                            validNetworks.add(network)
                            checkValidNetworks()
                        }
                    }
                }
            }
        }

        override fun onLost(network: Network) {
            validNetworks.remove(network)
            checkValidNetworks()
        }

        override fun onCapabilitiesChanged(
            network: Network,
            networkCapabilities: NetworkCapabilities
        ) {
            Log.d("NetworkCapabilities", networkCapabilities.linkDownstreamBandwidthKbps.toString())
            Log.d("NetworkCapabilities", networkCapabilities.signalStrength.toString())
        }
    }
}