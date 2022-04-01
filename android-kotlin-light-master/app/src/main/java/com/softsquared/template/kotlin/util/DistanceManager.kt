package com.softsquared.template.kotlin.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import java.lang.Math.toRadians
import java.util.*
import kotlin.math.pow
import kotlin.math.sqrt

object DistanceManager {
    private const val R = 6372.8 * 1000

    fun getDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Int {
        val dLat = toRadians(lat2 - lat1)
        val dLon = toRadians(lon2 - lon1)
        val a = kotlin.math.sin(dLat / 2).pow(2.0) + kotlin.math.sin(dLon / 2)
            .pow(2.0) * kotlin.math.cos(
            toRadians(lat1)
        ) * kotlin.math.cos(toRadians(lat2))
        val c = 2 * kotlin.math.asin(sqrt(a))
        return (R * c).toInt()
    }

    fun getMyLocation(context: Context): Location? {
        var check = false

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            check = true
        }

        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val providers = locationManager.getProviders(true)
        var bestLocation: Location? = null
        for (provider in providers) {
            if (check) {
                var location = locationManager.getLastKnownLocation(provider)
                if (location == null) {
                    continue
                }
                if (bestLocation == null || location.accuracy < bestLocation.accuracy) {
                    bestLocation = location
                }
            }
        }
        return bestLocation
    }
}