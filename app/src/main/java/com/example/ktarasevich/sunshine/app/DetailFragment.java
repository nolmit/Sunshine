package com.example.ktarasevich.sunshine.app;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

/**
 * Created by kit on 1/28/15.
 */
public  class DetailFragment extends Fragment {

    public DetailFragment() {
    }
    public String mess;

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if (id==R.id.action_settings)
        {
            Intent openSettings = new Intent(getActivity(),SettingsActivity.class);
            startActivity(openSettings);
            return  true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

      TextView myText =(TextView)rootView.findViewById(R.id.detailText);
       Intent intent = getActivity().getIntent();
        if (intent!=null) {
            mess = intent.getStringExtra(ForecastFragment.EXTRA_MESSAGE);
            myText.setText(mess);
        }
        return rootView;
    }
}


