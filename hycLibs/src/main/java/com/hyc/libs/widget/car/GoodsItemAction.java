package com.hyc.libs.widget.car;

/**
 * 商品item点击动作
 *
 * @author zhxumao
 *         Created on 2018/1/17 0017 11:22.
 */

public interface GoodsItemAction {

    void onCheck(int position, boolean check);

    void onAddition(int position, int oldNum, int curNum);

    void onSubtraction(int position, int oldNum, int curNum);

    void onItemTouch(int position);


}
