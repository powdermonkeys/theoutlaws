package Model;

import com.parse.ParseFile;
import com.parse.ParseObject;

import org.parceler.Parcel;

@Parcel
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

    //getter method for the attraction information
    public ParseObject getAttraction()
    {
        return getParseObject("attraction");
    }

    //setter method for the image information
    public void setImage(ParseFile image)
    {
        put(IMAGE, image);
    }

    //setter method for the attraction information
    public void setAttraction(ParseObject attraction)
    {
        put(ATTRACTION, attraction);
    }
}
