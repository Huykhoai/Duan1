package com.example.shopdientu.activity;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.shopdientu.R;
import com.example.shopdientu.adapter.AdapterTainghe;
import com.example.shopdientu.modul.Sanpham;
import com.example.shopdientu.ultil.CheckConnection;
import com.example.shopdientu.ultil.sever;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TaingheActivity extends AppCompatActivity {
        Toolbar toolbar;
        ListView listView;
        ArrayList<Sanpham> mangtainghe;
        AdapterTainghe adapterTainghe;
       int idTainghe = 2;
       int page = 1;
       SearchView searchView;
       View footer;
       boolean isLoadMore = false;
      boolean limitData = false;
       Mhander mhander;
       public class Mhander extends Handler{
           @Override
           public void handleMessage(@NonNull Message msg) {
               switch (msg.what){
                   case 0:
                       listView.addFooterView(footer);
                       break;
                   case 1:
                       GetData(++page);
                       isLoadMore = false;
                       break;
               }
               super.handleMessage(msg);
           }
       }
       public class ThreadData extends Thread{
           @Override
           public void run() {
               mhander.sendEmptyMessage(0);
               try {
                   Thread.sleep(3000);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
             Message message = mhander.obtainMessage(1);
               mhander.sendMessage(message);
               super.run();
           }
       }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tainghe);
        if (CheckConnection.HaveNetworkConnection(getApplicationContext())){
               Anhxa();
               ActionBar();
               GetData(page);
               GetidTainghe();
               LoadMoreData();
        }else {
            CheckConnection.ShowToastLong(getApplicationContext(), "khong co mang");
        }
    }

    private void LoadMoreData() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), Chitietsanpham.class);
                intent.putExtra("thongtinsanpham", mangtainghe.get(position));
                startActivity(intent);
            }
        });
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount!=0 && isLoadMore ==false && limitData == false){
                    isLoadMore =true;
                    ThreadData threadData = new ThreadData();
                    threadData.start();
                }
            }
        });
    }

    private void GetidTainghe() {
        idTainghe = getIntent().getIntExtra("idloaidsanpham", -1);
    }

    private void GetData(int p) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String path = sever.duongdansanpham+ String.valueOf(p);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, path, new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                  int id = 0;
                  String  tensptainghe ="";
                  Integer giasptainghe = 0;
                  String hinhanhsptainghe = "";
                  String motasptainghe = "";
                  int idSanpham= 0;
                  if(response!= null & response.length()!=2){
                      listView.removeFooterView(footer);
                      try {
                          JSONArray jsonArray = new JSONArray(response);
                          for (int i = 0; i<jsonArray.length();i++){
                              JSONObject jsonObject = jsonArray.getJSONObject(i);
                              id = jsonObject.getInt("id");
                              tensptainghe = jsonObject.getString("tensp");
                              giasptainghe = jsonObject.getInt("giasp");
                              hinhanhsptainghe = jsonObject.getString("hinhanhsp");
                              motasptainghe = jsonObject.getString("motasp");
                              idSanpham = jsonObject.getInt("idsanpham");
                              mangtainghe.add(new Sanpham(id, tensptainghe, giasptainghe, hinhanhsptainghe, motasptainghe, idSanpham));
                              adapterTainghe.notifyDataSetChanged();
                          }
                      } catch (JSONException e) {
                          e.printStackTrace();
                      }
                  }else{
                      listView.removeFooterView(footer);
                      limitData = true;
                      CheckConnection.ShowToastLong(getApplicationContext(), "Khong co du lieu");
                  }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<>();
                param.put("idsanpham", String.valueOf(idTainghe));
                return param;
            }
        };
          requestQueue.add(stringRequest);
    }

    private void ActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void Anhxa() {
        toolbar = findViewById(R.id.toolbartainghe);
        listView = findViewById(R.id.listviewTaignhe);
        mangtainghe = new ArrayList<>();
        adapterTainghe = new AdapterTainghe(getApplicationContext(), mangtainghe);
        listView.setAdapter(adapterTainghe);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
           //     Intent intent  = new Intent(getApplicationContext(),ChiTietSanPham.class);
             //   intent.putExtra("thongtinsanpham",mangtainghe.get(i));
               // startActivity(intent);
            }
        });
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footer = inflater.inflate(R.layout.progressbar, null);
        mhander = new Mhander();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        SearchManager manager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(manager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapterTainghe.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapterTainghe.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}