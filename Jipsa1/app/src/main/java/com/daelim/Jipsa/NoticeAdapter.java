package com.daelim.Jipsa;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class NoticeAdapter extends ArrayAdapter implements AdapterView.OnItemClickListener {
    private Context context;
    private List list;

    public NoticeAdapter(Context context, ArrayList list){
        super(context, 0, list);
        this.context = context;
        this.list = list;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(context, "clicked", Toast.LENGTH_SHORT).show();
    }

    class ViewHolder {
        public TextView tv_NoticeTitle;
        public TextView tv_NoticeDate;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;

        if (convertView == null){
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            convertView = layoutInflater.inflate(R.layout.row_noticelistview, parent, false);
        }

        viewHolder = new ViewHolder();
        viewHolder.tv_NoticeTitle = (TextView) convertView.findViewById(R.id.tv_listNTitle);
        viewHolder.tv_NoticeDate = (TextView) convertView.findViewById(R.id.tv_listNDate);

        final FragmentNotice.Notitle actor = (FragmentNotice.Notitle) list.get(position);
        viewHolder.tv_NoticeTitle.setText(actor.getNoticeTitle());
        viewHolder.tv_NoticeDate.setText(actor.getNoticeDate());

        viewHolder.tv_NoticeTitle.setTag(actor.getNoticeTitle());


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
