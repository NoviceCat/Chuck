package com.yxsh.uibase.widget.picker.common;

import com.yxsh.uibase.widget.picker.inner.WheelAdapter;

import java.util.List;

/**
 * The simple Array wheel adapter
 *
 * @param <T> the element type
 * @author novic
 * @date 2020/1/31
 */
public class ArrayWheelAdapter<T> implements WheelAdapter {


    // items
    private List<T> items;

    /**
     * Constructor
     *
     * @param items the items
     */
    public ArrayWheelAdapter(List<T> items) {
        this.items = items;

    }

    @Override
    public Object getItem(int index) {
        if (index >= 0 && index < items.size()) {
            return items.get(index);
        }
        return "";
    }

    @Override
    public int getItemsCount() {
        return items.size();
    }

    @Override
    public int indexOf(Object o) {
        return items.indexOf(o);
    }

}

