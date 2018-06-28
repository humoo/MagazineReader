package com.hyc.libs.widget.car;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyc.libs.R;
import com.hyc.libs.base.view.recyclerview.HRAdapter;
import com.hyc.libs.base.view.recyclerview.HRViewHolder;

import java.util.List;

/**
 * 购物车
 * <p>
 * 未完成
 *
 * @author zhxumao
 *         Created on 2018/1/17 0017 11:36.
 */

public class MyBuyCarView extends FrameLayout implements GoodsItemAction {

    private List<GoodsItemEntity> mEntityList;
    private HRAdapter hrAdapter;
    private Context mContext;
    private OnCommitListener commitListener;

    private RecyclerView recyclerView;
    private TextView tvAllPrice, tvCommit;
    private CheckBox checkBoxAll;


    public MyBuyCarView(Context context) {
        this(context, null);
    }

    public MyBuyCarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyBuyCarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        inflate(context, R.layout.frame_buy_car, this);
        recyclerView = findViewById(R.id.recyclerView);
        checkBoxAll = findViewById(R.id.checkbox);
        tvAllPrice = findViewById(R.id.tvAllPrice);
        tvCommit = findViewById(R.id.tvCommit);
        checkBoxAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                for (int i = 0; i < mEntityList.size(); i++) {
                    mEntityList.get(i).setCheck(isChecked);
                    hrAdapter.addMore(mEntityList);
                }
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        hrAdapter = new HRAdapter() {
            @Override
            public HRViewHolder getHolder(ViewGroup parent) {
                return new GoodsItemViewHolder(mContext, parent, MyBuyCarView.this);
            }
        };

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(hrAdapter);

        tvCommit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (commitListener != null && mEntityList != null) {
                    commitListener.onCommit(mEntityList);
                }
            }
        });
    }


    @Override
    public void onCheck(int position, boolean check) {
        for (int i = 0; i < mEntityList.size(); i++) {
            Log.e("logd", "p = " + i + " --- check = " + mEntityList.get(i).isCheck());
        }
        setAllPrice();
    }

    @Override
    public void onAddition(int position, int oldNum, int curNum) {
        GoodsItemEntity entity = mEntityList.get(position);
        Log.e("logd", "p = " + position + " --- count = " + entity.getCount());
        setAllPrice();
    }

    @Override
    public void onSubtraction(int position, int oldNum, int curNum) {
        setAllPrice();
    }

    @Override
    public void onItemTouch(int position) {

    }

    private void setAllPrice() {
        double allPrice = 0;
        for (int i = 0; i < mEntityList.size(); i++) {
            if (mEntityList.get(i).isCheck()) {
                allPrice = allPrice + mEntityList.get(i).getPrice() * mEntityList.get(i).getCount();
            }
        }
        tvAllPrice.setText(allPrice +"");
    }

    /********************
     * 开放的 API
     ****************/
    public void setGoodsData(List<GoodsItemEntity> list) {
        this.mEntityList = list;
        hrAdapter.addMore(mEntityList);
    }

    public void setOnCommitListener(OnCommitListener listener) {
        this.commitListener = listener;
    }


    class GoodsItemViewHolder extends HRViewHolder {

        ImageView ivGoodsPicture;
        CheckBox checkBox;
        TextView tvGoodsTitle, tvGoodsPrice, tvAdd, tvSub;
        EditText editText;

        GoodsItemAction goodsItemAction;
        GoodsItemEntity goodsItemEntity;

        int count = 1;
        double price;

        public GoodsItemViewHolder(Context context, ViewGroup root, GoodsItemAction goodsItemAction) {
            super(context, root, R.layout.car_goods_item, null);
            this.goodsItemAction = goodsItemAction;
        }

        @Override
        public void bindData(Object o, final int position, int selectedP) {
            goodsItemEntity = (GoodsItemEntity) o;
            count = goodsItemEntity.getCount();

            tvGoodsTitle.setText(goodsItemEntity.getGoodsTitle());
            editText.setText(goodsItemEntity.getCount() + "");
            tvGoodsPrice.setText(count * goodsItemEntity.getPrice() + "");

            checkBox.setChecked(goodsItemEntity.isCheck());

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    goodsItemEntity.setCheck(isChecked);
                    goodsItemAction.onCheck(position, isChecked);
                }
            });

            tvAdd.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    count++;
                    goodsItemEntity.setCount(count);
                    editText.setText("" + count);
                    tvGoodsPrice.setText(count * goodsItemEntity.getPrice() + "");
                    goodsItemAction.onAddition(position, count - 1, count);
                }
            });

            tvSub.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (count == 1) {
                        return;
                    }
                    count--;
                    goodsItemEntity.setCount(count);
                    editText.setText("" + count);
                    tvGoodsPrice.setText(count * goodsItemEntity.getPrice() + "");
                    goodsItemAction.onAddition(position, count + 1, count);
                }
            });
        }

        @Override
        public void bindView(View rootView) {
            ivGoodsPicture = rootView.findViewById(R.id.ivGoodsPicture);
            checkBox = rootView.findViewById(R.id.checkbox);
            tvGoodsTitle = rootView.findViewById(R.id.tvGoodsName);
            tvGoodsPrice = rootView.findViewById(R.id.tvGoodsPrice);
            tvAdd = rootView.findViewById(R.id.btnAdd);
            tvSub = rootView.findViewById(R.id.btnSub);
            editText = rootView.findViewById(R.id.tvNum);
        }
    }

    public interface OnCommitListener {
        void onCommit(List<GoodsItemEntity> entityList);
    }
}
