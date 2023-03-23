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

public class AdapterTainghe extends BaseAdapter implements Filterable {
    Context context;
    ArrayList<Sanpham> arrayList;
    ArrayList<Sanpham> arrayListOld;

    public AdapterTainghe(Context context, ArrayList<Sanpham> arrayList) {
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
        ViewHolderTainghe viewHolderTainghe = null;
        if(view==null){
            viewHolderTainghe = new ViewHolderTainghe();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_tainghe, null);
            viewHolderTainghe.txttentainghe = view.findViewById(R.id.textviewtainghe);
            viewHolderTainghe.txtgiatainghe = view.findViewById(R.id.textviewgiatainghe);
            viewHolderTainghe.txtmotatainghe = view.findViewById(R.id.textviewmotatainghe);
            viewHolderTainghe.imgtainghe = view.findViewById(R.id.imageviewtainghe);
             view.setTag(viewHolderTainghe);
        }else {
            viewHolderTainghe = (ViewHolderTainghe) view.getTag();
        }
        //xu ly du lieu
         Sanpham sanpham = (Sanpham) getItem(position);
        viewHolderTainghe.txttentainghe.setText(sanpham.getTensp());
        viewHolderTainghe.txttentainghe.setMaxLines(2);
        viewHolderTainghe.txttentainghe.setEllipsize(TextUtils.TruncateAt.END);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolderTainghe.txtgiatainghe.setText("Giá :"+ decimalFormat.format(sanpham.getGiasp())+"VNĐ");
        viewHolderTainghe.txtmotatainghe.setText(sanpham.getMotasp());
        viewHolderTainghe.txtmotatainghe.setMaxLines(2);
        viewHolderTainghe.txtmotatainghe.setEllipsize(TextUtils.TruncateAt.END);
        Picasso.get().load(sanpham.getHinhanhsp())
                .placeholder(R.drawable.home)
                .error(R.drawable.erro)
                .into(viewHolderTainghe.imgtainghe);

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
                    for (Sanpham sanpham : arrayListOld){
                        if(sanpham.getTensp().toLowerCase().contains(strSearch.toLowerCase())){
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

    public class ViewHolderTainghe{
        TextView txttentainghe,txtgiatainghe, txtmotatainghe;
        ImageView imgtainghe;
    }
}
