package com.example.admin.appquanlyquanhecanhan.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.appquanlyquanhecanhan.ChucNangKhac.SuaQHKhacActivity;
import com.example.admin.appquanlyquanhecanhan.Database.Database;
import com.example.admin.appquanlyquanhecanhan.MainActivity;
import com.example.admin.appquanlyquanhecanhan.Model.CuocGoi;
import com.example.admin.appquanlyquanhecanhan.Model.MoiQHKhac;
import com.example.admin.appquanlyquanhecanhan.R;

import java.util.List;

/**
 * Created by Admin on 14-Apr-18.
 */

public class AdapterQuanHe extends BaseAdapter {
    List<MoiQHKhac> list;
    Context context;
    int layout;

    public AdapterQuanHe(List<MoiQHKhac> list, Context context, int layout) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(layout,null);
        TextView txtHoTenQH = convertView.findViewById(R.id.txtHoTenQH);
        TextView txtSDTQH = convertView.findViewById(R.id.txtSDTQH);
        TextView txtKieuQH = convertView.findViewById(R.id.txtKieuQH);
        TextView txtNgaySinhQH=convertView.findViewById(R.id.txtNgaySinhQH);
        TextView txtDiaChiQH=convertView.findViewById(R.id.txtDiaChiQH);
        ImageButton btnTinNhan = convertView.findViewById(R.id.btnTinNhanKhac);
        ImageButton btnGoiDien = convertView.findViewById(R.id.btnGoiDienKhac);
        ImageButton btnUpBook = convertView.findViewById(R.id.btnBook);
        ImageButton btnChinhSua = convertView.findViewById(R.id.btnChinhSua);
        txtHoTenQH.setText("Họ tên: "+list.get(position).getHoTen());
        txtSDTQH.setText("SĐT: "+list.get(position).getSDTQH());
        txtKieuQH.setText("Mối quan hệ: "+list.get(position).getKieuQH());
        txtNgaySinhQH.setText("Ngày sinh: "+list.get(position).getNgaySinh());
        txtDiaChiQH.setText("Địa chỉ: "+list.get(position).getDiaChi());
        try{
            btnGoiDien.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("MissingPermission")
                @Override
                public void onClick(View v) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:"+list.get(position).getSDTQH()));
                    context.startActivity(callIntent);
                }
            });
            btnTinNhan.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("MissingPermission")
                @Override
                public void onClick(View v) {
                    Uri uri = Uri.parse("smsto:"+list.get(position).getSDTQH());
                    Intent nhanTin = new Intent(Intent.ACTION_SENDTO, uri);
                    nhanTin.putExtra("sms_body", "");
                    context.startActivity(nhanTin);
                }
            });
        }catch (Exception e){

        }
        btnUpBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase database = Database.initDatabase((Activity) context,"CSDL.sqlite");
                ContentValues contentValues = new ContentValues();
                contentValues.put("SDT",list.get(position).getSDTQH());
                contentValues.put("HoTen",list.get(position).getHoTen());
                contentValues.put("DiaChi",list.get(position).getDiaChi());
                contentValues.put("NgaySinh",list.get(position).getNgaySinh());
                database.insert("NguoiQH",null,contentValues);
                Toast.makeText(context, "Đã chuyển sang liên hệ chính", Toast.LENGTH_SHORT).show();
            }
        });
        btnChinhSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SuaQHKhacActivity.class);
                intent.putExtra("IDQH",list.get(position).getIDQH());
                intent.putExtra("IDN",list.get(position).getIDN());
                context.startActivity(intent);
            }
        });
        return convertView;
    }
}
