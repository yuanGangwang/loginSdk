package com.guuidea.towersdk.weight;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.guuidea.towersdk.R;
import com.guuidea.towersdk.utils.HideKeyBroadUtils;

import java.util.ArrayList;

public class EmailSuffixPopUtils {
    private static final String TAG = "EmailSuffixPopUtils";
    private static EmailSuffixPopUtils popUtils;
    private final View view;
    private final RecyclerView list;
    private final SuffixAdapter adapter;
    PopupWindow popWindow;
    private String[] data;

    private EmailSuffixPopUtils(final Context context) {

        data = context.getResources().getStringArray(R.array.email);

        popWindow = new PopupWindow();
        view = LayoutInflater.from(context).inflate(R.layout.eamil_pop_view, null);
        list = ((RecyclerView) view.findViewById(R.id.emailSuffixList));
        list.setLayoutManager(new LinearLayoutManager(context));
        adapter = new SuffixAdapter(context, data);
        list.setAdapter(adapter);

        list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                HideKeyBroadUtils.HideSoftInput(context, view.getWindowToken());
            }
        });

        popWindow.setContentView(view);
        popWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED); //解决不压键盘
        popWindow.setBackgroundDrawable(new BitmapDrawable());
        popWindow.setOutsideTouchable(true);

    }

    public static EmailSuffixPopUtils getInstance(Context context) {

        if (popUtils == null) {
            popUtils = new EmailSuffixPopUtils(context);
        }
        return popUtils;
    }

    public void showPopWindow(View anchor) {
        if (!popWindow.isShowing()) {
            popWindow.showAsDropDown(anchor);
        }
    }

    public boolean isWindowShowing() {
        return popWindow.isShowing();
    }

    public void updateEmail(String email) {
        if (email.contains("@")) {
            adapter.setEmail(email.substring(0, email.indexOf("@")));

            ArrayList<String> emails = new ArrayList<>();
            for (String s : data) {
                String substring = email.substring(email.indexOf("@")+1);
                if (s.contains(substring)) {
                    emails.add(s);
                }
            }
            if (emails.size() == 0) {
                dismiss();
            } else {
                String[] data = new String[emails.size()];
                for (int i = 0; i < emails.size(); i++) {
                    data[i] = emails.get(i);
                }
                adapter.setData(data);
            }
        } else {
            adapter.setEmail(email);
            adapter.setData(data);
        }
    }

    public void setOnSuffixClick(SuffixAdapter.OnSuffixClick onSuffixClick) {
        adapter.setOnSuffixClick(onSuffixClick);
    }

    public void dismiss() {
        popWindow.dismiss();
        popUtils = null;
    }

    static class SuffixAdapter extends RecyclerView.Adapter<SuffixAdapter.ViewHolder> {


        OnSuffixClick onSuffixClick;
        Context context;
        String email = "";
        private String[] data;

        public SuffixAdapter(Context context, String[] data) {
            this.context = context;
            this.data = data;
        }

        public void setOnSuffixClick(OnSuffixClick onSuffixClick) {
            this.onSuffixClick = onSuffixClick;
        }

        public void setEmail(String email) {
            this.email = email;
            notifyDataSetChanged();
        }

        public void setData(String[] data) {
            this.data = data;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_email_stuffix, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull SuffixAdapter.ViewHolder holder, int position) {
            holder.suffix.setText(String.format("%s%s", email, data[position]));
        }

        @Override
        public int getItemCount() {
            return data.length;
        }

        public interface OnSuffixClick {
            void onClick(String emailAll);
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            private final TextView suffix;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                suffix = ((TextView) itemView.findViewById(R.id.suffix));
                suffix.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onSuffixClick.onClick(String.format("%s%s", email, data[getLayoutPosition()]));
                    }
                });
            }
        }
    }
}
