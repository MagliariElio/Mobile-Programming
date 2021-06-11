package it.massimoregoli.mpacdemo

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import it.massimoregoli.mpacdemo.adapter.ChartAdapter


class SelectionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selection)
        val chartNameList: ArrayList<String> = ArrayList()
        chartNameList.add(resources.getString(R.string.tv_LineChart_text))
        chartNameList.add(resources.getString(R.string.tv_BarChart_text))
        chartNameList.add(resources.getString(R.string.tv_PieChart_text))
        chartNameList.add(resources.getString(R.string.tv_ScatterChart_text))
        chartNameList.add(resources.getString(R.string.tv_CandleStickChart_text))
        chartNameList.add(resources.getString(R.string.tv_BubbleChart_text))
        Holder(chartNameList)
    }

    internal inner class Holder(chartNameList: List<String>) {
        init {
            //attaching the RecyclerView by its id
            val rvSelectionChart = findViewById<RecyclerView>(R.id.rvSelectionChart)

            //setting GridLayoutManager for the RecyclerView of choosing chart
            if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                rvSelectionChart.layoutManager = GridLayoutManager(this@SelectionActivity, 2)
            } else {
                rvSelectionChart.layoutManager = GridLayoutManager(this@SelectionActivity, 3)
            }

            //setting ChartAdapter for the RecyclerView of choosing chart
            val adapter = ChartAdapter(this@SelectionActivity, chartNameList)
            rvSelectionChart.adapter = adapter
        }
    }
}