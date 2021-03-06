package com.shopkeeper.gallery;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import io.supercharge.shimmerlayout.ShimmerLayout;

public class GalleryAdapter extends ListAdapter<PhotoItem, MyViewHolder> {
    protected GalleryAdapter() {
        super(new DiffUtil.ItemCallback<PhotoItem>() {
            @Override
            public boolean areItemsTheSame(@NonNull PhotoItem oldItem, @NonNull PhotoItem newItem) {
                return oldItem==newItem;
            }

            @Override
            public boolean areContentsTheSame(@NonNull PhotoItem oldItem, @NonNull PhotoItem newItem) {
                return oldItem.getPhotoId()==newItem.getPhotoId();
            }
        });
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_cell,parent,false);
        final MyViewHolder holder=new MyViewHolder(view);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle=new Bundle();
                bundle.putParcelable("PHOTO", getItem(holder.getAdapterPosition()));
                NavController navController = Navigation.findNavController(holder.itemView);
                navController.navigate(R.id.action_galleryFragment_to_photoFragment, bundle);

            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        ImageView imageView=holder.itemView.findViewById(R.id.imageView);
        final ShimmerLayout shimmerLayout=holder.itemView.findViewById(R.id.shimmerLayoutCell);
        shimmerLayout.setShimmerColor(0x55ffffff);//55表示透明度
        shimmerLayout.setShimmerAngle(0);
        shimmerLayout.startShimmerAnimation();
        Glide.with(holder.itemView)//view
            .load(getItem(position).getPreViewURL())
                .placeholder(R.drawable.ic_baseline_photo_24)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        shimmerLayout.stopShimmerAnimation();
                        return false;
                    }
                })
                .into(imageView);
    }
}

class MyViewHolder extends RecyclerView.ViewHolder{

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
    }
}