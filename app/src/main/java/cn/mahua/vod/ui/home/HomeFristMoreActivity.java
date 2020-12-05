package cn.mahua.vod.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.mahua.vod.App;
import cn.mahua.vod.R;
import cn.mahua.vod.base.BaseActivity;
import cn.mahua.vod.bean.VodBean;
import cn.mahua.vod.ui.play.PlayActivity;
import cn.mahua.vod.ui.seek.SeekActivity;

public class HomeFristMoreActivity extends BaseActivity {

    @BindView(R.id.live_gridview)
    GridView liveGridView;
    @BindView(R.id.tv_task_title)
    TextView mTvTitle;
    @BindView(R.id.rlBack)
    RelativeLayout rlBack;

    List<VodBean> liveBeans;
    FristModeAdpter moreAdpter;

    public static void start(ArrayList<VodBean> page, String title) {
        Intent intent = new Intent(App.getInstance().getContext(), HomeFristMoreActivity.class);
        intent.putParcelableArrayListExtra("myData", page);
        intent.putExtra("title", title);
        ActivityUtils.startActivity(intent, R.anim.slide_in_right, R.anim.no_anim);
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_home_first_more;
    }

    @OnClick(R.id.iv_search)
    void seek() {
        ActivityUtils.startActivity(SeekActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent() != null) {
            liveBeans = getIntent().getParcelableArrayListExtra("myData");
        }

        if (liveBeans == null) {
            liveBeans = new ArrayList<VodBean>();
        }

        mTvTitle.setText(getIntent().getStringExtra("title"));
        moreAdpter = new FristModeAdpter(HomeFristMoreActivity.this, liveBeans);
        liveGridView.setAdapter(moreAdpter);
        moreAdpter.notifyDataSetChanged();


        liveGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                PlayActivity.startByVod(liveBeans.get(position));
            }
        });
        rlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
