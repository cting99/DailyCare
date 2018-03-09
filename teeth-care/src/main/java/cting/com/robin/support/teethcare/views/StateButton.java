package cting.com.robin.support.teethcare.views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;

import cting.com.robin.support.teethcare.R;

public class StateButton extends android.support.v7.widget.AppCompatButton implements View.OnClickListener {

    public static final int STATE_DONE = 1;
    public static final int STATE_ADD = 2;
    public static final int STATE_DELETE = 3;
    private static final int STATE_COUNT = 3;


    private int state = STATE_ADD;
    private Callback callback;
    private IEntryState entryState;
    private boolean isLastOne;
    private int position;


    public StateButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setBackgroundResource(R.drawable.circle_red_background);
        setGravity(Gravity.CENTER);
        setTextColor(getResources().getColor(R.color.state_btn_text_color));
        setTextSize(getResources().getDimensionPixelSize(R.dimen.state_btn_text_size));
        setTypeface(Typeface.DEFAULT_BOLD);
        setState(STATE_DONE);
        setOnClickListener(this);
    }

    public int getState() {
        return state;
    }

    private void setState(int state) {
        this.state = state;
        updateState();
    }


    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public void setEntryState(IEntryState entryState) {
        this.entryState = entryState;
    }

    public void setPosition(int position,int size) {
        this.position = position;
        this.isLastOne = position == size - 1;
    }

    public void refresh() {
        if (entryState.notFinished()) {
            prepareDone();
        } else if (!entryState.isEmpty()) {
            if (isLastOne) {
                prepareAdd();
            } else {
                prepareDelete();
            }
        }
    }

    private void prepareDone() {
        setState(STATE_DONE);
    }

    private void prepareAdd() {
        setState(STATE_ADD);
    }

    private void prepareDelete() {
        setState(STATE_DELETE);
    }

    private void updateState() {
        switch (state) {
            case STATE_ADD:
                setText("+");
                break;
            case STATE_DELETE:
                setText("-");
                break;
            case STATE_DONE:
                setText("√");
                break;
        }
    }

    @Override
    public void onClick(View v) {
        if (callback == null) {
            throw new RuntimeException("StateButton.Callback must not be null!");
        }
        switch (state) {
            case STATE_ADD:
                callback.onAdd();
                break;
            case STATE_DELETE:
                callback.onDelete(position);
                break;
            case STATE_DONE:
                callback.onDone(position);
                break;
        }
        setState((state + 1) / STATE_COUNT);
    }

    public interface Callback {
        void onAdd();

        void onDelete(int position);

        void onDone(int position);
    }

}
