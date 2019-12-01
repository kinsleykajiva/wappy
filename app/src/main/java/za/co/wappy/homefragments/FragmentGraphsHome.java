package za.co.wappy.homefragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.chart.common.listener.Event;
import com.anychart.chart.common.listener.ListenersInterface;
import com.anychart.charts.Pie;
import com.anychart.charts.Radar;
import com.anychart.core.radar.series.Line;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;
import com.anychart.enums.MarkerType;

import java.util.ArrayList;
import java.util.List;

import za.co.wappy.R;

public class FragmentGraphsHome extends Fragment {
    private  View view ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view =  inflater.inflate(R.layout.fragment_home_graph, container, false);
        //barchart();
        AnyChartView anyChartView = view.findViewById(R.id.any_chart_view);
        ProgressBar v = view. findViewById(R.id.progress_bar);
       anyChartView.setProgressBar(v);

        Pie pie = AnyChart.pie();

        pie.setOnClickListener(new ListenersInterface.OnClickListener(new String[]{"x", "value"}) {
            @Override
            public void onClick(Event event) {
                Toast.makeText(getContext(), event.getData().get("x") + ":" + event.getData().get("value"), Toast.LENGTH_SHORT).show();
            }
        });

        List<DataEntry> data = new ArrayList<>();
        data.add(new ValueDataEntry("Date Item 1", 6371664));
        data.add(new ValueDataEntry("Data item 2", 789622));
        data.add(new ValueDataEntry("Data item 3", 7216301));
        data.add(new ValueDataEntry("Data item 4", 1486621));
        data.add(new ValueDataEntry("Data item 5", 1200000));

        pie.data(data);

        pie.title("Data Item Reference");

        pie.labels().position("outside");

        pie.legend().title().enabled(true);
        pie.legend().title()
                .text("Data channels")
                .padding(0d, 0d, 10d, 0d);

        /*pie.legend()
                .position("center-bottom")
                .itemsLayout(LegendLayout.HORIZONTAL)
                .align(Align.CENTER);*/


        anyChartView.setChart(pie);


        return view;
    }


}
