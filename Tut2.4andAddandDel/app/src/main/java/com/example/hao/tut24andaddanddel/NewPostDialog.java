package com.example.hao.tut24andaddanddel;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

/**
 * Created by hao on 2/13/2016.
 */
public class NewPostDialog extends DialogFragment {
    private EditText mEditText;

    public interface NewPostDialogListener{
        void onResultReturn(String postTitle);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_new_post, null);

        mEditText = (EditText)view.findViewById(R.id.edit_text_new_post);

        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle(R.string.action_add)
                .setPositiveButton(android.R.string.cancel, null)
                .setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        NewPostDialogListener activity = (NewPostDialogListener) getActivity();
                        activity.onResultReturn(mEditText.getText().toString());
                    }
                })
                .create();
    }
}
