package uz.mobiler.gita.presenter.util

import android.Manifest
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.util.Log
import androidx.annotation.RequiresPermission
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class NetworkMonitor @Inject constructor(@ApplicationContext val context: Context) {
    
    private val connectivityManager = 
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    
    private var callback: NetworkConnectionCallback? = null
    private var isMonitoring = false
    
    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            callback?.onNetworkAvailable()
        }
        
        override fun onLosing(network: Network, maxMsToLive: Int) {
            callback?.onNetworkLosing()
        }
        
        override fun onLost(network: Network) {
            callback?.onNetworkLost()
        }
        
        override fun onUnavailable() {
            callback?.onNetworkUnavailable()
        }
    }

    //Monitoringni boshlash
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    fun startMonitoring(callback: NetworkConnectionCallback) {
        if (isMonitoring) return
        
        this.callback = callback
        
        val request = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .build()
        
        connectivityManager.registerNetworkCallback(request, networkCallback)
        isMonitoring = true
        
        // Hozirgi holatni tekshirish
        if (isConnected()) {
            callback.onNetworkAvailable()
        } else {
            callback.onNetworkUnavailable()
        }
    }

    // Monitoringni to'xtatish
    fun stopMonitoring() {
        if (!isMonitoring) return

        try {
            connectivityManager.unregisterNetworkCallback(networkCallback)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        isMonitoring = false
        callback = null
    }

    //Internet borligini tekshirish (bir martalik)
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    fun isConnected(): Boolean {
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
               capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
    }

    //WiFi ga ulanganligini tekshirish
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    fun isWifiConnected(): Boolean {
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        
        return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
    }

    // Mobil internetga ulanganligini tekshirish
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    fun isCellularConnected(): Boolean {
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        
        return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
    }

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    fun checkConnection(): Boolean {
        when {
            this.isWifiConnected() -> Log.d("TTT", "WiFi orqali ulangan")
            this.isCellularConnected() -> Log.d("TTT", "Mobil internet orqali ulangan")
            else -> Log.d("TTT", "Ulanish yo'q")
        }

        if (this.isConnected()) {
            Log.d("TTT", "Internet bor")
            return true
        } else {
            Log.d("TTT", "Internet yo'q")
            return false
        }
    }
}