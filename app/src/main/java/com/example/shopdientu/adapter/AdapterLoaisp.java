package com.example.shopdientu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.shopdientu.R;
import com.example.shopdientu.modul.Loaisp;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterLoaisp extends BaseAdapter {
    Context context;
    ArrayList<Loaisp> arrayList;

    public AdapterLoaisp(Context context, ArrayList<Loaisp> arrayList) {
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
        ViewHolder viewHolder= null;
        if (viewHolder ==null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_loaisp, null);
            viewHolder.tenLoaisp = view.findViewById(R.id.textviewLoaisp);
            viewHolder.img_Loaisp = view.findViewById(R.id.imageLoaisp);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        //xy ly su kien
        Loaisp loaisp = arrayList.get(position);
        viewHolder.tenLoaisp.setText(loaisp.getTenLoaiSP());
        Picasso.get().load(loaisp.getHinhanhSP())
                .placeholder(R.drawable.home)
                .error(R.drawable.erro)
                .into(viewHolder.img_Loaisp);

        return view;
    }
    public class ViewHolder{
        TextView tenLoaisp;
        ImageView img_Loaisp;
    }
}
