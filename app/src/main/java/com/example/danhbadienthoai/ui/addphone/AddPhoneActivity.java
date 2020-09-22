package com.example.danhbadienthoai.ui.addphone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.danhbadienthoai.R;
import com.example.danhbadienthoai.ui.danhba.DanhbaActivity;
import com.example.danhbadienthoai.data.db.Database;

public class AddPhoneActivity extends AppCompatActivity implements View.OnClickListener, AddPhoneMvpView{
    Button btnadd, btncancle;
    EditText edtname, edtphone, edtavatar;
    Database database;

    AddPhonePresenter addPhonePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_phone);

        addPhonePresenter = new AddPhonePresenter(this);

        findviewbyids();

    }

    private void findviewbyids(){
        edtname = findViewById(R.id.edit_name);
        edtphone = findViewById(R.id.edit_phone);
        edtavatar = findViewById(R.id.edit_avatar);
        btnadd = findViewById(R.id.button_add);
        btncancle = findViewById(R.id.button_cancle);
        btnadd.setOnClickListener(this);
        btncancle.setOnClickListener(this);
        getSupportActionBar().setTitle("Thêm Danh Ba");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button_add) {
            addPhonePresenter.onAddClick(edtname.getText().toString(), edtphone.getText().toString(), edtavatar.getText().toString());
        } else if (view.getId() == R.id.button_cancle) {
            addPhonePresenter.onCancleClick();
        }
    }

    @Override
    public void openMainActivity() {
        Intent intent = new Intent(this, DanhbaActivity.class);
        startActivity(intent);
    }

    @Override
    public void Error() {
        Toast.makeText(this, "Khong duoc bo trong so dien thoai va ten", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void Success() {
        Toast.makeText(this, "Them thanh cong", Toast.LENGTH_SHORT).show();
        database = new Database(this);
        database.addData(null, edtname.getText().toString(), edtphone.getText().toString(), edtavatar.getText().toString(), -1);
    }
}