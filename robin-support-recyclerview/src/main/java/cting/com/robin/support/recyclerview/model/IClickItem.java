package cting.com.robin.support.recyclerview.model;

public interface IClickItem<I extends IRobinListItem> {
    void onItemClick(I item, int position);
    boolean onItemLongClick(I item, int position);
}
