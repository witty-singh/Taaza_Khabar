package com.example.taazakhabar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Adapter adapter;
    private List<Headline> headlines;

    private String URL="https://newsapi.org/v2/top-headlines?page=2&country=in&apiKey=25c68eb9f29a4071817c6ecbe19b95a6";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("Top Picks");
        headlines=new ArrayList<>();
        recyclerView=findViewById(R.id.recyclerView);
        getData();
        BuildRecyclerView();
        SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @RequiresApi(api = 31)
            @Override
            public void onRefresh() {
                String page1="https://newsapi.org/v2/top-headlines?page=1&country=in&apiKey=25c68eb9f29a4071817c6ecbe19b95a6";
                URL=page1;
                headlines.clear();
                getData();
                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }
    private void getData(){
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {
            try {
                JSONArray jsonArray = response.getJSONArray("articles");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject article = jsonArray.getJSONObject(i);
                    String title= article.getString("title");
                    String imageUrl=article.getString("urlToImage");
                    String description=article.getString("description");
                    String date=article.getString("publishedAt");
                    String url=article.getString("url");
                    headlines.add(new Headline(imageUrl,title,description,date,url));
                    BuildRecyclerView();


                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(MainActivity.this, "Failed to load data", Toast.LENGTH_SHORT).show();
        }
    }
    ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("User-Agent", "Mozilla/5.0");
                return params;

            }
        };

        requestQueue.add(jsonObjectRequest);
    }
    private void BuildRecyclerView(){
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        adapter=new Adapter(this,headlines);
        recyclerView.setAdapter(adapter);
    }
}