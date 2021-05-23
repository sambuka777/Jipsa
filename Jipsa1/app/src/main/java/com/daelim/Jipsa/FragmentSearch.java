package com.daelim.Jipsa;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class FragmentSearch extends Fragment {

    private View view;
    String id;

    private List<String> list;
    private ListView listView;
    private EditText editSearch;
    private SearchAdapter adapter;
    private ArrayList<String> arraylist;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_search, container, false);

        editSearch = view.findViewById(R.id.ed_Searching);
        listView = view.findViewById(R.id.SearchList);

        list = new ArrayList<String>();
        settingList();

        arraylist = new ArrayList<String>();
        arraylist.addAll(list);

        adapter = new SearchAdapter(list, getContext());
        listView.setAdapter(adapter);

        editSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = editSearch.getText().toString();
                search(text);
            }
        });
        list.clear();

        return view;
    }

    public void search(String charText) {
        list.clear();

        if (charText.length() == 0) {
//            list.addAll(arraylist);
        }
        else {
            for(int i = 0;i < arraylist.size(); i++) {
                if (arraylist.get(i).toLowerCase().contains(charText)) {
                    list.add(arraylist.get(i));
                }
            }
        }
        adapter.notifyDataSetChanged();
    }

    //내가 적용한 값
    private void settingList() {
        list.add("김태형");
        list.add("장준철");
        list.add("권대웅");
        list.add("김찬우");
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        list.add("6");
        list.add("7");
        list.add("8");
        list.add("9");
        list.add("10");
        list.add("11");
        list.add("12");
        list.add("13");
        list.add("14");
        list.add("15");
        list.add("16");
        list.add("17");
        list.add("18");
        list.add("19");
        list.add("20");
        list.add("집사");
        list.add("캡스톤디자인");
        list.add("고양이");
        list.add("강아지");
    }

    public void set_id(String id){
        this.id = id;
    }
}

