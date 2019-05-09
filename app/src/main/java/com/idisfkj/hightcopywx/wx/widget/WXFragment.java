package com.idisfkj.hightcopywx.wx.widget;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.idisfkj.hightcopywx.R;
import com.idisfkj.hightcopywx.adapter.OnItemTouchListener;
import com.idisfkj.hightcopywx.adapter.WXAdapter;
import com.idisfkj.hightcopywx.dao.WxDataHelper;
import com.idisfkj.hightcopywx.util.SPUtils;
import com.idisfkj.hightcopywx.wx.WXItemDecoration;
import com.idisfkj.hightcopywx.wx.presenter.WXPresent;
import com.idisfkj.hightcopywx.wx.presenter.WXPresentImp;
import com.idisfkj.hightcopywx.wx.view.WXView;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 聊天通讯界面
 * Created by idisfkj on 16/4/19.
 * Email : idisfkj@qq.com.
 */
public class WXFragment extends Fragment implements WXView, LoaderManager.LoaderCallbacks<Cursor> {
    @InjectView(R.id.wx_recyclerView)
    RecyclerView wxRecyclerView;
    private WXAdapter wxAdapter;
    private WXPresent mWXPresent;
    private WxDataHelper mHelper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.wx_layout, null);
        ButterKnife.inject(this, view);
        init();
        return view;
    }

    public void init() {
        wxAdapter = new WXAdapter(getContext());
        mWXPresent = new WXPresentImp(this);
        wxRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        wxRecyclerView.addItemDecoration(new WXItemDecoration(getContext()));
        wxRecyclerView.setAdapter(wxAdapter);
        wxRecyclerView.addOnItemTouchListener(new OnItemTouchListener(wxRecyclerView) {
            @Override
            public void onItemListener(RecyclerView.ViewHolder vh) {
                Intent intent = new Intent(getActivity(), ChatActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("_id", vh.getLayoutPosition()+1);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        mHelper = new WxDataHelper(getContext());
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return mHelper.getCursorLoader(SPUtils.getString("userPhone"));
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (loader != null && data.getCount() <= 0) {
            mWXPresent.initData(mHelper);
        }
        wxAdapter.changeCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        wxAdapter.changeCursor(null);
    }
}
