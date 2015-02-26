package com.example.android_audioequalizer;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.audiofx.Equalizer;
import android.media.audiofx.Visualizer;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.android_audioeffects.R;

public class MainActivity extends Activity 
{
    private MediaPlayer mediaPlayer;
    private Visualizer visualizer;
    private Equalizer equalizer;

    private LinearLayout linearLayout;
    private VisualizerView visualizerView;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        setContentView(linearLayout);

        mediaPlayer = MediaPlayer.create(this, R.raw.duranduran);

        setupVisualizer();
        setupEqualizer();

        visualizer.setEnabled(true);


        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() 
        {
            public void onCompletion(MediaPlayer mediaPlayer)
            {
            		visualizer.setEnabled(false);
            }
        });

        mediaPlayer.start();
    }

    private void setupEqualizer() 
    {
    	// Создание и настройка эквалайзера
    	equalizer = new Equalizer(0, mediaPlayer.getAudioSessionId());
    	equalizer.setEnabled(true);

    	// Получить количество полос эквалайзера
        short bands = equalizer.getNumberOfBands();

        // Минимальный и максимальный уровень частот для полосы
        final short minEQLevel = equalizer.getBandLevelRange()[0];
        final short maxEQLevel = equalizer.getBandLevelRange()[1];

        for (short i = 0; i < bands; i++) 
        {
            final short band = i;

            // Показать маркер частоты для полосы
            TextView freqTextView = new TextView(this);
            freqTextView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            freqTextView.setGravity(Gravity.CENTER_HORIZONTAL);
            freqTextView.setText((equalizer.getCenterFreq(band) / 1000) + " Hz");
            linearLayout.addView(freqTextView);

            LinearLayout row = new LinearLayout(this);
            row.setOrientation(LinearLayout.HORIZONTAL);

            // Показать минимальное смещение частоты для полосы
            TextView minDbTextView = new TextView(this);
            minDbTextView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            minDbTextView.setText((minEQLevel / 100) + " dB");

            // Показать максимальное смещение частоты для полосы
            TextView maxDbTextView = new TextView(this);
            maxDbTextView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            maxDbTextView.setText((maxEQLevel / 100) + " dB");

            // Настройка регулятора частоты для полосы
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.weight = 1;
            SeekBar bar = new SeekBar(this);
            bar.setLayoutParams(layoutParams);
            bar.setMax(maxEQLevel - minEQLevel);
            bar.setProgress(equalizer.getBandLevel(band));

            // Настройка слушателя для регулятора частоты
            bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() 
            {
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) 
                {
                	// Установка смещения частоты для указанной полосы
                	equalizer.setBandLevel(band, (short) (progress + minEQLevel));
                }

                public void onStartTrackingTouch(SeekBar seekBar) {}
                public void onStopTrackingTouch(SeekBar seekBar) {}
            });

            row.addView(minDbTextView);
            row.addView(bar);
            row.addView(maxDbTextView);

            linearLayout.addView(row);
        }
    }

    private void setupVisualizer() 
    {
    	// Создание и настройка VisualizerView для отображения графика частот
    	visualizerView = new VisualizerView(this);
    	visualizerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 250));
        
    	linearLayout.addView(visualizerView);

    	// Создание и настройка Visualizer
        visualizer = new Visualizer(mediaPlayer.getAudioSessionId());
        visualizer.setCaptureSize(Visualizer.getCaptureSizeRange()[1]);
        
        // Настройка слушателя частот во время проигрывания
        // 1 - параметр - слушатель
        // 2 - частота вызова слушателя
        // 3 - надо ли вызывать метод onWaveFormDataCapture слушателя
        // 4 - надо ли вызывать метод onFftDataCapture слушателя
        visualizer.setDataCaptureListener(new Visualizer.OnDataCaptureListener() 
        {
            public void onWaveFormDataCapture(Visualizer visualizer, byte[] bytes, int samplingRate) 
            {
            	visualizerView.updateVisualizer(bytes);
            }

            public void onFftDataCapture(Visualizer visualizer, byte[] bytes, int samplingRate) {}
            
        }, Visualizer.getMaxCaptureRate() / 2, true, false);
    }

    @Override
    protected void onPause() 
    {
        super.onPause();

        if (isFinishing() && mediaPlayer != null) 
        {
        	visualizer.release();
        	equalizer.release();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}

class VisualizerView extends View 
{
    private byte[] bytes;
    private float[] points;
    
    // Размер области рисования
    private Rect rect = new Rect();

    // Объкт для отрисовки частот
    private Paint paint = new Paint();

    public VisualizerView(Context context) 
    {
        super(context);
        init();
    }

    private void init() 
    {
    	bytes = null;
    	paint.setStrokeWidth(1f);
    	paint.setAntiAlias(true);
    	paint.setColor(Color.rgb(0, 128, 255));
    }

    public void updateVisualizer(byte[] bytes) 
    {
    	this.bytes = bytes;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) 
    {
        super.onDraw(canvas);

        if (bytes == null) return;


        if (points == null || points.length < bytes.length * 4) 
        {
        	points = new float[bytes.length * 4];
        }

        rect.set(0, 0, getWidth(), getHeight());

        for (int i = 0; i < bytes.length - 1; i++) 
        {
        	points[i * 4] = rect.width() * i / (bytes.length - 1);
        	points[i * 4 + 1] = ((byte) (bytes[i] + 128)) + rect.height() / 2;
        	points[i * 4 + 2] = rect.width() * (i + 1) / (bytes.length - 1);
        	points[i * 4 + 3] = ((byte) (bytes[i + 1] + 128)) + rect.height() / 2;
        	
        	/*points[i * 4] = rect.width() * i / (bytes.length - 1);
    		points[i * 4 + 1] = rect.height();
    		points[i * 4 + 2] = rect.width() * i / (bytes.length - 1);
    		points[i * 4 + 3] = (byte) (bytes[i]+128) + rect.height()/2;*/
        }

        //canvas.drawLines(points, paint);
        canvas.drawPoints(points, paint);
    }
}
