package com.example.bungae.ui.map

import android.location.LocationManager
import android.location.LocationProvider
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.bungae.R
import com.example.bungae.databinding.FragmentMapBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapFragment : Fragment(), OnMapReadyCallback  {

    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!

    private lateinit var mMap: GoogleMap

    private var mapFragment: SupportMapFragment? = null

    private val permissionList = arrayOf(
        android.Manifest.permission.ACCESS_COARSE_LOCATION,
        android.Manifest.permission.ACCESS_FINE_LOCATION,
    )

    private val requestMultiplePermission =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result ->
            result.forEach {
                if (!it.value) {
                    Toast.makeText(context, "${it.key}권한 허용 필요", Toast.LENGTH_SHORT).show()
                }
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMapBinding.inflate(inflater, container, false)
        val root: View = binding.root

        mapFragment = childFragmentManager.findFragmentById(binding.map.id) as SupportMapFragment?

        mapFragment?.getMapAsync(this)

        requestMultiplePermission.launch(permissionList)
        Log.e("zz", "zz")
        return root
    }

    override fun onStart() {
        super.onStart()
        mapFragment?.onStart()
    }
    override fun onStop() {
        super.onStop()
        mapFragment?.onStop()
    }
    override fun onResume() {
        super.onResume()
        mapFragment?.onResume()
    }
    override fun onPause() {
        super.onPause()
        mapFragment?.onPause()
    }
    override fun onLowMemory() {
        super.onLowMemory()
        mapFragment?.onLowMemory()
    }

    override fun onDestroyView() {
        mapFragment?.onDestroy()
        super.onDestroyView()
        _binding = null
    }

    override fun onMapReady(googleMap: GoogleMap) {
        Log.e("11", "11")
        mMap = googleMap

        binding.fabCurrentLocation.setOnClickListener {
            Log.e("22", "22")
            val locationProvider = LocationProvider(requireContext())
            // 위도와 경도 정보를 가져옵니다.
            val latitude = locationProvider.getLocationLatitude()
            val longitude = locationProvider.getLocationLongitude()
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(latitude, longitude), 16f))
            setMarker()
        }
    }

    private fun setMarker() {
        Log.e("33", "33")
        mMap.let {
            it.clear()
            val markerOptions = MarkerOptions()
            markerOptions.position(it.cameraPosition.target)
            markerOptions.title("마커 위치")
            val marker = it.addMarker(markerOptions)

            it.setOnCameraMoveListener {
                Log.e("44", "44")
                marker?.let { marker ->
                    marker.position = it.cameraPosition.target
                }
            }
        }
    }
}