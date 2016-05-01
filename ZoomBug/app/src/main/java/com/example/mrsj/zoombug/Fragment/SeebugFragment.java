package com.example.mrsj.zoombug.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mrsj.zoombug.R;
import com.example.mrsj.zoombug.Seebug.Seebug;

/**
 * Created by MR.SJ on 2016/4/26.
 */
public class SeebugFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.seebug_fragment,container,false);
        RecyclerView recyclerView=(RecyclerView) view.findViewById(R.id.recycle_view);
        LinearLayoutManager manager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);
        Seebug seebug=new Seebug(this,getActivity(),recyclerView);
        seebug.execute("https://www.seebug.org/vuldb/vulnerabilities?sort=hot");
        return view;
    }

}
