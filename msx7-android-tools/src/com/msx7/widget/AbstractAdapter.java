/**   
 * @Title: AbstractAdapter.java
 * @Description: TODO(用一句话描述该文件做什么)
 * @author xiaowei xiaowei_8852@sina.com
 * @date 2012-9-6 下午8:17:02
 * @version V1.0   
 */
package com.msx7.widget;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * @ClassName: AbstractAdapter
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author xiaowei xiaowei_8852@sina.com
 * @date 2012-9-6 下午8:17:02
 * 
 */
public abstract class AbstractAdapter<T> extends BaseAdapter {

    protected Context context;
    protected List<T> data;
    protected LayoutInflater mInflater;

    public AbstractAdapter(Context context, List<T> data) {
        super();
        this.context = context;
        this.data = data;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public T getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void clear() {
        data.clear();
        notifyDataSetChanged();
    }

    public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public void addMore(List<T> data) {
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    public void changeData(List<T> data) {
        this.data.clear();
        addMore(data);
    }
	public Context getContext() {
		return this.context;
	}

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return createView(position, convertView, mInflater);
    }

    public abstract View createView(int position, View convertView, LayoutInflater inflater);
}

