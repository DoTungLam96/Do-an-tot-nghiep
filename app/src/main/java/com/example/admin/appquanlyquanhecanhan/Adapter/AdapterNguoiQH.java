package com.example.admin.appquanlyquanhecanhan.Adapter;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.multidex.MultiDex;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.appquanlyquanhecanhan.Model.NguoiQH;
import com.example.admin.appquanlyquanhecanhan.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Admin on 06-Apr-18.
 */

public class AdapterNguoiQH extends BaseAdapter {
    Context context;
    int layout;
    List<NguoiQH> list;

    public AdapterNguoiQH(Context context, int layout, List<NguoiQH> list) {
        this.context = context;
        this.layout = layout;
        this.list = list;
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
        return 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        try{
        if (view == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(layout,null);
            holder = new ViewHolder();
            holder.btnGmail = view.findViewById(R.id.btnGmail);
            holder.btnGoiDien = view.findViewById(R.id.btnGoiDien);
            holder.btnTinNhan = view.findViewById(R.id.btnTinNhan);
            holder.btnFB = view.findViewById(R.id.btnFacebook);
            holder.imgHinh =(de.hdodenhof.circleimageview.CircleImageView) view.findViewById(R.id.imgHinha);
            holder.txtHoTen = view.findViewById(R.id.txtHoTen);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
            NguoiQH nguoiQH = list.get(i);
            holder.txtHoTen.setText(nguoiQH.getHoTen());
            byte[] hinhAnh = nguoiQH.getAnhDaiDien();
            Bitmap bitmap = BitmapFactory.decodeByteArray(hinhAnh,0,hinhAnh.length);
            holder.imgHinh.setImageBitmap(bitmap);
        }catch (Exception e){

        }


        holder.btnGoiDien.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+list.get(i).getSDT()));
                context.startActivity(callIntent);
            }
        });
        holder.btnTinNhan.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("smsto:"+list.get(i).getSDT());
                Intent nhanTin = new Intent(Intent.ACTION_SENDTO, uri);
                nhanTin.putExtra("sms_body", "");
                context.startActivity(nhanTin);
            }
        });
        holder.btnGmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{
                    Intent intent = new Intent (Intent.ACTION_VIEW , Uri.parse("mailto:" + list.get(i).getEmail()+""));
                    intent.putExtra(Intent.EXTRA_SUBJECT, "");
                    intent.putExtra(Intent.EXTRA_TEXT, "");
                    context.startActivity(intent);
                }catch(ActivityNotFoundException e){
                    //TODO smth
                }
            }
        });
        holder.btnFB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               try {

                       Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://"+list.get(i).getFaceBook().toString()));
                       context.startActivity(browserIntent);
               }catch (Exception ex){
                Toast.makeText(context, "Đường dẫn bạn nhập không đúng", Toast.LENGTH_LONG).show();
               }




            }
        });
        return view;
    }

    private class ViewHolder{
       ImageButton btnTinNhan,btnGoiDien,btnGmail,btnFB;
        de.hdodenhof.circleimageview.CircleImageView imgHinh;
       TextView txtHoTen;
    }
}
