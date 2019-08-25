package com.royalcommission.bs.modules.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.royalcommission.bs.R;

import java.util.List;

/**
 * Allows adding a hint at the end of the list. It will show the hint when adding it and selecting
 * the last object. Otherwise, it will show the dropdown view implemented by the concrete class.
 */
public class SpinnerHintAdapter<T> extends ArrayAdapter<T> {
    private static final String TAG = SpinnerHintAdapter.class.getSimpleName();
    private static final int DEFAULT_LAYOUT_RESOURCE = android.R.layout.simple_spinner_dropdown_item;

    private int layoutResource;
    private String hintResource;
    private List<T> mObjects;
    private final LayoutInflater layoutInflater;

    public SpinnerHintAdapter(Context context, int hintResource, List<T> data) {
        this(context, DEFAULT_LAYOUT_RESOURCE, context.getString(hintResource), data);
        mObjects = data;
    }

    public SpinnerHintAdapter(Context context, String hint, List<T> data) {
        this(context, DEFAULT_LAYOUT_RESOURCE, hint, data);
        mObjects = data;
    }

    protected SpinnerHintAdapter(Context context, int layoutResource, int hintResource, List<T> data) {
        this(context, layoutResource, context.getString(hintResource), data);
        mObjects = data;
    }

    private SpinnerHintAdapter(Context context, int layoutResource, String hintResource, List<T> data) {
        // Create a copy, as we need to be able to add the hint without modifying the array passed in
        // or crashing when the user sets an unmodifiable.
        super(context, layoutResource, data);
        this.layoutResource = layoutResource;
        this.hintResource = hintResource;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    /**
     * Hook method to set a custom view.
     * <p>
     * Provides a default implementation using the simple spinner dropdown item.
     *
     * @param position    Position selected
     * @param convertView View
     * @param parent      Parent view group
     */
    protected View getCustomView(int position, View convertView, ViewGroup parent) {
        View view = inflateDefaultLayout(parent);
        Object item = getItem(position);
        TextView textView = view.findViewById(android.R.id.text1);
        assert item != null;
        textView.setText(item.toString());
        textView.setEllipsize(TextUtils.TruncateAt.END);
        textView.setHint("");
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                view.getContext().getResources().getDimension(R.dimen.text_size_medium));


        int padding = view.getContext().getResources().getDimensionPixelOffset(R.dimen.edit_text_padding);
        float scale = view.getContext().getResources().getDisplayMetrics().density;
        int dpAsPixels = (int) (padding * scale + 0.5f);
        //textView.setPadding(0, 0, 0, 0);
        textView.post(() -> textView.setSingleLine(false));


        return view;
    }

    private View inflateDefaultLayout(ViewGroup parent) {
        return inflateLayout(DEFAULT_LAYOUT_RESOURCE, parent);
    }

    private View inflateLayout(int resource, ViewGroup root) {
        return layoutInflater.inflate(resource, root, false);
    }

    protected View inflateLayout(ViewGroup root) {
        return layoutInflater.inflate(layoutResource, root, false);
    }


    @Override
    public @Nullable
    T getItem(int position) {
        if (position > mObjects.size())
            position = mObjects.size() - 1;
        return mObjects.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        Log.d(TAG, "position: " + position + ", getCount: " + getCount());
        View view;
        if (position == getHintPosition()) {
            view = getDefaultView(parent);
        } else {
            view = getCustomView(position, convertView, parent);
        }
        final Configuration config =
                getContext().getResources().getConfiguration();
        view.setLayoutDirection(config.getLayoutDirection());

        return view;
    }

    private View getDefaultView(ViewGroup parent) {
        View view = inflateDefaultLayout(parent);
        TextView textView = view.findViewById(android.R.id.text1);
        //View view = inflateLayout(parent);
        //TextView textView = view.findViewById(R.id.text);
        textView.setText("");
        textView.setHint(hintResource);
        textView.setEllipsize(TextUtils.TruncateAt.END);
        int padding = view.getContext().getResources().getDimensionPixelOffset(R.dimen.edit_text_padding);
        //float scale = view.getContext().getResources().getDisplayMetrics().density;
        //int dpAsPixels = (int) (padding * scale + 0.5f);
        //textView.setPadding(0, 0, 0, 0);

        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                view.getContext().getResources().getDimension(R.dimen.text_size_medium));
        textView.setTextColor(ContextCompat.getColor(textView.getContext(), R.color.borderColor));
        return view;
    }

    /**
     * Gets the position of the hint.
     *
     * @return Position of the hint
     */
    public int getHintPosition() {
        int count = getCount();
        return count > 0 ? count + 1 : count;
    }

}
