package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.model.ListModel;

public class ItemAdapter extends PagedListAdapter<ListModel, ItemAdapter.ItemViewHolder> {

    private Context mCtx;

    public ItemAdapter(Context mCtx) {
        super(DIFF_CALLBACK);
        this.mCtx = mCtx;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mCtx).inflate(R.layout.item_view, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        ListModel item = getItem(position);

        Glide.with(mCtx)
                .load(item.subListModel.avatar_url)
                .into(holder.imageView);
        holder.title1.setText(item.full_name);
        holder.title2.setText(item.description);


    }

    private static DiffUtil.ItemCallback<ListModel> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<ListModel>() {
                @Override
                public boolean areItemsTheSame(ListModel oldItem, ListModel newItem) {
                    return oldItem.id == newItem.id;
                }


                @Override
                public boolean areContentsTheSame(ListModel oldItem, ListModel newItem) {
                    return oldItem.equals(newItem);
                }
            };

    class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView title1, title2;

        public ItemViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            title1 = itemView.findViewById(R.id.title1);
            title2 = itemView.findViewById(R.id.title2);
        }
    }
}
