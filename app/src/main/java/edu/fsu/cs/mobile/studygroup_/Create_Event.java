package edu.fsu.cs.mobile.studygroup_;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Create_Event extends AppCompatActivity {
    private FirebaseUser user;
    private FirebaseAuth auth;
    private DatabaseReference mDatabase;
    private String UID;
    int temp;
    private DatabaseReference mUserReference;


    //DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_event);
        // Toolbar toolbar;
        //Object id = null;
        //toolbar = (Toolbar) findViewById(id.toolbar);
        //  setSupportActionBar(toolbar);

        auth= FirebaseAuth.getInstance();
        user= auth.getCurrentUser();
        UID=user.getUid();
        mDatabase= FirebaseDatabase.getInstance().getReference();
        mUserReference= FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid());


        Button button2 = (Button) findViewById(R.id.button2);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText Eventname = (EditText) findViewById(R.id.Eventname);
                EditText Date = (EditText) findViewById(R.id.Date);
                EditText Time = (EditText) findViewById(R.id.Time);
                EditText Location = (EditText) findViewById(R.id.Location);

                String en = Eventname.getText().toString();
                String dt = Date.getText().toString();
                String tm = Time.getText().toString();
                String loc = Location.getText().toString();

                DatabaseReference database = FirebaseDatabase.getInstance().getReference();

                DatabaseReference FBEventname = database.child("Event Name");
                DatabaseReference FBDate = database.child("Date");
                DatabaseReference FBTime = database.child("Time");
                DatabaseReference FBLocation = database.child("Location");

                FBEventname.setValue(en);
                FBDate.setValue(dt);
                FBTime.setValue(tm);
                FBLocation.setValue(loc);


        //add 20 points for creating an event
                mDatabase.child("users").child(UID).child("points").setValue(temp+20);


                Intent intent1 = new Intent(Create_Event.this, Event_Created.class);
              intent1.putExtra("event", en);
              intent1.putExtra("date", dt);
              intent1.putExtra("time", tm);
              intent1.putExtra("location", loc);
              startActivity(intent1);
            }
        });
    }

    //USE THIS TO GET THE CURRENT POINTS AND THEN INCREMENT POINTS - GRACE

    public void onStart(){
        super.onStart();

        //to get the current points.
        ValueEventListener userListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //get UserInformation object and use the values to update UI
                UserInformation ui = dataSnapshot.getValue(UserInformation.class);
                temp = ui.getPoints();
                //Add 20 points for creating an event


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        mUserReference.addValueEventListener(userListener);

    }
}


