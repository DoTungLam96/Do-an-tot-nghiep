package com.example.admin.appquanlyquanhecanhan;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.KeyguardManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.PersistableBundle;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.multidex.MultiDex;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.appquanlyquanhecanhan.Adapter.AdapterThongTinKhac;
import com.example.admin.appquanlyquanhecanhan.Adapter.AdapterViewPager;
import com.example.admin.appquanlyquanhecanhan.Database.Database;
import com.example.admin.appquanlyquanhecanhan.Model.NguoiQH;
import com.example.admin.appquanlyquanhecanhan.Model.Nhom;
import com.example.admin.appquanlyquanhecanhan.Model.ThongTinKhac;
import com.example.admin.appquanlyquanhecanhan.Notification.MyBroadcastReceiver;
import com.example.admin.appquanlyquanhecanhan.Other.ExitApplication;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.IDN;
import java.nio.channels.FileChannel;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import dmax.dialog.SpotsDialog;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener,TabHost.OnTabChangeListener {

    Toolbar toolbar;
    public static MaterialSearchView searchView;
    public static TabHost tabHost;
    public static ViewPager viewPager;
    AdapterViewPager adapterViewPager;
    DrawerLayout drawerLayout;
    ListView listView;
    AlertDialog alertDialog;
    List<ThongTinKhac> thongTinKhacList;
    AdapterThongTinKhac adapterThongTinKhac;
    NavigationView navigationView;
    public static ArrayList<String> listNgaySinh,listName;
    SimpleDateFormat format;
    AlarmManager alarmManager;
    Calendar calendar;
    public  static  int IDN;
    public  static  String ten;
    public static  MyBroadcastReceiver myBroadcastReceiver;

    public  static FloatingActionButton floatingActionButton;
    public static final String Database_name="CSDL.sqlite";
    public SQLiteDatabase database;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhXa();
        taoActionBar();
        TaoTabs();
        TaoThongTinNavigation();
        SuKienListView();
        FloatingAction();
        SinhNhat();
        ThongBaoSinhNhat();
    }

    private void ThongBaoSinhNhat() {
       try {
           int notificationID;
        if (ten != null) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this)
                    .setSmallIcon(R.drawable.gift)
                    .setContentTitle("Thông báo sinh nhật")
                    .setContentText("Hôm nay sinh nhật của : " + ten);

            Intent intentResult = new Intent(MainActivity.this, ThongTinChiTietActivity.class);
            intentResult.putExtra("IDN",IDN);
            PendingIntent intentt = PendingIntent.getActivity(MainActivity.this, 0, intentResult, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(intentt);
            Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            builder.setSound(uri);
            notificationID = 111;
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            manager.notify(notificationID, builder.build());
            IntentFilter filter =new IntentFilter("android.intent.action.TIME_TICK");
             myBroadcastReceiver = new MyBroadcastReceiver();
            registerReceiver(myBroadcastReceiver,filter);
        }
       }catch (Exception e){

       }

    }


    private void SinhNhat() {
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("dd/MM");
        Cursor cursor = database.rawQuery("Select * from NguoiQH where NgaySinh like '"+format.format(date)+"%'",null);
        listName.clear();
        try {
            while (cursor.moveToNext()) {
                IDN= cursor.getInt(0);
                ten = cursor.getString(2);
            }
                Log.e("Main", ten+" ID = "+IDN);

        }catch (Exception e){
        }


    }

    private void SuKienListView() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0 :{
                        drawerLayout.closeDrawer(GravityCompat.START);
                        alertDialog = new SpotsDialog(MainActivity.this,R.style.Custom);
                        alertDialog.show();
                        Handler  handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                copyDatabase(MainActivity.this,"CSDL.sqlite");
                                Snackbar.make(findViewById(android.R.id.content),"Lưu trữ dữ liệu thành công",Snackbar.LENGTH_SHORT).show();
                            }
                        },1500);

                    }break;
                    case 1:{
                        drawerLayout.closeDrawer(GravityCompat.START);
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setMessage("Dữ liệu hiện có sẽ được thay thế bằng dữ liệu mới?");

                        builder.setTitle("Lưu ý");
                        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                alertDialog = new SpotsDialog(MainActivity.this,R.style.Custom);
                                alertDialog.show();
                                Handler  handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        importDB();
                                    }
                                },2000);
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
                    case 2:{
                        KeyguardManager km = (KeyguardManager)getSystemService(KEYGUARD_SERVICE);
                        if(km.isKeyguardSecure()) {

                            Intent ab = km.createConfirmDeviceCredentialIntent("Nhập mã bảo mật", "");
                            startActivityForResult(ab, 2);
                        }
                        else
                            Toast.makeText(MainActivity.this, "Bạn không thiết lập mã khóa !", Toast.LENGTH_SHORT).show();


                    }break;
                    case 3:{
                        Dialog dialog = new Dialog(MainActivity.this);
                        dialog.getWindow().getAttributes().windowAnimations = R.style.animDialog;
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.dialog_layout_huong_dan);
                        dialog.show();
                    }break;
                    case 4:{
                        ExitApplication.ExitApp(MainActivity.this);
                    }
                }
            }
        });
    }

    private void TaoThongTinNavigation() {
        thongTinKhacList.add(0,new ThongTinKhac(R.drawable.backup,"Sao lưu dữ liệu"));
        thongTinKhacList.add(1,new ThongTinKhac(R.drawable.attach,"Tải dữ liệu từ bộ nhớ"));
        thongTinKhacList.add(2,new ThongTinKhac(R.drawable.deletee,"Xóa toàn bộ dữ liệu"));
        thongTinKhacList.add(3,new ThongTinKhac(R.drawable.help,"Hướng dẫn"));
        thongTinKhacList.add(4,new ThongTinKhac(R.drawable.exit,"Thoát"));

    }


    private void FloatingAction() {
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ThemLienHe.class);
                startActivity(intent);
            }
        });
    }

    private void TaoTabs() {
       tabHost.setup();

        TabHost.TabSpec tab1 = tabHost.newTabSpec("LienHe");
        tab1.setIndicator("Mối quan hệ");
        tab1.setContent(new NoiDungAo(MainActivity.this));
        TabHost.TabSpec tab2 = tabHost.newTabSpec("Nhom");
        tab2.setIndicator("Nhóm");
        tab2.setContent(new NoiDungAo(MainActivity.this));

        TabHost.TabSpec tab3 = tabHost.newTabSpec("UaThich");
        tab3.setIndicator("Mục ưa thích");
        tab3.setContent(new NoiDungAo(this));

        tabHost.addTab(tab1);
        tabHost.addTab(tab2);
        tabHost.addTab(tab3);
        tabHost.setOnTabChangedListener(this);
        viewPager.setOnPageChangeListener(this);
        for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
            View v = tabHost.getTabWidget().getChildAt(i);
            TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
            tv.setTextColor(getResources().getColor(R.color.white));
        }


    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        tabHost.setCurrentTab(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onTabChanged(String s) {
        int vitri = tabHost.getCurrentTab();
        viewPager.setCurrentItem(vitri);
        if (viewPager.getCurrentItem() == 1){
            MainActivity.floatingActionButton.setVisibility(View.INVISIBLE);
        }else {
            MainActivity.floatingActionButton.setVisibility(View.VISIBLE);
        }

    }

    class NoiDungAo implements TabHost.TabContentFactory {

        Context context;
        public NoiDungAo(Context context){
            this.context = context;
        }
        public View createTabContent(String s) {
            View view = new View(context);
            view.setMinimumWidth(0);
            view.setMinimumHeight(0);
            return view;
        }
    }



    private void taoActionBar() {
      setSupportActionBar(toolbar);
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
       toolbar.setNavigationOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               drawerLayout.openDrawer(GravityCompat.START);
           }
       });
    }
    private void anhXa() {
        toolbar = findViewById(R.id.toolBar);
        searchView = findViewById(R.id.meterialSearch);
        tabHost = findViewById(R.id.tabHost);
        viewPager = findViewById(R.id.viewPager);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigation);
        listView = findViewById(R.id.listView);
        thongTinKhacList = new ArrayList<>();
        adapterViewPager = new AdapterViewPager(getSupportFragmentManager());
        viewPager.setAdapter(adapterViewPager);
        navigationView.bringToFront();
        floatingActionButton = findViewById(R.id.fab);
        database = Database.initDatabase(MainActivity.this,Database_name);
        adapterThongTinKhac = new AdapterThongTinKhac(thongTinKhacList,R.layout.adapter_thong_tin_khac,MainActivity.this);
        listView.setAdapter(adapterThongTinKhac);
        listNgaySinh = new ArrayList<>();
        listName = new ArrayList<>();
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        calendar = Calendar.getInstance();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK && requestCode==2)
        {
            final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Xóa Dữ Liệu Ứng Dụng");
            builder.setMessage("Bạn có muốn xóa tất cả dữ liệu hiện có không?");
            builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();

                }
            });
            builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    alertDialog = new SpotsDialog(MainActivity.this,R.style.Custom);
                    alertDialog.show();
                    Handler  handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (Build.VERSION_CODES.KITKAT <= Build.VERSION.SDK_INT) {
                                ((ActivityManager) getApplicationContext().getSystemService(ACTIVITY_SERVICE))
                                        .clearApplicationUserData(); // note: it has a return value!
                            } else {
                                Toast.makeText(MainActivity.this, "Chỉ hỗ trợ xóa từ phiên bản Android 5.0", Toast.LENGTH_SHORT).show();
                            }
                        }
                    },4500);

                }
            });
            builder.show();
        }
        else
        {

        }
    }
    // tao menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuSearch:
                searchView.setMenuItem(item);
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        MultiDex.install(MainActivity.this);
    }

    // Luu tru file sqlite vao SD card
    public void copyDatabase(Context c, String DATABASE_NAME) {
        String databasePath = c.getDatabasePath(DATABASE_NAME).getPath();
        File f = new File(databasePath);
        OutputStream myOutput = null;
        InputStream myInput = null;
        Log.e("testing",databasePath);
        String duongdan = Environment.getExternalStorageDirectory().toString();
        Log.e("testing",duongdan+"/Database");
        alertDialog.dismiss();
        if (f.exists()) {
            try {
                File directory = new File(duongdan+"/Database");
                if (!directory.exists())
                directory.mkdir();
                myOutput = new FileOutputStream(directory.getAbsolutePath()
                        + "/" + DATABASE_NAME);
                myInput = new FileInputStream(databasePath);

                byte[] buffer = new byte[1024];
                int length;
                while ((length = myInput.read(buffer)) > 0) {
                    myOutput.write(buffer, 0, length);
                }

                myOutput.flush();
            } catch (Exception e) {
            } finally {
                try {
                    if (myOutput != null) {
                        myOutput.close();
                        myOutput = null;
                    }
                    if (myInput != null) {
                        myInput.close();
                        myInput = null;
                    }
                } catch (Exception e) {
                }
            }
        }
    }

    // import file sqlite
    public void importDB() {

        String dir = Environment.getExternalStorageDirectory().getAbsolutePath()+"/Database";
        Log.e("Duongdan",dir);
        File sd = new File(dir);
        File data = Environment.getDataDirectory();
        FileChannel source = null;
        FileChannel destination = null;
        String backupDBPath = "/data/com.example.admin.appquanlyquanhecanhan/databases/CSDL.sqlite";
        String currentDBPath = "CSDL.sqlite";
        File currentDB = new File(sd, currentDBPath);
        File backupDB = new File(data, backupDBPath);
        try {
            source = new FileInputStream(currentDB).getChannel();
            destination = new FileOutputStream(backupDB).getChannel();
            destination.transferFrom(source, 0, source.size());
            source.close();
            destination.close();
            Snackbar.make(findViewById(android.R.id.content),"Load dữ liệu thành công",Snackbar.LENGTH_SHORT).show();
            alertDialog.dismiss();
        } catch (IOException e) {
            Toast.makeText(this, "Lỗi rồi. Mời vào hướng dẫn để xem chi tiết", Toast.LENGTH_LONG).show();
            alertDialog.dismiss();
            e.printStackTrace();
        }
    }
}
