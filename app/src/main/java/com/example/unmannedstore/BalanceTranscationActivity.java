package com.example.unmannedstore;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.unmannedstore.component.AdapterWrapper;
import com.example.unmannedstore.component.StickyListAdapter;
import com.example.unmannedstore.component.StickyListHeadersListView;

import java.util.ArrayList;

public class BalanceTranscationActivity extends Activity implements AdapterWrapper.OnHeaderClickListener, AdapterView.OnItemClickListener
        , StickyListHeadersListView.OnLoadingMoreLinstener, StickyListHeadersListView.OnHeaderClickListener{

    private LayoutInflater inflater;
    public static int transId = 0;
    ArrayList<String> list;
    StickyListAdapter adapter;
    StickyListHeadersListView stickyLV;

    private RelativeLayout moredata;
    private View progressBarView;
    private TextView progressBarTextView;
    private AnimationDrawable loadingAnimation;
    private boolean isLoading = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TransactionDetail trans = new TransactionDetail();
        String a = trans.toString();
        String[] tr=a.split("/");

        setContentView(R.layout.activity_balance_transcation);
        list = new ArrayList<String>();
        /*
        TextView money =(TextView)findViewById(R.id.bill_list_item_money);
        TextView mine=(TextView)findViewById(R.id.bill_list_item_mine);
        TextView other=(TextView)findViewById(R.id.bill_list_item_other);
        TextView time=(TextView)findViewById(R.id.bill_list_item_time);
        TextView id=(TextView)findViewById(R.id.bill_list_item_id);*/

        //money.setText("9999");

        for(int j = 1; j <= 7; j++) {
            //set text view
            list.add("\n"+"\b\b" +tr[transId + 0] + tr[transId + 1] +"￥"+ "\n\n" +"\b\b" + "to:\b" + tr[transId + 2] +
                    "\b\b" + "fr:\b" + tr[transId + 3] + "\n\n" +"\b\b" + tr[transId + 4] +
                    "\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\bid:\b" + tr[transId + 5]);
            transId = transId + 6;
            /*money.setText(tr[i]+tr[i+1]);
            mine.setText(tr[i+2]);
            other.setText(tr[i+3]);
            time.setText(tr[i+4]);
            id.setText(tr[i+5]);*/
        }

        adapter = new StickyListAdapter();
        adapter.init(this, list);

        inflater = LayoutInflater.from(this);

        moredata = (RelativeLayout)inflater.inflate(R.layout.moredata, null);
        progressBarView = (View) moredata.findViewById(R.id.loadmore_foot_progressbar);
        progressBarTextView = (TextView) moredata.findViewById(R.id.loadmore_foot_text);

        stickyLV = (StickyListHeadersListView)this.findViewById(R.id.stickyList);

        loadingAnimation = (AnimationDrawable) progressBarView.getBackground();
        stickyLV.addFooterView(moredata);
        stickyLV.setAdapter(adapter);

        stickyLV.setOnItemClickListener(this);
        stickyLV.setOnHeaderClickListener(this);
        stickyLV.setLoadingMoreListener(this);

    }

    private void loadingFinished() {

        if (null != loadingAnimation && loadingAnimation.isRunning()) {
            loadingAnimation.stop();
        }
        progressBarView.setVisibility(View.INVISIBLE);
        progressBarTextView.setVisibility(View.INVISIBLE);
        isLoading = false;

        adapter.notifyDataSetChanged();
    }

    @Override
    public void OnLoadingMore() {
        progressBarView.setVisibility(View.VISIBLE);
        progressBarTextView.setVisibility(View.VISIBLE);

        loadingAnimation.start();

        if(!isLoading) {
            isLoading = true;
            new Handler().postDelayed(new Runnable() {

                TransactionDetail trans = new TransactionDetail();
                String a = trans.toString();
                String[] tr=a.split("/");
                int tr_length = tr.length;
                @Override
                public void run() {

                    for(int i = 0; i < 7; i ++) {
                        if(transId<tr_length) {
                            list.add("\n"+"\b\b" +tr[transId + 0] + tr[transId + 1] +"￥"+ "\n\n" +"\b\b" + "to:\b" + tr[transId + 2] +
                                    "\b\b" + "fr:\b" + tr[transId + 3] + "\n\n" +"\b\b" + tr[transId + 4] +
                                    "\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\bid:\b" + tr[transId + 5]+"\n");
                            transId = transId + 6;
                        }
                        else if(transId>=tr_length) list.add("$");
                    }
                    loadingFinished();
                }
            }, 1200);
        }
    }

    @Override
    public void onHeaderClick(StickyListHeadersListView l, View header,
                              int itemPosition, long headerId, boolean currentlySticky) {
        Toast.makeText(this, "header-list" + headerId, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        Toast.makeText(this, "item" + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onHeaderClick(View header, int itemPosition, long headerId) {
        Toast.makeText(this, "header" + headerId, Toast.LENGTH_SHORT).show();

    }
}