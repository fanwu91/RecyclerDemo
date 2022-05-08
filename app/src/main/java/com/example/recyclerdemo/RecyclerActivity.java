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
import android.view.ViewTreeObserver;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class RecyclerActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    MyAdapter mMyAdapter;
    List<News> mNewsList = new ArrayList<>();
    private static final String TAG = "SCROLL_TAG";
    private static final String[] SCROLL_STATES = {"IDEL", "DRAGGING", "SETTING"};

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
            View view = View.inflate(RecyclerActivity.this, R.layout.item_list, null);
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
                    Toast.makeText(RecyclerActivity.this, "Clicked: " + position, Toast.LENGTH_SHORT).show();
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
        setContentView(R.layout.activity_recycler);
        mRecyclerView = findViewById(R.id.recyclerv);

        for (int i = 0; i < 50; i++) {
            News news = new News();
            news.title = "Title " + i;
            news.content = "Content: Hello " + i;
            mNewsList.add(news);
        }
        mMyAdapter = new MyAdapter();
        mRecyclerView.setAdapter(mMyAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(RecyclerActivity.this);
        DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(divider);
        mRecyclerView.addOnScrollListener(scrollListener);
    }

    RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            Log.d(TAG, "SCROLL_STATE is: " + SCROLL_STATES[newState]);

            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                if (reachBottom(recyclerView)) {
                    Log.d(TAG, "Reach Bottom End");
                } else if (reachTop(recyclerView)) {
                    Log.d(TAG, "Reach Top End");
                }
            } else {
                if (reachBottom(recyclerView)) {
                    Log.d(TAG, "Reach Bottom Start");
                } else if (reachTop(recyclerView)) {
                    Log.d(TAG, "Reach Bottom Start");
                }
            }
        }

        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
        }
    };

    private boolean reachBottom(RecyclerView recyclerView) {
        return !recyclerView.canScrollVertically(1);
//        return recyclerView != null &&
//                recyclerView.computeVerticalScrollExtent() +
//                        recyclerView.computeVerticalScrollOffset() >=
//                recyclerView.computeVerticalScrollRange();
    }

    private boolean reachTop(RecyclerView recyclerView) {
        return !mRecyclerView.canScrollVertically(-1);
    }
}
