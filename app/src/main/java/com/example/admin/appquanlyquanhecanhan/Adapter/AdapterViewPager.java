package com.example.admin.appquanlyquanhecanhan.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.admin.appquanlyquanhecanhan.Fragment.FragmentLienHe;
import com.example.admin.appquanlyquanhecanhan.Fragment.FragmentNhom;
import com.example.admin.appquanlyquanhecanhan.Fragment.FragmentUaThich;

/**
 * Created by Admin on 05-Apr-18.
 */

public class AdapterViewPager extends FragmentStatePagerAdapter {
    public AdapterViewPager(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0 :{
                FragmentLienHe fragmentLienHe = new FragmentLienHe();
                return fragmentLienHe;
            }
            case 1: {
                FragmentNhom fragmentNhom = new FragmentNhom();
                return fragmentNhom;
            }
            case 2: {
                FragmentUaThich fragmentUaThich = new FragmentUaThich();
                return fragmentUaThich;
            }
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
