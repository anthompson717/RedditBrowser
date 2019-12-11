package com.anth.redditbrowser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class ContactListActivity extends AppCompatActivity {

    ListView contactList;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        Bundle extra = getIntent().getExtras();
        url = extra.getString("url");

        int[] to = {R.id.contact_name,R.id.contact_id};
        String[] from = {"name","id"};

        ArrayList<HashMap<String,String>> list = getList();
        SimpleAdapter simpleAdapter =
                new SimpleAdapter(this,list,R.layout.adapter_contact_item,from,to);

        contactList = findViewById(R.id.contact_list);
        contactList.setAdapter(simpleAdapter);

        setContactListListener();

    }

    private ArrayList getList() {

        ArrayList<HashMap<String,String>> list = new ArrayList<>();

        ContentResolver cr = getContentResolver();

        Cursor cursor = cr.query(
                ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        while (cursor.moveToNext()) {
            String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            Cursor cur1 = cr.query(
                    ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                    new String[]{contactId}, null);
            while (cur1.moveToNext()) {

                HashMap<String,String> hash = new HashMap<String, String>();
                hash.put("name",name);
                hash.put("id",contactId);
                String emailData = cur1.getString(cur1.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                if(emailData!=null) {
                    String email = cur1.getString(cur1.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS));
                    hash.put("email", email);
                } else {
                    hash.put("email", "");
                }
                list.add(hash);
            }
            cur1.close();
        }
        cursor.close();

        return list;
    }

    private void setContactListListener() {
        contactList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item text from ListView
                HashMap<String,String> selectedItem = (HashMap) parent.getItemAtPosition(position);

                String email = selectedItem.get("email");
                System.out.println("email: "+email);

                Intent intent = new Intent(android.content.Intent.ACTION_SEND);

                String[] emails = {email};
                intent.putExtra(Intent.EXTRA_EMAIL, emails);

                intent.putExtra(android.content.Intent.EXTRA_TEXT, url);
                intent.setType("vnd.android.cursor.dir/email");
                startActivity(intent);
            }
        });
    }
}
