package com.hyc.libs.widget;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.hyc.libs.R;
import com.hyc.libs.base.view.recyclerview.HRAdapter;
import com.hyc.libs.base.view.recyclerview.HRViewHolder;
import com.hyc.libs.log.Debug;

import java.io.File;
import java.util.ArrayList;

/**
 * 九宫格展示控件
 *
 * @author zhxumao
 *         Created on 2018/2/8 0008 18:20.
 */

public class NineGridView extends FrameLayout {

    private GridLayoutManager manager;
    private RecyclerView recyclerView;
    private HRAdapter adapter;

    private int mColumn = 3;
    private int mMax = 9;
    private boolean mWithAdd = false;
    private int mWidth;

    private NineGridViewListener mNineGridViewListener;
    private ArrayList<Object> mImagePathList = new ArrayList<>();

    public NineGridView(Context context) {
        super(context);
        init(context);
    }

    public NineGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public NineGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.widget_nine_grid, this);
        recyclerView = findViewById(R.id.recyclerView);
    }

    public NineGridView build(final Context context) {
        manager = new GridLayoutManager(context, mColumn) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }

            @Override
            public boolean canScrollHorizontally() {
                return false;
            }
        };

        adapter = new HRAdapter() {
            @Override
            public HRViewHolder getHolder(ViewGroup parent) {
                return new ItemViewHolder(context, parent);
            }
        };

        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        if (mWithAdd && mImagePathList.isEmpty()) {
            mImagePathList.add(R.drawable.add_pic);
        }

        post(new Runnable() {
            @Override
            public void run() {
                mWidth = getMeasuredWidth();
                int height = getMeasuredHeight();
                Debug.e("" + mWidth + " - " + height);
                adapter.addMore(mImagePathList);
            }
        });
        return this;
    }

    public NineGridView setMax(int max) {
        mMax = max;
        return this;
    }

    public NineGridView setColumn(int column) {
        mColumn = column;
        return this;
    }

    public NineGridView setWithAdd(boolean withAdd) {
        mWithAdd = withAdd;
        return this;
    }

    public NineGridView setNineGridListener(NineGridViewListener listener) {
        mNineGridViewListener = listener;
        return this;
    }

    public void setImagePath(ArrayList<Object> paths) {
        /**
         * 去除超出设置的张数
         */
        if (paths.size() > mMax) {
            for (int i = paths.size() - 1; i >= mMax; i--) {
                paths.remove(i);
            }
        }
        mImagePathList.clear();
        mImagePathList.addAll(paths);
        if (mImagePathList.size() < mMax && mWithAdd) {
            mImagePathList.add(R.drawable.add_pic);
        }
        adapter.addMore(mImagePathList);
    }

    public void removeImagePath(int position) {
        if (mImagePathList.size() - 1 < position) {
            return;
        }
        mImagePathList.remove(position);
        adapter.notifyItemRemoved(position);

        if (mWithAdd && !(mImagePathList.get(mImagePathList.size() - 1) instanceof Integer)) {
            mImagePathList.add(R.drawable.add_pic);
            adapter.notifyItemInserted(mImagePathList.size() - 1);
        }

    }

    private class ItemViewHolder extends HRViewHolder {

        ImageView imageView, ivClose;
        RelativeLayout rlContainer;

        ItemViewHolder(Context context, ViewGroup root) {
            super(context, root, R.layout.item_widget_grid, null);
        }

        @Override
        public void bindData(final Object o, int position, int selectedP) {

            if (o instanceof String) {
                imageView.setImageURI(Uri.fromFile(new File(o.toString())));
                ivClose.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mNineGridViewListener != null) {
                            mNineGridViewListener.onItemDelete(getAdapterPosition(), mMax);
                        }
                    }
                });

                if (mWithAdd) {
                    ivClose.setVisibility(VISIBLE);
                } else {
                    ivClose.setVisibility(GONE);
                }
            } else {
                imageView.setImageResource((int) o);
                ivClose.setVisibility(GONE);
            }

            imageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mNineGridViewListener != null) {
                        if (o instanceof String) {
                            mNineGridViewListener.onItemClick(getAdapterPosition(), mMax);
                        } else {
                            mNineGridViewListener.onOpenMore(mImagePathList.size());
                        }
                    }
                }
            });

            ViewGroup.LayoutParams params = rlContainer.getLayoutParams();
            params.height = mWidth / mColumn;
            params.width = mWidth / mColumn;
            rlContainer.setLayoutParams(params);
        }

        @Override
        public void bindView(View rootView) {
            rlContainer = rootView.findViewById(R.id.rlContainer);
            imageView = rootView.findViewById(R.id.imageView);
            ivClose = rootView.findViewById(R.id.ivClose);
        }
    }

    public interface NineGridViewListener {
        void onItemClick(int position, int max);

        void onItemDelete(int position, int max);

        void onOpenMore(int size);
    }

}
