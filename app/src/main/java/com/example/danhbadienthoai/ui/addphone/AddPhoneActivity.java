package com.example.danhbadienthoai.ui.addphone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.danhbadienthoai.R;
import com.example.danhbadienthoai.ui.danhba.DanhbaActivity;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddPhoneActivity extends AppCompatActivity implements AddPhoneMvpView {
    @BindView(R.id.edit_name)
    EditText edtname;
    @BindView(R.id.edit_phone)
    EditText edtphone;
    @BindView(R.id.edit_avatar)
    EditText edtavatar;
    @BindView(R.id.button_cancle)
    Button btncancle;

    AddPhonePresenter addPhonePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_phone);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Thêm Danh Ba");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);
        addPhonePresenter = new AddPhonePresenter(this, this);
    }

    @OnClick(R.id.button_add)
    void buttonAddClick() {
        addPhonePresenter.onAddClick(edtname.getText().toString(), edtphone.getText().toString(), edtavatar.getText().toString());
    }

    @OnClick(R.id.button_cancle)
    void
    buttonCancleClick() {
        addPhonePresenter.onCancleClick();
    }

    @Override
    public void openMainActivity() {
        Intent intent = new Intent(this, DanhbaActivity.class);
        startActivity(intent);
    }

    @Override
    public void Error() {
        Toast.makeText(this, "Không được bỏ trống số điện thoại và tên", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void Success() {
        Toast.makeText(this, "Thêm thành công", Toast.LENGTH_SHORT).show();
    }
}