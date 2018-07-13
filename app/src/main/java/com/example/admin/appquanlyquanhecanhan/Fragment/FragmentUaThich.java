package com.example.admin.appquanlyquanhecanhan.Fragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.admin.appquanlyquanhecanhan.Adapter.AdapterNguoiQH;
import com.example.admin.appquanlyquanhecanhan.Database.Database;
import com.example.admin.appquanlyquanhecanhan.MainActivity;
import com.example.admin.appquanlyquanhecanhan.Model.NguoiQH;
import com.example.admin.appquanlyquanhecanhan.R;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;

import static com.example.admin.appquanlyquanhecanhan.MainActivity.tabHost;
import static com.example.admin.appquanlyquanhecanhan.MainActivity.viewPager;

/**
 * Created by Admin on 05-Apr-18.
 */

public class FragmentUaThich extends Fragment {
    public ArrayList<NguoiQH> arrayList;
    public AdapterNguoiQH adapter;
    public ListView listView;
    TextView txtKQ;
    final static String Database_name="CSDL.sqlite";
    public SQLiteDatabase database;
    View view;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_fragment_lienhe,container,false);
        database = Database.initDatabase(getActivity(),Database_name);
        anhXa();
        getData();
        TimKiem();
        return view;
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
        try{
            Cursor cursor = database.rawQuery("select * from NguoiQH where UaThich = '1' ",null);
            while (cursor.moveToNext()){
                int IDN = cursor.getInt(0);
                String ten = cursor.getString(2);
                String SDT = cursor.getString(1);
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
                MainActivity.tabHost.setVisibility(View.INVISIBLE);
                MainActivity.viewPager.setVisibility(View.INVISIBLE);
                android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                FragmentTimKiem fragment = new FragmentTimKiem();
                fragmentTransaction.replace(R.id.frame, fragment);
                fragmentTransaction.commit();
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
