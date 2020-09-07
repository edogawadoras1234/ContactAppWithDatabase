package com.example.danhbadienthoai;


import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.CALL_PHONE;

public class ContactAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {
    Context context;
    List<Contact> contactList;
    List<Contact> contactListfull;
    danhba danhba;
    public static final String ID = "ID";
    public static final String NAME = "NAME";
    public static final String PHONE = "PHONE";
    public static final String AVATAR = "AVATAR";
    final int MY_PERMISSIONS_REQUEST_CALL_PHONE=1000;
   public ContactAdapter (Context context, List<Contact> contactList){
        this.context = context;
        this.contactList = contactList;
        this.contactListfull = new ArrayList<>(contactList);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        if (viewType == Common.VIEWTYPE_GROUP) {
            ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.group_layout, parent, false);
            GroupViewHolder groupViewHolder = new GroupViewHolder(viewGroup);

            return groupViewHolder;
        }
        else if(viewType == Common.VIEWTYPE_CONTACT) {
            ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.contact_layout, parent, false);
            ContactViewHolder contactViewHolder = new ContactViewHolder(viewGroup);
            return contactViewHolder;
        }
        else{
            ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.group_layout, parent, false);
            GroupViewHolder groupViewHolder = new GroupViewHolder(viewGroup);

            return groupViewHolder;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return contactList.get(position).getViewType();
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof ContactViewHolder){
            final ContactViewHolder contactViewHolder = (ContactViewHolder) holder;
            contactViewHolder.txt_name.setText(contactList.get(position).getName());
            contactViewHolder.txt_phone.setText(contactList.get(position).getPhone());
            final String avatar = contactList.get(position).getAvatar();

            ColorGenerator generator = ColorGenerator.MATERIAL;
            TextDrawable drawable = TextDrawable.builder().buildRound(String.valueOf(contactList.get(position).getName()
                    .charAt(0)),generator.getRandomColor());

            TextDrawable avatar2 = TextDrawable.builder().buildRound(String.valueOf(contactListfull.get(position).getAvatar()),generator.getRandomColor());

             // contactViewHolder.imgAvatar.setImageDrawable(drawable);
                Glide.with(context.getApplicationContext()).load(avatar)
                  .placeholder(drawable).into(contactViewHolder.imgAvatar);

            contactViewHolder.btngoi.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel: " + contactList.get(position).getPhone()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    if (ContextCompat.checkSelfPermission(context,
                            Manifest.permission.CALL_PHONE)
                            != PackageManager.PERMISSION_GRANTED) {

                        ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CALL_PHONE},
                                MY_PERMISSIONS_REQUEST_CALL_PHONE);

                    } else {
                        try {
                            context.startActivity(intent);
                        } catch (SecurityException e) {
                            e.printStackTrace();

                        }
                    }
                }
            });

            contactViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, activity_change_phone.class);
                    intent.putExtra(ID,contactList.get(position).getId());
                    intent.putExtra(NAME,contactList.get(position).getName());
                    intent.putExtra(PHONE,contactList.get(position).getPhone());
                    intent.putExtra(AVATAR,contactList.get(position).getAvatar());
                    context.startActivity(intent);
                }
            });
        }
            else if(holder instanceof GroupViewHolder){
            GroupViewHolder groupViewHolder = (GroupViewHolder) holder;
            groupViewHolder.txt_group_title.setText(contactList.get(position).getName());
            groupViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }
    public void remove(int pos){
        contactList.remove(pos);
        notifyItemRemoved(pos);
    }
    @Override
    public int getItemCount() {
        return contactList.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }
    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Contact> listFilter = new ArrayList<>();
            if (charSequence == null || charSequence.length() == 0){
                listFilter.addAll(contactListfull);
            }else{
                String filterPatern = charSequence.toString().toLowerCase().trim();
                for (Contact item : contactListfull){
                    if(item.getName().toLowerCase().contains(filterPatern)){
                        listFilter.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = listFilter;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults results) {
            contactList.clear();
            contactList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    private class GroupViewHolder extends RecyclerView.ViewHolder{

        TextView txt_group_title,txttext;
        public GroupViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_group_title = itemView.findViewById(R.id.txt_group_title);
        }
    }

    private class ContactViewHolder extends RecyclerView.ViewHolder{
        TextView txt_name, txt_phone;
        ImageView imgAvatar;
        Button btngoi;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_name = itemView.findViewById(R.id.txtten);
            txt_phone = itemView.findViewById(R.id.txtsdt);
            imgAvatar = (ImageView) itemView.findViewById(R.id.anhdaidien);
            btngoi = itemView.findViewById(R.id.btngoidien);
        }
    }
}

