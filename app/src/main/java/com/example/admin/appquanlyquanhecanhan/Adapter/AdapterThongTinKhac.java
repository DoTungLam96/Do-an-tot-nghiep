package com.example.admin.appquanlyquanhecanhan.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.appquanlyquanhecanhan.Model.ThongTinKhac;
import com.example.admin.appquanlyquanhecanhan.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Admin on 5/4/2018.
 */

public class AdapterThongTinKhac extends BaseAdapter {
   List<ThongTinKhac> list;
   int layout;
   Context context;

    public AdapterThongTinKhac(List<ThongTinKhac> list, int layout, Context context) {
        this.list = list;
        this.layout = layout;
        this.context = context;
    }

    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(layout,null);
        TextView txtThongTin = view.findViewById(R.id.txtThongTin);
        ImageView imgHinh = view.findViewById(R.id.imgThongTin);
        txtThongTin.setText(list.get(i).getChiTiet());
        imgHinh.setImageResource(list.get(i).getHinhAnh());
        return view;
    }
}
