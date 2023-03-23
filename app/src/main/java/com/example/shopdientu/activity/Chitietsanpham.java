package com.example.shopdientu.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;


import com.example.shopdientu.R;
import com.example.shopdientu.modul.Giohang;
import com.example.shopdientu.modul.Sanpham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class Chitietsanpham extends AppCompatActivity {
  Toolbar toolbar;
  TextView txtten,txtgia,txtmota;
  ImageView img_anh;
  Spinner spinner;
  Button btnthemhang;
  int id = 0;
  String tenchitiet = "";
  int giachitiet =0;
  String hinhanhchitiet="";
  String motachitiet = "";
  int IDsanpham = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitietsanpham);
        Anhxa();
        ActionBar();
        GetInformation();
        EvenButton();
       CatchevenSpinner();
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
        toolbar = findViewById(R.id.toolbarchitietsanpham);
        txtten = findViewById(R.id.textviewtenchitietsanpham);
        txtgia = findViewById(R.id.textviewgiachitietsanpham);
        txtmota = findViewById(R.id.textviewmotachitietsanpham);
        img_anh = findViewById(R.id.imageviewchitietsanpham);
        spinner = findViewById(R.id.spinner);
        btnthemhang = findViewById(R.id.buttondatmua);
    }

    private void CatchevenSpinner() {
        Integer[] soluong = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, soluong);
        spinner.setAdapter(arrayAdapter);
    }

    private void EvenButton() {
        btnthemhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.manggiohang.size()>0){
                   int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
                   boolean exits = false;
                   for (int i =0; i<MainActivity.manggiohang.size(); i++){
                       if (MainActivity.manggiohang.get(i).getId() == id){
                           MainActivity.manggiohang.get(i).setSoluongsp(MainActivity.manggiohang.get(i).getSoluongsp()+ soluong);
                        if(MainActivity.manggiohang.get(i).getSoluongsp()>10){
                            MainActivity.manggiohang.get(i).setSoluongsp(10);
                        }
                       //gia
                           MainActivity.manggiohang.get(i).setGiasp(MainActivity.manggiohang.get(i).getSoluongsp() * giachitiet);
                        exits = true;
                       }
                   }
                   if(exits==false){
                       int sl = Integer.parseInt(spinner.getSelectedItem().toString());
                        long Giamoi =giachitiet * sl;
                         MainActivity.manggiohang.add(new Giohang(id,tenchitiet, Giamoi, hinhanhchitiet, sl));
                   }
                }else{
                    int sl = Integer.parseInt(spinner.getSelectedItem().toString());
                    long Giamoi =giachitiet * sl;
                    MainActivity.manggiohang.add(new Giohang(id,tenchitiet, Giamoi, hinhanhchitiet, sl));
                }
                //demo5
                Intent intent =new Intent(getApplicationContext(), GiohangActivity.class);
                startActivity(intent);
            }
        });

    }

    private void GetInformation() {
        Sanpham sanpham = (Sanpham) getIntent().getSerializableExtra("thongtinsanpham");
        id = sanpham.getId();
        tenchitiet = sanpham.getTensp();
        giachitiet = sanpham.getGiasp();
        hinhanhchitiet = sanpham.getHinhanhsp();
        motachitiet = sanpham.getMotasp();

        //xu ly du lieu
        txtten.setText(tenchitiet);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtgia.setText("Giá : "+decimalFormat.format(giachitiet)+"VNĐ");
        txtmota.setText(motachitiet);
        Picasso.get().load(hinhanhchitiet)
                .placeholder(R.drawable.home)
                .error(R.drawable.erro)
                .into(img_anh);
    }
}