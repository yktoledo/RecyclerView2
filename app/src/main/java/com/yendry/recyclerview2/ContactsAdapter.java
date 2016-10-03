package com.yendry.recyclerview2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.abc.greendaoexample.db.DaoMaster;
import com.abc.greendaoexample.db.DaoSession;
import com.abc.greendaoexample.db.User;
import com.abc.greendaoexample.db.UserDao;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by User on 10/2/2016.
 */

// Create the basic adapter extending from RecyclerView.Adapter
// Note that we specify the custom ViewHolder which gives us access to our views
public class ContactsAdapter extends
        RecyclerView.Adapter<ContactsAdapter.ViewHolder> {



    private List<User> mContacts;
    private Context mContext;
    DaoSession daoSession;

    public ContactsAdapter(Context context) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "lease-db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        UserDao dataAccessObjUser = daoSession.getUserDao();
        mContacts = dataAccessObjUser.loadAll();
        mContext = context;



    }

    private Context getContext() {
        return mContext;
    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView nameTextView;
        public Button messageButton;
        public ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.contact_name);
            messageButton = (Button) itemView.findViewById(R.id.message_button);
            image = (ImageView)itemView.findViewById(R.id.imageId);
        }
        @Override
        public void onClick(View view) {
            int position = getAdapterPosition(); // gets item position

            //RecyclerView rv = ((RecyclerView) view.findViewById(R.id.rvContacts));

            if (position != RecyclerView.NO_POSITION) { // Check if an item was deleted, but the user clicked it before the UI removed it

                removeAt(position);

            }

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_contact, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
       final User contact = mContacts.get(position);

        TextView textView = viewHolder.nameTextView;
        textView.setText(contact.getName());
        Button button = viewHolder.messageButton;
        button.setText("Delete");
        ImageView image = viewHolder.image;
        Glide.with(getContext()).load(contact.getImage()).override(150,150).centerCrop().into(image);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //removeAt(position);
                Toast.makeText(getContext(), contact.getName(), Toast.LENGTH_SHORT).show();
                Log.d("button", contact.getName().toString());
            }
        });

    }

    @Override
    public int getItemCount() {
        return mContacts.size();
       //return 0;
    }

    public void addUserInDatabase(User newUser) {

        Log.d("DDDD", newUser.getName()+newUser.getImage());

        UserDao dataAccessObjUser = daoSession.getUserDao();

        dataAccessObjUser.insert(newUser);
        mContacts = dataAccessObjUser.loadAll();
        notifyItemInserted(0);

    }
    public void removeAt(int position){
        mContacts.remove(position);
        notifyItemRemoved(position);

        //notifyItemRangeChanged(position, mContacts.size());
    }
}