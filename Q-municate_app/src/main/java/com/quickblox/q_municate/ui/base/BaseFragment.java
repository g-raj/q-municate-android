package com.quickblox.q_municate.ui.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.quickblox.q_municate.App;
import com.quickblox.q_municate.core.bridge.ActionBarBridge;
import com.quickblox.q_municate.core.bridge.ConnectionBridge;
import com.quickblox.q_municate.core.bridge.LoadingBridge;
import com.quickblox.q_municate.core.listeners.ServiceConnectionListener;
import com.quickblox.q_municate.core.listeners.UserStatusChangingListener;
import com.quickblox.q_municate_core.qb.helpers.QBFriendListHelper;
import com.quickblox.q_municate_core.qb.helpers.QBGroupChatHelper;
import com.quickblox.q_municate_core.qb.helpers.QBPrivateChatHelper;
import com.quickblox.q_municate_core.service.QBService;

import butterknife.ButterKnife;

public abstract class BaseFragment extends Fragment implements UserStatusChangingListener, ServiceConnectionListener {

    protected App app;
    protected BaseActivity baseActivity;
    protected BaseActivity.FailAction failAction;
    protected String title;
    protected ConnectionBridge connectionBridge;
    protected ActionBarBridge actionBarBridge;
    protected LoadingBridge loadingBridge;

    protected QBFriendListHelper friendListHelper;
    protected QBPrivateChatHelper privateChatHelper;
    protected QBGroupChatHelper groupChatHelper;

    protected QBService service;

    public String getTitle() {
        return title;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (activity instanceof BaseActivity) {
            baseActivity = (BaseActivity) activity;
            service = baseActivity.service;
            friendListHelper = baseActivity.friendListHelper;
            privateChatHelper = baseActivity.privateChatHelper;
            groupChatHelper = baseActivity.groupChatHelper;
        }

        if (activity instanceof ConnectionBridge) {
            connectionBridge = (ConnectionBridge) activity;
        }

        if (activity instanceof ActionBarBridge) {
            actionBarBridge = (ActionBarBridge) activity;
        }

        if (activity instanceof LoadingBridge) {
            loadingBridge = (LoadingBridge) activity;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        baseActivity.setFragmentUserStatusChangingListener(this);
        baseActivity.setFragmentServiceConnectionListener(this);

        app = App.getInstance();
        failAction = baseActivity.failAction;
    }

    public void initActionBar() {
        actionBarBridge.setActionBarUpButtonEnabled(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        initActionBar();
    }

    protected boolean isExistActivity() {
        return ((!isDetached()) && (baseActivity != null));
    }

    protected void activateButterKnife(View view) {
        ButterKnife.bind(this, view);
    }

    @Override
    public void onChangedUserStatus(int userId, boolean online) {
        // nothing by default
    }

    @Override
    public void onConnectedToService(QBService service) {
        // nothing by default
    }
}