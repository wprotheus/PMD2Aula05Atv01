package com.wprotheus.pmd2a05atv01;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.CustomZoomButtonsController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.gestures.RotationGestureOverlay;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

public class MainActivity extends AppCompatActivity {
    private GeoPoint point = new GeoPoint(-10.250691, -48.325579);
    private Double latitude = null;
    private Double longitude = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MapView map = findViewById(R.id.mapview);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.getController().setZoom(15.2);

        MyLocationNewOverlay myLocationOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(this), map);
        myLocationOverlay.setDrawAccuracyEnabled(true);
        myLocationOverlay.enableMyLocation();

        map.getZoomController().setVisibility(CustomZoomButtonsController.Visibility.ALWAYS);
        map.setMultiTouchControls(true);

        RotationGestureOverlay mRotationGestureOverlay = new RotationGestureOverlay(map);
        mRotationGestureOverlay.setEnabled(true);
        map.setMultiTouchControls(true);
        map.getOverlays().add(mRotationGestureOverlay);

        Marker pointer = new Marker(map);
        Drawable icon = ResourcesCompat.getDrawable(getResources(), R.drawable.pointer_icon, null);
        pointer.setPosition(point);
        pointer.setIcon(icon);
        pointer.setDraggable(true);
        pointer.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER);
        map.getOverlays().add(pointer);
        map.getController().setCenter(point);

        pointer.setOnMarkerClickListener((marker, mapView) -> {
            GeoPoint markerPosition = marker.getPosition();
            latitude = markerPosition.getLatitude();
            longitude = markerPosition.getLongitude();
            point.setCoords(latitude, longitude);
            marker.setTitle("Pontos geográficos:");
            marker.setSnippet("Latitude: " + latitude + " Longitude: " + longitude);
            marker.showInfoWindow();
            return true;
        });
    }
}