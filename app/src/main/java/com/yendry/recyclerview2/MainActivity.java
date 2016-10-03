package com.yendry.recyclerview2;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.abc.greendaoexample.db.DaoMaster;
import com.abc.greendaoexample.db.DaoSession;
import com.abc.greendaoexample.db.User;
import com.abc.greendaoexample.db.UserDao;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ArrayList<User> contacts;
    Button btn;
    RecyclerView rvContacts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = (Button) findViewById(R.id.button2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), AddUser.class);
                startActivityForResult(i,1);
            }
        });
        rvContacts = (RecyclerView) findViewById(R.id.rvContacts);


        ContactsAdapter adapter = new ContactsAdapter(this);

        rvContacts.setAdapter(adapter);

        rvContacts.setLayoutManager(new LinearLayoutManager(this));


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (data!=null) {
                String name = data.getStringExtra("name");
                String image = data.getStringExtra("image");
                User user = new User();
                user.setName(name);
                user.setImage(image);
                ContactsAdapter adapter = ((ContactsAdapter) rvContacts.getAdapter());
                adapter.addUserInDatabase(user);
                rvContacts.setAdapter(adapter);

            }
        }

    }


}
