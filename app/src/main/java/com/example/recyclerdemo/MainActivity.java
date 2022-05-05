package com.example.recyclerdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    MyAdapter mMyAdapter;
    List<News> mNewsList = new ArrayList<>();
    private static final String TAG = "RECYCLE_V_TAG";

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mTitleTv;
        TextView mContentTv;
        ConstraintLayout mRootView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mTitleTv = itemView.findViewById(R.id.tv1);
            mContentTv = itemView.findViewById(R.id.tv2);
            mRootView = itemView.findViewById(R.id.rootview);
        }
    }

    class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = View.inflate(MainActivity.this, R.layout.item_list, null);
            MyViewHolder myviewHolder = new MyViewHolder(view);
            return myviewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            News news = mNewsList.get(position);
            holder.mTitleTv.setText(news.title);
            holder.mContentTv.setText(news.content);
            holder.mRootView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    Toast.makeText(MainActivity.this, "Clicked: " + position, Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return mNewsList.size();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.recyclerv);

        for (int i = 0; i < 50; i++) {
            News news = new News();
            news.title = "Title " + i;
            news.content = "Content: Hello " + i;
            mNewsList.add(news);
        }
        mMyAdapter = new MyAdapter();
        mRecyclerView.setAdapter(mMyAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
//        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(divider);
        mRecyclerView.addOnScrollListener(scrollListener);
    }

    RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);

            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                Log.d(TAG, "SCROLL_STATE_IDLE=" + (newState == RecyclerView.SCROLL_STATE_IDLE));

                if (!mRecyclerView.canScrollVertically(1)) {
                    Log.d(TAG, "Reach Bottom");
                    Toast.makeText(MainActivity.this, "Bottom reached", Toast.LENGTH_SHORT).show();
                } else if (!mRecyclerView.canScrollVertically(-1)) {
                    Log.d(TAG, "Reach Top");
                    Toast.makeText(MainActivity.this, "Top reached", Toast.LENGTH_SHORT).show();
                }
            }
        }

//        @Override
//        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//            super.onScrolled(recyclerView, dx, dy);
//        }
    };
}