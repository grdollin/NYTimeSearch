package mbds.ht.nytimesearch.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2/14/2018.
 */

public class Article {
    private String webUrl;

    public String getWebUrl() {
        return webUrl;
    }

    public String getHeadline() {
        return headline;
    }

    public String getThumbNail() {
        return thumbNail;
    }

    private String headline;
    private String thumbNail;
    public Article(JSONObject jsonObject){
        try{this.webUrl=jsonObject.getString("web_url");
        this.headline=jsonObject.getJSONObject("headline").getString("main");
            JSONArray multimedia = jsonObject.getJSONArray("multimedia");
            if (multimedia.length()>0) {
                 JSONObject multimediaJson = multimedia.getJSONObject(0);
                this.thumbNail = "https://www.nytimes.com/"+multimediaJson.getString("url");
            }
            else{
                this.thumbNail ="";
            }
        }
        catch (JSONException e)
        {e.printStackTrace();}
    }
    public static ArrayList<Article> fromJasonArray(JSONArray array) {
        ArrayList<Article> arrayArticle = new ArrayList<>();
        try {
            for (int i = 0; i < array.length(); i++) {
                arrayArticle.add(new Article(array.getJSONObject(i)));
            }
            Log.d("mes","mess");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayArticle;
    }
}
