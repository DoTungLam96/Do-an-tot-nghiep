package com.example.admin.appquanlyquanhecanhan.ChucNangKhac;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.appquanlyquanhecanhan.Database.Database;
import com.example.admin.appquanlyquanhecanhan.Other.ExitApplication;
import com.example.admin.appquanlyquanhecanhan.R;
import com.example.admin.appquanlyquanhecanhan.ThemLienHe;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SuaQHKhacActivity extends AppCompatActivity {

    EditText edtKieuQH,edtThemSDT,edtDiaChi,edtHoTen;
    TextView edtNgaySinh;
    TextView txtNgaySinh;
    Button btnSua,btnThoat;
    int ID,IDN;
    Toolbar toolbar;
    String Dataname = "CSDL.sqlite";
    SQLiteDatabase database;
    String ngaySinh,hoTen,diaChi,kieuQuanHe,sdtQuanHe;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_qhkhac);
        anhXa();
        layDuLieu();
        LayNgaySinh();
        taoActionBar();
       btnSua.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               chinhSua();
           }
       });
       btnThoat.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               ExitApplication.ExitApp(SuaQHKhacActivity.this);
           }
       });
    }

    private void taoActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SuaQHKhacActivity.this,QuanHeKhacActivity.class);
                intent.putExtra("IDN",IDN);
                startActivity(intent);
            }
        });
    }
    private void LayNgaySinh() {
        edtNgaySinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int ngay = calendar.get(Calendar.DATE);
                int thang = calendar.get(Calendar.MONTH);
                int nam = calendar.get(Calendar.YEAR);
                DatePickerDialog pickerDialog = new DatePickerDialog(SuaQHKhacActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        calendar.set(i,i1,i2);
                        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                        edtNgaySinh.setText(format.format(calendar.getTime()));

                    }
                },nam,thang,ngay);

                pickerDialog.show();
            }
        });
    }
    private void chinhSua() {
        try {
            String SDTQH = edtThemSDT.getText().toString();
            String hoTen = edtHoTen.getText().toString();
            String diaChi = edtDiaChi.getText().toString();
            String ngaySinh = edtNgaySinh.getText().toString();
            String kieuQH = edtKieuQH.getText().toString();
            if (SDTQH.equals("") || hoTen.equals("")){
                Snackbar.make(findViewById(android.R.id.content),"Mời nhập họ tên và SĐT !",Snackbar.LENGTH_SHORT).show();
            }else {
                ContentValues contentValues = new ContentValues();
                contentValues.put("HoTen",hoTen);
                contentValues.put("SDTQH",SDTQH);
                contentValues.put("KieuQH",kieuQH);
                contentValues.put("NgaySinh",ngaySinh);
                contentValues.put("DiaChi",diaChi);
                contentValues.put("IDN",IDN);
                database.update("NguoiQHKhac",contentValues,"IDQH=?",new String[]{ID+""});
                Snackbar.make(findViewById(android.R.id.content),"Chỉnh sửa thành công !",Snackbar.LENGTH_SHORT).show();
            }
        }catch (Exception e){

        }
    }

    private void layDuLieu() {
        Intent intent = getIntent();
        ID = intent.getIntExtra("IDQH",-1);
        IDN = intent.getIntExtra("IDN",-1);
     try {
         Cursor cursor = database.rawQuery("SELECT * FROM NguoiQHKhac WHERE IDQH="+ID,null);
         while (cursor.moveToNext()){
             hoTen = cursor.getString(1);
             sdtQuanHe = cursor.getString(2);
             kieuQuanHe = cursor.getString(3);
             ngaySinh = cursor.getString(4);
             diaChi = cursor.getString(5);
             edtHoTen.setText(hoTen);
             edtThemSDT.setText(sdtQuanHe);
             edtKieuQH.setText(kieuQuanHe);
             edtNgaySinh.setText(ngaySinh);
             edtDiaChi.setText(diaChi);
         }
     }catch (Exception e){

     }

    }

    private void anhXa() {
        edtNgaySinh = findViewById(R.id.edtNgaySinhQHK);
        edtHoTen = findViewById(R.id.edtNameQHK);
        edtKieuQH = findViewById(R.id.edtKieuQHK);
        edtThemSDT = findViewById(R.id.edtSDTQHK);
        edtDiaChi = findViewById(R.id.edtDiaChiQHK);
        txtNgaySinh = findViewById(R.id.edtNgaySinhQHK);
        btnSua = findViewById(R.id.btnSuaQHK);
        btnThoat = findViewById(R.id.btnThoatQHK);
        toolbar = findViewById(R.id.toolBarQHK);
        database = Database.initDatabase(SuaQHKhacActivity.this,Dataname);
    }
}
