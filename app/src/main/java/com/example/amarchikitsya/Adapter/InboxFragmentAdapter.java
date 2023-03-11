package com.example.amarchikitsya.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.amarchikitsya.fragment.InboxUserFragment;
import com.example.amarchikitsya.fragment.InboxDoctorUserFragment;

public class InboxFragmentAdapter extends FragmentPagerAdapter {
    String [] allTabs = {"Users","Doctors"};
    public InboxFragmentAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new InboxUserFragment();
            case 1:
                return new InboxDoctorUserFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return allTabs.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return allTabs[position];
    }
}
