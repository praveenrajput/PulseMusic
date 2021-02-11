package com.hardcodecoder.pulsemusic.fragments.nowplaying.screens;

import android.media.MediaMetadata;
import android.media.session.PlaybackState;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.slider.Slider;
import com.google.android.material.textview.MaterialTextView;
import com.hardcodecoder.pulsemusic.R;
import com.hardcodecoder.pulsemusic.fragments.nowplaying.base.BaseNowPlayingScreen;

public class StylishNowPlayingScreen extends BaseNowPlayingScreen {

    public static final String TAG = StylishNowPlayingScreen.class.getSimpleName();
    private Slider mProgressSlider;
    private ImageView mFavoriteBtn;
    private ImageView mRepeatBtn;
    private ImageView mTrackControl1;
    private FloatingActionButton mPlayPauseBtn;
    private ImageView mTrackControl2;
    private MaterialTextView mTitle;
    private MaterialTextView mSubTitle;
    private MaterialTextView mStartTime;
    private MaterialTextView mEndTime;
    private MaterialTextView mUpNext;

    public StylishNowPlayingScreen() {
        super(true);
    }

    @NonNull
    public static StylishNowPlayingScreen getInstance() {
        return new StylishNowPlayingScreen();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragmen_now_playing_stylish, container, false);
    }

    @Override
    public void onInitializeViews(@NonNull View view) {
        ViewPager2 pager = view.findViewById(R.id.stylish_nps_album_container);
        setUpPagerAlbumArt(pager,
                R.layout.stylish_nps_media_art,
                getMediaImageViewShapeAppearanceModel());

        mTitle = view.findViewById(R.id.stylish_nps_title);
        mSubTitle = view.findViewById(R.id.stylish_nps_sub_title);
        mProgressSlider = view.findViewById(R.id.stylish_nps_slider);
        mStartTime = view.findViewById(R.id.stylish_nps_start_time);
        mEndTime = view.findViewById(R.id.stylish_nps_end_time);
        mTrackControl1 = view.findViewById(R.id.stylish_nps_track_controls_1);
        mTrackControl2 = view.findViewById(R.id.stylish_nps_track_controls_2);
        mRepeatBtn = view.findViewById(R.id.stylish_nps_repeat_btn);
        mPlayPauseBtn = view.findViewById(R.id.stylish_nps_play_pause_btn);
        mFavoriteBtn = view.findViewById(R.id.stylish_nps_favourite_btn);
        mUpNext = view.findViewById(R.id.stylish_nps_up_next);

        view.findViewById(R.id.stylish_nps_close_btn).setOnClickListener(v -> dismiss());
        mRepeatBtn.setOnClickListener(v -> toggleRepeatMode());
        mPlayPauseBtn.setOnClickListener(v -> togglePlayPause());
        mFavoriteBtn.setOnClickListener(v -> toggleFavorite());

        setShowOptionsClickMenuListener(view.findViewById(R.id.stylish_nps_options_btn));
        setGotToCurrentQueueCLickListener(mUpNext);
        setUpSliderControls(mProgressSlider);
        setUpTrackControls(mTrackControl1, mTrackControl2);
        setDefaultTintToPlayBtn(mPlayPauseBtn);
    }

    @Override
    public void onRepeatStateChanged(boolean repeat) {
        handleRepeatStateChanged(mRepeatBtn, repeat);
    }

    @Override
    public void onFavoriteStateChanged(boolean isFavorite) {
        handleFavoriteStateChanged(mFavoriteBtn, isFavorite);
    }

    @Override
    public void onMetadataDataChanged(MediaMetadata metadata) {
        super.onMetadataDataChanged(metadata);
        long seconds = getDurationSeconds();
        resetSliderValues(mProgressSlider);
        mStartTime.setText(getFormattedElapsedTime(0));
        mEndTime.setText(getFormattedElapsedTime(seconds));
        mTitle.setText(metadata.getText(MediaMetadata.METADATA_KEY_TITLE));
        mSubTitle.setText(String.format("%s%s", mArtistTitle, metadata.getString(MediaMetadata.METADATA_KEY_ARTIST)));
    }

    @Override
    public void onPlaybackStateChanged(PlaybackState state) {
        super.onPlaybackStateChanged(state);
        togglePlayPauseAnimation(mPlayPauseBtn, state);
    }

    @Override
    public void onProgressUpdated(int progressInSeconds) {
        mProgressSlider.setValue(progressInSeconds);
        mStartTime.setText(getFormattedElapsedTime(progressInSeconds));
    }

    @Override
    protected void onUpNextItemChanged(String upNextTitle) {
        mUpNext.setText(upNextTitle);
    }

    @Override
    protected void onTrackControlButtonsChanged(boolean isSeekButtonEnabled) {
        setUpTrackControls(mTrackControl1, mTrackControl2);
    }
}