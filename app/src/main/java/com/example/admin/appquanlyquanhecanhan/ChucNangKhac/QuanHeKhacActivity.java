package com.example.admin.appquanlyquanhecanhan.ChucNangKhac;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.appquanlyquanhecanhan.Adapter.AdapterQuanHe;
import com.example.admin.appquanlyquanhecanhan.Database.Database;
import com.example.admin.appquanlyquanhecanhan.MainActivity;
import com.example.admin.appquanlyquanhecanhan.Model.MoiQHKhac;
import com.example.admin.appquanlyquanhecanhan.Model.NguoiQH;
import com.example.admin.appquanlyquanhecanhan.R;

import java.util.ArrayList;

public class QuanHeKhacActivity extends AppCompatActivity {
ListView listView;
TextView txtQHKhac;
final  String NameDatabse = "CSDL.sqlite";
FloatingActionButton btnThem;
SQLiteDatabase database;
AdapterQuanHe adapterQuanHe;
int IDN;
ArrayList<MoiQHKhac> arrayList;
Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_he_khac);
        anhXa();
        layDuLieu();
        TaoActionBar();
        xoaDuLieu();
    }

    private void xoaDuLieu() {
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(QuanHeKhacActivity.this);
                builder.setTitle("Xóa liên hệ");
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
                       database.delete("NguoiQHKhac","IDQH=?",new String[]{arrayList.get(position).getIDQH()+""});
                       arrayList.clear();
                       try{
                           Cursor cursor = database.rawQuery("SELECT * FROM NguoiQHKhac",null);
                           while (cursor.moveToNext()){
                               int IDQH = cursor.getInt(0);;
                               String SDT = cursor.getString(2);
                               String ten = cursor.getString(1);
                               String kieuQH = cursor.getString(3);
                               String diaChi = cursor.getString(5);
                               String ngaySinh = cursor.getString(4);
                               arrayList.add(new MoiQHKhac(IDQH,ten,SDT,kieuQH,ngaySinh,diaChi,IDN));
                           }
                           adapterQuanHe.notifyDataSetChanged();
                       }catch (Exception e){

                       }

                    }
                });
                builder.show();
                return true;
            }
        });
    }

    private void TaoActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(QuanHeKhacActivity.this,MainActivity.class));
            }
        });
    }
    private void layDuLieu() {
        Intent intent = getIntent();
        IDN = intent.getIntExtra("IDN",-1);
        Cursor cursor = database.rawQuery("Select *  from NguoiQHKhac Where IDN="+IDN+"",null);
        arrayList.clear();
        try{
            if (cursor.getCount()==0){
                txtQHKhac.setVisibility(View.VISIBLE);
            }else {
                txtQHKhac.setVisibility(View.INVISIBLE);
                while (cursor.moveToNext()) {
                    int idd = cursor.getInt(0);
                    String SDTQH = cursor.getString(2);
                    String ten = cursor.getString(1);
                    String kieuQH = cursor.getString(3);
                    String ngaySinh = cursor.getString(4);
                    String diaChi = cursor.getString(5);
                    arrayList.add(new MoiQHKhac(idd, ten, SDTQH, kieuQH, ngaySinh, diaChi, IDN));
                }
                adapterQuanHe = new AdapterQuanHe(arrayList, QuanHeKhacActivity.this, R.layout.adapter_layout_quanhekhac);
                listView.setAdapter(adapterQuanHe);
            }
        }catch (Exception e){
        }


    }
    private void anhXa() {
        listView = findViewById(R.id.listViewQHKhac);
        txtQHKhac = findViewById(R.id.txtQHK);
        btnThem = findViewById(R.id.fabThem);
        toolbar = findViewById(R.id.tool);
        arrayList = new ArrayList<>();
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuanHeKhacActivity.this, ThemQHKhacActivity.class);
                intent.putExtra("Data",IDN);
                startActivity(intent);
            }
        });
        database = Database.initDatabase(QuanHeKhacActivity.this,NameDatabse);
    }
}
