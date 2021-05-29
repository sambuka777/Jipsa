package com.daelim.Jipsa;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

//import com.bumptech.glide.Glide;
//import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends ArrayAdapter implements AdapterView.OnItemClickListener{

    private Context context;
    private List list;

    public CustomAdapter(Context context, ArrayList list){
        super(context, 0, list);
        this.context = context;
        this.list = list;
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(context, "clicked", Toast.LENGTH_SHORT).show();
        System.out.println("이게실행되는가");

    }

    class ViewHolder {
        public TextView ed_name;
        public TextView ed_date; //tv_summary
        public TextView ed_memo;
        public TextView ed_title;
        public TextView ed_viewn;
        //public ImageView iv_thumb;
    }



    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;

        if (convertView == null){
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            convertView = layoutInflater.inflate(R.layout.chat_item, parent, false);
        }

        viewHolder = new ViewHolder();
        viewHolder.ed_name = (TextView) convertView.findViewById(R.id.Ed_cname3);
        viewHolder.ed_title = (TextView) convertView.findViewById(R.id.Ed_ctitle3);
//        viewHolder.ed_memo = (TextView) convertView.findViewById(R.id.Ed_cmemo3);
        viewHolder.ed_date = (TextView) convertView.findViewById(R.id.Ed_cdate3);
        viewHolder.ed_viewn = (TextView) convertView.findViewById(R.id.Ed_views3);
        //viewHolder.iv_thumb = (ImageView) convertView.findViewById(R.id.imageView_thumb);

        final FragmentCommu.Actor actor = (FragmentCommu.Actor) list.get(position);
        viewHolder.ed_name.setText(actor.getName());
        viewHolder.ed_date.setText(actor.getdate());
//        viewHolder.ed_memo.setText(actor.getmemo());
        viewHolder.ed_title.setText(actor.gettitle());
        viewHolder.ed_viewn.setText(actor.getviewn());
//        Glide
//                .with(context)
//                .load(actor.getThumb_url())
//                .apply(new RequestOptions().override(250, 350))
//                .into(viewHolder.iv_thumb);
        viewHolder.ed_name.setTag(actor.getName());


//        //아이템 클릭 방법2 - 클릭시 아이템 반전 효과가 안 먹힘
//        convertView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(context, " " + actor.getName(), Toast.LENGTH_SHORT).show();
//            }
//        });

        //Return the completed view to render on screen
        return convertView;
    }
}

