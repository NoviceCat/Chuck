package com.yxsh.uibase.widget.dot;

/**
 * @author novic
 * @date 2020/2/18
 */
public interface PagerIndicator {

    void setNum(int num);

    int getTotal();

    void setSelected(int index);

    int getCurrentIndex();

}
