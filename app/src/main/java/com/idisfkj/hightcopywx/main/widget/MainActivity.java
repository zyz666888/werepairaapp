package com.idisfkj.hightcopywx.main.widget;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.idisfkj.hightcopywx.App;
import com.idisfkj.hightcopywx.R;
import com.idisfkj.hightcopywx.adapter.FragmentAdapter;
import com.idisfkj.hightcopywx.main.presenter.MainPresenter;
import com.idisfkj.hightcopywx.main.presenter.MainPresenterImp;
import com.idisfkj.hightcopywx.main.view.MainView;
import com.idisfkj.hightcopywx.ui.BaseActivity;
import com.idisfkj.hightcopywx.util.BadgeViewUtils;
import com.idisfkj.hightcopywx.util.SPUtils;
import com.idisfkj.hightcopywx.wx.widget.ChatActivity;
import com.readystatesoftware.viewbadger.BadgeView;
import java.util.ArrayList;
import java.util.List;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements MainView {

    @InjectView(R.id.viewPage)
    ViewPager viewPage;
   /* @InjectView(R.id.wei_xin_s)
    ImageView weiXinS;
    @InjectView(R.id.address_book_s)
    ImageView addressBookS;*/
    @InjectView(R.id.find_s)
    ImageView findS;
    @InjectView(R.id.me_s)
    ImageView meS;
  /*  @InjectView(R.id.tab_weiXin_s)
    TextView tabWeiXinS;
    @InjectView(R.id.tab_address_s)
    TextView tabAddressS;*/
    @InjectView(R.id.tab_find_s)
    TextView tabFindS;
    @InjectView(R.id.tab_me_s)
    TextView tabMeS;
    private MainPresenter mMainPresenter;
    private List<ImageView> mListImage = new ArrayList<>();
    private List<TextView> mListText = new ArrayList<>();
    private int[] viewId;
    private Bundle bundle;
    private int unReadNum;
    private BadgeView badgeView;
    private final String ACTION_FILTER = "com.idisfkj.hightcopywx.main";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        init();
        //是否是点击通知跳转
        bundle = getIntent().getExtras();
        if (bundle != null) {
            mMainPresenter.switchActivity();
        }
    }


    public void init() {
        mMainPresenter = new MainPresenterImp(this);
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());
        viewPage.setAdapter(adapter);
        findS.setAlpha(1.0f);
        tabFindS.setAlpha(1.0f);
        setViewPageListener();

        BroadcastReceiver receiver = new MainBoradcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_FILTER);
        this.registerReceiver(receiver, filter);

       // viewId = new int[]{R.id.ll_wx, R.id.ll_address, R.id.ll_find, R.id.ll_me};
        viewId = new int[]{ R.id.ll_find, R.id.ll_me};
      /*  mListImage.add(weiXinS);
        mListImage.add(addressBookS);*/
        mListImage.add(findS);
        mListImage.add(meS);
      /*  mListText.add(tabWeiXinS);
        mListText.add(tabAddressS);*/
        mListText.add(tabFindS);
        mListText.add(tabMeS);
    }

    public class MainBoradcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            mMainPresenter.callBadgeView();
        }
    }


    public void setViewPageListener() {
        viewPage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {


          /*  arg0 :当前页面，及你点击滑动的页面
            arg1:当前页面偏移的百分比
            arg2:当前页面偏移的像素位置*/
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (positionOffset > 0) {
                    mListImage.get(position).setAlpha(1 - positionOffset);
                    mListImage.get(position + 1).setAlpha(positionOffset);
                    mListText.get(position).setAlpha(1 - positionOffset);
                    mListText.get(position + 1).setAlpha(positionOffset);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

   /* @Override
    public void switchWX() {
        viewPage.setCurrentItem(0, false);
       *//* weiXinS.setAlpha(1.0f);
        tabWeiXinS.setAlpha(1.0f);*//*
    }

    @Override
    public void switchAddressBook() {
        viewPage.setCurrentItem(1, false);
      *//*  addressBookS.setAlpha(1.0f);
        tabAddressS.setAlpha(1.0f);*//*
    }*/

    @Override
    public void switchFind() {
        viewPage.setCurrentItem(0, false);
        findS.setAlpha(1.0f);
        tabFindS.setAlpha(1.0f);
    }

    @Override
    public void switchMe() {
        viewPage.setCurrentItem(1, false);
        meS.setAlpha(1.0f);
        tabMeS.setAlpha(1.0f);
    }

    @Override
    public void switchAlpha(int id) {
        for (int i = 0; i < viewId.length; i++) {
            if (id != viewId[i]) {
                mListImage.get(i).setAlpha(0f);
                mListText.get(i).setAlpha(0f);
            }
        }
    }

    @Override
    public void jumpChatActivity() {
        Intent intent = new Intent(this, ChatActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);

    }

    @Override
    public void createBadgeView() {
        unReadNum = SPUtils.getInt(App.UNREADNUM);
        if (badgeView != null)
            badgeView.hide();
        if (unReadNum > 0) {
          //  badgeView = BadgeViewUtils.create(this, weiXinS, String.valueOf(unReadNum));
        }
    }

    @OnClick({ R.id.ll_find, R.id.ll_me})
    public void onClick(View view) {
        mMainPresenter.switchNavigation(view.getId());
    }

    @Override
    protected void onResume() {
        mMainPresenter.callBadgeView();
        super.onResume();
    }

}
