package com.example.admin.appquanlyquanhecanhan.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.admin.appquanlyquanhecanhan.Model.TinNhan;
import com.example.admin.appquanlyquanhecanhan.R;

import java.util.List;

/**
 * Created by Admin on 12-Apr-18.
 */

public class AdapterTinNhan extends BaseAdapter {
   List<TinNhan> list;
   int layout;
   Context context;

    public AdapterTinNhan(List<TinNhan> list, int layout, Context context) {
        this.list = list;
        this.layout = layout;
        this.context = context;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(layout,null);
        TextView txtNoiDung = view.findViewById(R.id.txtNoiDung);
        TextView txtNgayGui = view.findViewById(R.id.txtNgayGui);
        txtNgayGui.setText("Ngày gửi: "+list.get(position).getNgay());
        txtNoiDung.setText("Nội dung: "+list.get(position).getNoiDung());
        return view;
    }
}
