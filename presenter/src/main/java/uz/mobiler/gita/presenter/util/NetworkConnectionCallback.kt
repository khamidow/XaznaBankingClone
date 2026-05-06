package uz.mobiler.gita.presenter.util

interface NetworkConnectionCallback {
    fun onNetworkAvailable()
    fun onNetworkLost()
    fun onNetworkLosing()
    fun onNetworkUnavailable()
}