package com.example.admin.appquanlyquanhecanhan;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.CallLog;
import android.provider.Telephony;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.appquanlyquanhecanhan.Adapter.AdapterCuocGoi;
import com.example.admin.appquanlyquanhecanhan.Adapter.AdapterNhom;
import com.example.admin.appquanlyquanhecanhan.Adapter.AdapterTinNhan;
import com.example.admin.appquanlyquanhecanhan.ChucNangKhac.QuanHeKhacActivity;
import com.example.admin.appquanlyquanhecanhan.Database.Database;
import com.example.admin.appquanlyquanhecanhan.Fragment.FragmentLienHe;
import com.example.admin.appquanlyquanhecanhan.Model.CuocGoi;
import com.example.admin.appquanlyquanhecanhan.Model.Nhom;
import com.example.admin.appquanlyquanhecanhan.Model.TinNhan;
import com.example.admin.appquanlyquanhecanhan.Notification.MyBroadcastReceiver;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
import dmax.dialog.SpotsDialog;

public class ThongTinChiTietActivity extends AppCompatActivity {
    ImageButton rowDown_a,rowDown_b;
    LinearLayout linearLayout;
    Animation animation_a,animation_b,animation_c;
    CircleImageView circleImageView;
    ImageButton btnGmail,btnFB,btnTN,btnGoi;
    int IDN;
    String SDT,ngaySinh,hoTen,diaChi,faceBook,email,SDTnhantin,noiDung,ngayNhanTin;
    SQLiteDatabase database;
    byte[] hinhAnh;
    ArrayList<CuocGoi> arrayListGoi;
    ArrayList<TinNhan> list;
    ArrayList<TinNhan> arrayListTN,arrayListGuiDi;
    AdapterTinNhan adapterTinNhan;
    AdapterCuocGoi adapterCuocGoi;
    Toolbar toolbar;
    ListView listTN,listCG,listTNNhan;
    SimpleDateFormat simpleDateFormat;
    AdapterNhom adapterNhom;
    ArrayList<Nhom> nhoms;
    ListView listView;
    int uaThich;
    int idNhom;
    TextView txtSDT,txtDiaChi,txtTen,txtNgaySinh,txtFB,txtEmail,txtThemGroup,txtSDTNT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_chi_tiet);
        anhXa();
        taoActionBar();
        setAnimation();
        HienDuLieu();
        layDuLieu();
        XoaDataTinNhan();
        deleteCuocGoi();
        LayDuLieuNhanTinGuiDen();
        LayDuLieuNhanTinGuiDi();
        ThongTinCuocGoi(this);
        getData();
        SuKienButton();
        themVaoGroup();
    }

    private void themVaoGroup() {
        txtThemGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nhoms = new ArrayList<>();
                final Dialog dialog = new Dialog(ThongTinChiTietActivity.this);
                dialog.getWindow().getAttributes().windowAnimations = R.style.animDialog;
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_layout_spinder);
                dialog.setTitle("Chọn nhóm");
                listView = dialog.findViewById(R.id.listViewSpiner);
                try {
                    Cursor cursor = database.rawQuery("SELECT * FROM Nhom",null);
                    while (cursor.moveToNext()){
                        int id = cursor.getInt(0);
                        String tenNhom = cursor.getString(1);
                        byte[] hinhAnh = cursor.getBlob(2);

                        nhoms.add(new Nhom(id,tenNhom,hinhAnh));
                    }
                    adapterNhom = new AdapterNhom(ThongTinChiTietActivity.this,nhoms,R.layout.adapter_layout_nhom);
                    listView.setAdapter(adapterNhom);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            idNhom = nhoms.get(position).getIDNhom();
                            insertVaoNhom();
                            dialog.cancel();
                            Snackbar.make(findViewById(android.R.id.content),"Đã thêm vào nhóm",Snackbar.LENGTH_SHORT).show();

                        }
                    });
                }catch (Exception e){

                }
                dialog.show();
            }
        });
    }

    private void insertVaoNhom() {
      try{
          ContentValues contentValues = new ContentValues();
          contentValues.put("SDT",SDT);
          contentValues.put("HoTen",hoTen);
          contentValues.put("Email",email);
          contentValues.put("Facebook",faceBook);
          contentValues.put("DiaChi",diaChi);
          contentValues.put("NgaySinh",ngaySinh);
          contentValues.put("UaThich",uaThich);
          contentValues.put("HinhAnh",hinhAnh);
          contentValues.put("IDNhom",idNhom);
          database.update("NguoiQH",contentValues,"IDN=?",new String[]{IDN+""});
      }catch (Exception e){

      }

    }

    private void SuKienButton() {
        Intent intent = getIntent();
        final String email = intent.getStringExtra("Email");
        final String fb = intent.getStringExtra("FB");
            btnGoi.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("MissingPermission")
                @Override
                public void onClick(View v) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:"+SDT));
                    startActivity(callIntent);
                }
            });
            btnTN.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("MissingPermission")
                @Override
                public void onClick(View v) {
                    Uri uri = Uri.parse("smsto:"+SDT);
                    Intent nhanTin = new Intent(Intent.ACTION_SENDTO, uri);
                    nhanTin.putExtra("sms_body", "");
                    startActivity(nhanTin);
                }
            });
            btnGmail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try{
                        Intent intent = new Intent (Intent.ACTION_VIEW , Uri.parse("mailto:" +email+""));
                        intent.putExtra(Intent.EXTRA_SUBJECT, "");
                        intent.putExtra(Intent.EXTRA_TEXT, "");
                        startActivity(intent);
                    }catch(ActivityNotFoundException e){
                        //TODO smth
                    }
                }
            });
            btnFB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {

                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://"+fb));
                        startActivity(browserIntent);
                    }catch (Exception ex){
                        Toast.makeText(ThongTinChiTietActivity.this, "Mời nhập đúng đường dẫn tới website", Toast.LENGTH_LONG).show();
                    }
                }
            });
    }

    private void XoaDataTinNhan() {
        database.delete("TinNhan",null,null);
    }
    //tin nhan
    private void LayDuLieuNhanTinGuiDi() {
        String SDTnhantin = null;
        Uri myMessage = Uri.parse("content://sms/sent");
        ContentResolver cr = this.getContentResolver();
        Cursor c = cr.query(myMessage, new String[]{"_id", "address", "date",
                "body", "read"}, null, null, Telephony.Sms.DATE + " DESC LIMIT 100");
        startManagingCursor(c);

        arrayListGuiDi.clear();
        try {
            while (c.moveToNext()) {
                int id = c.getInt(c.getColumnIndex("_id"));
                SDTnhantin = c.getString(c.getColumnIndex("address"));
                long ngay = (c.getLong(2));
                String ngayNhanTin = simpleDateFormat.format(new Date(ngay));
                String noiDung = c.getString(c.getColumnIndex("body"));
                arrayListGuiDi.add(new TinNhan(id,SDTnhantin,ngayNhanTin,noiDung,IDN));
            }
            list.clear();
            String sdtt = SDT.replace("0","+84");
            for (int i = 0 ;i<arrayListGuiDi.size();i++){

                if (arrayListGuiDi.get(i).getSDT().toString().equals(sdtt)){

                    String SDT = arrayListGuiDi.get(i).getSDT();
                    list.add(new TinNhan(arrayListGuiDi.get(i).getIDTN(),SDT,arrayListGuiDi.get(i).getNgay(),
                            arrayListGuiDi.get(i).getNoiDung(),IDN
                    ));
                }

            }
            adapterTinNhan = new AdapterTinNhan(list,R.layout.adapter_layout_tinnhan,ThongTinChiTietActivity.this);
            listTNNhan.setAdapter(adapterTinNhan);
        }catch (Exception e){

        }

    }
    private void LayDuLieuNhanTinGuiDen() {

        Uri myMessage = Uri.parse("content://sms/inbox");
        ContentResolver cr = this.getContentResolver();
        Cursor c = cr.query(myMessage, new String[]{"_id", "address", "date",
                "body", "read"}, null, null, Telephony.Sms.Inbox.DATE + " DESC LIMIT 100");
        startManagingCursor(c);

        while (c.moveToNext()) {
            long ngay = (c.getLong(2));
            SDTnhantin = c.getString(c.getColumnIndex("address")).replace("+84", "0");
            ngayNhanTin = simpleDateFormat.format(new Date(ngay));
            noiDung = c.getString(c.getColumnIndex("body"));
            ContentValues contentValues = new ContentValues();
            contentValues.put("SDT", SDTnhantin);
            contentValues.put("Ngay", ngayNhanTin);
            contentValues.put("NoiDung", noiDung);
            contentValues.put("IDN",IDN);
            database.insert("TinNhan", null, contentValues);
        }
        Cursor cursor = database.rawQuery("SELECT * FROM TinNhan WHERE SDT='"+SDT+"'",null);
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String sdt = cursor.getString(1);
            String ngay = cursor.getString(2);
            String noidung = cursor.getString(3);
            arrayListTN.add(new TinNhan(id,sdt,ngay,noidung,IDN));
        }
        adapterTinNhan = new AdapterTinNhan(arrayListTN,R.layout.adapter_layout_tinnhan,ThongTinChiTietActivity.this);
        listTN.setAdapter(adapterTinNhan);
    }
    // Cuoc goi
    private void deleteCuocGoi() {
        database.delete("CuocGoi",null,null);
    }
    public void ThongTinCuocGoi(Context context) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            @SuppressLint("MissingPermission") Cursor cursor = context.getContentResolver().query(CallLog.Calls.CONTENT_URI,
                    new String[]{CallLog.Calls.NUMBER, CallLog.Calls.DATE, CallLog.Calls.DURATION, CallLog.Calls.TYPE}, null, null, CallLog.Calls.DATE + " DESC LIMIT 100");
            int number = cursor.getColumnIndex(CallLog.Calls.NUMBER);
            int type = cursor.getColumnIndex(CallLog.Calls.TYPE);
            int date = cursor.getColumnIndex(CallLog.Calls.DATE);
            int duration = cursor.getColumnIndex(CallLog.Calls.DURATION);
            while (cursor.moveToNext()) {
                String phNumber = cursor.getString(number);
                String callType = cursor.getString(type);
                String callDate = cursor.getString(date);
                String callDayTime = dateFormat.format(new Date(Long.valueOf(callDate)));
                String callDuration = cursor.getString(duration);
                String tinhTrang = null;
                int dircode = Integer.parseInt(callType);
                switch (dircode) {
                    case CallLog.Calls.OUTGOING_TYPE:
                        tinhTrang = "Gọi đi";
                        break;
                    case CallLog.Calls.INCOMING_TYPE:
                        tinhTrang = "Gọi đến";
                        break;

                    case CallLog.Calls.MISSED_TYPE:
                        tinhTrang = "Gọi nhỡ";
                        break;
                }
                ContentValues contentValues = new ContentValues();
                contentValues.put("SDT", phNumber);
                contentValues.put("Ngay", callDayTime);
                contentValues.put("TinhTrang", tinhTrang);
                contentValues.put("ThoiLuong", callDuration);
                contentValues.put("IDN",IDN);
                database.insert("CuocGoi", null, contentValues);
            }
            cursor.close();

    }
    private void getData() {
        Cursor cursor = database.rawQuery("SELECT * FROM CuocGoi WHERE SDT='"+SDT+"'",null);
        arrayListGoi.clear();
        while (cursor.moveToNext()){
            int ID = cursor.getInt(0);
            String SDT = cursor.getString(1);
            String thoiLuong = cursor.getString(4);
            String Ngay = cursor.getString(2);
            String TinhTrang = cursor.getString(3);
            arrayListGoi.add(new CuocGoi(ID,SDT,Ngay,TinhTrang,thoiLuong,IDN));
        }
        adapterCuocGoi = new AdapterCuocGoi(arrayListGoi,ThongTinChiTietActivity.this,R.layout.adapter_layout_cuocgoi);
        listCG.setAdapter(adapterCuocGoi);
    }
    // hien du lieu
    private void HienDuLieu() {
        rowDown_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rowDown_a.clearAnimation();
                rowDown_b.clearAnimation();
                rowDown_a.setVisibility(View.INVISIBLE);
                rowDown_b.setVisibility(View.INVISIBLE);
                linearLayout.setVisibility(View.VISIBLE);
                linearLayout.setAnimation(animation_c);

            }
        });

    }
    private void taoActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ThongTinChiTietActivity.this,MainActivity.class));
            }
        });
    }
    private void setAnimation() {
        animation_a = AnimationUtils.loadAnimation(ThongTinChiTietActivity.this,R.anim.anim_down);
        rowDown_a.startAnimation(animation_a);
        animation_b = AnimationUtils.loadAnimation(ThongTinChiTietActivity.this,R.anim.anim_down);
        rowDown_b.startAnimation(animation_b);
        animation_c = AnimationUtils.loadAnimation(ThongTinChiTietActivity.this,R.anim.ani_frame);

    }
    private void anhXa() {
        linearLayout = findViewById(R.id.linear);
        rowDown_a = findViewById(R.id.rowDown_a);
        rowDown_b = findViewById(R.id.rowDown_b);
        linearLayout.setVisibility(View.INVISIBLE);
        circleImageView = findViewById(R.id.imgHinhChiTiet);
        btnFB = findViewById(R.id.btnFBB);
        toolbar = findViewById(R.id.toolBarThongTin);
        btnGmail = findViewById(R.id.btnGmaill);
        btnGoi = findViewById(R.id.btnGoiDienn);
        btnTN = findViewById(R.id.btnTinNhann);
        txtDiaChi = findViewById(R.id.txtChiTetDiaChi);
        txtSDT = findViewById(R.id.txtChiTietSDT);
        txtTen = findViewById(R.id.txtChiTietHoTen);
        txtNgaySinh = findViewById(R.id.txtChiTietNS);
        txtFB = findViewById(R.id.txtChiTietFB);
        txtSDTNT = findViewById(R.id.txtSDTT);
        txtEmail = findViewById(R.id.txtChiTietEmail);
        txtThemGroup = findViewById(R.id.txtThemGroup);
        listTN = findViewById(R.id.listViewTinNhan);
        list = new ArrayList<>();
        arrayListGoi = new ArrayList<>();
        listTNNhan = findViewById(R.id.listViewTinNhanNhan);
        listCG = findViewById(R.id.listViewCuocGoi);
        arrayListGuiDi = new ArrayList<>();
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        database = Database.initDatabase(ThongTinChiTietActivity.this,MainActivity.Database_name);
        arrayListTN = new ArrayList<>();


//        FragmentLienHe.alertDialog.dismiss();
    }
    private void layDuLieu() {
        Intent intent = getIntent();
        IDN = intent.getIntExtra("IDN",-1);
        Log.e("IDN",IDN+"");
        SDT = intent.getStringExtra("SDT");
        Cursor cursor = database.rawQuery("Select * from NguoiQH where IDN='"+IDN+"'",null);
        while (cursor.moveToNext()){
             hoTen = cursor.getString(2);
             email = cursor.getString(3);
             faceBook = cursor.getString(4);
             diaChi = cursor.getString(5);
             ngaySinh = cursor.getString(6);
             hinhAnh = cursor.getBlob(8);
             uaThich = cursor.getInt(7);
        }
        try{
            txtSDTNT.setText(SDT );
            txtSDT.setText("SĐT: "+SDT);
            txtTen.setText(hoTen+"");
            txtNgaySinh.setText("Ngày sinh: "+ngaySinh);
            txtDiaChi.setText("Địa chỉ: "+diaChi);
            Bitmap bitmap = BitmapFactory.decodeByteArray(hinhAnh,0,hinhAnh.length);
            circleImageView.setImageBitmap(bitmap);
            txtEmail.setText("Email: "+email);
            txtFB.setText("Facebook: "+faceBook);

        }catch (Exception e){

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_chitiet, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_chinhsua:{
                 Intent intent = new Intent(ThongTinChiTietActivity.this,SuaActivity.class);
                 intent.putExtra("IDN",IDN);
                 startActivity(intent);
            }break;
            case R.id.menu_moiquanhekhac:{
                Intent intent = new Intent(ThongTinChiTietActivity.this,QuanHeKhacActivity.class);
                intent.putExtra("IDN",IDN);
                startActivity(intent);
            }break;
            case R.id.menu_thoat:{
                AlertDialog.Builder builder = new AlertDialog.Builder(ThongTinChiTietActivity.this);
                builder.setMessage("Bạn có muốn thoát ứng dụng không?");

                builder.setTitle("Xác nhận");
                builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                        System.exit(0);
                    }
                });
                builder.setNegativeButton("Không đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.show();

            }break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStop() {
          arrayListGoi.clear();
          arrayListGuiDi.clear();
          arrayListTN.clear();
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        FragmentLienHe.alertDialog.dismiss();
    }
}
