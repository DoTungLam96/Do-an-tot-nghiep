package com.example.admin.appquanlyquanhecanhan.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.admin.appquanlyquanhecanhan.Model.CuocGoi;
import com.example.admin.appquanlyquanhecanhan.R;

import java.util.List;

/**
 * Created by Admin on 13-Apr-18.
 */

public class AdapterCuocGoi extends BaseAdapter {
    List<CuocGoi> list;
    Context context;
    int layout;

    public AdapterCuocGoi(List<CuocGoi> list, Context context, int layout) {
        this.list = list;
        this.context = context;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(layout,null);
        TextView txtNgayGoi = convertView.findViewById(R.id.txtNgayGoiDien);
        TextView txtTinhTrang = convertView.findViewById(R.id.txtTinhTrang);
        TextView txtThoiLuong = convertView.findViewById(R.id.txtThoiLuong);
        txtNgayGoi.setText("Ngày gọi: "+list.get(position).getNgay().toString());
        txtTinhTrang.setText("Trạng thái: "+list.get(position).getTinhTrang().toString());
        txtThoiLuong.setText("Thời lượng: "+list.get(position).getThoiGian().toString());
        return convertView;
    }
}
