package com.example.shopdientu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shopdientu.R;
import com.example.shopdientu.activity.GiohangActivity;
import com.example.shopdientu.activity.MainActivity;
import com.example.shopdientu.modul.Giohang;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class AdapterGiohang extends BaseAdapter {
    Context context;
    ArrayList<Giohang> arrayList;

    public AdapterGiohang(Context context, ArrayList<Giohang> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewholderGiohang viewholderGiohang = null;
        if (view==null) {
            viewholderGiohang = new ViewholderGiohang();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_giohang, null);
            viewholderGiohang.txtname = view.findViewById(R.id.textviewgiohang);
            viewholderGiohang.txtgia = view.findViewById(R.id.textviewgiagiohang);
            viewholderGiohang.btnTru = view.findViewById(R.id.buttonminus);
            viewholderGiohang.btnCong = view.findViewById(R.id.buttonplus);
            viewholderGiohang.btnGiatri = view.findViewById(R.id.buttonvalues);
            viewholderGiohang.imgGiohang = view.findViewById(R.id.imageviewgiohang);
            view.setTag(viewholderGiohang);
        }else {
            viewholderGiohang = (ViewholderGiohang) view.getTag();
        }
            //xu ly su kien
            Giohang giohang = (Giohang) getItem(position);
              viewholderGiohang.txtname.setText(giohang.getTensp());
            DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
              viewholderGiohang.txtgia.setText("Giá: " +decimalFormat.format(giohang.getGiasp()));
        Picasso.get().load(giohang.getHinhanhsp())
                    .placeholder(R.drawable.home)
                    .error(R.drawable.erro)
                    .into(viewholderGiohang.imgGiohang);
             viewholderGiohang.btnGiatri.setText(Integer.toString(giohang.getSoluongsp()));
        int sl = Integer.parseInt(viewholderGiohang.btnGiatri.getText().toString());
        if(sl>10){
            viewholderGiohang.btnCong.setVisibility(View.INVISIBLE);
            viewholderGiohang.btnTru.setVisibility(View.VISIBLE);
        }else if(sl<=1){
            viewholderGiohang.btnCong.setVisibility(View.VISIBLE);
            viewholderGiohang.btnTru.setVisibility(View.INVISIBLE);
        }else if(sl>=1){
            viewholderGiohang.btnCong.setVisibility(View.VISIBLE);
            viewholderGiohang.btnTru.setVisibility(View.VISIBLE);
        }
        ViewholderGiohang finalViewholderGiohang = viewholderGiohang;
        viewholderGiohang.btnCong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int slmoinhat = Integer.parseInt(finalViewholderGiohang.btnGiatri.getText().toString()) +1;
                int slhientai = MainActivity.manggiohang.get(position).getSoluongsp();
                long giahientai = MainActivity.manggiohang.get(position).getGiasp();
                MainActivity.manggiohang.get(position).setSoluongsp(slmoinhat);
                long giamoinhat = (giahientai * slmoinhat) / slhientai;
                MainActivity.manggiohang.get(position).setGiasp(giamoinhat);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewholderGiohang.txtgia.setText("Giá: " +decimalFormat.format(giamoinhat)+"VNĐ");
                GiohangActivity.UpdateTongtien();
                if(slmoinhat>9){
                    finalViewholderGiohang.btnCong.setVisibility(View.INVISIBLE);
                    finalViewholderGiohang.btnGiatri.setText(String.valueOf(slmoinhat));
                    finalViewholderGiohang.btnTru.setVisibility(View.VISIBLE);
                }else {
                    finalViewholderGiohang.btnCong.setVisibility(View.VISIBLE);
                    finalViewholderGiohang.btnGiatri.setText(String.valueOf(slmoinhat));
                    finalViewholderGiohang.btnTru.setVisibility(View.VISIBLE);
                }
            }
        });
        viewholderGiohang.btnTru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int slmoinhat = Integer.parseInt(finalViewholderGiohang.btnGiatri.getText().toString()) -1;
                int slhientai = MainActivity.manggiohang.get(position).getSoluongsp();
                long giahientai = MainActivity.manggiohang.get(position).getGiasp();
                MainActivity.manggiohang.get(position).setSoluongsp(slmoinhat);
                long giamoinhat = (giahientai * slmoinhat) / slhientai;
                MainActivity.manggiohang.get(position).setGiasp(giamoinhat);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewholderGiohang.txtgia.setText("Giá: " +decimalFormat.format(giamoinhat)+"VNĐ");
                GiohangActivity.UpdateTongtien();
                if(slmoinhat<2){
                    finalViewholderGiohang.btnCong.setVisibility(View.VISIBLE);
                    finalViewholderGiohang.btnTru.setVisibility(View.INVISIBLE);
                    finalViewholderGiohang.btnGiatri.setText(String.valueOf(slmoinhat));
                }else {
                    finalViewholderGiohang.btnCong.setVisibility(View.VISIBLE);
                    finalViewholderGiohang.btnTru.setVisibility(View.VISIBLE);
                    finalViewholderGiohang.btnGiatri.setText(String.valueOf(slmoinhat));
                }
            }

        });
        return view;
    }
    public class ViewholderGiohang{
        TextView txtname, txtgia;
        ImageView imgGiohang;
        Button btnCong, btnTru, btnGiatri;
    }
}
