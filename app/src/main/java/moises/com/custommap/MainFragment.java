package moises.com.custommap;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.model.LatLng;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import moises.com.custommap.map.CustomMap;
import moises.com.custommap.map.OnCustomMapListener;


public class MainFragment extends Fragment implements OnCustomMapListener{

    private static final LatLng buenosAires = new LatLng(-34.6037389, -58.3837591);
    public MainFragment() {
    }

    private CustomMap customMap;
    private Unbinder unbinder;

    public static MainFragment newInstance(){
        return new MainFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        unbinder = ButterKnife.bind(this, view);
        setUp();
        return view;
    }

    private void setUp() {
        customMap = (CustomMap)getChildFragmentManager().findFragmentById(R.id.map);
        customMap.setListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /** IMPLEMENTATION CUSTOM MAP LISTENER **/
    @Override
    public void onMapReady() {
        showBuenosAires();
    }

    @Override
    public void onMarkerClick(Object tag) {
    }

    private void showBuenosAires(){
        customMap.addMarker(buenosAires, "Obelisco");
        customMap.animateCamera(buenosAires);
    }
}
