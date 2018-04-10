package com.example.micke.clone.Utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.micke.clone.FeedActivity;
import com.example.micke.clone.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class dvAdapter extends ArrayAdapter<Data> {

    private Context context;
    private ArrayList<Data> data;
    private FeedActivity onItemClickListener;

    public dvAdapter(Context context, int textViewResourceId, ArrayList<Data> objects) {
        super(context, textViewResourceId, objects);
        this.context = context;
        this.data = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        View curView = convertView;
        if (curView == null) {
            holder = new ViewHolder();
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            curView = vi.inflate(R.layout.detail_view, null);
        }

        TextView tv_user_fullname = (TextView) curView.findViewById(R.id.tv_user_fullname);
        ImageView iv_photo = (ImageView) curView.findViewById(R.id.iv_photo);
        ImageView iv_profile = (ImageView) curView.findViewById(R.id.iv_profile);

        tv_user_fullname.setText(data.get(position).getUser().getFull_name());

        Picasso.with(context)
                .load(data.get(position).getUser().getProfile_picture())
                .resize(100, 100)
                .centerInside()
                .into(iv_profile);
        Picasso.with(context)
                .load(data.get(position).getImages().getStandard_resolution().getUrl())
                .into(iv_photo);

        return curView;
    }


    class ViewHolder{
        TextView name;
        TextView ct;
        TextView cl;
    }

    public void clearListView() {
        data.clear();
    }
}
