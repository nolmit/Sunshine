package com.example.ktarasevich.sunshine.app;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ShareActionProvider;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

/**
 * Created by kit on 1/28/15.
 */
public  class DetailFragment extends Fragment {
   ShareActionProvider mShareActionProvider;

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
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.share_menu,menu);

        MenuItem shareItem = menu.findItem(R.id.action_share);
        mShareActionProvider =(ShareActionProvider) shareItem.getActionProvider();
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(ShareIntent());


        }
        else Log.d("PROVIDER", "is null");
    }

    private Intent ShareIntent() {

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        intent.putExtra(Intent.EXTRA_TEXT, mess);
        intent.setType("text/plain");

        return  intent;
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


