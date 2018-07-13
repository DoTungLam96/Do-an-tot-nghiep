package com.example.admin.appquanlyquanhecanhan.Fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.admin.appquanlyquanhecanhan.Adapter.AdapterNguoiQH;
import com.example.admin.appquanlyquanhecanhan.Database.Database;
import com.example.admin.appquanlyquanhecanhan.MainActivity;
import com.example.admin.appquanlyquanhecanhan.Model.NguoiQH;
import com.example.admin.appquanlyquanhecanhan.R;
import com.example.admin.appquanlyquanhecanhan.ThongTinChiTietActivity;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

import static com.example.admin.appquanlyquanhecanhan.MainActivity.tabHost;
import static com.example.admin.appquanlyquanhecanhan.MainActivity.viewPager;

/**
 * Created by Admin on 06-Apr-18.
 */

public class FragmentTimKiem extends android.support.v4.app.Fragment {
    public  ArrayList<NguoiQH> arrayList,arr;
    public  AdapterNguoiQH adapter,adapter2;
    ListView listView;
    final static String Database_name="CSDL.sqlite";
    public SQLiteDatabase database;
    TextView txtKQ;
    int ID;
    View view;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_fragment_lienhe,container,false);
        database = Database.initDatabase(getActivity(),Database_name);
        anhXa();
        getData();
        TimKiem();
        SuKienItem();
        return view;
    }

    private void anhXa() {
        listView = view.findViewById(R.id.listView);
        arrayList = new ArrayList<>();
        txtKQ = view.findViewById(R.id.txtKetQua);
        txtKQ.setVisibility(View.INVISIBLE);
        arr = new ArrayList<>();
        adapter = new AdapterNguoiQH(getActivity(),R.layout.adapter_layout_lienhe,arrayList);
        listView.setAdapter(adapter);
    }
    private void getData() {
        try{
            Cursor cursor = database.rawQuery("Select *  from NguoiQH",null);
            arrayList.clear();
            while (cursor.moveToNext()){
                int IDN = cursor.getInt(0);
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
        }catch (Exception e){

        }

    }
    private void TimKiem() {

        MainActivity.searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {
                // khi dong listView se tra lai vi tri nhu cu
                startActivity(new Intent(getActivity(),MainActivity.class));
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
                getDataChange(newText);

                return true;
            }
        });

    }

    public void getDataChange(String textChange) {
        Cursor cursor = database.rawQuery("Select *  from NguoiQH where HoTen like "+"'%"+textChange+"' or SDT like "+"'%"+textChange+"'",null);
        arr.clear();
        while (cursor.moveToNext()){
            int IDN = cursor.getInt(0);
            String SDT = cursor.getString(1);
            String ten = cursor.getString(2);
            String email = cursor.getString(3);
            String fb = cursor.getString(4);
            String diaChi = cursor.getString(5);
            String ngaySinh = cursor.getString(6);
            byte[] hinhAnh = cursor.getBlob(8);
            int uaThich = cursor.getInt(7);
            arr.add(new NguoiQH(IDN,SDT,ten,email,fb,diaChi,ngaySinh,hinhAnh,uaThich));
        }
        adapter2 = new AdapterNguoiQH(getActivity(), R.layout.adapter_layout_lienhe, arr);
        listView.setAdapter(adapter2);
        if (cursor.getCount() == 0) txtKQ.setVisibility(View.VISIBLE);
    }
    private void SuKienItem() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    ID = arr.get(position).getIDN();
                    String sdt = arr.get(position).getSDT();
                    Intent intent = new Intent(getActivity(), ThongTinChiTietActivity.class);
                    intent.putExtra("SDT",sdt);
                    intent.putExtra("IDN",ID);
                    startActivity(intent);
                }catch (Exception e){

                }

            }
        });
    }
}
