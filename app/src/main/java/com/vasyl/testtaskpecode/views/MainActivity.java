package com.vasyl.testtaskpecode.views;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

import com.vasyl.testtaskpecode.R;
import com.vasyl.testtaskpecode.models.FragmentData;
import com.vasyl.testtaskpecode.utils.NotificationHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements MyFragment.MyFragmentListener {

    private ViewPager mViewPager;
    private List<FragmentData> mFragmentList;
    FragmentStatePagerAdapter mFragmentStatePagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (mFragmentList == null || mFragmentList.size() == 0) {
            mFragmentList = new ArrayList<>();
                for (int i = 1; i <= getIntent().getIntExtra(getString(R.string.fragment_id), 1); i++) {
                    FragmentData fragmentData = new FragmentData();
                    fragmentData.setId(i);
                    mFragmentList.add(fragmentData);
            }
        }
        mViewPager = findViewById(R.id.view_pager);
        FragmentManager fragmentManager = getSupportFragmentManager();
        mFragmentStatePagerAdapter = new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int i) {
                FragmentData fragment = mFragmentList.get(i);
                return MyFragment.newInstance(fragment.getId(), mFragmentList.size());
            }

            @Override
            public int getCount() {
                return mFragmentList.size();
            }

            @Override
            public int getItemPosition(@NonNull Object object) {
                return POSITION_NONE;
            }
        };
        mViewPager.setOffscreenPageLimit(5);
        mViewPager.setAdapter(mFragmentStatePagerAdapter);
        mViewPager.setCurrentItem(mFragmentList.size()-1);
    }

    @Override
    public void createNewFragment(FragmentData fragmentData) {
        mFragmentList.add(fragmentData);
        mFragmentStatePagerAdapter.notifyDataSetChanged();
        mViewPager.setCurrentItem(mFragmentList.size() - 1);
    }

    @Override
    public void deleteFragment(int id) {
        int position;
        position = getCurrentFragmentPosition(id);
        NotificationHelper.deleteNotification(this, mFragmentList.get(position).getNotificationIdList());
        mFragmentList.remove(position);
        mFragmentStatePagerAdapter.notifyDataSetChanged();
    }

    @Override
    public void addNotificationId(int fragmentId, int id) {
        List<Integer> idList = mFragmentList.get(getCurrentFragmentPosition(fragmentId)).getNotificationIdList();
        idList.add(id);
    }

    private int getCurrentFragmentPosition(int id) {
        for (FragmentData data : mFragmentList) {
            if (data.getId() == id) {
                return mFragmentList.indexOf(data);
            }
        }
        return 1;
    }
}
