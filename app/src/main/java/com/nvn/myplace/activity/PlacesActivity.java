package com.nvn.myplace.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nvn.myplace.ActivityUtils;
import com.nvn.myplace.R;
import com.nvn.myplace.adapter.PlacesAdapter;
import com.nvn.myplace.data.PlaceRepo;
import com.nvn.myplace.data.model.DBUtils;
import com.nvn.myplace.data.model.Place;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PlacesActivity extends AppCompatActivity {

    @BindView(R.id.lvPlacesAct)
    ListView lvPlaces;
    @BindView(R.id.txtPlaceAct_NoData)
    TextView txtNoData;


    private String categoryID;
    private PlacesAdapter placesAdapter;
    private PlaceRepo placeRepo;
    private List<Place> places = new ArrayList<>();

    private ProgressDialog progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        categoryID = getIntent().getStringExtra(ActivityUtils.CATEGORY_KEY_PUT_EXTRA);
        placeRepo = PlaceRepo.getInstance(this);
        placesAdapter = new PlacesAdapter(this, places);
        lvPlaces.setAdapter(placesAdapter);
        initProgressDialog();

        getPlaces();
        onPlaceClick();
    }

    private void initProgressDialog() {
        progress = new ProgressDialog(this);
        progress.setIndeterminate(true);
        progress.setMessage(getResources().getString(R.string.text_retrieving_data));
        progress.setCanceledOnTouchOutside(false);
        progress.show();
    }

    private void getPlaces() {
        places = placeRepo.getPlaces(categoryID);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progress.dismiss();
                if (!places.isEmpty()) {
                    txtNoData.setVisibility(View.GONE);
                }

                placesAdapter.updatePlaces(places);

            }
        }, 3000);

    }


    private void onPlaceClick() {

        lvPlaces.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Place place = places.get(i);
                Intent detailIntent = new Intent(PlacesActivity.this, DetailActivity.class);
                detailIntent.putExtra(ActivityUtils.PLACE_KEY_PUT_EXTRA, place.getPlaceID());
                detailIntent.putExtra(ActivityUtils.CATEGORY_KEY_PUT_EXTRA, place.getCategoryID());
                startActivity(detailIntent);
                finish();
            }
        });

    }

    @OnClick(R.id.fabPlacesAct_AddNewPlace)
    public void addNewPlace(View view) {
        Intent intent = new Intent(PlacesActivity.this, AddEditActivity.class);
        intent.putExtra(ActivityUtils.CATEGORY_KEY_PUT_EXTRA, categoryID);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.btnPlaceAct_ShowAllOnMap)
    public void showAllOnMap(View view) {
        Intent intentMap = new Intent(PlacesActivity.this, MapActivity.class);
        intentMap.putExtra(ActivityUtils.CATEGORY_KEY_PUT_EXTRA, categoryID);
        startActivity(intentMap);
    }
}
