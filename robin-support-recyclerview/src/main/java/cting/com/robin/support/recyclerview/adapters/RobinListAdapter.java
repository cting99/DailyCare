package cting.com.robin.support.recyclerview.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import cting.com.robin.support.recyclerview.model.IClickItem;
import cting.com.robin.support.recyclerview.model.IRobinListItem;

public class RobinListAdapter<I extends IRobinListItem, B extends ViewDataBinding>
        extends RecyclerView.Adapter<RobinListAdapter.ViewHolder> {
    public static final String TAG = "cting/list/adapter";

    protected LayoutInflater inflater;
    private ArrayList<I> dataList;
    private Callbacks callbacks;
    private Context context;
    private int backgroundResId;

    public RobinListAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);

        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(android.R.attr.selectableItemBackground, typedValue, true);
        this.backgroundResId = typedValue.resourceId;
    }

    public void setDataList(ArrayList<I> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    public void setCallbacks(Callbacks callbacks) {
        if (callbacks == null) {
            throw new RuntimeException("RobinListAdapter.Callbacks must not be null!");
        } else {
            this.callbacks = callbacks;
        }
    }

    protected I getItem(int position) {
        if (dataList == null) {
            return null;
        }
        return dataList.get(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        B binding = DataBindingUtil.inflate(inflater, callbacks.getItemLayoutId(), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        I item = getItem(position);
        callbacks.bindItemData(item, holder.binding);
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        if (dataList == null) {
            return 0;
        }
        return dataList.size();
    }
/*
    public void setDataList(ArrayList<I> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }*/

    public class ViewHolder<DB extends ViewDataBinding>
            extends RecyclerView.ViewHolder
            implements View.OnLongClickListener, View.OnClickListener {
        public DB binding;

        public ViewHolder(DB binding) {
            super(binding.getRoot());
            this.binding = binding;
            if (binding.getRoot().getBackground() == null) {
                Log.i(TAG, "ViewHolder: setBackground");
                binding.getRoot().setBackgroundResource(backgroundResId);
            }
            binding.getRoot().setOnClickListener(this);
            binding.getRoot().setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View v) {
            return callbacks.onItemLongClick(getItem(getAdapterPosition()));
        }

        @Override
        public void onClick(View v) {
            callbacks.onItemClick(getItem(getAdapterPosition()));
        }
    }

    public interface Callbacks<I extends IRobinListItem, B extends ViewDataBinding>
            extends IClickItem<I> {
        int getItemLayoutId();
        void bindItemData(I item, B binding);
    }
}
