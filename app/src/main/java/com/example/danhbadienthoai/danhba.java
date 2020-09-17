package com.example.danhbadienthoai;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
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

import com.example.danhbadienthoai.adapter.ContactAdapter;
import com.example.danhbadienthoai.db.Database;
import com.example.danhbadienthoai.model.Contact;
import com.example.danhbadienthoai.utils.Common;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;

import static android.Manifest.permission.READ_CONTACTS;

public class danhba extends AppCompatActivity {
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    ArrayList<Contact> arrayList= new ArrayList<>();
    Database database;
    ArrayList<Contact> contactList;
    ContactAdapter contactAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.danhba);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManagerSmooth(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, linearLayoutManager.getOrientation()));


        Dexter.withActivity(this)
                .withPermission(READ_CONTACTS)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        if (response.getPermissionName().equals(READ_CONTACTS)) {
                            addContact();
                            loaddata();
                        }
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        Toast.makeText(danhba.this, "Need Permiss", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();


        new ItemTouchHelper(itemtouchhelper).attachToRecyclerView(recyclerView);
    }
    public ItemTouchHelper.SimpleCallback itemtouchhelper = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            database = new Database(danhba.this);
            Toast.makeText(danhba.this, "Delete Success: " + contactList.get(viewHolder.getAdapterPosition()).getId(), Toast.LENGTH_SHORT).show();
            database.DeleteData(contactList.get(viewHolder.getAdapterPosition()).getId());
            remove(viewHolder.getAdapterPosition());
        }
    };
    public void remove(int pos) {
        contactList.remove(pos);
        contactAdapter.notifyItemRemoved(pos);
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
            Contact contact = new Contact(id1, name1, phone1, avatar1,-1);
            database.addData(id1, name1, phone1, avatar1,-1);
            contactList.add(contact);
        }
    }
    public void loaddata(){
        database = new Database(danhba.this);
        ArrayList<Contact> contactArrayList = new ArrayList<>();
        contactArrayList = new ArrayList<>();
        Cursor cursor = database.readAllData();
        if (cursor.getCount() == 0) {
            Toast.makeText(danhba.this, "No Data", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                Contact contact = new Contact(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3),cursor.getInt(4));
                contactArrayList.add(contact);
            }
        }
        contactList = Common.sortList(contactArrayList);
        contactList = Common.addAlpha(contactArrayList);
        contactAdapter = new ContactAdapter(danhba.this,contactList);
        recyclerView.setAdapter(contactAdapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

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
                Intent intent = new Intent(this, add_phone_number.class);
                startActivity(intent);
                return true;

            case R.id.itemthoat:
                DigalogThoat();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void DigalogThoat() {
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
}
