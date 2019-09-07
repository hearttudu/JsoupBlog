package com.example.tuduzhao.jsoupblog;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class BlogViewHolder extends RecyclerView.ViewHolder {
    public BlogViewHolder(@NonNull View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.blog_title);
        content = itemView.findViewById(R.id.blog_content);
        paid = itemView.findViewById(R.id.blog_paid);
        nick = itemView.findViewById(R.id.blog_name);
        like = itemView.findViewById(R.id.blog_like);
        img = itemView.findViewById(R.id.blog_img);
    }
    public TextView title;
    public TextView content;
    public TextView paid;
    public TextView nick;
    public TextView like;
    public ImageView img;
}
