package com.example.timestamp;

import com.example.timestamp.R;
import com.example.timestamp.R.layout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class EditFragment extends Fragment {
	
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
 
		View rootView = inflater.inflate(R.layout.activity_projectsettings_edit, container, false);
        
        return rootView;
    }

}
