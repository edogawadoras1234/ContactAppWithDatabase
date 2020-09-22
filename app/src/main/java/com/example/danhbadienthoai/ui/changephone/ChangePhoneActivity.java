package com.example.danhbadienthoai.ui.changephone;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.danhbadienthoai.R;
import com.example.danhbadienthoai.ui.danhba.ContactAdapter;
import com.example.danhbadienthoai.data.db.Database;
import com.example.danhbadienthoai.data.db.model.Contact;
import com.example.danhbadienthoai.ui.danhba.DanhbaActivity;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class ChangePhoneActivity extends AppCompatActivity {
    Database database;
    final int REQUEST_CODE_CAMERA = 123;
    final int REQUEST_CODE_FOLDER = 456;
    ImageView img_anhdaidien;
    EditText edtname, edtphone, edtavatar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_phone);

        findViewByIds();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        ImageView img_anhdaidien;
        img_anhdaidien = findViewById(R.id.img_change_avatar);

        //gán Image thành Image mình chụp
        if (requestCode == REQUEST_CODE_CAMERA & resultCode == RESULT_OK & data != null){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            img_anhdaidien.setImageBitmap(bitmap);
        }
        //Gán Image từ get file folder
        if (requestCode == REQUEST_CODE_FOLDER & resultCode == RESULT_OK & data != null){
            Uri uri = data.getData();
            EditText editText;
            editText = findViewById(R.id.edt_avatar_change);
            try{
                InputStream inputStream  = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                img_anhdaidien.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            editText.setText(String.valueOf(uri));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void menuPopup(){
        PopupMenu popupMenu = new PopupMenu(this,img_anhdaidien);
        popupMenu.getMenuInflater().inflate(R.menu.activity_news_app,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                switch (menuItem.getItemId()){
                    case R.id.menu_camera:
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent,REQUEST_CODE_CAMERA);
                        break;

                    case R.id.menu_folder:
                        intent = new Intent(Intent.ACTION_PICK);
                        intent.setType("image/*");
                        startActivityForResult(intent,REQUEST_CODE_FOLDER);
                        break;

                    case R.id.menu_clear_image:
                        edtavatar.setText("");
                        Glide.with(ChangePhoneActivity.this).load(R.drawable.ic_person).into(img_anhdaidien);
                        break;
                }
                return false;
            }
        });

        popupMenu.show();
    }

    private void findViewByIds(){
        database = new Database(this);

        Button btnaccept, btncancle;
        edtname = findViewById(R.id.edt_name_change);
        edtphone = findViewById(R.id.edt_phone_change);
        edtavatar = findViewById(R.id.edt_avatar_change);
        Intent intent = getIntent();
        final String id = intent.getStringExtra(ContactAdapter.ID);
        final String name  = intent.getStringExtra(ContactAdapter.NAME);
        final String phone = intent.getStringExtra(ContactAdapter.PHONE);
        final String avatar = intent.getStringExtra(ContactAdapter.AVATAR);
        edtname.setText(name);
        edtphone.setText(phone);
        edtavatar.setText(avatar);

        img_anhdaidien = findViewById(R.id.img_change_avatar);

        Glide.with(this.getApplicationContext()).load(avatar)
                .placeholder(R.drawable.ic_person).into(img_anhdaidien);


        img_anhdaidien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menuPopup();
            }
        });
        btnaccept = findViewById(R.id.btn_change_phone);
        btncancle = findViewById(R.id.btn_cancle_change);

        btncancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ChangePhoneActivity.this, DanhbaActivity.class);
                startActivity(i);

            }
        });
        btnaccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtphone.length() == 0) {
                    Toast.makeText(ChangePhoneActivity.this, "Không được bỏ trống số điện thoại", Toast.LENGTH_SHORT).show();
                } else {
                    database.UpdateData(edtname.getText().toString(), edtphone.getText().toString(), edtavatar.getText().toString(),-1, id);
                    Toast.makeText(ChangePhoneActivity.this, "Đã thay đổi thành công", Toast.LENGTH_SHORT).show();
                    Contact contact = new Contact(id,
                            edtname.getText().toString(),
                            edtphone.getText().toString(),
                            edtavatar.getText().toString(),
                            -1);
                    Intent i = new Intent(ChangePhoneActivity.this, DanhbaActivity.class);
                    startActivity(i);
                }

            }
        });
    }
}