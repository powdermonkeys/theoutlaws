package com.example.roopalk.voyager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;

import com.example.roopalk.voyager.Model.Photo;
import com.parse.FindCallback;
import com.parse.ParseException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TestActivity extends AppCompatActivity
{
    @BindView(R.id.getPhotosBtn) Button getPhotosBtn;
    @BindView(R.id.ivGottenPhoto) ImageView ivGottenPhoto;

    public ArrayList<Photo> photos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);

        final Photo.Query photoQuery = new Photo.Query();

        photoQuery.getAllImages();
        photoQuery.findInBackground(new FindCallback<Photo>()
        {
            @Override
            public void done(List<Photo> objects, ParseException e)
            {
                if(e == null)
                {
                    photos.add(objects.get(0));

                    Photo photo = photos.get(0);

                    String imagePath = photo.getImage().getUrl();

                    GlideApp.with(getBaseContext())
                            .load(imagePath)
                            .into(ivGottenPhoto);
                }
            }
        });
    }
}
