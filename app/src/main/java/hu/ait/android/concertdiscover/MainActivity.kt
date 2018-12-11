package hu.ait.android.concertdiscover

import android.graphics.Color
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.livinglifetechway.quickpermissions.annotations.WithPermissions
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener,
        MyLocationProvider.OnNewLocationAvailable {

    private lateinit var mMap: GoogleMap
    private lateinit var myLocationProvider: MyLocationProvider
    var lastRecordedLocation: Location? = null
//    private var adapter : MutableMap<Marker, ConcertEvent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        btnTest.setOnClickListener {
            Toast.makeText(this@MainActivity,
                    "Location: ${lastRecordedLocation?.latitude} ${lastRecordedLocation?.longitude}",
                    Toast.LENGTH_LONG).show()
        }
    }

    override fun onStart() {
        super.onStart()
        startLocation()
    }

    @WithPermissions(
            permissions = [android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION]
    )

    fun startLocation() {
        myLocationProvider = MyLocationProvider(
                this,
                this
        )
        myLocationProvider.startLocationMonitoring()
    }


    override fun onStop() {
        super.onStop()
        myLocationProvider.stopLocationMonitoring()
    }

    override fun onNewLocation(location: Location) {
        val cameraPosition = CameraPosition.Builder()
                .target(LatLng(location.latitude, location.longitude))
                .zoom(12f)
                .build()
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
        lastRecordedLocation = location

        //make api call here
        //do we need an marker adapter?
    }

    override fun onInfoWindowClick(p0: Marker?) {
        Toast.makeText(this@MainActivity, "Hello", Toast.LENGTH_LONG).show()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

//        mMap.isTrafficEnabled = true
        mMap.mapType = GoogleMap.
                MAP_TYPE_NORMAL

        mMap.uiSettings.isCompassEnabled = true
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.setOnInfoWindowClickListener(this)
    }
}