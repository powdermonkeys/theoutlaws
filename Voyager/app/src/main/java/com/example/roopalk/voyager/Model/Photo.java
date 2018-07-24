package com.example.roopalk.voyager.Model;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.io.File;

@ParseClassName("Photo")
public class Photo extends ParseObject
{
    //In the Parse dashboard, the image will be stored in column called image
    private static final String IMAGE = "image";

    //the attraction where this image was taken will be stored in a column called attraction
    private static final String ATTRACTION = "attraction";

    //getter method for the image information
    public ParseFile getImage()
    {
        return getParseFile(IMAGE);
    }

    public ParseObject getAttraction()
    {
        return getParseObject(ATTRACTION);
    }

    //setter method for the image information
    public void setImage(String imagePath)
    {
        File file = new File(imagePath);
        ParseFile image = new ParseFile(file);
        put(IMAGE, image);
    }

    public static class Query extends ParseQuery<Photo>
    {
        public Query()
        {
            super(Photo.class);
        }

        public Query withAttraction(String attractionId)
        {
            whereMatches("attraction", attractionId);
            return this;
        }

        public Query withCity(String cityId)
        {
            include(cityId);
            return this;
        }
    }
}