package com.defaultapps.blueprint.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.defaultapps.blueprint.App;
import com.defaultapps.blueprint.R;
import com.defaultapps.blueprint.ui.presenter.MainViewPresenterImpl;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class MainViewImpl extends Fragment implements MainView {

    @Inject
    MainViewPresenterImpl mainViewPresenter;

    private Unbinder unbinder;

    @BindView(R.id.errorTextView)
    TextView errorText;

    @BindView(R.id.errorButton)
    Button errorButton;

    @BindView(R.id.button)
    Button button;

    @BindView(R.id.testImageView)
    ImageView testImage;

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
        unbinder = ButterKnife.bind(this, view);
        mainViewPresenter.setView(this);

        if (savedInstanceState != null) {
            //TODO: Implement cache for JSON. Load image from cache on config changes and first launch.
//            Glide.with(this)
//                    .load(url)
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .into(testImage);
        }


        super.onViewCreated(view, savedInstanceState);
    }

    @OnClick(R.id.button)
    void onClick() {
        mainViewPresenter.requestUpdate();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        mainViewPresenter.detachView();
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
    public void updateView(String imageUrl) {
        Glide
                .with(this)
                .load(imageUrl)
                .into(testImage);
        testImage.setVisibility(View.VISIBLE);
    }
}
