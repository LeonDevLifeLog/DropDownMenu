package com.baiiu.dropdownmenu.view.betterDoubleGrid;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
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
 * auther: baiiu
 * time: 16/6/5 05 23:03
 * description:
 */
public class BetterDoubleGridView extends LinearLayout implements View.OnClickListener {

    public final static int SINGLE_SELECT = 0;
    public final static int MULTI_SELECT = 1;

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    private List<String> mTopGridData;
    private List<String> mBottomGridData;
    private OnFilterDoneListener mOnFilterDoneListener;

    private int selectMode = SINGLE_SELECT;
    private TextView mTopSelectedTextView;
    private TextView mBottomSelectedTextView;
    private List<TextView> mTopSelectedTextViews = new ArrayList<>();
    private List<TextView> mBottomSelectedTextViews = new ArrayList<>();

    public BetterDoubleGridView(Context context) {
        this(context, null);
    }

    public BetterDoubleGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BetterDoubleGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BetterDoubleGridView(Context context, AttributeSet attrs, int defStyleAttr,
                                int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    public int getSelectMode() {
        return selectMode;
    }

    public BetterDoubleGridView setSelectMode(int selectMode) {
        this.selectMode = selectMode;
        return this;
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

    public BetterDoubleGridView setmBottomGridData(List<String> mBottomGridData) {
        this.mBottomGridData = mBottomGridData;
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
        recyclerView.setAdapter(new DoubleGridAdapter(getContext(), mTopGridData, mBottomGridData, this));

        return this;
    }

    @Override
    public void onClick(View v) {

        TextView textView = (TextView) v;
        String text = (String) textView.getTag();

        if (getSelectMode() == SINGLE_SELECT) {
            if (textView == mTopSelectedTextView) {
                mTopSelectedTextView = null;
                textView.setSelected(false);
            } else if (textView == mBottomSelectedTextView) {
                mBottomSelectedTextView = null;
                textView.setSelected(false);
            } else if (mTopGridData.contains(text)) {
                if (mTopSelectedTextView != null) {
                    mTopSelectedTextView.setSelected(false);
                }
                mTopSelectedTextView = textView;
                textView.setSelected(true);
            } else {
                if (mBottomSelectedTextView != null) {
                    mBottomSelectedTextView.setSelected(false);
                }
                mBottomSelectedTextView = textView;
                textView.setSelected(true);
            }
        } else {
            if (mTopSelectedTextViews.contains(textView)) {
                mTopSelectedTextViews.remove(textView);
                textView.setSelected(false);
            } else if (mBottomSelectedTextViews.contains(textView)) {
                mBottomSelectedTextViews.remove(textView);
                textView.setSelected(false);
            } else if (mTopGridData.contains(text) && !mTopSelectedTextViews.contains(textView)) {
                mTopSelectedTextViews.add(textView);
                textView.setSelected(true);
            } else if (mBottomGridData.contains(text) && !mBottomSelectedTextViews.contains(textView)) {
                mBottomSelectedTextViews.add(textView);
                textView.setSelected(true);
            }
        }
    }


    public BetterDoubleGridView setOnFilterDoneListener(OnFilterDoneListener listener) {
        mOnFilterDoneListener = listener;
        return this;
    }

    @OnClick(R.id.bt_confirm)
    public void clickDone() {

        if (getSelectMode() == SINGLE_SELECT) {
            FilterUrl.instance().doubleGridTop = mTopSelectedTextView == null ? "" : (String) mTopSelectedTextView.getTag();
            FilterUrl.instance().doubleGridBottom = mBottomSelectedTextView == null ? "" : (String) mBottomSelectedTextView.getTag();
        } else {
            List<String> tmp = new ArrayList<>();
            if (mTopSelectedTextViews.size() > 0) {
                for (TextView t : mTopSelectedTextViews) {
                    tmp.add(t.getText().toString());
                }
                FilterUrl.instance().listDoubleGridTopMultiSelect.addAll(tmp);
            }
            tmp.clear();
            if (mBottomSelectedTextViews.size() > 0) {
                for (TextView t : mBottomSelectedTextViews) {
                    tmp.add(t.getText().toString());
                }
                FilterUrl.instance().listDoubleGridBottomMultiSelect.addAll(tmp);
            }
        }
        if (mOnFilterDoneListener != null) {
            mOnFilterDoneListener.onFilterDone(3, "", "");
        }
    }


}
