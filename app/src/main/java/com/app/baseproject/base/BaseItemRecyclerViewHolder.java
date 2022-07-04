package com.app.baseproject.base;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

/**
 * Created by test(test@gmail.com) on 11/11/16.
 */

public abstract class BaseItemRecyclerViewHolder<Data> extends RecyclerView.ViewHolder
        implements View.OnClickListener, View.OnLongClickListener {

    protected Context mContext;

    private BaseAdapterItemReyclerView.OnItemClickListener itemClickListener;
    private BaseAdapterItemReyclerView.OnLongItemClickListener longItemClickListener;

    private boolean hasHeader = false;

    public BaseItemRecyclerViewHolder(Context mContext, View itemView,
                                      BaseAdapterItemReyclerView.OnItemClickListener itemClickListener,
                                      BaseAdapterItemReyclerView.OnLongItemClickListener longItemClickListener) {
        super(itemView);
        this.mContext = mContext;
        this.itemClickListener = itemClickListener;
        this.longItemClickListener = longItemClickListener;
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    public abstract void bind(Data data);

    public boolean isHasHeader() {
        return hasHeader;
    }

    public void setHasHeader(boolean hasHeader) {
        this.hasHeader = hasHeader;
    }

    @Override
    public void onClick(View v) {
        if (itemClickListener != null) {
            itemClickListener.onItemClick(v, hasHeader ? getAdapterPosition() - 1 : getAdapterPosition());
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (longItemClickListener != null) {
            longItemClickListener.onLongItemClick(v,
                    hasHeader ? getAdapterPosition() - 1 : getAdapterPosition());
            return true;
        }
        return false;
    }
}

