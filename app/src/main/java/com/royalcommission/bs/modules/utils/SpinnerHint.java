package com.keybs.rc.views.customviews;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.keybs.rc.modules.utils.SpinnerHintAdapter;

/**
 * Created by Prashant on 7/18/2018.
 */
public class SpinnerHint<T> {
    private static final String TAG = SpinnerHint.class.getSimpleName();

    /**
     * Used to handle the spinner events.
     *
     * @param <T> Type of the data used by the spinner
     */
    public interface Callback<T> {
        /**
         * When a spinner item has been selected.
         *
         * @param position       Position selected
         * @param itemAtPosition Item selected
         */
        void onItemSelected(int position, T itemAtPosition);
    }

    private final Spinner spinner;
    private final SpinnerHintAdapter<T> adapter;
    private final Callback<T> callback;

    public SpinnerHint(Spinner spinner, SpinnerHintAdapter<T> adapter, Callback<T> callback) {
        this.spinner = spinner;
        this.adapter = adapter;
        this.callback = callback;
    }

    /**
     * Initializes the hint spinner.
     * <p>
     * By default, the hint is selected when calling this method.
     */
    @SuppressWarnings("unchecked")
    public void init() {
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "position selected: " + position);
                if (SpinnerHint.this.callback == null) {
                    throw new IllegalStateException("callback cannot be null");
                }
                if (!isHintPosition(position)) {
                    Object item = SpinnerHint.this.spinner.getItemAtPosition(position);
                    SpinnerHint.this.callback.onItemSelected(position, (T) item);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d(TAG, "Nothing selected");
            }
        });
        selectHint();
    }

    private boolean isHintPosition(int position) {
        return position == adapter.getHintPosition();
    }

    /**
     * Selects the hint element.
     */
    private void selectHint() {
        spinner.setSelection(adapter.getHintPosition());
    }
}

