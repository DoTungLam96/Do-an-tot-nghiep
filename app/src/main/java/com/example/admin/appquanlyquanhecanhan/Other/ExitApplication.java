package com.example.admin.appquanlyquanhecanhan.Other;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

import com.example.admin.appquanlyquanhecanhan.MainActivity;

/**
 * Created by Admin on 6/3/2018.
 */

public class ExitApplication {
    public static  void ExitApp(final Activity activity){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage("Bạn có muốn thoát ứng dụng không?");

        builder.setTitle("Xác nhận");
        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                activity.finish();
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
    }
}
