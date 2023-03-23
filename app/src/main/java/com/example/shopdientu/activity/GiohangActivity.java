package com.example.shopdientu.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;


import com.example.shopdientu.R;
import com.example.shopdientu.adapter.AdapterGiohang;
import com.example.shopdientu.modul.Giohang;
import com.example.shopdientu.ultil.CheckConnection;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class GiohangActivity extends AppCompatActivity {
     Toolbar toolbar;
     ListView listView;
     Button btnthanhtoan;
     Button btntieptucmuahang;
     TextView txtthongbao;
     static TextView txtgiatri;
     AdapterGiohang adapterGiohang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giohang);
        Anhxa();
        Actionbar();
        CheckData();
        ClickItemListview();
        EvenButton();
        UpdateTongtien();
    }

    public static void UpdateTongtien() {
          long tongtien = 0;
          for(int i = 0; i<MainActivity.manggiohang.size();i++){
              tongtien += MainActivity.manggiohang.get(i).getGiasp();
          }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
          txtgiatri.setText("Giá :"+decimalFormat.format(tongtien)+"VNĐ");
    }

    private void EvenButton() {
        btnthanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.manggiohang.size()>0){
                     Intent intent = new Intent(getApplicationContext(), Thongtinkhachhang.class);
                     startActivity(intent);

                }else{
                    CheckConnection.ShowToastLong(getApplicationContext(), "Giỏ hàng của bạn chưa có sản phẩm nào để thanh toán");
                }
            }
        });
        btntieptucmuahang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void ClickItemListview() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(MainActivity.manggiohang.size()<1){
                    txtthongbao.setVisibility(View.VISIBLE);
                }else{
                    MainActivity.manggiohang.remove(position);
                    adapterGiohang.notifyDataSetChanged();
                    UpdateTongtien();
                    if(MainActivity.manggiohang.size()<1){
                        txtthongbao.setVisibility(View.VISIBLE);
                    }else{
                        txtthongbao.setVisibility(View.INVISIBLE);
                        adapterGiohang.notifyDataSetChanged();
                        UpdateTongtien();

                    }
                }
            }
        });
    }

    private void CheckData() {
        if(MainActivity.manggiohang.size()<=0){
               txtthongbao.setVisibility(View.VISIBLE);
               listView.setVisibility(View.INVISIBLE);
               adapterGiohang.notifyDataSetChanged();
        }else{
            txtthongbao.setVisibility(View.INVISIBLE);
            listView.setVisibility(View.VISIBLE);
            adapterGiohang.notifyDataSetChanged();
        }
    }

    private void Actionbar() {
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
        toolbar =findViewById(R.id.toolbargiohang);
        listView =  findViewById(R.id.listviewgiohang);
        btnthanhtoan = findViewById(R.id.buttonthanhtoangiohang);
        btntieptucmuahang = findViewById(R.id.buttontieptucmuahang);
        txtthongbao = findViewById(R.id.textviewthongbao);
        txtgiatri = findViewById(R.id.textviewtongtien);
        adapterGiohang = new AdapterGiohang(getApplicationContext(), MainActivity.manggiohang);
        listView.setAdapter(adapterGiohang);

    }
}