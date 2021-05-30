package com.daelim.Jipsa;

import android.content.Context;
import android.os.Bundle;
import android.os.ParcelUuid;
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

public class FragmentQnAPre extends Fragment {

    private View view;
    private MainActivity mainActivity;

    ImageButton ImgBtnQpBack, ImgBtnQpWrite;

    public void onAttach(Context context){
        super.onAttach(context);
        mainActivity= (MainActivity) getActivity();
    }

    public void onDetach(){
        super.onDetach();
        mainActivity = null;
    }

    ArrayList<QnApre> qnapres;
    ListView ListQp;
    private static QnApreAdapter qnapreAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_qnapre, container, false);

        qnapres = new ArrayList<>();
        qnapres.add(new QnApre("문의사항입니다.", "2021-05-30"));
        qnapres.add(new QnApre("나문희세요?", "2021-05-30"));
        qnapres.add(new QnApre("고구마호박 맛있네요.", "2021-05-30"));
        qnapres.add(new QnApre("어머니 호박~! 고구마요~", "2021-05-30"));
        qnapres.add(new QnApre("호.박.고.구.마 호박고구마~!~!~!", "2021-05-30"));

        ListQp = view.findViewById(R.id.list_Qp);
        qnapreAdapter = new QnApreAdapter(getContext(), qnapres);
        ListQp.setAdapter(qnapreAdapter);
        ListQp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = view.findViewById(R.id.tv_listQpQuestion).getTag().toString();
                Toast.makeText(getContext(), "Clicked: " + position +" " + selectedItem, Toast.LENGTH_SHORT).show();
                mainActivity.setFrag(14, null);
            }
        });

        ImgBtnQpBack = view.findViewById(R.id.Imgbtn_QpBack);
        ImgBtnQpBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.setFrag(4, null);
            }
        });

        ImgBtnQpWrite = view.findViewById(R.id.Imgbtn_QpWrite);
        ImgBtnQpWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.setFrag(10, null);
            }
        });


        return view;
    }

    class QnApre {
        private String qpQuestion;
        private String qpDate;

        public QnApre(String qpQuestion, String qpDate) {
            this.qpQuestion = qpQuestion;
            this.qpDate = qpDate;
        }

        public String getQpQuestion() {
            return qpQuestion;
        }

        public String getQpDate() {
            return qpDate;
        }
    }

}
