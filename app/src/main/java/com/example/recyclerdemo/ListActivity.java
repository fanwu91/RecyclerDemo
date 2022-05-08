package com.example.recyclerdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

public class ListActivity extends AppCompatActivity {
    private String[] data = {
            "Apple", "Banana", "Orange", "Watermelon", "Pear", "Grape", "Pineapple",
            "Strawberry", "Cherry", "Mango", "Durian", "Dragon Fruit", "Blueberry",
            "Blackberry", "Apple", "Banana", "Orange", "Watermelon", "Pear", "Grape",
            "Pineapple", "Strawberry", "Cherry", "Mango", "Durian", "Dragon Fruit",
            "Blueberry", "Blackberry",
            "Apple", "Banana", "Orange", "Watermelon", "Pear", "Grape", "Pineapple",
            "Strawberry", "Cherry", "Mango", "Durian", "Dragon Fruit", "Blueberry",
            "Blackberry", "Apple", "Banana", "Orange", "Watermelon", "Pear", "Grape",
            "Pineapple", "Strawberry", "Cherry", "Mango", "Durian", "Dragon Fruit",
            "Blueberry", "Blackberry"
    };

    private String[] scrollStates = { "IDEL", "SCROLL", "FLYING" };

    private final static String SCROLL_TAG = "SCROLL_TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                ListActivity.this,
                android.R.layout.simple_list_item_1,
                data);

        FruitListView fruitListView = (FruitListView) findViewById(R.id.fruit);

        fruitListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int scrollState) {
                Log.d(SCROLL_TAG, scrollStates[scrollState]);
                switch (scrollState) {
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                        if (reachTop(absListView)) {
                            Log.d(SCROLL_TAG, "Reach Top End");
                        } else if (reachBottom(absListView)) {
                            Log.d(SCROLL_TAG, "Reach Bottom End");
                        }
                        break;
                    case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
                    case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                        if (reachTop(absListView)) {
                            Log.d(SCROLL_TAG, "Reach Top Start");
                        } else if (reachBottom(absListView)) {
                            Log.d(SCROLL_TAG, "Reach Bottom Start");
                        }
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                if (firstVisibleItem == 0) {
                    View firstVisibleItemView = absListView.getChildAt(0);
                    // distance from its parent in pixels
                    if (firstVisibleItemView != null && firstVisibleItemView.getTop() == 0) {
                        Toast.makeText(ListActivity.this, "Reach Top", Toast.LENGTH_SHORT);
                        Log.d("ListView", "Reach Top");
                    }
                } else if ((firstVisibleItem + visibleItemCount) == totalItemCount) {
                    View lastVisibleItemView = absListView.getChildAt(absListView.getChildCount() - 1);
                    if (lastVisibleItemView != null && lastVisibleItemView.getBottom() == absListView.getHeight()) {
                        Toast.makeText(ListActivity.this, "Reach bottom", Toast.LENGTH_SHORT);
                        Log.d("ListView", "Reach Bottom");
                    }
                }
            }

            private boolean reachTop(AbsListView absListView) {
                View firstVisibleItemView = absListView.getChildAt(0);
                return firstVisibleItemView != null && firstVisibleItemView.getTop() == 0;
            }

            private boolean reachBottom(AbsListView absListView) {
                View lastVisibleItemView = absListView.getChildAt(absListView.getChildCount() - 1);
                return lastVisibleItemView != null && lastVisibleItemView.getBottom() == absListView.getHeight();
            }
        });
        fruitListView.setAdapter(adapter);
    }
}
