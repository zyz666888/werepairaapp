package com.idisfkj.hightcopywx.main;

import android.support.v4.app.Fragment;

import com.idisfkj.hightcopywx.contact.ContactFragment;
import com.idisfkj.hightcopywx.find.FindFragment;
import com.idisfkj.hightcopywx.me.MeFragment;
import com.idisfkj.hightcopywx.wx.widget.WXFragment;

/**
 * Fragment工厂
 * Created by idisfkj on 16/4/19.
 * Email : idisfkj@qq.com.
 */
public class FragmentFactory {
    private Fragment mFragment;
    private int mSize;
    public FragmentFactory(int size) {
        mSize = size;
    }

    public int getSize(){
        return mSize;
    }

    public Fragment createFragment(int position){
        switch (position){
           /* case 0:
                mFragment = new WXFragment();
                break;
            case 1:
                mFragment = new ContactFragment();
                break;*/
            case 0:
                mFragment = new FindFragment();
                break;
            case 1:
                mFragment = new MeFragment();
                break;
        }
        return mFragment;
    }
}
