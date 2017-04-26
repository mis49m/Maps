package tr.com.mis49m.maps;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.Hashtable;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Hashtable<String, StadiumInfo> markers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        markers=new Hashtable<String, StadiumInfo>();
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

        //-- Set camera position
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(41.085298, 29.046704))
                .zoom(12)
                .bearing(0)
                .tilt(0)
                .build();
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        //mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

        // Add a marker in Bogazici University and move the camera
        LatLng boun = new LatLng(41.085298, 29.046704);
        mMap.addMarker(new MarkerOptions()
                .position(boun)
                .title("Boğaziçi Üniversitesi 2")
                .snippet("Rumeli Hisarı Mh. 34470 İstanbul")
                .draggable(true)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));

        //-- set onclick listener on marker
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Toast.makeText(MapsActivity.this, marker.getTitle(), Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        //-- set drag drop listener on marker
        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {

            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                Toast.makeText(MapsActivity.this, marker.getPosition().toString(), Toast.LENGTH_SHORT).show();
            }
        });


        //-- Add TT Arena Marker
        Marker markerTTArena = mMap.addMarker(new MarkerOptions().position(new LatLng(41.103425, 28.991200)));
        markers.put(markerTTArena.getId(), new StadiumInfo(R.drawable.gs, "Türk Telekom Arena", "Seyrantepe"));
        //-- Add Vodafone Arena Marker
        Marker markerVodafone = mMap.addMarker(new MarkerOptions().position(new LatLng(41.039085, 28.993848)));
        markers.put(markerVodafone.getId(), new StadiumInfo(R.drawable.bjk, "İnönü Stadyumu", "Vişnezade Mh., Beşiktaş"));
        //-- Add Saracoglu Marker
        Marker markerSaracoglu = mMap.addMarker(new MarkerOptions().position(new LatLng(40.987480, 29.035684)));
        markers.put(markerSaracoglu.getId(), new StadiumInfo(R.drawable.fb, "Şükrü Saraçoğlu Stadyumu", "Zühtüpşa Mh, Kadıköy"));
        //-- Add Custom info window
        InfoWindow infoWindow = new InfoWindow();
        mMap.setInfoWindowAdapter(infoWindow);
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                // StadiumInfo stadiumInfo = markers.get(marker.getId());
                Toast.makeText(MapsActivity.this, marker.getPosition().toString(),Toast.LENGTH_SHORT).show();
            }
        });


        //-- add shapes on map
        addPolyline();
        addPolygon();
        addCircle();

    }

    //-- draw line on map
    private void addPolyline(){
        PolylineOptions options = new PolylineOptions();
        options.add(new LatLng(41.0852, 29.0468))
                .add(new LatLng(41.0848, 29.0407))
                .add(new LatLng(41.0775, 29.0287))
                .add(new LatLng(41.0773, 29.0226))
                .add(new LatLng(41.0742, 29.0152));

        Polyline polyline = mMap.addPolyline(options);
        polyline.setColor(Color.BLUE);
    }

    //-- draw polygon on map
    private void addPolygon(){
        PolygonOptions options = new PolygonOptions();
        options.add(new LatLng(41.0897, 29.0499))
                .add(new LatLng(41.0868, 29.0498))
                .add(new LatLng(41.0852, 29.0468))
                .add(new LatLng(41.0822, 29.0511))
                .add(new LatLng(41.0843, 29.0553))
                .add(new LatLng(41.0866, 29.0524))
                .add(new LatLng(41.0881, 29.0530));

        Polygon polygon = mMap.addPolygon(options);
        polygon.setFillColor(Color.YELLOW);
    }

    //-- draw circle on map
    private void addCircle(){
        CircleOptions circleOptions = new CircleOptions();
        circleOptions.center(new LatLng(41.0852,29.0468));
        circleOptions.radius(200);

        Circle circle = mMap.addCircle(circleOptions);
        circle.setFillColor(Color.YELLOW);
        circle.setStrokeColor(Color.RED);
        circle.setStrokeWidth(8);
    }


    //-- Custom info window adapter
    public class InfoWindow implements GoogleMap.InfoWindowAdapter {

        @Override
        public View getInfoWindow(Marker marker) {
            return null;
        }

        @Override
        public View getInfoContents(Marker marker) {
            StadiumInfo stadiumInfo = markers.get(marker.getId());
            if(stadiumInfo==null)
                return null;

            // Getting view from the layout file info_window_layout
            View v = getLayoutInflater().inflate(R.layout.info_window, null);

            // Getting references
            ImageView imgLogo = (ImageView) v.findViewById(R.id.img_logo);
            TextView tvName = (TextView) v.findViewById(R.id.tv_name);
            TextView tvAddress = (TextView) v.findViewById(R.id.tv_address);

            // Setting the values
            imgLogo.setImageResource(stadiumInfo.getImage());
            tvName.setText(stadiumInfo.getName());
            tvAddress.setText(stadiumInfo.getAddress());

            // Returning the view containing InfoWindow contents
            return v;
        }

    }

}
