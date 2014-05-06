package com.example.timestamp;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

public abstract class ViewPagerAdapter extends PagerAdapter
{
    
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
 
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ViewPager pager = (ViewPager) container;
        View view = getView(position, pager);
 
        pager.addView(view);
 
        return view;
    }
 
    
    @Override
    public void destroyItem(ViewGroup container, int position, Object view) {
        ((ViewPager) container).removeView((View) view);
    }
    
    @Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public View getView(int position, ViewPager pager) {
		// TODO Auto-generated method stub
		return null;
	}
}