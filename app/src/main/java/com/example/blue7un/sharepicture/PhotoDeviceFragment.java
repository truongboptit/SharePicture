package com.example.blue7un.sharepicture;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.blue7un.sharepicture.adapters.RecyclerViewDataAdapter;
import com.example.blue7un.sharepicture.models.SectionDataModel;
import com.example.blue7un.sharepicture.models.SingleItemModel;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

/**
 * Created by blue7un on 8/28/17.
 */

public class PhotoDeviceFragment extends Fragment {

    ArrayList<SectionDataModel> allSampleData;
    RecyclerView my_recycler_view;
    public static final String TAG = "AAA";
    ArrayList<SingleItemModel> list = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.galery, container, false);
        allSampleData = new ArrayList<SectionDataModel>();
        createDummyData2();


        my_recycler_view = (RecyclerView) rootView.findViewById(R.id.recyclerView);

        my_recycler_view.setHasFixedSize(true);

        RecyclerViewDataAdapter adapter = new RecyclerViewDataAdapter(getContext(), allSampleData);

        my_recycler_view.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        my_recycler_view.setAdapter(adapter);
        return rootView;
    }
    public void createDummyData() {
        list = getAllShownImagesPath(getActivity());
        for (int i = 1; i <= 5; i++) {

            SectionDataModel dm = new SectionDataModel();

            dm.setHeaderTitle("Section " + i);

            ArrayList<SingleItemModel> singleItem = new ArrayList<SingleItemModel>();
            for (int j = 0; j <= 5; j++) {
                singleItem.add(new SingleItemModel("Item " + j, "URL " + j));
            }

            dm.setAllItemsInSection(singleItem);

            allSampleData.add(dm);

        }
    }
    public void createDummyData2() {
        list = getAllShownImagesPath(getActivity());
//        Collections.sort(list, new Comparator<SingleItemModel>() {
//            @Override
//            public int compare(SingleItemModel o1, SingleItemModel o2) {
//                if (o1.getDate() == null || o2.getDate() == null)
//                    return 0;
//                return o1.getDate().compareTo(o2.getDate());
//            }
//        });
        int k=0;
        SectionDataModel dm = new SectionDataModel();
        ArrayList<SingleItemModel> singleItem = new ArrayList<SingleItemModel>();
        while(k!=(list.size()-1)) {

             if(k==0){

                 dm.setHeaderTitle(""+list.get(0).getDate());
                 singleItem.add(new SingleItemModel(list.get(0).getDate(),list.get(0).getImagePath()));
             }
             if(list.get(k).getDate().equals(list.get(k+1).getDate())){

                 singleItem.add(new SingleItemModel(list.get(k+1).getDate(),list.get(k+1).getImagePath()));

             }else{

                 dm.setAllItemsInSection(singleItem);

                 allSampleData.add(dm);
                 dm= new SectionDataModel();
                 singleItem = new ArrayList<SingleItemModel>();
                 dm.setHeaderTitle(""+list.get(k+1).getDate());
                 singleItem.add(new SingleItemModel(list.get(k+1).getDate(),list.get(k+1).getImagePath()));
                 if(k==(list.size()-2)){
                     dm.setAllItemsInSection(singleItem);allSampleData.add(dm);
                 }
             }
            k++;

        }
        Log.d(TAG, "createDummyData2: "+allSampleData.size());
    }
    private ArrayList<SingleItemModel> getAllShownImagesPath(Activity activity) {
        Uri uri;
        Cursor cursor;
        int column_index_data, column_index_folder_name;
        ArrayList<SingleItemModel> listOfAllImages = new ArrayList<>();
        String absolutePathOfImage = null;
        uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = { MediaStore.MediaColumns.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME };

        cursor = activity.getContentResolver().query(uri, projection, null,
                null, null);

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        column_index_folder_name = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(column_index_data);
//                listOfAllImages.add(absolutePathOfImage);
            File file = new File(absolutePathOfImage);
            Date lastModeDate = new Date(file.lastModified());
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String date = sdf.format(lastModeDate);
            SingleItemModel photo = new SingleItemModel(date,absolutePathOfImage);
            listOfAllImages.add(photo);
            Log.d(TAG, "getAllShownImagesPath: "+date);
        }
        return listOfAllImages;
    }
}
