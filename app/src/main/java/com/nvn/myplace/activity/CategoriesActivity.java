package com.nvn.myplace.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.nvn.myplace.ActivityUtils;
import com.nvn.myplace.R;
import com.nvn.myplace.data.PlaceRepo;
import com.nvn.myplace.data.model.Category;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoriesActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.txtCategoriesAct_Restaurent)
    TextView txtRestaurent;
    @BindView(R.id.txtCategoriesAct_Cinema)
    TextView txtCinema;
    @BindView(R.id.txtCategoriesAct_Fashion)
    TextView txtFashion;
    @BindView(R.id.txtCategoriesAct_ATM)
    TextView txtATM;

    private PlaceRepo placeRepo;
    private List<Category> categories = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        ButterKnife.bind(this);
        init();
    }

    private void init() {
        placeRepo = PlaceRepo.getInstance(this);
        categories = placeRepo.getCategories();
        setCategories();

        findViewById(R.id.layoutCategoriesAct_Restaurent).setOnClickListener(this);
        findViewById(R.id.layoutCategoriesAct_Cinema).setOnClickListener(this);
        findViewById(R.id.layoutCategoriesAct_Fashion).setOnClickListener(this);
        findViewById(R.id.layoutCategoriesAct_ATM).setOnClickListener(this);

        Log.d("test", String.valueOf(categories.size()));
    }

    private void setCategories(){
        txtRestaurent.setText(categories.get(0).getCategoryName());
        txtCinema.setText(categories.get(1).getCategoryName());
        txtFashion.setText(categories.get(2).getCategoryName());
        txtATM.setText(categories.get(3).getCategoryName());

    }

    private void startPlacesAct(String categoryID){
        Intent placesActIntent = new Intent(this, PlacesActivity.class);
        placesActIntent.putExtra(ActivityUtils.CATEGORY_KEY_PUT_EXTRA, categoryID);
        startActivity(placesActIntent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.layoutCategoriesAct_Restaurent:
                String categoryIDRestaurent = categories.get(0).getCategoryId();
                startPlacesAct(categoryIDRestaurent);
                break;
            case R.id.layoutCategoriesAct_Cinema:
                String categoryIDCinema = categories.get(1).getCategoryId();
                startPlacesAct(categoryIDCinema);
                break;
            case R.id.layoutCategoriesAct_Fashion:
                String categoryIDFashion = categories.get(2).getCategoryId();
                startPlacesAct(categoryIDFashion);
                break;
            case R.id.layoutCategoriesAct_ATM:
                String categoryIDATM = categories.get(3).getCategoryId();
                startPlacesAct(categoryIDATM);
                break;
            default:
                break;
        }
    }
}
