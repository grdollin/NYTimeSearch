package mbds.ht.nytimesearch.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import mbds.ht.nytimesearch.R;
import mbds.ht.nytimesearch.adapters.ArticleArrayAdapter;
import mbds.ht.nytimesearch.adapters.ItemClickListenerInterface;
import mbds.ht.nytimesearch.models.Article;

public class SearchActivity extends AppCompatActivity implements ItemClickListenerInterface,FilterDialogFragment.EditCustomDialogListener{
    EditText etSearch;
    Button btnSearch;
    RecyclerView rvResult;
    ArrayList<Article> arrayArticle;
    ArticleArrayAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupViews();

    }
    private void setupViews(){
        //etSearch = (EditText) findViewById(R.id.etQuery);
        //btnSearch=(Button) findViewById(R.id.btnSearch);
        rvResult=(RecyclerView) findViewById(R.id.gvResults);
        arrayArticle =new ArrayList<>();
        adapter = new ArticleArrayAdapter(this,arrayArticle);
        rvResult.setAdapter(adapter);
        adapter.setClickListener(this);
        rvResult.setLayoutManager(new LinearLayoutManager(this));
        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        rvResult.setLayoutManager(gridLayoutManager);

    }

    public void onArticleSearch(String query){
       // String query = etSearch.getText().toString();
        Toast.makeText(this,"Searching for "+ query, Toast.LENGTH_LONG).show();
        AsyncHttpClient client = new AsyncHttpClient();
        String url ="https://api.nytimes.com/svc/search/v2/articlesearch.json";
        RequestParams params = new RequestParams();
        params.put("api-key","945373ce2cf842b3a4433c1097bf4840");
        params.put("page",0);
        params.put("query",query);
        client.get(url,params,new JsonHttpResponseHandler(){
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {

                Log.d("erreurJson", "test");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {




                try {
                    JSONArray articleJSONArray;
                    if(response != null) {
                        // Get the docs json array
                        articleJSONArray =response.getJSONObject("response").getJSONArray("docs");
                        // Parse json array into array of model objects
                        final ArrayList<Article> articles = Article.fromJasonArray(articleJSONArray);

                        // Parse json array into array of model objects
                        // Remove all books from the adapter


                        // Remove all books from the adapter
                        arrayArticle.clear();
                        // Load model objects into the adapter
                        for (Article article : articles) {
                            arrayArticle.add(article); // add book through the adapter
                        }
                        adapter.notifyDataSetChanged();
                        Log.d("Debug","test");
                    }
                } catch (JSONException e) {
                    // Invalid JSON format, show appropriate error.t
                    e.printStackTrace();
                }



        }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu_search, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                onArticleSearch(query);
                // perform query here

                // workaround to avoid issues with some emulators and keyboard devices firing twice if a keyboard enter is used
                // see https://code.google.com/p/android/issues/detail?id=24599
                searchView.clearFocus();

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        Log.d("test", "onCreateOptionsMenu: stop ");
        return super.onCreateOptionsMenu(menu);

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            //Intent intent = new Intent(this, SettingActivity.class);
           // startActivity(intent);
            FragmentManager fm = getSupportFragmentManager();
            FilterDialogFragment dialogFragment = new FilterDialogFragment ();
            dialogFragment.show(fm, "Sample Fragment");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view, int position) {
        Intent intent = new Intent(getApplicationContext(),ArticleActivity.class);
        Article article = arrayArticle.get(position);
        intent.putExtra("url",article.getWebUrl());
        startActivity(intent);

    }

    @Override
    public void onFinishEditDialog(String inputText) {
Toast.makeText(this, "Hi, "+ inputText,Toast.LENGTH_SHORT).show();
    }
}
