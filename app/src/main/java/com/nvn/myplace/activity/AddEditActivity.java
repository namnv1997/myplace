package com.nvn.myplace.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nvn.myplace.ActivityUtils;
import com.nvn.myplace.R;
import com.nvn.myplace.data.PlaceRepo;
import com.nvn.myplace.data.model.Category;
import com.nvn.myplace.data.model.Place;
import com.nvn.myplace.map.ServiceAPI;
import com.nvn.myplace.map.geocoding.GeocodingRoot;
import com.nvn.myplace.map.geocoding.Location;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddEditActivity extends AppCompatActivity {

    @BindView(R.id.imgAddEditAct_Picture)
    ImageView imgPlacePicture;
    @BindView(R.id.edtAddEditAct_placeName)
    EditText edtPlaceName;
    @BindView(R.id.edtAddEditAct_placeAddress)
    EditText edtPlaceAddress;
    @BindView(R.id.edtAddEditAct_placeDescription)
    EditText edtPlaceDescription;

    private String placeID;
    private String categoryID;
    private PlaceRepo placeRepo;
    private boolean hasImage = false;
    private boolean allowSave = false;
    private Retrofit retrofit;
    private Location location;

    private ProgressDialog progressDialog;

    private static final int IMAGE_CAPTURE_REQUEST_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        ButterKnife.bind(this);
        init();
    }


    private void init() {
        placeID = getIntent().getStringExtra(ActivityUtils.PLACE_KEY_PUT_EXTRA);
        categoryID = getIntent().getStringExtra(ActivityUtils.CATEGORY_KEY_PUT_EXTRA);
        placeRepo = PlaceRepo.getInstance(this);
        initRetrofit();
        initProgressDialog();
        Log.d("idd", placeID + " -- " +categoryID);
//        setPlace(placeID, categoryID);




        if (placeID != null) {
            hasImage = true;
            setPlace(placeID, categoryID);
            Log.d("idd", placeID + " -- " +categoryID);
        }
    }

    private void initRetrofit(){

        retrofit = new Retrofit.Builder()
                .baseUrl(ActivityUtils.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private void initProgressDialog(){
        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(getResources().getString(R.string.text_saving));
        progressDialog.setCanceledOnTouchOutside(false);

    }

    private void setPlace(String plID, String cateID){
        Place place = placeRepo.getPlace(cateID, plID);
        Bitmap placeImg = BitmapFactory.decodeByteArray(place.getPlaceImage(), 0, place.getPlaceImage().length);
        imgPlacePicture.setImageBitmap(placeImg);
        edtPlaceName.setText(place.getPlaceName());
        edtPlaceAddress.setText(place.getPlaceAddress());
        edtPlaceDescription.setText(place.getPlaceDescription());

    }

    private void getLatLng(String address){
        ServiceAPI serviceAPI = retrofit.create(ServiceAPI.class);
        Call<GeocodingRoot> call = serviceAPI.getLocation(address+"");
        call.enqueue(new Callback<GeocodingRoot>() {
            @Override
            public void onResponse(Call<GeocodingRoot> call, Response<GeocodingRoot> response) {

                GeocodingRoot geocodingRoot = response.body();
                Double lat;
                Double lng;
                if (geocodingRoot.getStatus().equals("ZERO_RESULTS")){
                    lat = 0.0;
                    lng = 0.0;
                }else {

                    lat = geocodingRoot.getResults().get(0).getGeometry().getLocation().getLat();
                    lng = geocodingRoot.getResults().get(0).getGeometry().getLocation().getLng();
                }

                location = new Location(lat, lng);

            }

            @Override
            public void onFailure(Call<GeocodingRoot> call, Throwable t) {

            }
        });
    }

    private byte[] convertImageViewToByteArray(ImageView imageView){
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        return baos.toByteArray();
    }

    private void redirectPlacesAct(){
        Intent intent = new Intent(this, PlacesActivity.class);
        intent.putExtra(ActivityUtils.CATEGORY_KEY_PUT_EXTRA, categoryID);
        startActivity(intent);
        finish();
    }

    private void redirectDetailAct(){
        Intent detailIntent = new Intent(this, DetailActivity.class);
        detailIntent.putExtra(ActivityUtils.PLACE_KEY_PUT_EXTRA, placeID);
        detailIntent.putExtra(ActivityUtils.CATEGORY_KEY_PUT_EXTRA,categoryID);
        startActivity(detailIntent);
        finish();
    }

    @OnClick(R.id.btnAddEditAct_Save)
    public void savePlace(View v) {
        final String placeName = edtPlaceName.getText().toString();
        final String placeAddress = edtPlaceAddress.getText().toString();
        final String placeDescription = edtPlaceDescription.getText().toString();

        if (Place.validateInput(placeName, placeAddress, placeDescription, categoryID)) {
            allowSave = true;
            getLatLng(placeName + ", " + placeAddress);
        } else {
            Toast.makeText(getApplicationContext(), "Fill", Toast.LENGTH_SHORT).show();
        }

        if (allowSave) {
            //edit
            if (placeID != null){
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Place place = new Place.Builder()
                                .setPlaceID(placeID)
                                .setCategoryID(categoryID)
                                .setPlaceName(placeName)
                                .setPlaceAddress(placeAddress)
                                .setPlaceDescription(placeDescription)
                                .setPlaceImage(convertImageViewToByteArray(imgPlacePicture))
                                .setPlaceLat(location.getLat())
                                .setPlaceLng(location.getLng())
                                .build();
                        placeRepo.update(place);
                        progressDialog.dismiss();
                        redirectDetailAct();
                    }
                }, 3000);
            }
            // add new
            progressDialog.show();
            if (hasImage && placeID == null) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Place place = new Place.Builder()
                                .setPlaceID(UUID.randomUUID().toString())
                                .setCategoryID(categoryID)
                                .setPlaceName(placeName)
                                .setPlaceAddress(placeAddress)
                                .setPlaceDescription(placeDescription)
                                .setPlaceImage(convertImageViewToByteArray(imgPlacePicture))
                                .setPlaceLat(location.getLat())
                                .setPlaceLng(location.getLng())
                                .build();
                        placeRepo.insert(place);
                        progressDialog.dismiss();
                        redirectPlacesAct();
                    }
                }, 3000);
            }

        }
    }

    @OnClick(R.id.imgAddEditAct_Picture)
    public void openCamera(View v) {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_CAPTURE_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data == null) {
                // add new
                if (placeID == null) {
                    hasImage = false;
                    allowSave = false;
                } else {//update
                    hasImage = true;
                }
            } else {
                hasImage = true;
                allowSave = true;
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                imgPlacePicture.setImageBitmap(bitmap);
            }
        }
    }

}
