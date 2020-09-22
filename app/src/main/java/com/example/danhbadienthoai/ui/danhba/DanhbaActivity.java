package com.example.danhbadienthoai.ui.danhba;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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

        new ItemTouchHelper(itemtouchhelper).attachToRecyclerView(recyclerView);
        findViewByIds();

    }

    public ItemTouchHelper.SimpleCallback itemtouchhelper = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            database = new Database(DanhbaActivity.this);
            Toast.makeText(DanhbaActivity.this, "Delete Success: " + contactList.get(viewHolder.getAdapterPosition()).getId(), Toast.LENGTH_SHORT).show();
            database.DeleteData(contactList.get(viewHolder.getAdapterPosition()).getId());
            contactList.remove(viewHolder.getAdapterPosition());
            contactAdapter.notifyDataSetChanged();
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_danh_ba, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
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
            case R.id.itemadd:
                danhbaPresenter.onClickMenuAddPhone();
                return true;

            case R.id.itemthoat:
                danhbaPresenter.onClickMenuExit();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showDiaglog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Bạn có muốn thoát không?");
        builder.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                System.exit(1);
            }
        });
        builder.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();
    }

    @Override
    public void showAddPhone() {
        Intent intent = new Intent(this, AddPhoneActivity.class);
        startActivity(intent);
    }

    @Override
    public void showLoadDataSuccessed(ArrayList<Contact> contactList) {
        contactList = ContactUtils.sortList(contactList);
        contactList = ContactUtils.addAlpha(contactList);
        contactAdapter = new ContactAdapter(this, contactList);
        recyclerView.setAdapter(contactAdapter);
    }

    @Override
    public void showLoadDataFailed() {
        Toast.makeText(DanhbaActivity.this, "No Data", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void addData(String id, String name, String phone, String avatar) {
        database = new Database(this);
        danhbaPresenter.addData(id, name, phone, avatar);
    }

    private void findViewByIds() {
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
                            danhbaPresenter.onAddData();
                            danhbaPresenter.onLoadData();
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
}
