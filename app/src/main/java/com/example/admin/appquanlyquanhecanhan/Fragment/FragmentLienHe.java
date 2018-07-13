package com.example.admin.appquanlyquanhecanhan.Fragment;

import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.appquanlyquanhecanhan.Adapter.AdapterNguoiQH;
import com.example.admin.appquanlyquanhecanhan.Database.Database;
import com.example.admin.appquanlyquanhecanhan.MainActivity;
import com.example.admin.appquanlyquanhecanhan.Model.NguoiQH;
import com.example.admin.appquanlyquanhecanhan.R;
import com.example.admin.appquanlyquanhecanhan.ThongTinChiTietActivity;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.rey.material.app.Dialog;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;

import static com.example.admin.appquanlyquanhecanhan.MainActivity.tabHost;
import static com.example.admin.appquanlyquanhecanhan.MainActivity.viewPager;

/**
 * Created by Admin on 05-Apr-18.
 */

public class FragmentLienHe extends Fragment {
public  ArrayList<NguoiQH> arrayList;
public  AdapterNguoiQH adapter;
public  ListView listView;
TextView txtKQ;
    final static String Database_name="CSDL.sqlite";
    public SQLiteDatabase database;
    View view;
   public static AlertDialog alertDialog;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_fragment_lienhe,container,false);
        database = Database.initDatabase(getActivity(),Database_name);
        anhXa();
        getData();
        TimKiem();
        SuKienItem();
        return view;
    }


    private void SuKienItem() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                alertDialog = new SpotsDialog(getActivity(),R.style.Custom);
                alertDialog.show();
                Intent intent = new Intent(getActivity(), ThongTinChiTietActivity.class);
                intent.putExtra("IDN",arrayList.get(position).getIDN());
                intent.putExtra("SDT",arrayList.get(position).getSDT().toString());
                intent.putExtra("Email",arrayList.get(position).getEmail()+"");
                intent.putExtra("FB",arrayList.get(position).getFaceBook()+"");
                startActivity(intent);

            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Xóa liên hệ ");
                builder.setMessage("Bạn có muốn xóa "+arrayList.get(position).getHoTen()+" không?");
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                          dialog.dismiss();
                    }
                });
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                 try{
                     database.delete("NguoiQH","IDN=?",new String[]{arrayList.get(position).getIDN()+""});
                     arrayList.clear();
                     Cursor cursor = database.rawQuery("Select *  from NguoiQH",null);
                     while (cursor.moveToNext()){
                         int IDN = cursor.getInt(0);;
                         String SDT = cursor.getString(1);
                         String ten = cursor.getString(2);
                         String email = cursor.getString(3);
                         String fb = cursor.getString(4);
                         String diaChi = cursor.getString(5);
                         String ngaySinh = cursor.getString(6);
                         byte[] hinhAnh = cursor.getBlob(8);
                         int uaThich = cursor.getInt(7);
                         arrayList.add(new NguoiQH(IDN,SDT,ten,email,fb,diaChi,ngaySinh,hinhAnh,uaThich));

                     }
                     adapter.notifyDataSetChanged();
                     Snackbar.make(getActivity().findViewById(android.R.id.content),"Đã xóa",Snackbar.LENGTH_SHORT).show();
                 }catch (Exception e){

                 }
                    }
                });
                builder.show();
                return true;
            }
        });
    }


    private void anhXa() {
        listView = view.findViewById(R.id.listView);
        txtKQ = view.findViewById(R.id.txtKetQua);
        arrayList = new ArrayList<>();
        adapter = new AdapterNguoiQH(getActivity(),R.layout.adapter_layout_lienhe,arrayList);
        listView.setAdapter(adapter);
        txtKQ.setVisibility(View.INVISIBLE);
    }
    private void getData() {
        Cursor cursor = database.rawQuery("Select *  from NguoiQH",null);
        arrayList.clear();
        try {
            while (cursor.moveToNext()) {
                int IDN = cursor.getInt(0);
                String SDT = cursor.getString(1);
                String ten = cursor.getString(2);
                String email = cursor.getString(3);
                String fb = cursor.getString(4);
                String diaChi = cursor.getString(5);
                String ngaySinh = cursor.getString(6);
                byte[] hinhAnh = cursor.getBlob(8);
                int uaThich = cursor.getInt(7);
                arrayList.add(new NguoiQH(IDN, SDT, ten, email, fb, diaChi, ngaySinh, hinhAnh, uaThich));
            }
            adapter.notifyDataSetChanged();
        }catch (Exception e){
            }
    }
    private void TimKiem() {
        MainActivity.searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                MainActivity.tabHost.setVisibility(View.INVISIBLE);
                MainActivity.viewPager.setVisibility(View.INVISIBLE);
                try {
                    android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
                    android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    FragmentTimKiem fragment = new FragmentTimKiem();
                    fragmentTransaction.replace(R.id.frame, fragment);
                    fragmentTransaction.commit();
                }catch (Exception e){

                }
            }

            @Override
            public void onSearchViewClosed() {
                // khi dong listView se tra lai vi tri nhu cu
                viewPager.setVisibility(View.VISIBLE);
                tabHost.setVisibility(View.VISIBLE);
            }
        });

        //thuc hien lenh search
        MainActivity.searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return true;
            }
        });

    }


}
