package com.example.danhbadienthoai.addphone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.danhbadienthoai.R;
import com.example.danhbadienthoai.danhba;
import com.example.danhbadienthoai.db.Database;

public class add_phone_number extends AppCompatActivity implements View.OnClickListener, ViewAddPhone {
    Button btnadd, btncancle;
    EditText edtname, edtphone, edtavatar;
    Database database;


    PresenterAddPhone presenterAddPhone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_phone);

        presenterAddPhone = new PresenterAddPhone(this);
        findviewbyids();

    }

    private void findviewbyids(){
        edtname = findViewById(R.id.edtname);
        edtphone = findViewById(R.id.edtphone);
        edtavatar = findViewById(R.id.edtavata);
        btnadd = findViewById(R.id.btnadd);
        btncancle = findViewById(R.id.btncancle);
        btnadd.setOnClickListener(this);
        btncancle.setOnClickListener(this);
        getSupportActionBar().setTitle("Thêm Danh Ba");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnadd) {
            presenterAddPhone.received(edtname.getText().toString(), edtphone.getText().toString(), edtavatar.getText().toString());
        } else if (view.getId() == R.id.btncancle) {
            presenterAddPhone.cancle();
        }
    }

    @Override
    public void onFailed1() {
        Toast.makeText(this, "Xin hãy nhập tên và số điện thoại", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailed2() {
        Toast.makeText(this, "Không được bỏ trống Tên hoặc số điện thoại", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSusscess() {
                database = new Database(this);
                database.addData(null, edtname.getText().toString(), edtphone.getText().toString(), edtavatar.getText().toString(), -1);
                Toast.makeText(this, "Add Success", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, danhba.class);
                startActivity(intent);
    }

    @Override
    public void cancle() {
            Intent intent = new Intent(this,danhba.class);
            startActivity(intent);
    }
}