package com.royalcommission.bs.views.dialogs;


import com.royalcommission.bs.modules.utils.ButtonTag;

public interface DialogClickListener {

    void positiveButtonClicked(ButtonTag tag);

    void negativeButtonClicked(ButtonTag tag);
}
