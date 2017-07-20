package moises.com.custommap.map;

import android.support.annotation.NonNull;

import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.Polygon;

import java.util.List;

public interface CustomMapManager {
    void setListener(OnCustomMapListener listener);

    boolean isMapReady();

    Marker addMarker(@NonNull LatLng latLng, @NonNull String title);

    Marker addMarker(@NonNull LatLng latLng, @NonNull String title,
                     @NonNull String snippet, int markerIcon);

    void animateCamera(@NonNull LatLng latLng);

    void animateCameraWithZoom(@NonNull LatLng latLng, int zoom);

    Polygon addPolygon(List<LatLng> latLngList, int color);

    Circle addCircle(LatLng latLng, double radius, int color);

    void removeAllMaker();
}