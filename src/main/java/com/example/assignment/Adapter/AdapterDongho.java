package com.example.assignment.Adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.assignment.Modul.Sanpham;
import com.example.assignment.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class AdapterDongho extends BaseAdapter {
        Context context;
        ArrayList<Sanpham> arrayList;

    public AdapterDongho(Context context, ArrayList<Sanpham> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
         ViewHolderSanpham viewHolderSanpham = null;
         if(view ==null){
             viewHolderSanpham = new ViewHolderSanpham();
             LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
             view = inflater.inflate(R.layout.item_sanham, null);
             viewHolderSanpham.txtName = view.findViewById(R.id.txtNamesp);
             viewHolderSanpham.txtGia = view.findViewById(R.id.txtGiasp);
             viewHolderSanpham.txtMota = view.findViewById(R.id.txtChitietsp);
             viewHolderSanpham.imgAnh = view.findViewById(R.id.image_sanpham);
             view.setTag(viewHolderSanpham);
         }else{
             viewHolderSanpham = (ViewHolderSanpham) view.getTag();
         }
         //thực hiện
        Sanpham sanpham = arrayList.get(i);
        viewHolderSanpham.txtName.setText(sanpham.getTensanpham());
        viewHolderSanpham.txtName.setMaxLines(2);
        viewHolderSanpham.txtName.setEllipsize(TextUtils.TruncateAt.END);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolderSanpham.txtGia.setText("Giá: "+decimalFormat.format(sanpham.getGiasanpham()));
        viewHolderSanpham.txtMota.setText(sanpham.getMotasanpham());
        viewHolderSanpham.txtMota.setMaxLines(2);
        viewHolderSanpham.txtMota.setEllipsize(TextUtils.TruncateAt.END);
        Picasso.get().load(sanpham.getHinhanhsanpham())
                .placeholder(R.drawable.home)
                .error(R.drawable.erro)
                .into(viewHolderSanpham.imgAnh);
        return view;
    }

    public class ViewHolderSanpham{
           TextView txtName, txtGia, txtMota;
           ImageView imgAnh;

    }
}
