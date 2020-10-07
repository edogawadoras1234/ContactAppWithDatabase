package com.example.danhbadienthoai.ui.bottomplayingnow;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.danhbadienthoai.R;
import com.example.danhbadienthoai.service.MusicService;

import java.util.Objects;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class BottomPlayingControls extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trang_chu_news, container, false);
        ButterKnife.bind(this,view);

        return view;
    }
}
