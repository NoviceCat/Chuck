package com.zxtx.uibase.widget.wheel;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.os.Handler;
import android.os.Message;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.Scroller;


import com.zxtx.uibase.R;

import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Numeric wheel view.
 *
 * @author Yuri Kanivets
 */
public class WheelView extends View {
    /**
     * Scrolling duration
     */
    private final int SCROLLING_DURATION = 200;
    /**
     * Minimum delta for scrolling
     */
    private final int MIN_DELTA_FOR_SCROLLING = 1;
    /**
     * Current value & label text color
     */
    private int mValueTextColor = getResources().getColor(R.color.c_323233);
    /**
     * Items text color
     */
    // private final int ITEMS_TEXT_COLOR = 0xFF000000;
    private final int ITEMS_TEXT_COLOR = getResources().getColor(R.color.c_656566);
    /**
     * Top and bottom shadows colors
     */
    private final int[] SHADOWS_COLORS = new int[]{0xFF111111, 0x00AAAAAA,
            0x00AAAAAA};
    /**
     * Additional width for items layout
     */
    private final int ADDITIONAL_ITEMS_SPACE = 20;
    /**
     * Label offset
     */
    // private final int LABEL_OFFSET = 8;
    private final int LABEL_OFFSET = 8;
    /**
     * Left and right padding value
     */
    private final int PADDING = 10;
    /**
     * Default count of visible items
     */
    private final int DEF_VISIBLE_ITEMS = 5;
    // Messages
    private final int MESSAGE_SCROLL = 0;
    private final int MESSAGE_JUSTIFY = 1;
    // Cyclic
    private boolean isCyclic = true;
    /**
     * Additional items height (is added to standard text item height)
     */
    private int ADDITIONAL_ITEM_HEIGHT = 16;
    /**
     * Text size
     */
    private int TEXT_SIZE = 16;
    /**
     * Top and bottom items offset (to hide that)
     */
    private int ITEM_OFFSET = TEXT_SIZE / 4;
    // Wheel Values
    private String[] adapter = null;
    private int currentItem = -1;
    // Widths
    private int itemsWidth = 0;
    private int labelWidth = 0;
    // Count of visible items
    private int visibleItems = DEF_VISIBLE_ITEMS;
    // Item height
    private int itemHeight = 0;
    // Text paints
    private Paint linePaint;
    private TextPaint itemsPaint;
    private TextPaint valuePaint;
    // Layouts

    private Layout itemsLayout;
    private Layout labelLayout;
    private Layout valueLayout;
    // Label & background
    private String label;
    private Drawable centerDrawable;
    // Shadows drawables
    private GradientDrawable topShadow;
    private GradientDrawable bottomShadow;
    // Scrolling
    private boolean isScrollingPerformed;
    private int scrollingOffset;
    // Scrolling animation
    private GestureDetector gestureDetector;
    private Scroller scroller;
    private int lastScrollY;

    private int linePaintColor = R.color.line2; // ???????????????

    // Listeners
    private List<OnWheelChangedListener> changingListeners = new LinkedList<OnWheelChangedListener>();
    private OnWheelScrollListener scrollListener;
    // ???????????????
    private boolean canClick = true;
    private Timer timer;
    // gesture listener
    private int wordLimit = 0;
    private SimpleOnGestureListener gestureListener = new SimpleOnGestureListener() {
        @Override
        public boolean onDown(MotionEvent e) {
            if (isScrollingPerformed) {
                scroller.forceFinished(true);
                clearMessages();
                return true;
            }
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2,
                                float distanceX, float distanceY) {
            /*
             * if(!isCyclic){ int maxY = isCyclic ? 0x7FFFFFFF : adapter.length
             * * getItemHeight(); int minY = isCyclic ? -maxY : 0; if(e2.getY()
             * <= minY || e2.getY()>=maxY){ return true; } }
             */
            startScrolling();
            doScroll((int) -distanceY);

            // invalidateLayouts();
            // invalidate();
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                               float velocityY) {
            lastScrollY = currentItem * getItemHeight() + scrollingOffset;
            int maxY = isCyclic ? 0x7FFFFFFF : adapter.length * getItemHeight();
            int minY = isCyclic ? -maxY : 0;
            scroller.fling(0, lastScrollY, 0, (int) -velocityY / 2, 0, 0, minY,
                    maxY);
            setNextMessage(MESSAGE_SCROLL);
            return true;
        }
    };
    // animation handler
    private Handler animationHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            scroller.computeScrollOffset();
            int currY = scroller.getCurrY();
            int delta = lastScrollY - currY;
            lastScrollY = currY;
            if (delta != 0) {
                doScroll(delta);
            }

