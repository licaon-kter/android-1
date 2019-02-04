package mobileapp.ctemplar.com.ctemplarapp.message;

import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.List;

import mobileapp.ctemplar.com.ctemplarapp.R;
import mobileapp.ctemplar.com.ctemplarapp.folders.AddFolderActivity;
import mobileapp.ctemplar.com.ctemplarapp.folders.ManageFoldersActivity;
import mobileapp.ctemplar.com.ctemplarapp.net.response.Folders.FoldersResponse;
import mobileapp.ctemplar.com.ctemplarapp.net.response.Folders.FoldersResult;

public class MoveDialogFragment extends DialogFragment {

    private ViewMessagesViewModel viewMessagesModel;
    private List<FoldersResult> customFoldersList;

    @Override
    public void onResume() {
        super.onResume();
        getCustomFolders();
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_messages_move_dialog, container, false);

        Bundle bundleArguments = this.getArguments();
        if (bundleArguments == null) {
            return view;
        }
        final long parentMessageId = bundleArguments.getLong(ViewMessagesActivity.PARENT_ID, -1);

        viewMessagesModel = ViewModelProviders.of(this).get(ViewMessagesViewModel.class);
        getCustomFolders();

        viewMessagesModel.getFoldersResponse().observe(this, new Observer<FoldersResponse>() {
            @Override
            public void onChanged(@Nullable FoldersResponse foldersResponse) {
                if (foldersResponse != null) {
                    handleFoldersResponse(view, foldersResponse);
                }
            }
        });

        ImageView closeDialog = view.findViewById(R.id.fragment_messages_move_dialog_close);
        closeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        Button buttonCancel = view.findViewById(R.id.fragment_messages_move_dialog_action_cancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        Button buttonApply = view.findViewById(R.id.fragment_messages_move_dialog_action_apply);
        buttonApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioGroup foldersRadioGroup = view.findViewById(R.id.fragment_messages_move_dialog_group);
                int checkedId = foldersRadioGroup.getCheckedRadioButtonId();
                for (FoldersResult folderItem :
                        customFoldersList) {
                    if (checkedId == folderItem.getId()) {
                        String folderName = folderItem.getName();
                        viewMessagesModel.moveToFolder(parentMessageId, folderName);
                        String toastMessage = getResources().getString(R.string.toast_message_moved_to, folderName);
                        Toast.makeText(getActivity(), toastMessage, Toast.LENGTH_SHORT).show();
                        dismiss();
                    }
                }
            }
        });

        return view;
    }

    private void handleFoldersResponse(View view, FoldersResponse foldersResponse) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        LinearLayout foldersListLayout = view.findViewById(R.id.fragment_messages_move_dialog_group);
        customFoldersList = foldersResponse.getFoldersList();

        foldersListLayout.removeAllViewsInLayout();

        for (FoldersResult folderItem :
                customFoldersList) {
            View folderItemButton = inflater.inflate(R.layout.item_move_folder_radiobutton, foldersListLayout, false);
            RadioButton radioButton = folderItemButton.findViewById(R.id.radio_button);
            radioButton.setId((int) folderItem.getId());
            radioButton.setText(folderItem.getName());

            Resources resources = getContext().getResources();
            Drawable folderLeftDrawable = resources.getDrawable(R.drawable.ic_manage_folders);
            Drawable folderRightDrawable = resources.getDrawable(R.drawable.selector_check);
            folderLeftDrawable.mutate();
            folderRightDrawable.mutate();

            int folderColor = Color.parseColor(folderItem.getColor());
            folderLeftDrawable.setColorFilter(folderColor, PorterDuff.Mode.SRC_IN);
            radioButton.setCompoundDrawablesWithIntrinsicBounds(folderLeftDrawable, null, folderRightDrawable, null);
            foldersListLayout.addView(folderItemButton);
        }

        View addFolderLayout = inflater.inflate(R.layout.manage_folders_footer, foldersListLayout, false);
        Button addFolderButton = addFolderLayout.findViewById(R.id.manager_folders_footer_btn);
        addFolderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addFolder = new Intent(getActivity(), AddFolderActivity.class);
                startActivity(addFolder);
            }
        });
        View manageFolderLayout = inflater.inflate(R.layout.item_manage_folders, foldersListLayout, false);
        Button manageFolderButton = manageFolderLayout.findViewById(R.id.manager_folders);
        manageFolderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addFolder = new Intent(getActivity(), ManageFoldersActivity.class);
                startActivity(addFolder);
            }
        });

        if (customFoldersList.isEmpty()) {
            foldersListLayout.addView(addFolderLayout);
        } else {
            foldersListLayout.addView(manageFolderLayout);
        }
    }


    private void getCustomFolders() {
        viewMessagesModel.getFolders(200, 0);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new Dialog(getActivity(), R.style.DialogAnimation);
    }
}