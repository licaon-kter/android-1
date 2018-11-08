package mobileapp.ctemplar.com.ctemplarapp.main;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;

import mobileapp.ctemplar.com.ctemplarapp.R;

public class InboxMessageViewHolder extends RecyclerView.ViewHolder{

    public TextView txtUsername;
    public TextView txtChildren;
    public TextView txtStatus;
    public TextView txtDate;
    public TextView txtSubject;
    public TextView txtContent;
    public ImageView imgUnread;
    public ImageView imgProtection;
    public ImageView imgStarred;
    public ImageView imgAttachment;
    public SwipeLayout swipeItem;
    public ImageView imgSwipeSpam;
    public ImageView imgSwipeFolder;
    public ImageView imgSwipeTrash;

    public InboxMessageViewHolder(@NonNull View itemView) {
        super(itemView);

        txtUsername = itemView.findViewById(R.id.message_holder_username);
        txtChildren = itemView.findViewById(R.id.message_holder_children);
        txtStatus = itemView.findViewById(R.id.message_holder_status);
        txtDate = itemView.findViewById(R.id.message_holder_date);
        txtSubject = itemView.findViewById(R.id.message_holder_subject);
        txtContent = itemView.findViewById(R.id.message_holder_content);
        imgUnread = itemView.findViewById(R.id.message_holder_new);
        imgProtection = itemView.findViewById(R.id.message_holder_protection);
        imgStarred = itemView.findViewById(R.id.message_holder_starred);
        imgAttachment = itemView.findViewById(R.id.message_holder_attachment);

        swipeItem = itemView.findViewById(R.id.message_swipe_item);
        swipeItem.setShowMode(SwipeLayout.ShowMode.PullOut);
        swipeItem.addDrag(SwipeLayout.DragEdge.Right, swipeItem.findViewById(R.id.message_swipe_layout));
        imgSwipeSpam = swipeItem.findViewById(R.id.message_move_to_spam);
        imgSwipeFolder = swipeItem.findViewById(R.id.message_move_to_folder);
        imgSwipeTrash = swipeItem.findViewById(R.id.message_move_to_trash);
    }
}
