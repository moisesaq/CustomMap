package moises.com.custommap.map;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import moises.com.custommap.R;

public class CustomMap extends Fragment implements OnMapReadyCallback, CustomMapManager,
        GoogleMap.OnMarkerClickListener, GoogleMap.OnPolygonClickListener, GoogleMap.OnCircleClickListener{
    private static final String TAG = CustomMap.class.getSimpleName();
    private static final float DEFAULT_PADDING_PERCENTAGE = 0.10f;
    private static final int DEFAULT_ZOOM = 15;
    private static final int DEFAULT_MARKER = 0;
    private static final int DEFAULT_TILT = 45;
    private static final String NONE = "";
    public static final int COLOR_TRANSPARENT_ACCENT = 0x55E2147E;
    public static final int COLOR_TRANSPARENT_GRAY = 0x55757575;

    @BindView(R.id.map_view) MapView mapView;
    private GoogleMap googleMap;
    private OnCustomMapListener listener;
    private Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.map_custom, container, false);
        unbinder = ButterKnife.bind(this, view);
        setUpMap(savedInstanceState);
        return view;
    }

    private void setUpMap(Bundle savedInstanceState) {
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        this.googleMap.setOnMarkerClickListener(this);
        this.googleMap.setOnPolygonClickListener(this);
        this.googleMap.setOnCircleClickListener(this);
        if (listener != null) listener.onMapReady();
        if (locationPermissionsAreGranted())
            setMyLocation();
    }

    private boolean locationPermissionsAreGranted() {
        return isAccessFineLocationGranted() && isAccessCoarseLocationGranted();
    }

    private boolean isAccessFineLocationGranted() {
        return ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    private boolean isAccessCoarseLocationGranted() {
        return ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    private void setMyLocation() {
        try {
            googleMap.setMyLocationEnabled(true);
        } catch (SecurityException e) {
            onPermissionsNotGranted();
        }
    }

    private void onPermissionsNotGranted() {
        Snackbar.make(getActivity().findViewById(android.R.id.content), "It's required the permissions", Snackbar.LENGTH_LONG);
    }

    private int getWindowsWidthPixels() {
        return getResources().getDisplayMetrics().widthPixels;
    }

    private int getWindowsHeightPixels() {
        return getResources().getDisplayMetrics().heightPixels;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        marker.showInfoWindow();
        return true;
    }

    @Override
    public void onCircleClick(Circle circle) {
        circle.setFillColor(COLOR_TRANSPARENT_ACCENT);
    }

    @Override
    public void onPolygonClick(Polygon polygon) {
        polygon.setFillColor(COLOR_TRANSPARENT_ACCENT);
    }

    private void unselectCircles(){
    }
    /**
     * IMPLEMENTATION CUSTOM MAP MANAGER
     */
    @Override
    public Marker addMarker(@NonNull LatLng latLng, @NonNull String title) {
        return addMarker(latLng, title, "", 0);
    }

    @Override
    public Marker addMarker(@NonNull LatLng latLng, @NonNull String title, @NonNull String snippet, int markerIcon) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        if(!title.isEmpty()) markerOptions.title(title);
        if(!snippet.isEmpty()) markerOptions.snippet(snippet);
        if(markerIcon != DEFAULT_MARKER) markerOptions.icon(BitmapDescriptorFactory.fromResource(markerIcon));

        return googleMap.addMarker(markerOptions);
    }

    @Override
    public void animateCamera(@NonNull LatLng latLng) {
        animateCameraWithZoom(latLng, DEFAULT_ZOOM);
    }

    @Override
    public void animateCameraWithZoom(@NonNull LatLng latLng, int zoom) {
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLng)
                .zoom(zoom > 0 ? zoom : DEFAULT_ZOOM)
                .tilt(DEFAULT_TILT)
                .build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    @Override
    public Polygon addPolygon(List<LatLng> latLngList, int color) {
        //TODO Add custom marker like polygon title
        return googleMap.addPolygon(new PolygonOptions()
                .addAll(latLngList::iterator)
                .strokeColor(Color.TRANSPARENT)
                .fillColor(color)
                .clickable(true));
    }

    @Override
    public Circle addCircle(LatLng latLng, double radius, int color) {
        //TODO Add custom marker like circle title
        return googleMap.addCircle(new CircleOptions()
                .center(latLng)
                .radius(radius)
                .strokeColor(Color.TRANSPARENT)
                .fillColor(color)
                .clickable(true));
    }

    @Override
    public void removeAllMaker() {
        googleMap.clear();
    }

    @Override
    public void setListener(OnCustomMapListener listener) {
        this.listener = listener;
    }

    @Override
    public boolean isMapReady() {
        return googleMap != null;
    }
}