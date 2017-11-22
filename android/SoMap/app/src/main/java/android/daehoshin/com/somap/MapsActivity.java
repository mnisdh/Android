package android.daehoshin.com.somap;

import android.daehoshin.com.somap.domain.CarApi;
import android.daehoshin.com.somap.domain.Data;
import android.daehoshin.com.somap.domain.ZoneApi;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private CarsAdapter adapter;
    private RecyclerView rvCars;
    private ProgressBar progressCars;

    private GoogleMap mMap;
    private Map<String, Marker> markers = new HashMap<>();
//    private ClusterManager<MarkerItem> clusterManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        init();
    }

    private void init(){

        progressCars = findViewById(R.id.progressCars);
        adapter = new CarsAdapter();
        rvCars = findViewById(R.id.rvCars);

        rvCars.setAdapter(adapter);
        rvCars.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
//        clusterManager = new ClusterManager<MarkerItem>(this, mMap);
//        mMap.setOnCameraIdleListener(clusterManager);
//        mMap.setOnMarkerClickListener(clusterManager);

        mMap.setOnMarkerClickListener(marker -> {
            adapter.clearData();
            if(marker.getTag() != null && !"".equals(marker.getTag().toString())){
                String zone_id = marker.getTag().toString();

                CarApi.getCars(zone_id, data -> {
                    adapter.addData(data);
                });
            }

            return false;
        });

        mMap.setOnCameraChangeListener(cameraPosition -> {
            LatLngBounds curScreen = mMap.getProjection().getVisibleRegion().latLngBounds;

//            clusterManager.clearItems();
            ZoneApi.getZones(curScreen, data -> addMarker(data));
        });

        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(37.515787, 127.021316)));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(10));
    }

    private void addMarker(Data data){
        double lat = Double.parseDouble(data.getLat());
        double lng = Double.parseDouble(data.getLng());

//        clusterManager.addItem(new MarkerItem(lat, lng));

        if(!markers.containsKey(data.getZone_id())){
            LatLng latLng = new LatLng(lat, lng);
            Marker marker = mMap.addMarker(new MarkerOptions().position(latLng).title(data.getZone_name()));
            marker.setTag(data.getZone_id());
            markers.put(data.getZone_id(), marker);
        }
    }

    public void onMoveButton(View v){
        LatLng latLng = null;
        switch (v.getId()){
            case R.id.btnSeoul: latLng = new LatLng(37.552498, 126.986796); break;
            case R.id.btnInchoun: latLng = new LatLng(37.457171, 126.705934); break;
            case R.id.btnGwangjoo: latLng = new LatLng(35.155069, 126.842425); break;
            case R.id.btnBusan: latLng = new LatLng(35.160500, 129.048109); break;
            case R.id.btnJeju: latLng = new LatLng(33.497782, 126.534546); break;
        }

        if(latLng != null){
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.moveCamera(CameraUpdateFactory.zoomTo(10));
        }
    }


//    class MarkerItem implements ClusterItem {
//        private final LatLng position;
//
//        public MarkerItem(double lat, double lng) {
//            position = new LatLng(lat, lng);
//        }
//
//        @Override
//        public LatLng getPosition() {
//            return position;
//        }
//    }

}
