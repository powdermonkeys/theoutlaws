package com.example.roopalk.voyager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.roopalk.voyager.Model.Photo;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.SaveCallback;

import java.io.File;

public class PhotoUpload extends AppCompatActivity {
    private final String TAG = "PhotoUploadActivity";
    private static final String imagePath = "/storage/emulated/0/Download/Seattle_Space_Needle.jpg";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_upload);

        File file = new File(imagePath);
        ParseFile parseFile = new ParseFile(file);

        final Photo newPhoto = new Photo();

        newPhoto.setImage(parseFile);

        newPhoto.saveInBackground(new SaveCallback()
        {
            @Override
            public void done(ParseException e)
            {
                if(e == null)
                {
                    Log.i(TAG, "works");
                }
                else
                {
                    Log.e(TAG, "doesn't work");
                }
            }
        });
    }
}
