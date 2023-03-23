package com.example.shopdientu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.shopdientu.R;
import com.example.shopdientu.ultil.CheckConnection;
import com.example.shopdientu.ultil.sever;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Thongtinkhachhang extends AppCompatActivity {
          public EditText edtenkh,edsdtkh,edemailkh, eddiachi;
          public Button btnXacnhan, btnTrove;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongtinkhachhang);
        Anhxa();
        if (CheckConnection.HaveNetworkConnection(getApplicationContext())){
            EvenButton();
        }else{
            CheckConnection.ShowToastLong(getApplicationContext(), "khong co mang");
        }
    }


    private void EvenButton() {

        btnXacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final   String tenkh = edtenkh.getText().toString();
                final   String sdtkh = edsdtkh.getText().toString();
                final   String emailkh = edemailkh.getText().toString();
                final   String diachikh = eddiachi.getText().toString();
                if(tenkh.length()>0 && sdtkh.length() >0 && emailkh.length()>0 && diachikh.length()>0){
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, sever.duongdandonhang, new Response.Listener<String>() {
                        @Override
                        public void onResponse(final String madonhang) {
                            Log.d("madonhang", madonhang);
                            if(Double.parseDouble(madonhang)>0){
                                RequestQueue requestQueue1 = Volley.newRequestQueue(getApplicationContext());
                                StringRequest stringRequest1 = new StringRequest(Request.Method.POST, sever.duongdandonhangchitiet, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        if (response.equals("1")){
                                            MainActivity.manggiohang.clear();
                                            CheckConnection.ShowToastLong(getApplicationContext(), "Bạn đã thêm dữ liệu vào giỏ hàng thành công");
                                            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                            startActivity(intent);
                                            CheckConnection.ShowToastLong(getApplicationContext(), "Mời bạn tiếp tục mua hàng");
                                        }else {
                                            CheckConnection.ShowToastLong(getApplicationContext(), "dữ liệu giỏ hàng của bạn bị lỗi");
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
                                        JSONArray jsonArray = new JSONArray();
                                        for(int i=0; i<MainActivity.manggiohang.size(); i++){

                                            JSONObject jsonObject = new JSONObject();
                                            try {
                                                jsonObject.put("madonhang", madonhang);
                                                jsonObject.put("masanpham", MainActivity.manggiohang.get(i).getId());
                                                jsonObject.put("tensanpham", MainActivity.manggiohang.get(i).getTensp());
                                                jsonObject.put("giasanpham", MainActivity.manggiohang.get(i).getGiasp());
                                                jsonObject.put("soluongsanpham", MainActivity.manggiohang.get(i).getSoluongsp());
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            jsonArray.put(jsonObject);
                                        }
                                        HashMap<String,String> param = new HashMap<>();
                                        param.put("json", jsonArray.toString());
                                        return param;
                                    }
                                };
                                requestQueue1.add(stringRequest1);
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
                            HashMap<String,String> param = new HashMap<>();
                            param.put("tenkhachhang",tenkh);
                            param.put("sodienthoai",sdtkh);
                            param.put("email",emailkh);
                            param.put("diachi",diachikh);
                            return param;
                        }
                    };
                    requestQueue.add(stringRequest);
                }else{
                    Toast.makeText(Thongtinkhachhang.this, "Bạn chưa điền đủ thông tin" , Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnTrove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }



    private void Anhxa() {
        edtenkh = findViewById(R.id.edttenKH);
        edsdtkh = findViewById(R.id.edtSDT);
        edemailkh = findViewById(R.id.edtEmail);
        eddiachi = findViewById(R.id.edtdc);
        btnXacnhan = findViewById(R.id.btnXN);
        btnTrove = findViewById(R.id.btnTV);
    }

}