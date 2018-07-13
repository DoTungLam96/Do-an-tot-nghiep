package com.example.admin.appquanlyquanhecanhan.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.appquanlyquanhecanhan.Adapter.AdapterNguoiQH;
import com.example.admin.appquanlyquanhecanhan.Adapter.AdapterNhom;
import com.example.admin.appquanlyquanhecanhan.Database.Database;
import com.example.admin.appquanlyquanhecanhan.MainActivity;
import com.example.admin.appquanlyquanhecanhan.Model.NguoiQH;
import com.example.admin.appquanlyquanhecanhan.Model.Nhom;
import com.example.admin.appquanlyquanhecanhan.R;
import com.example.admin.appquanlyquanhecanhan.ThongTinChiTietActivity;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Admin on 05-Apr-18.
 */

public class FragmentNhom extends Fragment {
    TextView txtNhom;
    CircleImageView imgIcon;
    AdapterNhom  adapterNhom;
    ListView listView;
    CircleImageView imgthemIcon;
    FloatingActionButton btnThemNhom;
    View view;
    ArrayList<Nhom> arrayListNhom;
    SQLiteDatabase database;
    int Code_folder = 9;
    int idNhom;
    ListView listViewNhom;
    ArrayList<NguoiQH> nguoiQHS;
    AdapterNguoiQH adapterNguoiQH;
    String Name_Database = "CSDL.sqlite";
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       view  = inflater.inflate(R.layout.layout_fragment_nhom,container,false);
        anhXa();
        layGiaTri();
        kiemTra();
        suKienClick();
        xoaNhom();
        batSuKienClickItem();
        return view;
    }

    private void batSuKienClickItem() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Dialog dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().getAttributes().windowAnimations = R.style.animDialog;
                dialog.setContentView(R.layout.dialog_layout_thongtin_nhom);
                idNhom = arrayListNhom.get(position).getIDNhom();
                listViewNhom = dialog.findViewById(R.id.listViewTTNhom);
                Cursor cursor = database.rawQuery("Select *  from NguoiQH where IDNhom="+idNhom,null);
                nguoiQHS.clear();
                try {
                    while (cursor.moveToNext()) {
                        int IDN = cursor.getInt(0);
                        String SDT = cursor.getString(1);
                        String ten = cursor.getString(2);
                        String email = cursor.getString(3);
                        String fb = cursor.getString(4);
                        String diaChi = cursor.getString(5);
                        String ngaySinh = cursor.getString(6);
                        byte[] hinhAnh = cursor.getBlob(8);
                        int uaThich = cursor.getInt(7);
                        nguoiQHS.add(new NguoiQH(IDN, SDT, ten, email, fb, diaChi, ngaySinh, hinhAnh, uaThich));
                    }
                    adapterNguoiQH = new AdapterNguoiQH(getActivity(),R.layout.adapter_layout_lienhe,nguoiQHS);
                    listViewNhom.setAdapter(adapterNguoiQH);
                    dialog.show();
                    listViewNhom.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            int idNhom = nguoiQHS.get(position).getIDN();
                            String sdt = nguoiQHS.get(position).getSDT();
                            Intent intent = new Intent(getActivity(),ThongTinChiTietActivity.class);
                            intent.putExtra("IDN",idNhom);
                            intent.putExtra("SDT",sdt);
                            startActivity(intent);
                        }
                    });
                }catch (Exception e){
                }
            }
        });
    }

    private void xoaNhom() {
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Thông báo xóa");
                builder.setMessage("Bạn có muốn xóa nhóm "+arrayListNhom.get(position).getTenNhom()+" không?");
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                      dialog.dismiss();
                    }
                });
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       database.delete("Nhom","IDnhom=?",new String[]{arrayListNhom.get(position).getIDNhom()+""});
                       Cursor cursor = database.rawQuery("Select * from Nhom",null);
                       arrayListNhom.clear();
                       while(cursor.moveToNext()){
                           int id = cursor.getInt(0);
                           String ten = cursor.getString(1);
                           byte[] icon = cursor.getBlob(2);
                           arrayListNhom.add(new Nhom(id,ten,icon));
                       }
                       adapterNhom.notifyDataSetChanged();
                        Snackbar.make(getActivity().findViewById(android.R.id.content),"Đã xóa",Snackbar.LENGTH_SHORT).show();
                    }
                });
                builder.show();
                return true;
            }
        });
    }

    private void suKienClick() {
        btnThemNhom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getActivity());
                dialog.setCancelable(false);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.them_nhom_dialog);
                imgthemIcon = dialog.findViewById(R.id.imgthemIcon);
                final EditText edtThemNhom = dialog.findViewById(R.id.edtThemNhom);
                Button btnThem = dialog.findViewById(R.id.btnThemNhom);
                Button btnThoat = dialog.findViewById(R.id.btnThoatNhom);
                btnThoat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
                imgthemIcon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setType("image/*");
                        startActivityForResult(intent,Code_folder);
                    }
                });
                btnThem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String ten = edtThemNhom.getText().toString();
                        if (ten.equals(""))
                            Toast.makeText(getActivity(), "Mời nhập tên nhóm", Toast.LENGTH_SHORT).show();
                        else {

                                ContentValues values = new ContentValues();
                                byte[] hinhAnh = ChuyenAnh(imgthemIcon);
                                values.put("TenNhom",ten);
                                values.put("Icon",hinhAnh);
                                database.insert("Nhom",null,values);
                                layGiaTri();
                                txtNhom.setVisibility(View.INVISIBLE);
                                Toast.makeText(getActivity(), "Đã thêm", Toast.LENGTH_SHORT).show();
                                dialog.cancel();
                        }
                    }
                });
                dialog.show();
            }
        });
    }

    private void kiemTra() {
    if (arrayListNhom.size() == 0){
        txtNhom.setVisibility(View.VISIBLE);
    }else {
        txtNhom.setVisibility(View.INVISIBLE);
    }

    }

    private void layGiaTri() {
        Cursor cursor = database.rawQuery("SELECT * FROM Nhom",null);
        arrayListNhom.clear();
        try {
            while (cursor.moveToNext()){
                int ID = cursor.getInt(0);
                String tenNhom = cursor.getString(1);
                byte[] hinhAnh = cursor.getBlob(2);
                arrayListNhom.add(new Nhom(ID,tenNhom,hinhAnh));
            }
            adapterNhom.notifyDataSetChanged();
        }catch (Exception e){

        }
    }

    private void anhXa() {
        txtNhom = view.findViewById(R.id.txtNhom);
        imgIcon = view.findViewById(R.id.imgIcon);
        listView = view.findViewById(R.id.listViewNhom);
        btnThemNhom = view.findViewById(R.id.btnThemNhom);
        database = Database.initDatabase(getActivity(),Name_Database);
        arrayListNhom = new ArrayList<>();
        nguoiQHS = new ArrayList<>();
        adapterNhom = new AdapterNhom(getActivity(),arrayListNhom,R.layout.adapter_layout_nhom);
        listView.setAdapter(adapterNhom);
    }

    private byte[] ChuyenAnh(ImageView imageView){
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] hinhAnh = stream.toByteArray();
        return hinhAnh;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Code_folder && resultCode == getActivity().RESULT_OK){
            Uri uri = data.getData();
            try {
                InputStream inputStream = getActivity().getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgthemIcon.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
