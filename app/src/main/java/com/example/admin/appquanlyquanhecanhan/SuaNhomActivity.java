package com.example.admin.appquanlyquanhecanhan;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.admin.appquanlyquanhecanhan.Database.Database;
import com.example.admin.appquanlyquanhecanhan.Model.Nhom;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SuaNhomActivity extends AppCompatActivity {
Button btna,btnb,btnc;
CircleImageView img;
EditText edt;
int ID;
    byte[] hinh;
    List<Nhom> list;
String namedatabase= "CSDL.sqlite";
SQLiteDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_nhom);
        anhXa();
        getDuLieu();
        layGT();
        suKienClick();

    }

    private void suKienClick() {
        btna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,2);
            }
        });
        btnb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                suaNhom();
            }
        });
        btnc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SuaNhomActivity.this,MainActivity.class));
            }
        });
    }

    private void layGT() {
        try{
            Cursor cursor = database.rawQuery("SELECT * FROM Nhom WHERE IDNhom="+ID,null);
            while (cursor.moveToNext()){
                int id = cursor.getInt(0);
                String ten = cursor.getString(1);
                hinh = cursor.getBlob(2);
                edt.setText(ten);
                Bitmap bitmap = BitmapFactory.decodeByteArray(hinh,0,hinh.length);
                img.setImageBitmap(bitmap);
            }

        }catch (Exception e){

        }
    }

    private void suaNhom() {
        String ten = edt.getText().toString();
        try {
            if (ten.equals("")){
                Toast.makeText(this, "Mời nhập tên nhóm", Toast.LENGTH_SHORT).show();
            }else {
                byte[] hinh = ChuyenAnh(img);
                ContentValues contentValues = new ContentValues();
                contentValues.put("TenNhom",ten);
                contentValues.put("Icon",hinh);
                database.update("Nhom",contentValues,"IDNhom=?",new String[]{ID+""});
                Snackbar.make(findViewById(android.R.id.content),"Đã sửa thành công",Snackbar.LENGTH_SHORT).show();
            }
        }catch (Exception e){

        }

    }

    private void getDuLieu() {
        Intent intent = getIntent();
        ID = intent.getIntExtra("IDNhom",-1);
        Log.e("ID",ID+"");
    }

    private void anhXa() {
        btna = findViewById(R.id.btna);
        btnb = findViewById(R.id.btnb);
        btnc = findViewById(R.id.btnc);
        img = findViewById(R.id.imgSuaNhom);
        edt = findViewById(R.id.edt);
        list = new ArrayList<>();
        database = Database.initDatabase(SuaNhomActivity.this,namedatabase);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 2 && resultCode == RESULT_OK){
            Uri uri = data.getData();
            Picasso.with(this).load(uri).into(img);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private byte[] ChuyenAnh(ImageView imageView){
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] hinhAnh = stream.toByteArray();
        return hinhAnh;
    }
}
