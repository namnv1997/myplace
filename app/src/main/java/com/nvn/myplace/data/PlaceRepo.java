package com.nvn.myplace.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nvn.myplace.data.PlaceSqliteHelper;
import com.nvn.myplace.data.model.Category;
import com.nvn.myplace.data.model.DBUtils;
import com.nvn.myplace.data.model.Place;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by n on 05/11/2017.
 */

public class PlaceRepo {

    private static PlaceRepo instance;
    private static PlaceSqliteHelper placeSqliteHelper;

    private PlaceRepo(Context context) {
        placeSqliteHelper = new PlaceSqliteHelper(context);
    }

    public static PlaceRepo getInstance(Context context) {
        return (instance == null) ? new PlaceRepo(context) : instance;
    }

    public List<Category> getCategories() {
        List<Category> categories = new ArrayList<>();

        SQLiteDatabase database = placeSqliteHelper.getReadableDatabase();

        String[] columns = {
                DBUtils.COLUMN_CATEGORY_ID,
                DBUtils.COLUMN_CATEGORY_NAME
        };

        Cursor cursor = database.query(DBUtils.CATEGORY_TBL_NAME, columns, null, null, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String categoryID = cursor.getString(cursor.getColumnIndexOrThrow(DBUtils.COLUMN_CATEGORY_ID));
                String categoryName = cursor.getString(cursor.getColumnIndexOrThrow(DBUtils.COLUMN_CATEGORY_NAME));

                categories.add(new Category(categoryID, categoryName));

            }

        }

        if (cursor != null) {
            cursor.close();
        }

        database.close();

