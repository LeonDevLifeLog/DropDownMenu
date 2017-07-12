package com.baiiu.dropdownmenu.view.betterDoubleGrid;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.baiiu.dropdownmenu.R;
import com.baiiu.dropdownmenu.entity.FilterUrl;
import com.baiiu.filter.interfaces.OnFilterDoneListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @auther: Leon
 * time: 2017-07-12 18:33
 * description:
 */

public class BetterDoubleGridMultiSelectView extends BetterDoubleGridView {
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    private List<String> mTopGridData;
    private List<String> mBottomGridList;
    private OnFilterDoneListener mOnFilterDoneListener;
    private List<TextView> mTopSelectedTextView = new ArrayList<>();
    private List<TextView> mBottomSelectedTextView = new ArrayList<>();

    public BetterDoubleGridMultiSelectView(Context context) {
        super(context);
    }

    public BetterDoubleGridMultiSelectView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BetterDoubleGridMultiSelectView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public BetterDoubleGridMultiSelectView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        setBackgroundColor(Color.WHITE);
        inflate(context, R.layout.merge_filter_double_grid, this);
        ButterKnife.bind(this, this);
    }

    public BetterDoubleGridView setmTopGridData(List<String> mTopGridData) {
        this.mTopGridData = mTopGridData;
        return this;
    }

    public BetterDoubleGridView setmBottomGridList(List<String> mBottomGridList) {
        this.mBottomGridList = mBottomGridList;
        return this;
    }

    public BetterDoubleGridView build() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getContext(), 4);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position == 0 || position == mTopGridData.size() + 1) {
                    return 4;
                }
                return 1;
            }
        });
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(new DoubleGridAdapter(getContext(), mTopGridData, mBottomGridList, this));
        return this;
    }

    @Override
    public void onClick(View v) {
        TextView textView = (TextView) v;
        String text = (String) textView.getTag();

        if (mTopSelectedTextView.contains(textView)) {
            mTopSelectedTextView.remove(textView);
            textView.setSelected(false);
        } else if (mBottomSelectedTextView.contains(textView)) {
            mBottomSelectedTextView.remove(textView);
            textView.setSelected(false);
        } else if (mTopGridData.contains(text) && !mTopSelectedTextView.contains(textView)) {
            mTopSelectedTextView.add(textView);
            textView.setSelected(true);
        } else if (mBottomGridList.contains(text) && !mBottomSelectedTextView.contains(textView)) {
            mBottomSelectedTextView.add(textView);
            textView.setSelected(true);
        }
    }


    public BetterDoubleGridView setOnFilterDoneListener(OnFilterDoneListener listener) {
        mOnFilterDoneListener = listener;
        return this;
    }

    @OnClick(R.id.bt_confirm)
    public void clickDone() {
        List<String> tmp = new ArrayList<>();
        for (TextView t : mTopSelectedTextView) {
            tmp.add(t.getText().toString());
        }
        FilterUrl.instance().listDoubleGridTopMultiSelect = tmp;
        tmp.clear();
        for (TextView t : mBottomSelectedTextView) {
            tmp.add(t.getText().toString());
        }
        FilterUrl.instance().listDoubleGridBottomMultiSelect = tmp;
        if (mOnFilterDoneListener != null) {
            mOnFilterDoneListener.onFilterDone(3, "", "");
        }
    }

}
