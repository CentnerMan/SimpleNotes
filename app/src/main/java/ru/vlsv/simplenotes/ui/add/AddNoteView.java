package ru.vlsv.simplenotes.ui.add;

import android.os.Bundle;

import androidx.annotation.StringRes;

import ru.vlsv.simplenotes.entities.Note;

public interface AddNoteView {

    void showProgress();

    void hideProgress();

    void setActionButtonText(@StringRes int title);

    void setName(String noteName);

    void setText(String noteText);

    void actionCompleted(String key, Bundle bundle);

}
