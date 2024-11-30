package com.example.funnymath_Luis_Ignacio_Flores_Martinez;

import android.content.Context;
import android.media.MediaPlayer;

public class SoundManager {
    private static SoundManager instance;
    private MediaPlayer mainMediaPlayer, quizMediaPlayer, evaluatingMediaPlayer;
    private boolean isMainPlaying = false, isQuizPlaying = false, isEvaluatingPlaying = false;

    private SoundManager(){}

    public static synchronized SoundManager getInstance(){
        if(instance == null){
            instance = new SoundManager();
        }
        return instance;
    }

    public void playQuizSound(Context context, int soundResId){
        if(quizMediaPlayer == null){
            quizMediaPlayer = MediaPlayer.create(context, soundResId);
            quizMediaPlayer.setLooping(true);
        }
        if(!isQuizPlaying){
            quizMediaPlayer.start();
            isQuizPlaying = true;
        }
    }

    public void stopQuizSound(){
        if(quizMediaPlayer != null && quizMediaPlayer.isPlaying()){
            quizMediaPlayer.stop();
            quizMediaPlayer.release();
            quizMediaPlayer = null;
            isQuizPlaying = false;
        }
    }

    public void pauseQuizSound(){
        if(quizMediaPlayer != null && quizMediaPlayer.isPlaying()){
            quizMediaPlayer.pause();
            isQuizPlaying = false;
        }
    }

    public void resumeQuizSound(){
        if(quizMediaPlayer != null && !quizMediaPlayer.isPlaying()){
            quizMediaPlayer.start();
            isQuizPlaying = true;
        }
    }

    public void playMainSound(Context context, int soundResId){
        if(mainMediaPlayer == null){
            mainMediaPlayer = MediaPlayer.create(context, soundResId);
            mainMediaPlayer.setLooping(true);
        }
        if(!isMainPlaying){
            mainMediaPlayer.start();
            isMainPlaying = true;
        }
    }

    public void stopMainSound(){
        if(mainMediaPlayer != null && mainMediaPlayer.isPlaying()){
            mainMediaPlayer.stop();
            mainMediaPlayer.release();
            mainMediaPlayer = null;
            isMainPlaying = false;
        }
    }

    public void pauseMainSound(){
        if(mainMediaPlayer != null && mainMediaPlayer.isPlaying()){
            mainMediaPlayer.pause();
            isMainPlaying = false;
        }
    }

    public void resumeMainSound(){
        if(mainMediaPlayer != null && !mainMediaPlayer.isPlaying()){
            mainMediaPlayer.start();
            isMainPlaying = true;
        }
    }

    public void playEvaluatingSound(Context context, int soundResId){
        if(evaluatingMediaPlayer == null){
            evaluatingMediaPlayer = MediaPlayer.create(context, soundResId);
            evaluatingMediaPlayer.setLooping(true);
        }
        if(!isEvaluatingPlaying){
            evaluatingMediaPlayer.start();
            isEvaluatingPlaying = true;
        }
    }

    public void stopEvaluatingSound(){
        if(evaluatingMediaPlayer != null && evaluatingMediaPlayer.isPlaying()){
            evaluatingMediaPlayer.stop();
            evaluatingMediaPlayer.release();
            evaluatingMediaPlayer = null;
            isEvaluatingPlaying = false;
        }
    }

    public void pauseEvaluatingSound(){
        if(evaluatingMediaPlayer != null && evaluatingMediaPlayer.isPlaying()){
            evaluatingMediaPlayer.pause();
            isEvaluatingPlaying = false;
        }
    }

    public boolean isMainSoundPlaying(){
        return isMainPlaying;
    }

    public boolean isQuizSoundPlaying(){
        return isQuizPlaying;
    }

    public boolean isEvaluatingSoundPlaying(){
        return isEvaluatingPlaying;
    }
}
