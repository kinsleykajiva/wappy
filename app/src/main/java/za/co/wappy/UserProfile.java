package za.co.wappy;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.MPPointF;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;

import java.util.ArrayList;
import java.util.List;

public class UserProfile extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener,
        OnChartValueSelectedListener {
    private HorizontalBarChart chart;
    private SeekBar seekBarX, seekBarY;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("User Profile");
        chart = findViewById(R.id.chart1);

        seekBarX = findViewById(R.id.seekBar1);
        seekBarY = findViewById(R.id.seekBar2);
        chart.setOnChartValueSelectedListener(this);
        // chart.setHighlightEnabled(false);

        chart.setDrawBarShadow(false);

        chart.setDrawValueAboveBar(true);

        chart.getDescription().setEnabled(false);

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        chart.setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        chart.setPinchZoom(false);

        // draw shadows for each bar that show the maximum value
        // chart.setDrawBarShadow(true);

        chart.setDrawGridBackground(false);

        XAxis xl = chart.getXAxis();
        xl.setPosition(XAxisPosition.BOTTOM);
       // xl.setTypeface(tfLight);
        xl.setDrawAxisLine(true);
        xl.setDrawGridLines(false);
        xl.setGranularity(10f);

        YAxis yl = chart.getAxisLeft();
       // yl.setTypeface(tfLight);
        yl.setDrawAxisLine(true);
        yl.setDrawGridLines(true);
        yl.setAxisMinimum(0f); // this replaces setStartAtZero(true)
//        yl.setInverted(true);

        YAxis yr = chart.getAxisRight();
       // yr.setTypeface(tfLight);
        yr.setDrawAxisLine(true);
        yr.setDrawGridLines(false);
        yr.setAxisMinimum(0f); // this replaces setStartAtZero(true)
//        yr.setInverted(true);

        chart.setFitBars(true);
        chart.animateY(2500);

        // setting data
        seekBarY.setProgress(50);
        seekBarX.setProgress(12);

        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setFormSize(8f);
        l.setXEntrySpace(4f);


    }

        private void setData(int count, float range) {

            float barWidth = 9f;
            float spaceForBar = 10f;
            ArrayList<BarEntry> values = new ArrayList<>();

            for (int i = 0; i < count; i++) {
                float val = (float) (Math.random() * range);
                values.add(new BarEntry(i * spaceForBar, val,
                        getResources().getDrawable(R.drawable.star)));
            }

            BarDataSet set1;

            if (chart.getData() != null &&
                    chart.getData().getDataSetCount() > 0) {
                set1 = (BarDataSet) chart.getData().getDataSetByIndex(0);
                set1.setValues(values);
                chart.getData().notifyDataChanged();
                chart.notifyDataSetChanged();
            } else {
                set1 = new BarDataSet(values, "DataSet 1");

                set1.setDrawIcons(false);

                ArrayList<IBarDataSet> dataSets = new ArrayList<>();
                dataSets.add(set1);

                BarData data = new BarData(dataSets);
                data.setValueTextSize(10f);
                //data.setValueTypeface(tfLight);
                data.setBarWidth(barWidth);
                chart.setData(data);
            }
        }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                onBackPressed();
                break;
            /*return true;*/
        }
        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bar, menu);
        return true;
    }
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        setData(seekBarX.getProgress(), seekBarY.getProgress());
        chart.setFitBars(true);
        chart.invalidate();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
    private final RectF mOnValueSelectedRectF = new RectF();
    @Override
    public void onValueSelected(Entry e, Highlight h) {
        if (e == null)
            return;

        RectF bounds = mOnValueSelectedRectF;
        chart.getBarBounds((BarEntry) e, bounds);

        MPPointF position = chart.getPosition(e, chart.getData().getDataSetByIndex(h.getDataSetIndex())
                .getAxisDependency());

        Log.i("bounds", bounds.toString());
        Log.i("position", position.toString());

        MPPointF.recycleInstance(position);
    }

    @Override
    public void onNothingSelected() {

    }
}
