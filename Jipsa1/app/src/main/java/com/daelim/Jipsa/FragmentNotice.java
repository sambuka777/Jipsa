package com.daelim.Jipsa;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class FragmentNotice extends Fragment {

    private View view;
    private MainActivity mainActivity;

    ImageButton ImgBtnNoticeBack;

    public void onAttach(Context context){
        super.onAttach(context);
        mainActivity= (MainActivity) getActivity();
    }

    public void onDetach(){
        super.onDetach();
        mainActivity = null;
    }

    ArrayList<Notitle> notitles;
    ListView ListNotice;
    private static NoticeAdapter noticeAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_notice, container, false);

        notitles = new ArrayList<>();
        notitles.add(new Notitle("머리아프다", "7777-07-77"));
        notitles.add(new Notitle("나는 누구인가", "8888-08-88"));
        notitles.add(new Notitle("또 여긴 어디인가", "9999-09-99"));
        notitles.add(new Notitle("공지사항 제목4", "공지사항 등록일4"));
        notitles.add(new Notitle("공지사항 제목5", "공지사항 등록일5"));

        ListNotice = view.findViewById(R.id.list_Notice);
        noticeAdapter = new NoticeAdapter(getContext(), notitles);
        ListNotice.setAdapter(noticeAdapter);
        ListNotice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = view.findViewById(R.id.tv_listNTitle).getTag().toString();
                Toast.makeText(getContext(), "Clicked: " + position +" " + selectedItem, Toast.LENGTH_SHORT).show();
                mainActivity.setFrag(13, null);
            }
        });

        ImgBtnNoticeBack = view.findViewById(R.id.Imgbtn_NoticeBack);
        ImgBtnNoticeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.setFrag(4, null);
            }
        });

        return view;
    }

    class Notitle {
        private String noticeTitle;
        private String noticeDate;

        public Notitle(String noticeTitle, String noticeDate) {
            this.noticeTitle = noticeTitle;
            this.noticeDate = noticeDate;
        }

        public String getNoticeTitle() {
            return noticeTitle;
        }

        public String getNoticeDate() {
            return noticeDate;
        }
    }
}
