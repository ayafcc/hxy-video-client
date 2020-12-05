package cn.mahua.vod.ui.seek;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import cn.mahua.vod.R;
import cn.mahua.vod.bean.VodBean;

public class AssociateAdapter extends BaseQuickAdapter<VodBean, BaseViewHolder> {
    public AssociateAdapter() {
        super(R.layout.item_associate);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, VodBean item) {
        helper.setText(R.id.tv, item.getVodName());
    }
}
