package com.baiiu.dropdownmenu.entity;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 描述：
 */
public class FilterUrl {
    private volatile static FilterUrl filterUrl;

    private FilterUrl() {
    }

    public static FilterUrl instance() {
        if (filterUrl == null) {
            synchronized (FilterUrl.class) {
                if (filterUrl == null) {
                    filterUrl = new FilterUrl();
                }
            }
        }
        return filterUrl;
    }

    public String singleListPosition;
    public String doubleListLeft;
    public String doubleListRight;
    public String singleGridPosition;
    public String doubleGridTop;
    public String doubleGridBottom;
    public List<String> listDoubleGridTopMultiSelect = new ArrayList<>();
    public List<String> listDoubleGridBottomMultiSelect = new ArrayList<>();

    public int position;
    public String positionTitle;

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();

        if (!TextUtils.isEmpty(singleListPosition)) {
            buffer.append("singleListPosition=");
            buffer.append(singleListPosition);
            buffer.append("\n");
        }


        if (!TextUtils.isEmpty(doubleListLeft)) {
            buffer.append("doubleListLeft=");
            buffer.append(doubleListLeft);
            buffer.append("\n");
        }

        if (!TextUtils.isEmpty(doubleListRight)) {
            buffer.append("doubleListRight=");
            buffer.append(doubleListRight);
            buffer.append("\n");
        }

        if (!TextUtils.isEmpty(singleGridPosition)) {
            buffer.append("singleGridPosition=");
            buffer.append(singleGridPosition);
            buffer.append("\n");
        }

        if (!TextUtils.isEmpty(doubleGridTop)) {
            buffer.append("doubleGridTop=");
            buffer.append(doubleGridTop);
            buffer.append("\n");
        }

        if (!TextUtils.isEmpty(doubleGridBottom)) {
            buffer.append("doubleGridBottom=");
            buffer.append(doubleGridBottom);
            buffer.append("\n");
        }

        if (listDoubleGridTopMultiSelect.size() > 0) {
            buffer.append("listDoubleGridTopMultiSelect=");
            buffer.append(Arrays.deepToString(listDoubleGridTopMultiSelect.toArray()));
            buffer.append("\n");
        }

        if (listDoubleGridBottomMultiSelect.size() > 0) {
            buffer.append("listDoubleGridBottomMultiSelect=");
            buffer.append(Arrays.deepToString(listDoubleGridBottomMultiSelect.toArray()));
            buffer.append("\n");
        }
        return buffer.toString();
    }

    public void clear() {
        filterUrl = null;
    }


}