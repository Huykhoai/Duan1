package com.example.assignment.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.assignment.Adapter.AdapterDongho;
import com.example.assignment.Modul.Sanpham;
import com.example.assignment.R;
import com.example.assignment.util.checkConnection;
import com.example.assignment.util.server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DonghoActivity extends AppCompatActivity {
      Toolbar toolbar;
      ListView listView;
      ArrayList<Sanpham> mangsanpham;
      AdapterDongho adapterSanpham;
      View footer;
      boolean isLoadMore= false;
      boolean limitData = false;
      boolean isEndata = false;
       mhander mhander;
    public int page = 1;
    public int idloaisanpham=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sanpham);
        Anhxa();
        ActionBar();
        GetData(page);
        getIdsanpham();

    }
    public class mhander extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
          switch (msg.what){
              case 0:
              listView.addFooterView(footer);
              break;
              case 1:
                  GetData(++page);
                  isLoadMore =false;
                  break;
          }
        }
    }
  public class ThreadData extends Thread{
      @Override
      public void run() {
          mhander.sendEmptyMessage(0);
          try {
              Thread.sleep(3000);
          } catch (InterruptedException e) {
              throw new RuntimeException(e);
          }
          runOnUiThread(new Runnable() {
              @Override
              public void run() {
                  Message message =mhander.obtainMessage(1);
                  mhander.handleMessage(message);
              }
          });

          super.run();

      }
  }
    private void getIdsanpham() {
        idloaisanpham = getIntent().getIntExtra("idloaisanpham", -1);
        Log.d("getIdsanpham: ",String.valueOf(idloaisanpham));
    }


    private void GetData(int p) {
        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String path = server.duongdansanpham + String.valueOf(p);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, path, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d( "jsonDongho: ", response.toString());
                int id=0;
                String tendongho = "";
                Integer giadongho = 0;
                String hinhanhdongho = "";
                String motadongho = "";
                int idSanpham = 0;
                if(response != null && response.length() !=2){
                    listView.removeFooterView(footer);

                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for(int i = 0; i<jsonArray.length();i++){

                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            tendongho = jsonObject.getString("tensanpham");
                            giadongho = jsonObject.getInt("giasanpham");
                            motadongho = jsonObject.getString("motasanpham");
                            hinhanhdongho = jsonObject.getString("hinhanhsanpham");
                            idSanpham = jsonObject.getInt("idsanpham");
                            mangsanpham.add(new Sanpham(id, tendongho, giadongho, motadongho,hinhanhdongho, idSanpham));
                            adapterSanpham.notifyDataSetChanged();
                        }
                    }catch (JSONException e) {
                        e.printStackTrace();
                    }

                }else{
                     limitData =true;
                     isEndata = false;
                     listView.removeFooterView(footer);
                    checkConnection.ShowToastLong(getApplicationContext(), "không có dữ liệu");
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
                param.put("idsanpham", String.valueOf(idloaisanpham));
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
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void Anhxa() {
        listView = findViewById(R.id.listviewSanpham);
        toolbar = findViewById(R.id.toolbarSanpham);
        mangsanpham = new ArrayList<>();
        adapterSanpham = new AdapterDongho(getApplicationContext(),mangsanpham);
        listView.setAdapter(adapterSanpham);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(),ChitietsanphamActivity.class);
                intent.putExtra("thongtinchitiet", mangsanpham.get(i));
                startActivity(intent);
            }
        });
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
                if(i+i1 == i2 && i2!=0 && isLoadMore ==false && limitData == false ){
                    isLoadMore = false;
                    if(isEndata){
                        Toast.makeText(DonghoActivity.this, "Hết dữ liệu", Toast.LENGTH_SHORT).show();
                    }
                    Thread thread = new ThreadData();
                    thread.start();
                }
            }
        });
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
         footer =layoutInflater.inflate(R.layout.progressbar, null);
         mhander = new mhander();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
       if(item.getItemId()==R.id.giohang){
           Intent intent = new Intent(getApplicationContext(),GiohangActivity.class);
           startActivity(intent);
       }
        return super.onOptionsItemSelected(item);
    }
}