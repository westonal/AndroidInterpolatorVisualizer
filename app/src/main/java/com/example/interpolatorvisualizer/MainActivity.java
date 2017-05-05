package com.example.interpolatorvisualizer;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.*;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                                .add(R.id.container, new PlaceholderFragment()).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container,
                    false);

            ListView lv = (ListView) rootView.findViewById(R.id.listView1);
            List<Interpolator> ls = new ArrayList<Interpolator>();

            ls.add(new AccelerateDecelerateInterpolator());
            ls.add(new AccelerateInterpolator());
            ls.add(new AnticipateInterpolator());
            ls.add(new AnticipateOvershootInterpolator());
            ls.add(new BounceInterpolator());
            ls.add(new CycleInterpolator(5));
            ls.add(new DecelerateInterpolator());
            ls.add(new LinearInterpolator());
            ls.add(new OvershootInterpolator());

            //Custom one:
            ls.add(new DecelerateAccelerateInterpolator());

            lv.setAdapter(new MyAdapter(container.getContext(),
                    R.layout.interpolator, R.id.textView1, ls));

            return rootView;
        }
    }

    public static class MyAdapter extends ArrayAdapter<Interpolator> {

        public MyAdapter(Context context, int resource, int textViewId,
                         List<Interpolator> objects) {
            super(context, resource, textViewId, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = super.getView(position, convertView, parent);
            InterpolatorView iv = (InterpolatorView) view
                    .findViewById(R.id.interpolatorView1);
            iv.setInterpolator(getItem(position));
            return view;
        }

    }

}
