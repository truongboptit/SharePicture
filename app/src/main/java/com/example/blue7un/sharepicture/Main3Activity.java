package com.example.blue7un.sharepicture;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class Main3Activity extends Activity {

    // private Integer[] mImageIds = {R.drawable.splash, R.drawable.splash,
    // R.drawable.splash, R.drawable.splash, R.drawable.splash,
    // R.drawable.splash, R.drawable.splash};

    private static Uri[] mUrls = null;
    private static String[] strUrls = null;
    private String[] mNames = null;
    private GridView gridview = null;
    private Cursor cc = null;
    private Button btnMoreInfo = null;
    ProgressDialog myProgressDialog ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);


        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main3);
        // It have to be matched with the directory in SDCard
        cc = this.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null,
                null);

        // File[] files=f.listFiles();
        if (cc != null) {

            myProgressDialog = new ProgressDialog(Main3Activity.this);
            myProgressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            //myProgressDialog.setIcon(R.drawable.blind);
            myProgressDialog.show();

            new Thread() {
                public void run() {
                    try {
                        cc.moveToFirst();
                        mUrls = new Uri[cc.getCount()];
                        strUrls = new String[cc.getCount()];
                        mNames = new String[cc.getCount()];
                        for (int i = 0; i < cc.getCount(); i++) {
                            cc.moveToPosition(i);
                            mUrls[i] = Uri.parse(cc.getString(1));
                            strUrls[i] = cc.getString(1);
                            mNames[i] = cc.getString(3);
                            //Log.e("mNames[i]",mNames[i]+":"+cc.getColumnCount()+ " : " +cc.getString(3));
                        }

                    } catch (Exception e) {
                    }
                    myProgressDialog.dismiss();
                }
            }.start();
            gridview = (GridView) findViewById(R.id.gridview);
            gridview.setAdapter(new ImageAdapter(this));

            gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v,
                                        int position, long id) {
                    Intent i = new Intent(Main3Activity.this, Main2Activity.class);
                    Log.e("intent : ", ""+position);
                    i.putExtra("position", position);
                    i.putExtra("imgUrls", strUrls);
                    i.putExtra("abc","123");
                    Log.d("AAA", "onItemClick: "+strUrls[position]);
                    startActivity(i);
                }
            });

        }

    }

    /**
     * This class loads the image gallery in grid view.
     *
     */
    public class ImageAdapter extends BaseAdapter {
        private Context mContext;

        public ImageAdapter(Context c) {
            mContext = c;
        }

        public int getCount() {
            return cc.getCount();
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        // create a new ImageView for each item referenced by the Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.galery_item, null);

            try {

                ImageView imageView = (ImageView) v.findViewById(R.id.thumbImage);
                //imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                // imageView.setPadding(8, 8, 8, 8);
                Bitmap bmp = decodeURI(mUrls[position].getPath());Log.d("AAA", "onCreate: "+bmp);
                //BitmapFactory.decodeFile(mUrls[position].getPath());
                imageView.setImageBitmap(bmp);
                //bmp.
//                TextView txtName = (TextView) v.findViewById(R.id.TextView01);
//                txtName.setText(mNames[position]);
            } catch (Exception e) {

            }
            return v;
        }
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
    }
    // @Override
    // protected void onStop() {
    // TODO Auto-generated method stub
    // super.onStop();
    // FlurryAgent.onEndSession(this);

    // }

    /**
     * This method is to scale down the image
     */
    public Bitmap decodeURI(String filePath){

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        // Only scale if we need to
        // (16384 buffer for img processing)
        Boolean scaleByHeight = Math.abs(options.outHeight - 100) >= Math.abs(options.outWidth - 100);
        if(options.outHeight * options.outWidth * 2 >= 16384){
            // Load, scaling to smallest power of 2 that'll get it <= desired dimensions
            double sampleSize = scaleByHeight
                    ? options.outHeight / 100
                    : options.outWidth / 100;
            options.inSampleSize =
                    (int)Math.pow(2d, Math.floor(
                            Math.log(sampleSize)/Math.log(2d)));
        }

        // Do the actual decoding
        options.inJustDecodeBounds = false;
        options.inTempStorage = new byte[512];
        Bitmap output = BitmapFactory.decodeFile(filePath, options);

        return output;
    }
}
