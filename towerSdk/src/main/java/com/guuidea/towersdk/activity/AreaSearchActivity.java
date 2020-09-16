package com.guuidea.towersdk.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.guuidea.towersdk.R;
import com.guuidea.towersdk.bean.AreaResponse;
import com.guuidea.towersdk.bean.PhoneArea;
import com.guuidea.towersdk.bus.AreaListener;
import com.guuidea.towersdk.net.CallBackUtil;
import com.guuidea.towersdk.net.Constants;
import com.guuidea.towersdk.net.HeaderManager;
import com.guuidea.towersdk.net.UrlHttpUtil;
import com.guuidea.towersdk.utils.ToastUtil;
import com.guuidea.towersdk.weight.NavigaView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class AreaSearchActivity extends BaseActivity {

    private static final String TAG = "AreaSearchActivity";
    LinkedHashMap<PhoneArea, Integer> chooseData = new LinkedHashMap<>();
    ArrayList<PhoneArea> phoneAreas;
    AreaResponse areaResponse;
    private RecyclerView areaList;
    private RecyclerView tagSelectList;
    private AreaAdapter adapter;
    private TagSelectAdapter tagSelectAdapter;
    private boolean mShouldScroll;
    //记录目标项位置
    private int mToPosition;
    private LinearLayoutManager manager;
    private EditText searchEt;
    private View cancelSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_search);

        ((NavigaView) findViewById(R.id.areaTitle)).setOnBackClickListener(this);
        areaList = findViewById(R.id.areaList);
        tagSelectList = findViewById(R.id.tagSelectList);
        searchEt = findViewById(R.id.searchEt);
        cancelSearch = findViewById(R.id.cancelSearch);

        initList();
        initSearch();

        getData();
    }

    private void initSearch() {

        cancelSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchEt.setText("");
                searchEt.clearFocus();
                if (areaResponse != null)
                    generateTag(areaResponse.getData());
            }
        });
        searchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (areaResponse != null) {
                    matchSearch(s.toString().toLowerCase());
                }
            }
        });
    }

    private void matchSearch(String key) {
        ArrayList<PhoneArea> searchAreas = new ArrayList<>();
        for (PhoneArea phoneArea : areaResponse.getData()) {
            if (phoneArea.getCode().toLowerCase().contains(key) || phoneArea.getName().toLowerCase().contains(key)) {
                searchAreas.add(phoneArea);
            }
        }
        generateTag(searchAreas);

    }

    private void initList() {
        manager = new LinearLayoutManager(this);
        areaList.setLayoutManager(manager);
        tagSelectList.setLayoutManager(new LinearLayoutManager(this));

        adapter = new AreaAdapter(this);
        areaList.setAdapter(adapter);


        adapter.setOnItemClick(new AreaAdapter.OnItemClick() {
            @Override
            public void onClick(PhoneArea phoneArea) {
                AreaListener.getInstance().updateData(phoneArea);
                finish();
            }
        });
        tagSelectAdapter = new TagSelectAdapter(this);
        tagSelectAdapter.setOnclick(new TagSelectAdapter.Onclick() {
            @Override
            public void onClick(int layoutPosition) {
                Integer position = chooseData.get(new ArrayList<>(chooseData.keySet()).get(layoutPosition));
//                smoothMoveToPosition(areaList, position);

                final TopSmoothScroller mScroller = new TopSmoothScroller(AreaSearchActivity.this);
                mScroller.setTargetPosition(position);
                manager.startSmoothScroll(mScroller);
            }
        });
        tagSelectList.setAdapter(tagSelectAdapter);

        areaList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                if (phoneAreas!=null&&phoneAreas.size() > 0) {
                    if (newState == RecyclerView.SCROLL_STATE_IDLE || newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                        int firstItemPosition = areaList.getChildLayoutPosition(areaList.getChildAt(0));
                        String tag = phoneAreas.get(firstItemPosition).getTag();
                        int position = 0;
                        for (PhoneArea phoneArea : new ArrayList<>(chooseData.keySet())) {
                            if (phoneArea.getTag().equals(tag)) {
                                tagSelectAdapter.setSelectPosition(position);
                            }
                            position++;
                        }
                    }
                }

                super.onScrollStateChanged(recyclerView, newState);
            }
        });
    }

    /**
     * 滑动到指定位置
     */
    private void smoothMoveToPosition(RecyclerView mRecyclerView, final int position) {
        // 第一个可见位置
        int firstItem = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(0));
        // 最后一个可见位置
        int lastItem = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(mRecyclerView.getChildCount() - 1));
        if (position < firstItem) {
            // 第一种可能:跳转位置在第一个可见位置之前
            mRecyclerView.smoothScrollToPosition(position);
        } else if (position <= lastItem) {
            // 第二种可能:跳转位置在第一个可见位置之后
            int movePosition = position - firstItem;
            if (movePosition >= 0 && movePosition < mRecyclerView.getChildCount()) {
                int top = mRecyclerView.getChildAt(movePosition).getTop();
                mRecyclerView.smoothScrollBy(0, top);
            }
        } else {
            // 第三种可能:跳转位置在最后可见项之后
            mRecyclerView.smoothScrollToPosition(position);
            mToPosition = position;
            mShouldScroll = true;
        }
    }

    private void getData() {
        UrlHttpUtil.get(Constants.getSPhoneArea, null, HeaderManager.makeHeader(),
                new CallBackUtil() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        ToastUtil.getInstance(AreaSearchActivity.this).showCommon(throwable.getMessage());
                    }

                    @Override
                    public void onResponse(JsonObject response) {
                        areaResponse = new Gson().fromJson(response, AreaResponse.class);
                        generateTag(areaResponse.getData());
                    }

                }
        );
    }

    private void generateTag(List<PhoneArea> data) {

        if (data==null){
            return;
        }

        phoneAreas = new ArrayList<>();
        chooseData = new LinkedHashMap<>();
        int position = 0;

        if (data.size() > 0) {

            char tag = data.get(position).getCode().charAt(0);
            addTagData(String.valueOf(tag), phoneAreas);

            for (PhoneArea area : data) {
                if (!area.isShowTag()) {
                    char tagNow = area.getCode().charAt(0);
                    area.setTag(String.valueOf(tagNow));
                    if (tagNow != tag) {
                        addTagData(String.valueOf(tagNow), phoneAreas);
                        phoneAreas.add(area);
                        tag = tagNow;
                    } else {
                        phoneAreas.add(area);
                    }
                }

                position++;
            }
        }

        adapter.setData(phoneAreas);
        tagSelectAdapter.setData(chooseData);
    }

    private void addTagData(String tag, List<PhoneArea> data) {
        PhoneArea tagPosition = new PhoneArea();
        tagPosition.setTag(String.valueOf(tag));
        tagPosition.setShowTag(true);
        data.add(tagPosition);
        chooseData.put(tagPosition, data.size() - 1);
    }

    static class TagSelectAdapter extends RecyclerView.Adapter<TagSelectAdapter.ViewHolder> {

        Onclick onclick;
        private LinkedHashMap<PhoneArea, Integer> data = new LinkedHashMap<>();
        private Context mContext;
        private int selectPosition = -1;

        public TagSelectAdapter(Context mContext) {
            this.mContext = mContext;
        }

        public void setSelectPosition(int selectPosition) {
            this.selectPosition = selectPosition;
            notifyDataSetChanged();
        }

        public void setData(LinkedHashMap<PhoneArea, Integer> data) {
            this.data = data;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public TagSelectAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.area_tag, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull TagSelectAdapter.ViewHolder holder, int position) {
            if (position == selectPosition) {
                holder.tagArea.setSelected(true);
            } else {
                holder.tagArea.setSelected(false);
            }

            holder.tagArea.setText(new ArrayList<>(data.keySet()).get(position).getTag());

        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        public void setOnclick(Onclick onclick) {
            this.onclick = onclick;
        }

        public interface Onclick {
            void onClick(int layoutPosition);
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            private final TextView tagArea;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                tagArea = itemView.findViewById(R.id.tagArea);
                tagArea.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setSelectPosition(getLayoutPosition());
                        onclick.onClick(getLayoutPosition());
                    }
                });
            }
        }
    }

    static class AreaAdapter extends RecyclerView.Adapter<AreaAdapter.ViewHolder> {

        OnItemClick onItemClick;
        private List<PhoneArea> data = new ArrayList<>();
        private Context mContext;

        public AreaAdapter(Context mContext) {
            this.mContext = mContext;
        }

        public void setData(List<PhoneArea> data) {
            this.data = data;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public AreaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.area_item, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull AreaAdapter.ViewHolder holder, int position) {
            if (data.get(position).isShowTag()) {
                holder.root.setBackgroundColor(ContextCompat.getColor(mContext, R.color.gray_f3));
                holder.code.setText(data.get(position).getTag());
                holder.name.setVisibility(View.GONE);
                holder.line.setVisibility(View.GONE);
            } else {
                holder.root.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
                holder.name.setVisibility(View.VISIBLE);
                holder.line.setVisibility(View.VISIBLE);
                holder.code.setText("+" + data.get(position).getCode());
                holder.name.setText(data.get(position).getName());
            }

            if (position+1<data.size()&&data.get(position+1).isShowTag()){
                holder.line.setVisibility(View.GONE);
            }
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        public void setOnItemClick(OnItemClick onItemClick) {
            this.onItemClick = onItemClick;
        }

        interface OnItemClick {
            void onClick(PhoneArea phoneArea);
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            private final TextView code;
            private final TextView name;
            private final View root;
            private final View line;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                name = itemView.findViewById(R.id.areaName);
                code = itemView.findViewById(R.id.areaCode);
                root = itemView.findViewById(R.id.root);
                line = itemView.findViewById(R.id.line);
                root.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PhoneArea phoneArea = data.get(getLayoutPosition());
                        if (!phoneArea.isShowTag()) {
                            onItemClick.onClick(phoneArea);
                        }
                    }
                });
            }
        }
    }

    public class TopSmoothScroller extends LinearSmoothScroller {
        TopSmoothScroller(Context context) {
            super(context);
        }

        @Override
        protected int getHorizontalSnapPreference() {
            return SNAP_TO_START;
        }

        @Override
        protected int getVerticalSnapPreference() {
            return SNAP_TO_START;
        }
    }
}
