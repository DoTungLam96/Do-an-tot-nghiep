package com.example.admin.appquanlyquanhecanhan;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.appquanlyquanhecanhan.Database.Database;
import com.example.admin.appquanlyquanhecanhan.Other.ExitApplication;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class SuaActivity extends AppCompatActivity {

    EditText edtTen,edtSDT,edtEmail,edtFaceBook,edtDiaChi;
    TextView edtNgaySinh;
    CircleImageView imgHinhDaiDien;
    ImageButton btnSuaCamera,btnSuaFolder;
    FloatingActionButton btnSuaLike;
    Toolbar toolbar;
    int IDN;
    int uaThich;
    ArrayList<String> listCheck;
    int CODE_FOLDER = 2,CODE_CAMERA=3;
    SQLiteDatabase database;
    boolean isClick = true;
    String DATABASE_NAME = "CSDL.sqlite";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua);
        anhXa();
        taoActionBar();
        layDuLieu();
        LayNgaySinh();
        suaKienClick();
        chonHinhAnh();

    }

    private void chonHinhAnh() {
       btnSuaFolder.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(Intent.ACTION_PICK);
               intent.setType("image/*");
               startActivityForResult(intent,CODE_FOLDER);
           }
       });
       btnSuaCamera.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
               startActivityForResult(intent,CODE_CAMERA);
           }
       });
    }

    private void chinhSua() {
        try {
            if (edtSDT.getText().toString().equals("") || edtTen.getText().toString().equals("")){
                Snackbar.make(findViewById(android.R.id.content),"Mời nhập họ tên và SĐT !",Snackbar.LENGTH_SHORT).show();
            }
            else {
                    String SDT = edtSDT.getText().toString();
                    String hoTen = edtTen.getText().toString();
                    String email = edtEmail.getText().toString();
                    String fb = edtFaceBook.getText().toString();
                    String diaChi = edtDiaChi.getText().toString();
                    String ngaySinh = edtNgaySinh.getText().toString();
                    byte[] hinh = ChuyenDoiAnh(imgHinhDaiDien);
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("SDT",SDT);
                    contentValues.put("HoTen",hoTen);
                    contentValues.put("Email",email);
                    contentValues.put("Facebook",fb);
                    contentValues.put("DiaChi",diaChi);
                    contentValues.put("NgaySinh",ngaySinh);
                    contentValues.put("UaThich",uaThich);
                    contentValues.put("HinhAnh",hinh);
                    database.update("NguoiQH",contentValues,"IDN=?",new String[]{IDN+""});
                Toast.makeText(this, "Dữ liệu đã được sửa", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(SuaActivity.this,MainActivity.class));
            }
        }catch (Exception e)
        {

        }


    }


    private void suaKienClick() {
        btnSuaLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isClick == true) {
                    btnSuaLike.setImageResource(R.drawable.star);
                    btnSuaLike.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.bg)));
                    uaThich = 1;
                    Toast.makeText(SuaActivity.this, "Thích", Toast.LENGTH_SHORT).show();
                    isClick = false;
                }else {
                    btnSuaLike.setImageResource(R.drawable.unstarpng);
                    btnSuaLike.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.bg2)));
                    Toast.makeText(SuaActivity.this, "Bỏ thích", Toast.LENGTH_SHORT).show();
                    uaThich = 0;
                    isClick = true;
                }
            }
        });

    }

    private void layDuLieu() {
      Intent intent = getIntent();
      IDN = intent.getIntExtra("IDN",-1);
        Cursor cursor = database.rawQuery("SELECT * FROM NguoiQH WHERE IDN ="+IDN,null);
        try {
            while (cursor.moveToNext()){
                int ID = cursor.getInt(0);
                String SDT = cursor.getString(1);
                String hoTen = cursor.getString(2);
                String email = cursor.getString(3);
                String faceBook = cursor.getString(4);
                String diaChi = cursor.getString(5);
                String ngaySinh = cursor.getString(6);
                uaThich = cursor.getInt(7);
                byte[] hinhAnh = cursor.getBlob(8);
                edtSDT.setText(SDT);
                edtTen.setText(hoTen);
                edtEmail.setText(email);
                edtFaceBook.setText(faceBook);
                edtDiaChi.setText(diaChi);
                edtNgaySinh.setText(ngaySinh);
                Bitmap bitmap = BitmapFactory.decodeByteArray(hinhAnh,0,hinhAnh.length);
                imgHinhDaiDien.setImageBitmap(bitmap);
            }
            if (uaThich==1){
                btnSuaLike.setImageResource(R.drawable.star);
                btnSuaLike.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.bg)));
            }else {
                btnSuaLike.setImageResource(R.drawable.unstarpng);
                btnSuaLike.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.bg2)));
            }
        }catch (Exception e){

        }

    }

    private void taoActionBar() {
       setSupportActionBar(toolbar);
       getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       toolbar.setNavigationOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
              finish();
           }
       });
    }

    private void anhXa() {
       edtTen = findViewById(R.id.edtSuaTen);
       edtSDT = findViewById(R.id.edtSuaSDT);
       edtEmail = findViewById(R.id.edtSuaEmail);
       edtFaceBook = findViewById(R.id.edtSuaFacebook);
       edtDiaChi = findViewById(R.id.edtSuaDiaChi);
       edtNgaySinh = findViewById(R.id.edtSuaNgaySinh);
       imgHinhDaiDien = findViewById(R.id.imgSuaHinhDaiDien);
       btnSuaCamera = findViewById(R.id.btnSuaCamera);
       btnSuaFolder = findViewById(R.id.btnSuaFolder);
       toolbar = findViewById(R.id.toolBar_a);
       btnSuaLike = findViewById(R.id.btnSuaLike);
       listCheck = new ArrayList<>();
       database = Database.initDatabase(SuaActivity.this,DATABASE_NAME);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CODE_FOLDER && resultCode == RESULT_OK){
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgHinhDaiDien.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
        if (requestCode == CODE_CAMERA && resultCode == RESULT_OK){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imgHinhDaiDien.setImageBitmap(bitmap);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private byte[] ChuyenDoiAnh(ImageView imageView){
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,outputStream);
        byte[] hinhAnh = outputStream.toByteArray();
        return hinhAnh;
    }
    private void LayNgaySinh() {
        edtNgaySinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int ngay = calendar.get(Calendar.DATE);
                int thang = calendar.get(Calendar.MONTH);
                int nam = calendar.get(Calendar.YEAR);
                DatePickerDialog pickerDialog = new DatePickerDialog(SuaActivity.this, new DatePickerDialog.OnDateSetListener() {
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_update, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_save :{
                chinhSua();
            }break;
            case R.id.menu_clearEditText:{
                edtDiaChi.setText("");
                edtTen.setText("");
                edtFaceBook.setText("");
                edtSDT.setText("");
                edtEmail.setText("");
                edtNgaySinh.setText("");
            }break;
            case R.id.menu_exit: {
                ExitApplication.ExitApp(SuaActivity.this);
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
