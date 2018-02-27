package cting.com.robin.support.commom.model;

public interface IClick<I> {
    void onItemClick(I item);
    boolean onItemLongClick(I item);
}
