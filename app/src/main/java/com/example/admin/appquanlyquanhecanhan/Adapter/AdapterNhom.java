package com.example.admin.appquanlyquanhecanhan.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.admin.appquanlyquanhecanhan.Model.Nhom;
import com.example.admin.appquanlyquanhecanhan.R;
import com.example.admin.appquanlyquanhecanhan.SuaActivity;
import com.example.admin.appquanlyquanhecanhan.SuaNhomActivity;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Admin on 15-Apr-18.
 */

public class AdapterNhom extends BaseAdapter {
    Context context;
    List<Nhom> list;
    int layout;

    public AdapterNhom(Context context, List<Nhom> list, int layout) {
        this.context = context;
        this.list = list;
        this.layout = layout;
    }

    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(layout,null);
        TextView txtTenNhom = convertView.findViewById(R.id.txtTenNhom);
        CircleImageView imgIcon = convertView.findViewById(R.id.imgIcon);
        ImageButton btnSuaNhom = convertView.findViewById(R.id.btnSuaNhom);
        btnSuaNhom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SuaNhomActivity.class);
                intent.putExtra("IDNhom",list.get(position).getIDNhom());
                ((Activity) context).startActivity(intent);
            }
        });
        txtTenNhom.setText(list.get(position).getTenNhom());
        Bitmap bitmap = BitmapFactory.decodeByteArray(list.get(position).getIcon(),0,list.get(position).getIcon().length);
        imgIcon.setImageBitmap(bitmap);
        return convertView;
    }
}
