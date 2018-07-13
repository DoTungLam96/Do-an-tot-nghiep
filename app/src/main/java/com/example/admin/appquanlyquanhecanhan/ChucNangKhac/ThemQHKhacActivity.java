package com.example.admin.appquanlyquanhecanhan.ChucNangKhac;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.appquanlyquanhecanhan.Database.Database;
import com.example.admin.appquanlyquanhecanhan.MainActivity;
import com.example.admin.appquanlyquanhecanhan.Other.ExitApplication;
import com.example.admin.appquanlyquanhecanhan.R;
import com.example.admin.appquanlyquanhecanhan.ThemLienHe;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ThemQHKhacActivity extends AppCompatActivity {

    EditText edtKieuQH,edtThemSDT,edtDiaChi,edtHoTen;
    TextView edtNgaySinh;
    TextView txtNgaySinh;
    Button btnThem,btnThoat;
    int IDN;
    Toolbar toolbar;
    String Dataname = "CSDL.sqlite";
    SQLiteDatabase database;
    String ngaySinh,hoTen,diaChi,kieuQuanHe,sdtQuanHe;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_qhkhac);
        anhXa();
        layDuLieu();
        LayNgaySinh();
        taoActionBar();
        SuKienClick();

    }

    private void SuKienClick() {
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThemDuLieu();
            }
        });
        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExitApplication.ExitApp(ThemQHKhacActivity.this);
            }
        });
    }

    private void taoActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ThemQHKhacActivity.this,QuanHeKhacActivity.class);
                intent.putExtra("IDN",IDN);
                startActivity(intent);
            }
        });
    }

    private void layDuLieu() {
        Intent intent = getIntent();
        IDN = intent.getIntExtra("Data",-1);
    }
    private void anhXa() {
        edtNgaySinh = findViewById(R.id.edtNgaySinhThem);
        edtHoTen = findViewById(R.id.edtName);
        edtKieuQH = findViewById(R.id.edtKieuQH);
        edtThemSDT = findViewById(R.id.edtSDTThem);
        edtDiaChi = findViewById(R.id.edtDiaChiQH);
        txtNgaySinh = findViewById(R.id.edtNgaySinhThem);
        btnThem = findViewById(R.id.btnThemQH);
        btnThoat = findViewById(R.id.btnThoat);
        toolbar = findViewById(R.id.toolBarQH);
        database = Database.initDatabase(ThemQHKhacActivity.this,Dataname);
    }
    private void ThemDuLieu() {
        try{
            if (edtThemSDT.getText().toString().equals("") || edtHoTen.getText().toString().equals("")){
                Snackbar.make(findViewById(android.R.id.content),"Mời nhập họ tên và SĐT !",Snackbar.LENGTH_SHORT).show();
            }
            else {
                sdtQuanHe = edtThemSDT.getText().toString();
                hoTen = edtHoTen.getText().toString();
                ngaySinh = edtNgaySinh.getText().toString();
                diaChi = edtDiaChi.getText().toString();
                kieuQuanHe = edtKieuQH.getText().toString();
                ContentValues contentValues = new ContentValues();
                contentValues.put("SDTQH",sdtQuanHe);
                contentValues.put("HoTen",hoTen);
                contentValues.put("KieuQH",kieuQuanHe);
                contentValues.put("DiaChi",diaChi);
                contentValues.put("NgaySinh",ngaySinh);
                contentValues.put("IDN",IDN);
                database.insert("NguoiQHKhac",null,contentValues);
                Snackbar.make(findViewById(android.R.id.content),"Dữ liệu đã thêm thành công",Snackbar.LENGTH_SHORT).show();
            }
        }catch (Exception ex){
            Snackbar.make(findViewById(android.R.id.content),"Thất bại,mời bạn kiểm tra lại SĐT hoặc thông tin khác",Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStop() {

        super.onStop();
    }
    private void LayNgaySinh() {
        edtNgaySinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int ngay = calendar.get(Calendar.DATE);
                int thang = calendar.get(Calendar.MONTH);
                int nam = calendar.get(Calendar.YEAR);
                DatePickerDialog pickerDialog = new DatePickerDialog(ThemQHKhacActivity.this, new DatePickerDialog.OnDateSetListener() {
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
}
