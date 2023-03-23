package com.example.shopdientu.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shopdientu.R;
import com.example.shopdientu.modul.Sanpham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class AdapterDongho extends BaseAdapter implements Filterable {
    Context context;
    ArrayList<Sanpham> arrayList;
    ArrayList<Sanpham> arrayListOld;

    public AdapterDongho(Context context, ArrayList<Sanpham> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        this.arrayListOld = arrayList;
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
        ViewHolderDongho viewHolderDongho = null;
        if (view == null){
            viewHolderDongho = new ViewHolderDongho();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.item_dongho, null);
            viewHolderDongho.tv_tendongho = view.findViewById(R.id.textviewdongho);
            viewHolderDongho.tv_giadongho = view.findViewById(R.id.textviewgiadongho);
            viewHolderDongho.tv_motadongho = view.findViewById(R.id.textviewmotadongho);
            viewHolderDongho.img_dongho = view.findViewById(R.id.imageviewdongho);
            view.setTag(viewHolderDongho);
        }
        else{
            viewHolderDongho = (ViewHolderDongho) view.getTag();
        }
            //xu ly du lieu
            Sanpham sanpham = (Sanpham) getItem(position    );
            viewHolderDongho.tv_tendongho.setText(sanpham.getTensp());
            viewHolderDongho.tv_tendongho.setMaxLines(2);
            viewHolderDongho.tv_tendongho.setEllipsize(TextUtils.TruncateAt.END);
            DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
            viewHolderDongho.tv_giadongho.setText("Giá : " +decimalFormat.format(sanpham.getGiasp())+"VND");
            viewHolderDongho.tv_motadongho.setText(sanpham.getMotasp());
            viewHolderDongho.tv_motadongho.setMaxLines(2);
            viewHolderDongho.tv_motadongho.setEllipsize(TextUtils.TruncateAt.END);
            Picasso.get().load(sanpham.getHinhanhsp())
                    .placeholder(R.drawable.home)
                    .error(R.drawable.erro)
                    .into(viewHolderDongho.img_dongho);

        return view;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSearch = constraint.toString();
                if(strSearch.isEmpty()){
                    arrayList = arrayListOld;
                }else{
                    ArrayList<Sanpham> list = new ArrayList<>();
                    for(Sanpham sanpham : arrayListOld){
                        if (sanpham.getTensp().toLowerCase().contains(strSearch.toLowerCase())){
                            list.add(sanpham);
                        }
                    }
                    arrayList = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = arrayList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                       arrayList = (ArrayList<Sanpham>) results.values;
                       notifyDataSetChanged();
            }
        };
    }

    public class ViewHolderDongho{
        TextView tv_tendongho, tv_giadongho, tv_motadongho;
        ImageView img_dongho;
    }
}