        return categories;
    }

    public List<Place> getPlaces(String cateID) {
        List<Place> places = new ArrayList<>();

        SQLiteDatabase database = placeSqliteHelper.getReadableDatabase();

        String[] columns = {
                DBUtils.COLUMN_PLACE_ID,
                DBUtils.COLUMN_PLACE_CATEGORY_ID,
                DBUtils.COLUMN_PLACE_NAME,
                DBUtils.COLUMN_PLACE_ADDRESS,
                DBUtils.COLUMN_PLACE_DESCRIPTION,
                DBUtils.COLUMN_PLACE_IMAGE,
                DBUtils.COLUMN_PLACE_LAT,
                DBUtils.COLUMN_PLACE_LNG,
        };

        String selection = DBUtils.COLUMN_PLACE_CATEGORY_ID + " = ?";
        String[] selectionArgs = {cateID};

        Cursor cursor = database.query(DBUtils.PLACE_TBL_NAME, columns, selection, selectionArgs, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {

                String placeID = cursor.getString(cursor.getColumnIndexOrThrow(DBUtils.COLUMN_PLACE_ID));
                String categoryID = cursor.getString(cursor.getColumnIndexOrThrow(DBUtils.COLUMN_PLACE_CATEGORY_ID));
                String placeName = cursor.getString(cursor.getColumnIndexOrThrow(DBUtils.COLUMN_PLACE_NAME));
                String placeAddress = cursor.getString(cursor.getColumnIndexOrThrow(DBUtils.COLUMN_PLACE_ADDRESS));
                String placeDescription = cursor.getString(cursor.getColumnIndexOrThrow(DBUtils.COLUMN_PLACE_DESCRIPTION));
                byte[] placeImage = cursor.getBlob(cursor.getColumnIndexOrThrow(DBUtils.COLUMN_PLACE_IMAGE));
                double placeLat = cursor.getDouble(cursor.getColumnIndexOrThrow(DBUtils.COLUMN_PLACE_LAT));
                double placeLng = cursor.getDouble(cursor.getColumnIndexOrThrow(DBUtils.COLUMN_PLACE_LNG));

                Place place = new Place.Builder()
                        .setPlaceID(placeID)
                        .setCategoryID(categoryID)
                        .setPlaceName(placeName)
                        .setPlaceAddress(placeAddress)
                        .setPlaceDescription(placeDescription)
                        .setPlaceImage(placeImage)
                        .setPlaceLat(placeLat)
                        .setPlaceLng(placeLng)
                        .build();

                places.add(place);

            }

        }

        if (cursor != null) {
            cursor.close();
        }

        database.close();

        return places;
    }

    public Place getPlace(String cateID, String plID) {
        Place place = null;

        SQLiteDatabase database = placeSqliteHelper.getReadableDatabase();

        String[] columns = {
                DBUtils.COLUMN_PLACE_ID,
                DBUtils.COLUMN_PLACE_CATEGORY_ID,
                DBUtils.COLUMN_PLACE_NAME,
                DBUtils.COLUMN_PLACE_ADDRESS,
                DBUtils.COLUMN_PLACE_DESCRIPTION,
                DBUtils.COLUMN_PLACE_IMAGE,
                DBUtils.COLUMN_PLACE_LAT,
                DBUtils.COLUMN_PLACE_LNG,
        };

        String selection = DBUtils.COLUMN_PLACE_ID + " = ?" + " AND " + DBUtils.COLUMN_PLACE_CATEGORY_ID + "= ?";

        String[] selectionArgs = {plID, cateID};

        Cursor cursor = database.query(DBUtils.PLACE_TBL_NAME, columns, selection, selectionArgs, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();

            String placeID = cursor.getString(cursor.getColumnIndexOrThrow(DBUtils.COLUMN_PLACE_ID));
            String categoryID = cursor.getString(cursor.getColumnIndexOrThrow(DBUtils.COLUMN_PLACE_CATEGORY_ID));
            String placeName = cursor.getString(cursor.getColumnIndexOrThrow(DBUtils.COLUMN_PLACE_NAME));
            String placeAddress = cursor.getString(cursor.getColumnIndexOrThrow(DBUtils.COLUMN_PLACE_ADDRESS));
            String placeDescription = cursor.getString(cursor.getColumnIndexOrThrow(DBUtils.COLUMN_PLACE_DESCRIPTION));
            byte[] placeImage = cursor.getBlob(cursor.getColumnIndexOrThrow(DBUtils.COLUMN_PLACE_IMAGE));
            double placeLat = cursor.getDouble(cursor.getColumnIndexOrThrow(DBUtils.COLUMN_PLACE_LAT));
            double placeLng = cursor.getDouble(cursor.getColumnIndexOrThrow(DBUtils.COLUMN_PLACE_LNG));

            place = new Place.Builder()
                    .setPlaceID(placeID)
                    .setCategoryID(categoryID)
                    .setPlaceName(placeName)
                    .setPlaceAddress(placeAddress)
                    .setPlaceDescription(placeDescription)
                    .setPlaceImage(placeImage)
                    .setPlaceLat(placeLat)
                    .setPlaceLng(placeLng)
                    .build();


        }

        if (cursor != null) {
            cursor.close();
        }

        database.close();
        return place;
    }

    public void insert(Place place){
        SQLiteDatabase database = placeSqliteHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DBUtils.COLUMN_PLACE_ID, place.getPlaceID());
        contentValues.put(DBUtils.COLUMN_PLACE_CATEGORY_ID, place.getCategoryID());
        contentValues.put(DBUtils.COLUMN_PLACE_NAME, place.getPlaceName());
        contentValues.put(DBUtils.COLUMN_PLACE_ADDRESS, place.getPlaceAddress());
        contentValues.put(DBUtils.COLUMN_PLACE_DESCRIPTION, place.getPlaceDescription());
        contentValues.put(DBUtils.COLUMN_PLACE_IMAGE, place.getPlaceImage());
        contentValues.put(DBUtils.COLUMN_PLACE_LAT, place.getPlaceLat());
        contentValues.put(DBUtils.COLUMN_PLACE_LNG, place.getPlaceLng());

        database.insert(DBUtils.PLACE_TBL_NAME, null, contentValues);
        database.close();

    }

    public void update(Place place){
        SQLiteDatabase database = placeSqliteHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DBUtils.COLUMN_PLACE_ID, place.getPlaceID());
        contentValues.put(DBUtils.COLUMN_PLACE_CATEGORY_ID, place.getCategoryID());
        contentValues.put(DBUtils.COLUMN_PLACE_NAME, place.getPlaceName());
        contentValues.put(DBUtils.COLUMN_PLACE_ADDRESS, place.getPlaceAddress());
        contentValues.put(DBUtils.COLUMN_PLACE_DESCRIPTION, place.getPlaceDescription());
        contentValues.put(DBUtils.COLUMN_PLACE_IMAGE, place.getPlaceImage());
        contentValues.put(DBUtils.COLUMN_PLACE_LAT, place.getPlaceLat());
        contentValues.put(DBUtils.COLUMN_PLACE_LNG, place.getPlaceLng());

        String selection = DBUtils.COLUMN_PLACE_ID + "= ?";
        String[] selectionArgs = {place.getPlaceID()};

        database.update(DBUtils.PLACE_TBL_NAME,contentValues, selection, selectionArgs);
        database.close();
    }

    public void delete(String plID){
        SQLiteDatabase database = placeSqliteHelper.getWritableDatabase();

        String selection = DBUtils.COLUMN_PLACE_ID + " = ?";
        String[] selectionArgs = {plID};

        database.delete(DBUtils.PLACE_TBL_NAME, selection, selectionArgs);
        database.close();
    }
}
