package com.example.tuduzhao.jsoupblog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.List;

public class BlogAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<JianShuBlog> blogList;
    private Context context;

    public BlogAdapter(Context context, List<JianShuBlog> blogList){
        this.blogList = blogList;
        this.context = context;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.blog_item, viewGroup, false);
        return new BlogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        BlogViewHolder holder = (BlogViewHolder) viewHolder;
        final JianShuBlog blog = blogList.get(i);
        holder.title.setText(blog.getTitle());
        holder.content.setText(blog.getAbs());
        holder.like.setText(blog.getLike());
        holder.nick.setText(blog.getName());
        holder.paid.setText(blog.getPaid());

        Glide.with(context)
                .load(blog.getImg())
                .into(holder.img);
    }

    @Override
    public int getItemCount() {
        return blogList.size();
    }
}
