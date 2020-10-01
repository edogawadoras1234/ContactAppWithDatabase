package com.example.danhbadienthoai.ui.changephone;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.danhbadienthoai.R;
import com.example.danhbadienthoai.ui.danhba.ContactAdapter;
import com.example.danhbadienthoai.data.db.Database;
import com.example.danhbadienthoai.ui.danhba.DanhbaActivity;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChangePhoneActivity extends AppCompatActivity {
    Database database;
    final int REQUEST_CODE_CAMERA = 123;
    final int REQUEST_CODE_FOLDER = 456;
    @BindView(R.id.img_change_avatar)
    ImageView img_anhdaidien;
    @BindView(R.id.edit_name_change)
    EditText edtname;
    @BindView(R.id.edit_phone_change)
    EditText edtphone;
    @BindView(R.id.edit_avatar_change)
    EditText edtavatar;
    String id, name, phone, avatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_phone);

        ButterKnife.bind(this);
        findViewByIds();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        ImageView img_anhdaidien;
        img_anhdaidien = findViewById(R.id.img_change_avatar);

        //gán Image thành Image mình chụp
        if (requestCode == REQUEST_CODE_CAMERA & resultCode == RESULT_OK & data != null) {
            Bitmap bitmap = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");
            img_anhdaidien.setImageBitmap(bitmap);
        }
        //Gán Image từ get file folder
        if (requestCode == REQUEST_CODE_FOLDER & resultCode == RESULT_OK & data != null) {
            Uri uri = data.getData();
            EditText editText;
            editText = findViewById(R.id.edit_avatar_change);
            try {
                assert uri != null;
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                img_anhdaidien.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            editText.setText(String.valueOf(uri));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void menuPopup() {
        PopupMenu popupMenu = new PopupMenu(this, img_anhdaidien);
        popupMenu.getMenuInflater().inflate(R.menu.activity_news_app, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(menuItem -> {

            switch (menuItem.getItemId()) {
                case R.id.menu_camera:
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CODE_CAMERA);
                    break;

                case R.id.menu_folder:
                    intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityForResult(intent, REQUEST_CODE_FOLDER);
                    break;

                case R.id.menu_clear_image:
                    edtavatar.setText("");
                    Glide.with(ChangePhoneActivity.this).load(R.drawable.ic_person).into(img_anhdaidien);
                    break;
            }
            return false;
        });
        popupMenu.show();
    }

    @OnClick(R.id.btn_change_phone)
    void onClickAccept() {
        if (edtphone.length() == 0) {
            Toast.makeText(ChangePhoneActivity.this, "Không được bỏ trống số điện thoại", Toast.LENGTH_SHORT).show();
        } else {
            database.UpdateData(edtname.getText().toString(), edtphone.getText().toString(), edtavatar.getText().toString(), -1, id);
            Toast.makeText(ChangePhoneActivity.this, "Đã thay đổi thành công", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(ChangePhoneActivity.this, DanhbaActivity.class);
            startActivity(i);
        }
    }

    @OnClick(R.id.button_cancle_change)
    void onClickBack() {
        Intent i = new Intent(ChangePhoneActivity.this, DanhbaActivity.class);
        startActivity(i);
    }

    @OnClick(R.id.img_change_avatar)
    void onClickImage() {
        menuPopup();
    }

    private void findViewByIds() {
        database = new Database(this);
        Intent intent = getIntent();
        id = intent.getStringExtra(ContactAdapter.ID);
        name = intent.getStringExtra(ContactAdapter.NAME);
        phone = intent.getStringExtra(ContactAdapter.PHONE);
        avatar = intent.getStringExtra(ContactAdapter.AVATAR);
        edtname.setText(name);
        edtphone.setText(phone);
        edtavatar.setText(avatar);

        Glide.with(this.getApplicationContext()).load(avatar)
                .placeholder(R.drawable.ic_person).into(img_anhdaidien);
    }
}