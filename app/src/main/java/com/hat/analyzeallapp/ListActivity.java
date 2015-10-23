package com.hat.analyzeallapp;

import android.app.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

/**
 * Created by anting.hu on 2015/10/23.
 */
public class ListActivity extends Activity implements AppSearchCallback{

    private LinearLayout mLoadingLayout;
    private LinearLayout mContentLayout;

    private Spinner mSpinner;
    private ListView mListView;

    Hashtable permissionHash = new Hashtable(); //key：权限, value: PackageInfo
    Hashtable appHash = new Hashtable(); //key： 应用名称


    List<String> mSpinnerData = new ArrayList<String>();
    List<String> mListviewData = new ArrayList<String>();
    ArrayAdapter mListviewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        mListView = (ListView)findViewById(R.id.listView);
        mSpinner = (Spinner)findViewById(R.id.spinner);
        mLoadingLayout = (LinearLayout)findViewById(R.id.loading_layout);
        mContentLayout = (LinearLayout)findViewById(R.id.content_layout);
        mLoadingLayout.setVisibility(View.VISIBLE);
        mContentLayout.setVisibility(View.GONE);

        ReadAppTask task = new ReadAppTask(ListActivity.this, permissionHash, appHash,
                new AppSearchHandler(this));
        task.execute("");
    }

    @Override
    public void OnFinish() {

        mLoadingLayout.setVisibility(View.GONE);
        mContentLayout.setVisibility(View.VISIBLE);
        Iterator it = permissionHash.keySet().iterator();
        while(it.hasNext())
        {
            mSpinnerData.add(PermissionDes.PermissionToDes((String)it.next()));
        }

        UpdateListData(0);
        mListviewAdapter = new ArrayAdapter<String>(ListActivity.this, android.R.layout.simple_list_item_1, mListviewData);
        mListView.setAdapter(mListviewAdapter);

        ArrayAdapter adapter = new ArrayAdapter<String>(ListActivity.this, android.R.layout.simple_spinner_item, mSpinnerData);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                UpdateListData(position);
                mListviewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
    }

    //更新List数据
    void UpdateListData(int position)
    {
        String key = PermissionDes.DesToPermission(mSpinnerData.get(position));
        mListviewData.clear();
        Hashtable listHash = (Hashtable)permissionHash.get(key);
        Iterator it = listHash.keySet().iterator();
        while(it.hasNext()) {
            String tmp = (String)it.next();
            Log.i("Test", "Key=" + tmp);
            mListviewData.add(tmp);
        }
    }
}

