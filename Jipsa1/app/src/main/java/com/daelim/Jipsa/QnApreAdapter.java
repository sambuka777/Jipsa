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

public class QnApreAdapter extends ArrayAdapter implements AdapterView.OnItemClickListener {
    private Context context;
    private List list;

    public QnApreAdapter(Context context, ArrayList list){
        super(context, 0, list);
        this.context = context;
        this.list = list;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(context, "clicked", Toast.LENGTH_SHORT).show();
    }

    class ViewHolder {
        public TextView tv_QnApreQuestion;
        public TextView tv_QnApreDate;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;

        if (convertView == null){
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            convertView = layoutInflater.inflate(R.layout.row_listqp, parent, false);
        }

        viewHolder = new ViewHolder();
        viewHolder.tv_QnApreQuestion = (TextView) convertView.findViewById(R.id.tv_listQpQuestion);
        viewHolder.tv_QnApreDate = (TextView) convertView.findViewById(R.id.tv_listQpDate);

        final FragmentQnAPre.QnApre qnApre = (FragmentQnAPre.QnApre) list.get(position);
        viewHolder.tv_QnApreQuestion.setText(qnApre.getQpQuestion());
        viewHolder.tv_QnApreDate.setText(qnApre.getQpDate());

        viewHolder.tv_QnApreQuestion.setTag(qnApre.getQpQuestion());


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
