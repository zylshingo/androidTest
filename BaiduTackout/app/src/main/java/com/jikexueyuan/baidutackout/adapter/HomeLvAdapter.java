package com.jikexueyuan.baidutackout.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import com.jikexueyuan.baidutackout.R;

/**
 * Created by Administrator on 2016/6/17 0017.
 */
public class HomeLvAdapter extends BaseAdapter {
    private int[] mImageTitle = {R.drawable.shopone, R.drawable.shoptwo, R.drawable.shopthree, R.drawable.shopfour};
    private String[] mTvtitle = {"正宗重庆麻辣烫", "德克士（重庆店）", "椒盐记（重庆店）", "南粉北面"};
    private String[] mTvyishou = {"已售999份", "已售888份", "已售777份", "已售666份"};
    private String[] mTvjuli = {"469m", "666m", "854m", "1.0km"};
    private String[] mTvqisong = {"起送￥20 | 配送￥5 | 平均30分钟", "起送￥40 | 配送￥5 | 平均45分钟", "起送￥88 | 配送￥12 | 平均50分钟",
            "起送￥28 | 配送￥3 | 平均23分钟"};
    private String[] mTvjian = {"满30减2元（在线支付专享）", "无", "满80减10元（在线支付专享）", "无"};
    private Context mContext = null;

    public HomeLvAdapter(Context _mContext) {
        this.mContext = _mContext;
    }

    @Override
    public int getCount() {
        return mImageTitle.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HomeViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new HomeViewHolder();
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.main_lv_cell, null);

            viewHolder.iv_cell_icon = (ImageView) convertView.findViewById(R.id.iv_cell_icon);
            viewHolder.iv_cell_jian = (ImageView) convertView.findViewById(R.id.iv_cell_jian);
            viewHolder.iv_cell_juan = (ImageView) convertView.findViewById(R.id.iv_cell_juan);
            viewHolder.ratingBar = (RatingBar) convertView.findViewById(R.id.ratingBar);
            viewHolder.tv_cell_jian = (TextView) convertView.findViewById(R.id.tv_cell_jian);
            viewHolder.tv_cell_juli = (TextView) convertView.findViewById(R.id.tv_cell_juli);
            viewHolder.tv_cell_qisong = (TextView) convertView.findViewById(R.id.tv_cell_qisong);
            viewHolder.tv_cell_title = (TextView) convertView.findViewById(R.id.tv_cell_title);
            viewHolder.tv_cell_yishou = (TextView) convertView.findViewById(R.id.tv_cell_yishou);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (HomeViewHolder) convertView.getTag();
        }
        viewHolder.iv_cell_icon.setImageResource(mImageTitle[position]);
        viewHolder.iv_cell_jian.setImageResource(R.drawable.waimai_shoplist_item_icon_jian);
        viewHolder.iv_cell_juan.setImageResource(R.drawable.waimai_shoplist_item_icon_juan);
        viewHolder.tv_cell_title.setText(mTvtitle[position]);
        viewHolder.tv_cell_yishou.setText(mTvyishou[position]);
        viewHolder.tv_cell_juli.setText(mTvjuli[position]);
        viewHolder.tv_cell_qisong.setText(mTvqisong[position]);
        viewHolder.tv_cell_jian.setText(mTvjian[position]);
        viewHolder.tv_cell_jian.setVisibility(View.VISIBLE);
        viewHolder.iv_cell_jian.setVisibility(View.VISIBLE);
        if (viewHolder.tv_cell_jian.getText().toString().equals("无")) {
            viewHolder.tv_cell_jian.setVisibility(View.INVISIBLE);
            viewHolder.iv_cell_jian.setVisibility(View.INVISIBLE);
        }
        viewHolder.ratingBar.setRating(4);

        return convertView;
    }


    private class HomeViewHolder {
        private ImageView iv_cell_icon;
        private ImageView iv_cell_juan;
        private ImageView iv_cell_jian;
        private TextView tv_cell_title;
        private TextView tv_cell_yishou;
        private TextView tv_cell_juli;
        private TextView tv_cell_qisong;
        private TextView tv_cell_jian;
        private RatingBar ratingBar;
    }


}
