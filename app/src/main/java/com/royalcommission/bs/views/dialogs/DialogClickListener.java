package com.qanawat.views.interfaces;

import com.qanawat.modules.utils.ButtonTag;

public interface DialogClickListener {

    void positiveButtonClicked(ButtonTag tag);

    void negativeButtonClicked(ButtonTag tag);
}