            // scrolling is not finished when it comes to final Y
            // so, finish it manually
            if (Math.abs(currY - scroller.getFinalY()) < MIN_DELTA_FOR_SCROLLING) {
                currY = scroller.getFinalY();
                scroller.forceFinished(true);
            }
            if (!scroller.isFinished()) {
                animationHandler.sendEmptyMessage(msg.what);
            } else if (msg.what == MESSAGE_SCROLL) {
                justify();
            } else {
                finishScrolling();
            }
        }
    };

    /**
     * Constructor
     */
    public WheelView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initData(context);
    }

    /**
     * Constructor
     */
    public WheelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData(context);
    }

    /**
     * Constructor
     */
    public WheelView(Context context) {
        super(context);
        initData(context);
    }

    /**
     * Initializes class data
     *
     * @param context the context
     */
    private void initData(Context context) {
        float density = getResources().getDisplayMetrics().density;
        ADDITIONAL_ITEM_HEIGHT = (int) (density * ADDITIONAL_ITEM_HEIGHT);
        TEXT_SIZE = (int) (density * TEXT_SIZE);
        ITEM_OFFSET = TEXT_SIZE / 4;
        gestureDetector = new GestureDetector(context, gestureListener);
        gestureDetector.setIsLongpressEnabled(false);
        scroller = new Scroller(context);

    }

    public void setAdapter(int[] adapter) {
        String[] s = new String[adapter.length];
        for (int i = 0; i < adapter.length; i++) {
            s[i] = String.valueOf(adapter[i]);
        }
        setAdapter(s);
    }

    /**
     * Gets wheel adapter
     *
     * @return the adapter
     */
    public String[] getAdapter() {
        return adapter;
    }

    /**
     * Sets wheel adapter
     *
     * @param adapter the new wheel adapter
     */
    public void setAdapter(String[] adapter) {
        this.adapter = adapter;
        invalidateLayouts();
        invalidate();
    }

    /**
     * Set the the specified scrolling interpolator
     *
     * @param interpolator the interpolator
     */
    public void setInterpolator(Interpolator interpolator) {
        scroller.forceFinished(true);
        scroller = new Scroller(getContext(), interpolator);
    }

    /**
     * Gets count of visible items
     *
     * @return the count of visible items
     */
    public int getVisibleItems() {
        return visibleItems;
    }

    /**
     * Sets count of visible items
     *
     * @param count the new count
     */
    public void setVisibleItems(int count) {
        visibleItems = count;
        invalidate();
    }

    /**
     * Gets label
     *
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * Sets label
     *
     * @param newLabel the label to set
     */
    public void setLabel(String newLabel) {
        if (label == null || !label.equals(newLabel)) {
            label = newLabel;
            labelLayout = null;
            invalidate();
        }
    }

    /**
     * Adds wheel changing listener
     *
     * @param listener the listener
     */
    public void addChangingListener(OnWheelChangedListener listener) {
        changingListeners.add(listener);
    }

    /**
     * ??????????????????
     *
     * @param wordLimit the listener
     */
    public void setWordLimit(int wordLimit) {
        this.wordLimit = wordLimit;
    }

    /**
     * Notifies changing listeners
     *
     * @param oldValue the old wheel value
     * @param newValue the new wheel value
     */
    private void notifyChangingListeners(int oldValue, int newValue) {
        for (OnWheelChangedListener listener : changingListeners) {
            listener.onChanged(this, oldValue, newValue);
        }
    }

    /**
     * Gets current value
     *
     * @return the current value
     */
    public int getCurrentItem() {
        return currentItem;
    }

    /**
     * Sets the current item w/o animation. Does nothing when index is wrong.
     *
     * @param index the item index
     */
    public void setCurrentItem(int index) {
        setCurrentItem(index, false);
    }

    /**
     * Sets the current item. Does nothing when index is wrong.
     *
     * @param index    the item index
     * @param animated the animation flag
     */
    private void setCurrentItem(int index, boolean animated) {
        if (adapter == null || adapter.length == 0) {
            return; // throw?
        }
        if (index < 0 || index >= adapter.length) {
            if (isCyclic) {
                while (index < 0) {
                    index += adapter.length;
                }
                index %= adapter.length;
            } else {
                return; // throw?
            }
        }
        if (index != currentItem) {
            if (animated) {
                scroll(index - currentItem, SCROLLING_DURATION);
            } else {
                invalidateLayouts();
                int old = currentItem;
                currentItem = index;
                notifyChangingListeners(old, currentItem);
                invalidate();
            }
        }
    }

    /**
     * Tests if wheel is cyclic. That means before the 1st item there is shown
     * the last one
     *
     * @return true if wheel is cyclic
     */
    public boolean isCyclic() {
        return isCyclic;
    }

    /**
     * Set wheel cyclic flag
     *
     * @param isCyclic the flag to set
     */
    public void setCyclic(boolean isCyclic) {
        this.isCyclic = isCyclic;
        invalidate();
        invalidateLayouts();
    }

    /**
     * Invalidates layouts
     */
    private void invalidateLayouts() {
        itemsLayout = null;
        valueLayout = null;
        scrollingOffset = 0;
    }

    /**
     * Initializes resources
     */
    private void initResourcesIfNecessary() {

        if (linePaint == null) {
            linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        }
        if (itemsPaint == null) {
            itemsPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG /*
             * | Paint.
             * FAKE_BOLD_TEXT_FLAG
             */);
            // itemsPaint.density = getResources().getDisplayMetrics().density;
            itemsPaint.setTextSize(TEXT_SIZE);
        }
        if (valuePaint == null) {
            valuePaint = new TextPaint(Paint.ANTI_ALIAS_FLAG /*
             * | Paint.
             * FAKE_BOLD_TEXT_FLAG
             */
                    | Paint.DITHER_FLAG);
            // valuePaint.density = getResources().getDisplayMetrics().density;
            valuePaint.setTextSize(TEXT_SIZE);
            valuePaint.setShadowLayer(0.1f, 0, 0.1f, 0xFFC0C0C0);
        }
        if (centerDrawable == null) {
            centerDrawable = getContext().getResources().getDrawable(
                    R.drawable.wheel_val);
        }
        if (topShadow == null) {
            topShadow = new GradientDrawable(Orientation.TOP_BOTTOM,
                    SHADOWS_COLORS);
        }
        if (bottomShadow == null) {
            bottomShadow = new GradientDrawable(Orientation.BOTTOM_TOP,
                    SHADOWS_COLORS);
        }

        // setBackgroundResource(R.drawable.wheel_bg);
    }

    /**
     * Calculates desired height for layout
     *
     * @param layout the source layout
     * @return the desired layout height
     */
    private int getDesiredHeight(Layout layout) {
        if (layout == null) {
            return 0;
        }
        int desired = getItemHeight() * visibleItems - ITEM_OFFSET * 2
                - ADDITIONAL_ITEM_HEIGHT;
        // Check against our minimum height
        desired = Math.max(desired, getSuggestedMinimumHeight());
        return desired;
    }

    /**
     * Returns text item by index
     *
     * @param index the item index
     * @return the item or null
     */
    private String getTextItem(int index) {
        if (adapter == null || adapter.length == 0) {
            return null;
        }
        int count = adapter.length;
        if ((index < 0 || index >= count) && !isCyclic) {
            return null;
        } else {
            while (index < 0) {
                index = count + index;
            }
        }
        index %= count;
        return adapter[index];
    }

    /**
     * Builds text depending on current value
     * ??????wheel??????????????????
     *
     * @param useCurrentValue
     * @return the text
     */
    private String buildText(boolean useCurrentValue) {
        StringBuilder itemsText = new StringBuilder();
        int addItems = visibleItems / 2 + 1;

        for (int i = currentItem - addItems; i <= currentItem + addItems; i++) {
            if (useCurrentValue || i != currentItem) {
                String text = getTextItem(i);
                if (text != null) {
                    if (wordLimit != 0 && text.length() > wordLimit) {
                        text = text.subSequence(0, wordLimit) + "...";
                    }
                    itemsText.append(text);
                }
            }
            if (i < currentItem + addItems) {
                itemsText.append("\n");
            }
        }

        return itemsText.toString();
    }

    /**
     * Returns the max item length that can be present
     *
     * @return the max length
     */
    private int getMaxTextLength() {
        // LogUtils.e("MAX", adapter + "");
        String maxText = null;
        int addItems = visibleItems / 2;
        try {
            for (int i = Math.max(currentItem - addItems, 0); i < Math.min(
                    currentItem + visibleItems, adapter.length); i++) {
                String text = adapter[i];
                if (text != null
                        && (maxText == null || maxText.length() < text.length())) {
                    maxText = text;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            maxText = adapter[0];
        }
        return maxText != null ? maxText.length() : 0;
    }

    /**
     * Returns height of wheel item
     *
     * @return the item height
     */
    private int getItemHeight() {
        if (itemHeight != 0) {
            return itemHeight;
        } else if (itemsLayout != null && itemsLayout.getLineCount() > 2) {
            itemHeight = itemsLayout.getLineTop(2) - itemsLayout.getLineTop(1);
            return itemHeight;
        }
        return getHeight() / visibleItems;
    }

    /**
     * Calculates control width and creates text layouts
     *
     * @param widthSize the input layout width
     * @param mode      the layout mode
     * @return the calculated control width
     */
    private int calculateLayoutWidth(int widthSize, int mode) {
        initResourcesIfNecessary();
        int width = widthSize;
        int maxLength = getMaxTextLength();
        if (maxLength > 0) {
            itemsWidth = (int) Math.ceil(Layout.getDesiredWidth(adapter[0], itemsPaint));
        } else {
            itemsWidth = 0;
        }
        itemsWidth += ADDITIONAL_ITEMS_SPACE; // make it some more
        labelWidth = 0;
        if (label != null && label.length() > 0) {
            labelWidth = (int) Math.ceil(Layout.getDesiredWidth(label,
                    valuePaint));
        }
        boolean recalculate = false;
        if (mode == MeasureSpec.EXACTLY) {
            width = widthSize;
            recalculate = true;
        } else {
            width = itemsWidth + labelWidth + 2 * PADDING;
            if (labelWidth > 0) {
                width += LABEL_OFFSET;
            }
            // Check against our minimum width
            width = Math.max(width, getSuggestedMinimumWidth());
            if (mode == MeasureSpec.AT_MOST && widthSize < width) {
                width = widthSize;
                recalculate = true;
            }
        }
        if (recalculate) {
            // recalculate width
            int pureWidth = width - LABEL_OFFSET - 2 * PADDING;
            if (pureWidth <= 0) {
                itemsWidth = labelWidth = 0;
            }
            if (labelWidth > 0) {
                double newWidthItems = (double) itemsWidth * pureWidth
                        / (itemsWidth + labelWidth);
                itemsWidth = (int) newWidthItems;
                labelWidth = pureWidth - itemsWidth;
            } else {
                itemsWidth = pureWidth + LABEL_OFFSET; // no label
            }
        }
        if (itemsWidth > 0) {
            createLayouts(itemsWidth, labelWidth);
        }
        return width;
    }

    /**
     * Creates layouts
     *
     * @param widthItems width of items layout
     * @param widthLabel width of label layout
     */
    private void createLayouts(int widthItems, int widthLabel) {
        // if (itemsLayout == null || itemsLayout.getWidth() > widthItems)
        // {
        itemsLayout = new StaticLayout(buildText(isScrollingPerformed), 0, buildText(isScrollingPerformed).length(),
                itemsPaint, widthItems,
                widthLabel > 0 ? Layout.Alignment.ALIGN_OPPOSITE
                        : Layout.Alignment.ALIGN_CENTER, 1,
                ADDITIONAL_ITEM_HEIGHT, false,
                TextUtils.TruncateAt.MARQUEE, widthItems);
        // }
        // else
        // {
        // itemsLayout.increaseWidthTo(widthItems);
        // }

        if (!isScrollingPerformed
                && (valueLayout == null || valueLayout.getWidth() > widthItems)) {
            String text = getAdapter() != null ? getAdapter()[currentItem]
                    : null;
            if (wordLimit != 0 && text != null && text.length() > wordLimit) {
                text = text.subSequence(0, wordLimit) + "...";
            }
            valueLayout = new StaticLayout(text != null ? text : "", 0, text != null ? text.length() : 0,
                    valuePaint, widthItems,
                    widthLabel > 0 ? Layout.Alignment.ALIGN_OPPOSITE
                            : Layout.Alignment.ALIGN_CENTER, 1,
                    ADDITIONAL_ITEM_HEIGHT, false,
                    TextUtils.TruncateAt.MARQUEE, widthItems);

        } else if (isScrollingPerformed) {
            valueLayout = null;
        } else {
            valueLayout.increaseWidthTo(widthItems);
        }

        if (widthLabel > 0) {
            if (labelLayout == null || labelLayout.getWidth() > widthLabel) {
                labelLayout = new StaticLayout(label, valuePaint, widthLabel,
                        Layout.Alignment.ALIGN_NORMAL, 1,
                        ADDITIONAL_ITEM_HEIGHT, false);
            } else {
                labelLayout.increaseWidthTo(widthLabel);
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width = calculateLayoutWidth(widthSize, widthMode);

        int height;
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            height = getDesiredHeight(itemsLayout);

            if (heightMode == MeasureSpec.AT_MOST) {
                height = Math.min(height, heightSize);
            }
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (itemsLayout == null) {
            if (itemsWidth == 0) {
                calculateLayoutWidth(getWidth(), MeasureSpec.EXACTLY);
            } else {
                createLayouts(itemsWidth, labelWidth);
            }
        }

        if (itemsWidth > 0) {
            canvas.save();
            // Skip padding space and hide a part of top and bottom items
            canvas.translate(PADDING, ITEM_OFFSET / 2);
            drawItems(canvas);
            drawValue(canvas);
            canvas.restore();
        }

        drawCenterRect(canvas);
        // drawShadows(canvas);

    }

    /**
     * Draws value and label layout
     *
     * @param canvas the canvas for drawing
     */
    private void drawValue(Canvas canvas) {

        valuePaint.setColor(mValueTextColor);
        // valuePaint.setColor(getResources().getColor(R.color.xiyou_pink));
        // valuePaint.setTextSize(TEXT_SIZE*1.2f);
        valuePaint.drawableState = getDrawableState();

        Rect bounds = new Rect();
        itemsLayout.getLineBounds(visibleItems / 2, bounds);

        // draw label
        if (labelLayout != null) {
            canvas.save();
            canvas.translate(itemsLayout.getWidth() + LABEL_OFFSET, bounds.top);
            labelLayout.draw(canvas);
            canvas.restore();
        }

        // draw current value
        if (valueLayout != null) {
            canvas.save();

            canvas.translate(0, bounds.top + scrollingOffset);// ??????
            valueLayout.draw(canvas);
            canvas.restore();
        }
    }

    /**
     * Draws items
     *
     * @param canvas the canvas for drawing
     */
    private void drawItems(Canvas canvas) {
        canvas.save();

        int top = itemsLayout.getLineTop(1);
        canvas.translate(0, -top + scrollingOffset);

        itemsPaint.setColor(ITEMS_TEXT_COLOR);
        itemsPaint.drawableState = getDrawableState();
        itemsLayout.draw(canvas);

        canvas.restore();
    }

    public void setLinePaintColor(int resId) {
        linePaintColor = resId;
    }

    /**
     * Draws rect for current value
     *
     * @param canvas the canvas for drawing
     */
    private void drawCenterRect(Canvas canvas) {
        int center = getHeight() / 2;
        int offset = getItemHeight() / 2;
        // centerDrawable.setBounds(0, center - offset, getWidth(), center +
        // offset);
        // centerDrawable.draw(canvas);

        // linePaint.setStrokeWidth(1);
        linePaint.setColor(getResources().getColor(linePaintColor));
        canvas.drawLine(0, center - offset, getWidth(), center - offset,
                linePaint);
        canvas.drawLine(0, center + offset, getWidth(), center + offset,
                linePaint);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        handleTouch(event);

        String[] adapter = getAdapter();
        if (adapter == null) {
            return true;
        }

        if (!gestureDetector.onTouchEvent(event)
                && event.getAction() == MotionEvent.ACTION_UP) {
            justify();
        }

        return true;
    }

    // ???????????????
    private void resetCanClick() {
        if (timer == null) {
            timer = new Timer();
        }
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                canClick = true;
            }
        }, 300);
    }

    private int moveCount = 0;

    // ????????????
    private void handleTouch(MotionEvent event) {
        try {

            // ????????????
            if (event.getAction() == MotionEvent.ACTION_MOVE) {
                moveCount += 1;
            }

            if (event.getAction() != MotionEvent.ACTION_UP) {
                return;
            }
            // ???????????????????????????
            if (moveCount > 8) {
                moveCount = 0;
                return;
            }

            if (canClick) {
                canClick = false;
                resetCanClick();
            } else {
                return;
            }

            // ?????????????????????
            float y = event.getY();
            // float y = clickY;
            // ??????????????????
            int itemHeight = getItemHeight();

            int count = (int) y / itemHeight;
            // count=2;
            switch (count) {
                case 0:
                    setCurrentItem(getCurrentItem() - 2);
                    notifyClickComplete();
                    break;
                case 1:
                    // scroll(getCurrentItem()-1,200);
                    setCurrentItem(getCurrentItem() - 1);
                    notifyClickComplete();
                    break;
                case 2:
                    break;
                case 3: {
                    setCurrentItem(getCurrentItem() + 1);
                    notifyClickComplete();
                    break;
                }
                case 4: {
                    // scroll(getCurrentItem()+2,500);
                    setCurrentItem(getCurrentItem() + 2);
                    notifyClickComplete();
                    break;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void notifyClickComplete() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // To change body of implemented methods use File | Settings |
                // File Templates.
                if (scrollListener != null) {
                    scrollListener.onScrollingFinished(WheelView.this);
                }
            }
        }, 50);
    }

    /**
     * Scrolls the wheel
     *
     * @param delta the scrolling value
     */
    private void doScroll(int delta) {

        // delta = Math.min(delta,getItemHeight()*adapter.length);

        scrollingOffset += delta;

        // ??????????????????
        if (scrollingOffset >= 0) {
            scrollingOffset = Math.min(scrollingOffset, getItemHeight()
                    * adapter.length);
        } else {
            scrollingOffset = Math.max(scrollingOffset, -getItemHeight()
                    * adapter.length);
        }

        int count = scrollingOffset / getItemHeight();
        int pos = currentItem - count;
        if (isCyclic && adapter.length > 0) {
            // fix position by rotating
            while (pos < 0) {
                pos += adapter.length;
            }
            pos %= adapter.length;
            //
        } else if (isScrollingPerformed) {
            if (pos < 0) {
                count = currentItem;
                pos = 0;
            } else if (pos >= adapter.length) {
                count = currentItem - adapter.length + 1;
                pos = adapter.length - 1;
            }
        } else {
            // fix position
            pos = Math.max(pos, 0);
            pos = Math.min(pos, adapter.length - 1);
        }

        int offset = scrollingOffset;
        if (pos != currentItem) {
            setCurrentItem(pos, false);
        } else {
            invalidate();
        }

        // update offset
        scrollingOffset = offset - count * getItemHeight();
        if (scrollingOffset > getHeight()) {
            scrollingOffset = scrollingOffset % getHeight() + getHeight();
        }
    }

    /**
     * Set next message to queue. Clears queue before.
     *
     * @param message the message to set
     */
    private void setNextMessage(int message) {
        clearMessages();
        animationHandler.sendEmptyMessage(message);
    }

    /**
     * Clears messages from queue
     */
    private void clearMessages() {
        animationHandler.removeMessages(MESSAGE_SCROLL);
        animationHandler.removeMessages(MESSAGE_JUSTIFY);
    }

    /**
     * Justifies wheel
     */
    private void justify() {
        if (adapter == null) {
            return;
        }
        lastScrollY = 0;
        int offset = scrollingOffset;
        int itemHeight = getItemHeight();
        boolean needToIncrease = offset > 0 ? currentItem < adapter.length
                : currentItem > 0;
        if ((isCyclic || needToIncrease)
                && Math.abs((float) offset) > (float) itemHeight / 2) {
            if (offset < 0)
                offset += itemHeight + MIN_DELTA_FOR_SCROLLING;
            else
                offset -= itemHeight + MIN_DELTA_FOR_SCROLLING;
        }
        // ??????????????????
        offset = Math.min(offset, getItemHeight() * adapter.length);
        if (offset >= 0) {
            offset = Math.min(offset, getItemHeight() * adapter.length);
        } else {
            offset = Math.max(offset, -getItemHeight() * adapter.length);
        }
        if (Math.abs(offset) > MIN_DELTA_FOR_SCROLLING) {
            scroller.startScroll(0, 0, 0, offset, SCROLLING_DURATION);
            setNextMessage(MESSAGE_JUSTIFY);
        } else {
            finishScrolling();
        }
    }

    /**
     * Starts scrolling
     */
    private void startScrolling() {
        if (!isScrollingPerformed) {
            isScrollingPerformed = true;
            if (scrollListener != null) {
                scrollListener.onScrollingStarted(this);
            }
        }
    }

    /**
     * Finishes scrolling
     */
    private void finishScrolling() {
        if (isScrollingPerformed) {
            isScrollingPerformed = false;
            if (scrollListener != null) {
                scrollListener.onScrollingFinished(this);
            }
        }
        invalidateLayouts();
        invalidate();
    }

    /**
     * Scroll the wheel
     *
     * @param
     * @param time scrolling duration
     */
    private void scroll(int itemsToScroll, int time) {
        scroller.forceFinished(true);

        lastScrollY = scrollingOffset;
        int offset = itemsToScroll * getItemHeight();

        scroller.startScroll(0, lastScrollY, 0, offset - lastScrollY, time);
        setNextMessage(MESSAGE_SCROLL);

        startScrolling();
    }

    public void setOnScrollListener(OnWheelScrollListener listener) {
        scrollListener = listener;
    }

    public interface OnWheelChangedListener {
        /**
         * Callback method to be invoked when current item changed
         *
         * @param wheel    the wheel view whose state has changed
         * @param oldValue the old value of current item
         * @param newValue the new value of current item
         */
        void onChanged(WheelView wheel, int oldValue, int newValue);
    }

    public interface OnWheelScrollListener {
        /**
         * Callback method to be invoked when scrolling started.
         *
         * @param wheel the wheel view whose state has changed.
         */
        void onScrollingStarted(WheelView wheel);

        /**
         * Callback method to be invoked when scrolling ended.
         *
         * @param wheel the wheel view whose state has changed.
         */
        void onScrollingFinished(WheelView wheel);
    }

    public void setValuePaintColor(int color) {
        mValueTextColor = color;
        invalidate();
    }

}
