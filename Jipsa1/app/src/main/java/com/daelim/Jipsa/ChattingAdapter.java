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

public class ChattingAdapter extends ArrayAdapter implements AdapterView.OnItemClickListener {

    private Context context;
    private List list;

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(context, "clicked", Toast.LENGTH_SHORT).show();
    }

    class ViewHolder {
        public TextView tv_ChatName;
        public TextView tv_ChatContent;
        public TextView tv_ChatDate;
    }

    public ChattingAdapter(Context context, ArrayList list){
        super(context, 0, list);
        this.context = context;
        this.list = list;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;

        if (convertView == null){
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            convertView = layoutInflater.inflate(R.layout.row_chatting, parent, false);
        }

        viewHolder = new ViewHolder();
        viewHolder.tv_ChatName = (TextView) convertView.findViewById(R.id.ch_name1);
        viewHolder.tv_ChatContent = (TextView) convertView.findViewById(R.id.ch_chat1);
        viewHolder.tv_ChatDate = (TextView) convertView.findViewById(R.id.ch_date1);


        final FragmentChat.Chat chat = (FragmentChat.Chat) list.get(position);

        viewHolder.tv_ChatName.setText(chat.getChatName());
        viewHolder.tv_ChatContent.setText(chat.getChatContents());
        viewHolder.tv_ChatDate.setText(chat.getChatDate());

        viewHolder.tv_ChatName.setTag(chat.getChatName());


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
