package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Review extends AppCompatActivity implements View.OnClickListener {

   private RatingBar ratingBar;
   private EditText editText;
   private TextView textView;
   private Button submit_button;
   private Button cancel_button;
   private FirebaseDatabase firebaseDatabase;
   private DatabaseReference databaseReference;

   private String userId;
   private String review_text;
   private float review_stars;
   int cnt=2;

   static ArrayList<String> arrayIndex = new ArrayList<String>();
   static ArrayList<String> arrayData = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //리뷰 액티비티 구현
        setContentView(R.layout.activity_review);

        //참조
        textView = (TextView)findViewById(R.id.review_textview);
        ratingBar = (RatingBar)findViewById(R.id.rating_bar);
        editText = (EditText)findViewById(R.id.edit_review);
        cancel_button = (Button) findViewById(R.id.cancel_button);
        cancel_button.setOnClickListener((View.OnClickListener) this);
        submit_button = (Button) findViewById(R.id.submit_button);
        submit_button.setOnClickListener((View.OnClickListener) this);

        //버튼 클릭 이벤트 구현
        submit_button.setOnClickListener(this);
        cancel_button.setOnClickListener(this);

    }

    public void postFirebaseDatabase(boolean add){
        databaseReference = FirebaseDatabase.getInstance().getReference();
        Map<String,Object> childUpdates = new HashMap<>();
        Map<String,Object> postValues = null;

        if(add){
            Post post = new Post(review_stars, review_text);
            postValues = post.toMap();
        }

        childUpdates.put("/perfume/perfume_01/review/"+userId,postValues);
        databaseReference.updateChildren(childUpdates);

    }
    @Override
    public void onClick(View v)
    {
        if (v == submit_button) {
            userId = "user" + cnt;
            cnt++;
            review_text = editText.getText().toString();
            review_stars=ratingBar.getRating();
            postFirebaseDatabase(true);

        }
        else if (v == cancel_button)
            finish(); //액티비티 종료
    }
}