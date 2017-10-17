package android.daehoshin.com.bicycle;

import android.daehoshin.com.bicycle.domain.BaseBike;
import android.daehoshin.com.bicycle.domain.Row;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        // 맵이 사용할 준비가 되었는지를 비동기로 확인하는 작업
        mapFragment.getMapAsync(this);
        // 맵이 사용할 준비가 되었으면 -> OnMapReadyCallback.onMapReady를 호출
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        load();
    }

    private void load(){
        new AsyncTask<Void, Void, String>(){
            @Override
            protected String doInBackground(Void... params) {
                return Remote.getData("http://openapi.seoul.go.kr:8088/6f65416d616d6e6932346a4e516453/json/GeoInfoBikeConvenientFacilitiesWGS/1/50");
            }

            @Override
            protected void onPostExecute(String jsonString) {
                Gson gson = new Gson();
                BaseBike result = gson.fromJson(jsonString, BaseBike.class);

                Row[] rows = result.getGeoInfoBikeConvenientFacilitiesWGS().getRow();
                for (Row row : rows){
                    LatLng latLng = new LatLng(Double.parseDouble(row.getLAT()), Double.parseDouble(row.getLNG()));
                    mMap.addMarker(new MarkerOptions().position(latLng).title(row.getCLASS()));
                }

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.5612542,126.9914683), 10));
            }
        }.execute();
    }
}
