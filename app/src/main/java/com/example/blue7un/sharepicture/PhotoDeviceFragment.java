package com.example.blue7un.sharepicture;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * Created by blue7un on 8/28/17.
 */

public class PhotoDeviceFragment extends Fragment {
    private int count;
    private Bitmap[] thumbnails;
    private boolean[] thumbnailsselection;
    private String[] arrPath;
    private ImageAdapter imageAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.galery, container, false);

//        final String[] columns = { MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID };
//        final String orderBy = MediaStore.Images.Media._ID;
//        Cursor imagecursor = managedQuery(
//                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null,
//                null, orderBy);
//        int image_column_index = imagecursor.getColumnIndex(MediaStore.Images.Media._ID);
//        this.count = imagecursor.getCount();
//        this.thumbnails = new Bitmap[this.count];
//        this.arrPath = new String[this.count];
//        this.thumbnailsselection = new boolean[this.count];
//        for (int i = 0; i < this.count; i++) {
//            imagecursor.moveToPosition(i);
//            int id = imagecursor.getInt(image_column_index);
//            int dataColumnIndex = imagecursor.getColumnIndex(MediaStore.Images.Media.DATA);
//            thumbnails[i] = MediaStore.Images.Thumbnails.getThumbnail(
//                    getContext().getContentResolver(), id,
//                    MediaStore.Images.Thumbnails.MICRO_KIND, null);
//            arrPath[i]= imagecursor.getString(dataColumnIndex);
//        }
//        GridView imagegrid = (GridView) rootView.findViewById(R.id.PhoneImageGrid);
//        imageAdapter = new ImageAdapter();
//        imagegrid.setAdapter(imageAdapter);
//        imagecursor.close();

        return rootView;
    }
    public class ImageAdapter extends BaseAdapter {
        private LayoutInflater mInflater;

        public ImageAdapter() {
            mInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public int getCount() {
            return count;
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = mInflater.inflate(
                        R.layout.galery_item, null);
                holder.imageview = (ImageView) convertView.findViewById(R.id.thumbImage);

                convertView.setTag(holder);
            }
            else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.checkbox.setId(position);
            holder.imageview.setId(position);
            holder.imageview.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    int id = v.getId();
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.parse("file://" + arrPath[id]), "image/*");
                    startActivity(intent);
                }
            });
            holder.imageview.setImageBitmap(thumbnails[position]);
            holder.checkbox.setChecked(thumbnailsselection[position]);
            holder.id = position;
            return convertView;
        }
    }
    class ViewHolder {
        ImageView imageview;
        CheckBox checkbox;
        int id;
    }
}
