package com.example.admin.appquanlyquanhecanhan;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.appquanlyquanhecanhan.Database.Database;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;
import dmax.dialog.SpotsDialog;

public class ThemLienHe extends AppCompatActivity {
   ImageButton btnFolder,btnCamera;
   EditText edtTen,edtSDT,edtEmail,edtFaceBook,edtDiaChi;
   TextView edtNgaySinh;
   FloatingActionButton btnLike;
   int UaThich=0;
   CircleImageView imgHinhDaiDien;
   int CODE_FOLDER =10 ,CODE_CHUP=11;
   String ngaySinh,hoTen,FB,email,diaChi;
   String SDT,check;
   ArrayList<String> listCheck;
   Toolbar toolbar;
   boolean isClick = true;
    public SQLiteDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_lien_he);
        anhXa();
        TaoActionBar();
        LayNgaySinh();
        LayAnh();
        SuKienActionButton();

    }

    private void ThemDuLieu() {
          try {
              if (edtSDT.getText().toString().equals("") || edtTen.getText().toString().equals("")){
                  Snackbar.make(findViewById(android.R.id.content),"Mời nhập họ tên và SĐT !",Snackbar.LENGTH_SHORT).show();
              }
              else {
                      SDT = edtSDT.getText().toString();
                      hoTen = edtTen.getText().toString();
                      ngaySinh = edtNgaySinh.getText().toString();
                      FB = edtFaceBook.getText().toString();
                      email = edtEmail.getText().toString();
                      diaChi = edtDiaChi.getText().toString();
                      byte[] hinh = ChuyenDoiAnh(imgHinhDaiDien);
                      ContentValues contentValues = new ContentValues();
                      contentValues.put("SDT", SDT);
                      contentValues.put("HoTen", hoTen);
                      contentValues.put("Email", email);
                      contentValues.put("Facebook", FB);
                      contentValues.put("DiaChi", diaChi);
                      contentValues.put("NgaySinh", ngaySinh);
                      contentValues.put("UaThich", UaThich);
                      contentValues.put("HinhAnh", hinh);
                      database.insert("NguoiQH", null, contentValues);
                      Snackbar.make(findViewById(android.R.id.content), "Dữ liệu đã thêm thành công", Snackbar.LENGTH_SHORT).show();
              }
           }catch (Exception e)
          {

          }
    }

    private void LayAnh() {
        btnFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,CODE_FOLDER);
            }
        });
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,CODE_CHUP);
            }
        });
    }

    private void SuKienActionButton() {
        btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isClick == true) {
                    btnLike.setImageResource(R.drawable.star);
                    btnLike.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.bg)));
                    Toast.makeText(ThemLienHe.this, "Đã thích", Toast.LENGTH_SHORT).show();
                    UaThich = 1;
                    isClick = false;

                }else {
                    btnLike.setImageResource(R.drawable.unstarpng);
                    btnLike.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.bg2)));
                    Toast.makeText(ThemLienHe.this, "Bỏ thích", Toast.LENGTH_SHORT).show();
                    UaThich = 0;
                    isClick = true;
                }
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
                DatePickerDialog pickerDialog = new DatePickerDialog(ThemLienHe.this, new DatePickerDialog.OnDateSetListener() {
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

    private void TaoActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ThemLienHe.this,MainActivity.class));
            }
        });
    }

    private void anhXa() {
        imgHinhDaiDien = findViewById(R.id.imgHinhDaiDien);
        btnFolder = findViewById(R.id.btnFolder);
        btnCamera = findViewById(R.id.btnCamera);
        edtTen = findViewById(R.id.edtTen);
        edtSDT = findViewById(R.id.edtSDT);
        edtEmail = findViewById(R.id.edtEmail);
        edtFaceBook = findViewById(R.id.edtFacebook);
        edtDiaChi = findViewById(R.id.edtDiaChi);
        btnLike = findViewById(R.id.btnLike);
        listCheck = new ArrayList<>();
        edtNgaySinh = findViewById(R.id.edtNgaySinh);
        toolbar = findViewById(R.id.toolBar_themLH);
        database = Database.initDatabase(ThemLienHe.this,MainActivity.Database_name);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_update, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_save :{
                ThemDuLieu();
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
                finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == CODE_CHUP && resultCode == RESULT_OK){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imgHinhDaiDien.setImageBitmap(bitmap);
        }
        if (requestCode == CODE_FOLDER && resultCode == RESULT_OK){

            Uri  uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgHinhDaiDien.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public byte[] ChuyenDoiAnh(ImageView img){
        BitmapDrawable bitmapDrawable = (BitmapDrawable) img.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
       bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
       byte[] hinh = outputStream.toByteArray();
       return hinh;
    }
}
