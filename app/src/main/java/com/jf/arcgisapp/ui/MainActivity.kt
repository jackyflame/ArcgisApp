package com.jf.arcgisapp.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.jf.arcgisapp.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //mapView.map = ArcGISMap(Basemap.Type.TOPOGRAPHIC, 34.056295, -117.195800, 16)
    }

    override fun onPause(){
        //mapView.pause();
        super.onPause();
    }

    override fun onResume(){
        super.onResume();
        //mapView.resume();
    }
}
