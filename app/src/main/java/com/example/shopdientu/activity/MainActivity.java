package com.example.shopdientu.activity;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.shopdientu.R;
import com.example.shopdientu.adapter.AdapterLoaisp;
import com.example.shopdientu.adapter.AdapterSanpham;
import com.example.shopdientu.adapter.Photoadapter;
import com.example.shopdientu.modul.Giohang;
import com.example.shopdientu.modul.Loaisp;
import com.example.shopdientu.modul.Photo;
import com.example.shopdientu.modul.Sanpham;
import com.example.shopdientu.ultil.CheckConnection;
import com.example.shopdientu.ultil.sever;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator3;

public class MainActivity extends AppCompatActivity {
  public static ArrayList<Giohang> manggiohang;
    DrawerLayout drawerLayout ;
    Toolbar toolbar;
    NavigationView navigationView;
    RecyclerView recyclerView;
    ListView listView;
    //loaisp

    ArrayList<Loaisp> mangloaisp;
    AdapterLoaisp adapterLoaisp;
    int id= 0;
    String tenloaisp= "";
    String hinhanhloaisp = "";
    //samphammoinhat

    ArrayList<Sanpham> mangsanpham;
    AdapterSanpham adapterSanpham;
    //Viewpaper

    ViewPager2 viewPager2;
    CircleIndicator3 circleIndicator3;
    private List<Photo> listPhoto;
    Handler handler = new Handler(Looper.getMainLooper());
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            int currentPosision = viewPager2.getCurrentItem();
            if(currentPosision ==listPhoto.size()-1){
                viewPager2.setCurrentItem(0);
            }else{
                viewPager2.setCurrentItem(currentPosision+1);
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(CheckConnection.HaveNetworkConnection(getApplicationContext())){
            Anhxa();
            ActionBar();
           // Viewflipper();
            ViewPaper();
            Getdata();
            Getsanphammoinhat();
            GetOnclickItemListview();
        }else {
            CheckConnection.ShowToastLong(getApplicationContext(), "khong co mang");
        }

    }
    private void ViewPaper() {

             listPhoto = Mangquangcao();
        Photoadapter photoadapter = new Photoadapter(this, Mangquangcao());
        viewPager2.setAdapter(photoadapter);
        circleIndicator3.setViewPager(viewPager2);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable, 3000);
            }
        });
    }

    private List<Photo> Mangquangcao() {
        ArrayList<Photo> mangquangcao = new ArrayList<>();
        mangquangcao.add(new Photo(R.drawable.dongho1));
        mangquangcao.add(new Photo(R.drawable.tainghe1));
        mangquangcao.add(new Photo(R.drawable.dongho2));
        mangquangcao.add(new Photo(R.drawable.tainghe2));
        mangquangcao.add(new Photo(R.drawable.dongho3));
        mangquangcao.add(new Photo(R.drawable.dongho3));
        return mangquangcao;

    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.postDelayed(runnable, 3000);
    }

    private void GetOnclickItemListview() {
          listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
              @Override
              public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                  switch (position){
                      case 0:
                          if(CheckConnection.HaveNetworkConnection(getApplicationContext())){
                              Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                              startActivity(intent);
                          }else{
                              CheckConnection.ShowToastLong(getApplicationContext(), "khong co mang");
                          }
                           drawerLayout.closeDrawer(GravityCompat.START);
                          break;
                      case 1:
                          if(CheckConnection.HaveNetworkConnection(getApplicationContext())){
                              Intent intent = new Intent(getApplicationContext(), DonghoActivity.class);
                              intent.putExtra("idloaidsanpham", mangloaisp.get(position).getId());
                              startActivity(intent);
                          }else{
                              CheckConnection.ShowToastLong(getApplicationContext(), "khong co mang");
                          }
                          drawerLayout.closeDrawer(GravityCompat.START);
                          break;
                      case 2:
                          if(CheckConnection.HaveNetworkConnection(getApplicationContext())){
                              Intent intent = new Intent(getApplicationContext(), TaingheActivity.class);
                              intent.putExtra("idloaidsanpham", mangloaisp.get(position).getId());
                              startActivity(intent);
                          }else{
                              CheckConnection.ShowToastLong(getApplicationContext(), "khong co mang");
                          }
                          drawerLayout.closeDrawer(GravityCompat.START);
                          break;
                      case 3:
                          Intent intent =new Intent(getApplicationContext(), LienheActivity.class);
                                  startActivity(intent);
                          drawerLayout.closeDrawer(GravityCompat.START);
                                  break;
                      case 4:
                          Intent intent1 =new Intent(getApplicationContext(), ThongtinActivity.class);
                          startActivity(intent1);
                          drawerLayout.closeDrawer(GravityCompat.START);
                          break;
                  }
              }
          });
    }

    private void Getsanphammoinhat() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(sever.duongdansanphammoinhat, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                if(response!=null){
                    int id= 0;
                    String tensp= "";
                    Integer giasp = 0;
                    String hinhanhsp="";
                    String motasp= "";
                    int idSanpham = 0;
                    for(int i =0; i<response.length(); i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            tensp = jsonObject.getString("tensp");
                            giasp = jsonObject.getInt("giasp");
                            motasp = jsonObject.getString("motasp");
                            hinhanhsp = jsonObject.getString("hinhanhsp");
                            idSanpham = jsonObject.getInt("idsanpham");
                            mangsanpham.add(new Sanpham(id, tensp, giasp, hinhanhsp, motasp, idSanpham));
                            adapterSanpham.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void Getdata() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(sever.duongdanloaisp, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                 if(response!= null){
                     for (int i = 0; i<response.length(); i++){
                         try {
                             JSONObject jsonObject = response.getJSONObject(i);
                             id = jsonObject.getInt("id");
                             tenloaisp = jsonObject.getString("tenloaisp");
                             hinhanhloaisp = jsonObject.getString("hinhanhloaisanpham");
                             mangloaisp.add(new Loaisp(id, tenloaisp, hinhanhloaisp));
                             adapterLoaisp.notifyDataSetChanged();

                         } catch (JSONException e) {
                             e.printStackTrace();
                         }
                     }

                     mangloaisp.add(3,new Loaisp(0,"Liên hệ","https://www.air-it.co.uk/wp-content/uploads/2015/02/kpi-icons-01.png"));
                     mangloaisp.add(4,new Loaisp(0,"Thông tin","http://www.mobilegiving.ca/wp-content/uploads/2015/06/icon_info_lg2.png"));
                 }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void ActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.actionbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
                drawerLayout =(DrawerLayout) findViewById(R.id.drawerLayout);
            }
        });

    }

    private void Anhxa() {
        //giohang
        if(manggiohang != null){

        }else{
            manggiohang = new ArrayList<>();
        }
        //end
        drawerLayout = findViewById(R.id.drawerLayout);
        toolbar = findViewById(R.id.toolbarmanhinhchinh);
        //viewFlipper = findViewById(R.id.Viewfliper);
        viewPager2 = findViewById(R.id.viewpaper);
        circleIndicator3 = findViewById(R.id.cricler);
        navigationView = findViewById(R.id.navigationView);
        recyclerView = findViewById(R.id.recyclerViewmanhinhchinh);
        listView = findViewById(R.id.listviewmanhinhchinh);
        mangloaisp = new ArrayList<>();
        mangloaisp.add(new Loaisp(0, "Trang Chủ", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSezDEUYGChOZMdZHGM3BSsEGqzGiSgoT0YNrnGrKRVmm6Qj5OC"));
        adapterLoaisp = new AdapterLoaisp(getApplicationContext(), mangloaisp);
        listView.setAdapter(adapterLoaisp);
        //
        mangsanpham = new ArrayList<>();
        adapterSanpham = new AdapterSanpham(getApplicationContext(), mangsanpham);
        recyclerView.setAdapter(adapterSanpham);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
          switch (item.getItemId()){
              case R.id.giohang:
                  Intent intent = new Intent(getApplicationContext(), GiohangActivity.class);
                  startActivity(intent);
          }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapterSanpham.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapterSanpham.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}