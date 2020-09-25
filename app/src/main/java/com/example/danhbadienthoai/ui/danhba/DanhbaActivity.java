package com.example.danhbadienthoai.ui.danhba;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.danhbadienthoai.R;
import com.example.danhbadienthoai.ui.addphone.AddPhoneActivity;
import com.example.danhbadienthoai.data.db.Database;
import com.example.danhbadienthoai.data.db.model.Contact;
import com.example.danhbadienthoai.utils.ContactUtils;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;


import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static android.Manifest.permission.READ_CONTACTS;

public class DanhbaActivity extends AppCompatActivity implements DanhbaMvpView {
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    Database database;
    ArrayList<Contact> contactList;
    ContactAdapter contactAdapter;
    DanhbaPresenter danhbaPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_ba);
        recyclerView = findViewById(R.id.recycler_view);
        new ItemTouchHelper(itemtouchhelper).attachToRecyclerView(recyclerView);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, linearLayoutManager.getOrientation()));
        danhbaPresenter = new DanhbaPresenter(this, this);

        Dexter.withActivity(this)
                .withPermission(READ_CONTACTS)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        if (response.getPermissionName().equals(READ_CONTACTS)) {
//                            danhbaPresenter.onAddData();
//                            danhbaPresenter.onLoadData();
                            addContact();
                            loaddata();
                        }
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        Toast.makeText(DanhbaActivity.this, "Need Permiss", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();


    }

    public ItemTouchHelper.SimpleCallback itemtouchhelper = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            database = new Database(DanhbaActivity.this);
            Toast.makeText(DanhbaActivity.this, "Delete Success: " + contactList.get(viewHolder.getBindingAdapterPosition()).getId(), Toast.LENGTH_SHORT).show();
            database.DeleteData(contactList.get(viewHolder.getBindingAdapterPosition()).getId());
            contactList.remove(viewHolder.getBindingAdapterPosition());
            contactAdapter.notifyDataSetChanged();
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_danh_ba, menu);

        MenuItem searchItem = menu.findItem(R.id.item_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Search");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                contactAdapter.getFilter().filter(s);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_add:
                danhbaPresenter.onClickMenuAddPhone();
                return true;

            case R.id.item_thoat:
                danhbaPresenter.onClickMenuExit();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showDiaglog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Bạn có muốn thoát không?");
        builder.setPositiveButton("Exit", (dialogInterface, i) -> System.exit(1));
        builder.setNegativeButton("Cancle", (dialogInterface, i) -> {

        });
        builder.show();
    }

    @Override
    public void showAddPhone() {
        Intent intent = new Intent(this, AddPhoneActivity.class);
        startActivity(intent);
    }

    @SuppressLint("CheckResult")
    @Override
    public void showLoadDataSuccessed(ArrayList<Contact> contactList) {
        contactList = ContactUtils.sortList(contactList);
        contactList = ContactUtils.addAlpha(contactList);
        contactAdapter = new ContactAdapter(DanhbaActivity.this, contactList);
        recyclerView.setAdapter(contactAdapter);
    }

    @Override
    public void showLoadDataFailed() {
        Toast.makeText(DanhbaActivity.this, "No Data", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void addData(String id, String name, String phone, String avatar) {
        danhbaPresenter.addData(id, name, phone, avatar);
    }

    String id1, name1, phone1, avatar1;

    private void addContact() {
        contactList = new ArrayList<>();
        database = new Database(this);
        Cursor cursor2 = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        while (cursor2.moveToNext()) {
            id1 = cursor2.getString(cursor2.getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID));
            name1 = cursor2.getString(cursor2.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            phone1 = cursor2.getString(cursor2.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            avatar1 = cursor2.getString(cursor2.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));
            database.addData(id1, name1, phone1, avatar1, -1);
            Contact contact = new Contact(id1, name1, phone1, avatar1, -1);
            contactList.add(contact);
        }
    }

    public void loaddata() {
        database = new Database(DanhbaActivity.this);
        ArrayList<Contact> contactArrayList = new ArrayList<>();
        contactArrayList = new ArrayList<>();
        Cursor cursor = database.readAllData();
        if (cursor.getCount() == 0) {
            Toast.makeText(DanhbaActivity.this, "No Data", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                Contact contact = new Contact(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getInt(4));
                contactArrayList.add(contact);
                contactList = ContactUtils.sortList(contactArrayList);
                contactList = ContactUtils.addAlpha(contactArrayList);
                contactAdapter = new ContactAdapter(DanhbaActivity.this, contactList);
                recyclerView.setAdapter(contactAdapter);
            }
        }
    }
}