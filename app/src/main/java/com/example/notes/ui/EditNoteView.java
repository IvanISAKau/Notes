package com.example.notes.ui;

import android.os.Bundle;

import androidx.annotation.StringRes;

import com.example.notes.domain.Note;

public interface EditNoteView {

    void setButtonTitle(@StringRes int title);

    void setNoteTitle(String title);

    void setNoteDescription(String description);

    void showProgress();

    void hideProgress();

    void setActionButtonEnabled(boolean isEnabled);

    void publishResult(String key, Bundle bundle);

}
