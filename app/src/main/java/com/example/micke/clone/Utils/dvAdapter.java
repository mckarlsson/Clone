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
            curView = vi.inflate(R.layout.comment_view, null);
        }

        TextView tv_user_fullname = (TextView) curView.findViewById(R.id.comment_username);
        TextView tv_comment_text = (TextView) curView.findViewById(R.id.comment_text);


        tv_user_fullname.setText(data.get(position).getUser().getFull_name());
        tv_comment_text.setText(data.get(position).getComment().getComments());


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
