package com.example.shopdientu.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



import com.example.shopdientu.R;
import com.example.shopdientu.activity.Chitietsanpham;
import com.example.shopdientu.modul.Sanpham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class AdapterSanpham extends RecyclerView.Adapter<AdapterSanpham.ViewHolderRecycler> implements Filterable {
    Context context;
    ArrayList<Sanpham> sanphamArrayList;
    ArrayList<Sanpham> sanphamArrayListOld;

    public AdapterSanpham(Context context, ArrayList<Sanpham> sanphamArrayList) {
        this.context = context;
        this.sanphamArrayList = sanphamArrayList;
        this.sanphamArrayListOld = sanphamArrayList;
    }

    @NonNull
    @Override
    public ViewHolderRecycler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_san_pham_moi_nhat,parent, false);
        ViewHolderRecycler viewHolderRecycler = new ViewHolderRecycler(view);
        return viewHolderRecycler;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderRecycler holder, int position) {
           Sanpham sanpham = sanphamArrayList.get(position);
           holder.txttensp.setText(sanpham.getTensp());
           holder.txttensp.setMaxLines(2);
           holder.txttensp.setEllipsize(TextUtils.TruncateAt.END);
           DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
           holder.txtGiasp.setText("Giá :"+decimalFormat.format(sanpham.getGiasp()) +"VND");
           Picasso.get().load(sanpham.getHinhanhsp())
                   .placeholder(R.drawable.home)
                   .error(R.drawable.erro)
                   .into(holder.img_sanpham);
    }

    @Override
    public int getItemCount() {
        if (sanphamArrayList!= null)
           return sanphamArrayList.size();
        return 0;
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSearch = constraint.toString();
                if(strSearch.isEmpty()){
                    sanphamArrayList = sanphamArrayListOld;
                }else{
                    ArrayList<Sanpham> list = new ArrayList<>();
                    for(Sanpham sanpham : sanphamArrayListOld){
                        if(sanpham.getTensp().toLowerCase().contains(strSearch.toLowerCase())){
                            list.add(sanpham);
                        }

                    }
                    sanphamArrayList = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = sanphamArrayList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                    sanphamArrayList = (ArrayList<Sanpham>) results.values;
                    notifyDataSetChanged();
            }
        };
    }

    public class ViewHolderRecycler extends RecyclerView.ViewHolder {
        TextView txttensp,txtGiasp;
        ImageView img_sanpham;
        public ViewHolderRecycler(@NonNull View itemView) {
            super(itemView);
            txttensp= itemView.findViewById(R.id.textviewtensanpham);
            txtGiasp = itemView.findViewById(R.id.textviewgiasanpham);
            img_sanpham = itemView.findViewById(R.id.imageviewsanpham);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, Chitietsanpham.class);
                    intent.putExtra("thongtinsanpham", sanphamArrayList.get(getPosition()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
        }
    }
}
