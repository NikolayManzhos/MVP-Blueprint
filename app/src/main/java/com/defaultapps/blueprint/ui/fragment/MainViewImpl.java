package com.defaultapps.blueprint.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.defaultapps.blueprint.App;
import com.defaultapps.blueprint.R;
import com.defaultapps.blueprint.ui.adapter.PhotosAdapter;
import com.defaultapps.blueprint.ui.presenter.MainViewPresenterImpl;
import com.squareup.leakcanary.RefWatcher;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class MainViewImpl extends Fragment implements MainView, MenuItem.OnMenuItemClickListener {

    @Inject
    MainViewPresenterImpl mainViewPresenter;

    @Inject
    PhotosAdapter photosAdapter;

    private Unbinder unbinder;

    @BindView(R.id.errorTextView)
    TextView errorText;

    @BindView(R.id.errorButton)
    Button errorButton;

    @BindView(R.id.photosRecyclerView)
    RecyclerView photosRecycler;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getAppComponent(getActivity()).inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        unbinder = ButterKnife.bind(this, view);
        mainViewPresenter.setView(this);
        initRecyclerView();
        if (savedInstanceState != null) {
            mainViewPresenter.restoreViewState();
        } else {
            mainViewPresenter.requestCachedData();
        }


        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main_view, menu);
        MenuItem refreshItem = menu.findItem(R.id.updateData);
        refreshItem.setOnMenuItemClickListener(this);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.updateData:
                mainViewPresenter.requestUpdate();
        }
        return false;
    }

    @OnClick(R.id.errorButton)
    void onClick() {
        mainViewPresenter.requestUpdate();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        photosRecycler.setAdapter(null);
        unbinder.unbind();
        mainViewPresenter.detachView();
        RefWatcher refWatcher = App.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }

    @Override
    public void showError() {
        errorText.setVisibility(View.VISIBLE);
        errorButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideError() {
        errorText.setVisibility(View.GONE);
        errorButton.setVisibility(View.GONE);
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void updateView(List<String> photosUrl, List<String> photosTitle ) {
        photosAdapter.setPhotosData(photosUrl, photosTitle);
    }

    @Override
    public void hidePhotosList() {
        photosRecycler.setVisibility(View.GONE);
    }

    @Override
    public void showPhotosList() {
        photosRecycler.setVisibility(View.VISIBLE);
    }

    private void initRecyclerView() {
        photosRecycler.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        photosRecycler.setAdapter(photosAdapter);
    }

}
