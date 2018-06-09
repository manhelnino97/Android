package com.example.hoangtienmanh.imagelistview.Fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Hoang Tien Manh on 3/24/2018.
 */

public class FragmentAdapter extends FragmentStatePagerAdapter{
    private String listTab[] = {"Tổng hợp","Thể thao", "Thời sự","Giải trí","Kinh tế","Pháp luật","Công nghệ","Game","Giáo dục","Sức khoẻ","Đời sống"};
    private FirstFragment firstFragment;
    private SecondFragment secondFragment;
    private ThirdFragment thirdFragment;
    private FourthFragment fourthFragment;
    private FifthFragment fifthFragment;
    private SixthFragment sixthFragment;
    private SeventhFragment seventhFragment;
    private EighthFragment eighthFragment;
    private NinethFragment ninethFragment;
    private TenthFragment tenthFragment;
    private EleventhFragment eleventhFragment;

    public FragmentAdapter(FragmentManager fm) {
        super(fm);
        firstFragment = new FirstFragment();
        secondFragment = new SecondFragment();
        thirdFragment = new ThirdFragment();
        fourthFragment = new FourthFragment();
        fifthFragment = new FifthFragment();
        sixthFragment = new SixthFragment();
        seventhFragment = new SeventhFragment();
        eighthFragment = new EighthFragment();
        ninethFragment = new NinethFragment();
        tenthFragment = new TenthFragment();
        eleventhFragment = new EleventhFragment();
    }

    @Override
    public Fragment getItem(int position) {
        if(position ==0){
            return firstFragment;
        }
        else if(position==1){
            return secondFragment;
        }
        else if(position==2){
            return thirdFragment;
        }
        else if(position==3){
            return fourthFragment;
        }
        else if(position==4){
            return fifthFragment;
        }
        else if(position==5){
            return sixthFragment;
        }
        else if(position==6){
            return seventhFragment;
        }
        else if(position==7){
            return eighthFragment;
        }
        else if(position==8){
            return ninethFragment;
        }
        else if(position==9){
            return tenthFragment;
        }
        else if(position==10){
            return eleventhFragment;
        }
        else {

        }
        return null;
    }

    @Override
    public int getCount() {
        return listTab.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return listTab[position];
    }
}
