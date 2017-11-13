package com.nvn.myplace.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nvn.myplace.ActivityUtils;
import com.nvn.myplace.R;
import com.nvn.myplace.data.PlaceRepo;
import com.nvn.myplace.data.model.Place;

import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.imgDetails)
    ImageView imagePlacePicture;
    @BindView(R.id.edtDetails_placeName)
    EditText edtPlaceName;
    @BindView(R.id.edtDetails_placeAddress)
    EditText edtPlaceAddress;
    @BindView(R.id.edtDetails_placeDescription)
    EditText edtPlaceDescription;

    private String placeID;
    private String cateID;
    private PlaceRepo placeRepo;
    private Place place;

    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        placeID = getIntent().getStringExtra(ActivityUtils.PLACE_KEY_PUT_EXTRA);
        cateID = getIntent().getStringExtra(ActivityUtils.CATEGORY_KEY_PUT_EXTRA);
        placeRepo = PlaceRepo.getInstance(this);
        initProgressDialog();
        setPlace();

        findViewById(R.id.ibDetails_delete).setOnClickListener(this);
        findViewById(R.id.ibDetails_direction).setOnClickListener(this);
        findViewById(R.id.ibDetails_edit).setOnClickListener(this);
    }

    private void setPlace() {
        place = placeRepo.getPlace(cateID, placeID);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
                if (place.getPlaceImage() != null) {
                    Bitmap placeBitmap = BitmapFactory.decodeByteArray(place.getPlaceImage(), 0, place.getPlaceImage().length);
                    imagePlacePicture.setImageBitmap(placeBitmap);
                }

                edtPlaceName.setText(place.getPlaceName());
                edtPlaceAddress.setText(place.getPlaceAddress());
                edtPlaceDescription.setText(place.getPlaceDescription());
            }
        }, 3000);

    }



    private void initProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(getResources().getString(R.string.text_retrieving_data));
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.ibDetails_delete:
                btnDelete();
                break;
            case R.id.ibDetails_edit:
                btnEdit();
                break;
            case R.id.ibDetails_direction:
                break;
            default:
                break;
        }
    }

    private void btnDelete() {
        AlertDialog.Builder alertDialog =  new AlertDialog.Builder(DetailActivity.this);
        alertDialog.setTitle(getResources().getString(R.string.text_warning));
        alertDialog.setIcon(R.drawable.ic_warning_red_600_24dp);
        alertDialog.setMessage(
                getResources().getString(R.string.warning_do_you_want_to_delete)
                + " '"
                + place.getPlaceName()
                + "' ?"
        );
         alertDialog.setPositiveButton(getResources().getString(R.string.text_positive),
                 new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialogInterface, int i) {
                         Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_SHORT).show();
                         placeRepo.delete(placeID);
                         Intent placesActIntent = new Intent(DetailActivity.this, PlacesActivity.class);
                         placesActIntent.putExtra(ActivityUtils.CATEGORY_KEY_PUT_EXTRA, cateID);
                         startActivity(placesActIntent);
                     }
                 }
         );
        alertDialog.setNegativeButton(getResources().getString(R.string.text_negative),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getApplicationContext(), "NO", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        alertDialog.show();
    }

    private void btnEdit() {
        Intent addEditIntent = new Intent(DetailActivity.this, AddEditActivity.class);
        addEditIntent.putExtra(ActivityUtils.PLACE_KEY_PUT_EXTRA, place.getPlaceID());
        addEditIntent.putExtra(ActivityUtils.CATEGORY_KEY_PUT_EXTRA, place.getCategoryID());
        startActivity(addEditIntent);
        finish();
    }
}
