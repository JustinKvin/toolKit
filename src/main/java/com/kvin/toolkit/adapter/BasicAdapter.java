package com.kvin.toolkit.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by sunmo on 2017/1/15.
 */
public abstract class BasicAdapter<V extends BasicAdapter.ViewHolder, T> extends android.widget.BaseAdapter {
    protected final static String IMAGE_PREFIX = "file:///";
    private List<T> list;
    private Context context;
    private LayoutInflater inflater;

    protected OnChildClickListener mOnChildClickListener;

    public BasicAdapter(List<T> list, Context context) {
        this.list = list;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list == null ? null : list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * v can be a view or a viewHolder
     *
     * @return
     * @explain_1:inflater,parent,context use for inflate layout
     * @explain_2:list,position use for choosing layout
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        V v;
        if (convertView == null) {
            convertView = getConvertView(inflater, parent, context, list, position);
            v = initView(convertView, parent, context);
            convertView.setTag(v);
        } else {
            v = (V) convertView.getTag();
        }
        bindData(v, position, list, context);
        return convertView;
    }

    /**
     * child view click listener
     * @param mOnChildClickListener
     */
    public void setOnChildClickListener(OnChildClickListener mOnChildClickListener) {
        this.mOnChildClickListener = mOnChildClickListener;
    }

    /**
     * getConvertView
     *
     * @param inflater
     * @param parent
     * @param context
     * @param list
     * @param position
     */
    protected abstract View getConvertView(LayoutInflater inflater, ViewGroup parent, Context context, List<T> list, int position);

    /**
     * init view
     */
    protected abstract V initView(View cv, ViewGroup parent, Context context);

    /**
     * bind data
     */
    protected abstract void bindData(V vh, int position, List<T> list, Context context);

    /**
     * view holder
     */
    public static class ViewHolder{

        public ViewHolder(View v) {
        }
    }
}