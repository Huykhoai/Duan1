package com.example.shopdientu.adapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.shopdientu.fragment.PhotoFragment;
import com.example.shopdientu.modul.Photo;

import java.util.List;

public class Photoadapter extends FragmentStateAdapter {
    public List<Photo> arrayList;
    public Photoadapter(@NonNull FragmentActivity fragmentActivity, List<Photo> arrayList ) {
        super(fragmentActivity);
        this.arrayList = arrayList;

    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Photo photo = arrayList.get(position);
        Bundle bundle = new Bundle();
        bundle.putSerializable("objectPhoto", photo);
        PhotoFragment photoFragment = new PhotoFragment();
        photoFragment.setArguments(bundle);
        return photoFragment;
    }

    @Override
    public int getItemCount() {
        if(arrayList!=null)
            return arrayList.size();
        return 0;
    }
}
