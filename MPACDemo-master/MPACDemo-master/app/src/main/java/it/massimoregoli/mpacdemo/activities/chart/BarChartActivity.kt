package it.massimoregoli.mpacdemo.activities.chart

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.DataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import it.massimoregoli.mpacdemo.R
import java.util.*
import kotlin.collections.ArrayList

class BarChartActivity : AppCompatActivity() {
    private lateinit var holder: Holder
    private var rnd: Random = Random()
    private var numberOfGroups = 0
    private var barWidth = 0f
    private var groupSpace = 0f
    private var barSpace = 0f

    // ------- INSERIRE GESTIONE ERRORI DI INSERIMENTO DATI DA PARTE DELL'UTENTE --------
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bar_chart)
        holder = Holder()
        rnd = Random()

        //getting the intent and its data
        val intentData = intent
        val groups = intentData.getStringArrayExtra("groups")
        val xAxises = intentData.getStringArrayExtra("xAxises")
        val yAxises = intentData.getStringArrayExtra("yAxises")
        numberOfGroups = intentData.getIntExtra("n", 1)
        val barData = BarData()

        //creating the groups list of the BarChart
        for (i in 0 until numberOfGroups) {
            //each set is a bar on the BarChart
            val set: BarDataSet =
                createDataSet(groups!![i], formatDataToFloat(yAxises!![i])) as BarDataSet

            //each set is added to the BarData
            barData.addDataSet(set)
            barData.notifyDataChanged()
        }

        //customization of BarData
        personalizeDataChart(barData)

        //adding data to the BarChart
        holder.barChart.data = barData
        holder.barChart.invalidate()


        //customization of the BarChart
        personalizeChart(holder.barChart)
        addLabels(holder.barChart, xAxises!![0])
        holder.barChart.invalidate()
    }

    private fun addLabels(barChart: BarChart, name: String) {
        barChart.xAxis.valueFormatter = IndexAxisValueFormatter(formatDataToString(name))
    }

    private fun formatString(string: String): String {
        var lstring = string
        while (lstring.contains("  ")) {
            lstring = lstring.replace("  ", " ")
        }
        lstring = lstring.replace(" ", ",")
        return lstring
    }

    private fun formatDataToString(values: String): ArrayList<String> {
        var lvalues = values
        val list: ArrayList<String> = ArrayList()

        //data format controls
        lvalues = formatString(lvalues)

        //adding single strings to a list of strings
        val strings = lvalues.split(",".toRegex()).toTypedArray()
        Collections.addAll(list, *strings)
        return list
    }

    private fun formatDataToFloat(values: String): List<Float> {
        var lvalues = values
        val list: MutableList<Float> = ArrayList()

        //data format controls
        lvalues = formatString(lvalues)

        //conversion of data from String to Float
        val strings = lvalues.split(",".toRegex()).toTypedArray()
        for (string in strings) {
            list.add(java.lang.Float.valueOf(string))
        }
        return list
    }

    private fun createDataSet(name: String, values: List<Float>): DataSet<BarEntry> {
        val numberOfEntry = values.size

        //creation of BarEntry and list of BarEntry
        val entries: MutableList<BarEntry> = ArrayList()
        for (i in 0 until numberOfEntry) {
            entries.add(BarEntry(i.toFloat(), values[i]))
        }

        //dataset creation
        val set = BarDataSet(entries, name)


        //customization of the DataSet
        personalizeDataSet(set)
        return set
    }

    private fun personalizeDataSet(set: DataSet<BarEntry>) {
        set.color = Color.rgb(rnd.nextInt(255),
            rnd.nextInt(255),
            rnd.nextInt(255))
        set.valueTextColor = getColor(R.color.colorTextChart)
    }

    private fun personalizeDataChart(barData: BarData) {
        groupSpace = 0.2f
        barWidth = 0.7f / numberOfGroups
        barSpace = 0.1f / numberOfGroups
        barData.barWidth = barWidth
        if (numberOfGroups > 1) barData.groupBars(0f, groupSpace, barSpace)
    }

    private fun personalizeChart(chart: BarChart) {
        //setting colors
        chart.xAxis.textColor = getColor(R.color.colorTextChart)
        chart.axisLeft.textColor = getColor(R.color.colorTextChart)
        chart.axisRight.textColor = getColor(R.color.colorTextChart)
        chart.legend.textColor = getColor(R.color.colorTextChart)

        //fitting the bars in order to properly show them
        chart.setFitBars(true)

        //customization of x-axis
        val xAxis: XAxis = chart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.granularity = 1F
        xAxis.isGranularityEnabled = true
        xAxis.setCenterAxisLabels(true)
        chart.axisLeft.granularity = 1f
        chart.axisRight.setDrawLabels(false)

        //setting the axises range
        chart.axisLeft.axisMinimum = 0f
        chart.xAxis.axisMinimum = 0f
        val numberOfEntry: Int = chart.barData.dataSets[0].entryCount
        if (numberOfGroups == 1) {
            chart.xAxis.axisMinimum = -0.5f
            xAxis.setCenterAxisLabels(false)
        }
        chart.xAxis.axisMaximum = numberOfEntry.toFloat()

        //disabling description of the chart
        chart.description.isEnabled = false
        chart.setFitBars(true)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            holder.barChart.saveToGallery(resources.getString(R.string.tv_PieChart_text))
            Toast.makeText(
                this@BarChartActivity,
                this@BarChartActivity.resources.getText(R.string.toast_saved),
                Toast.LENGTH_LONG
            ).show()
        } else {
            Toast.makeText(
                this@BarChartActivity,
                this@BarChartActivity.resources.getText(R.string.toast_no_permission),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    inner class Holder : View.OnClickListener {
        val barChart: BarChart = findViewById(R.id.barChart)
        private val btnSave: Button = findViewById(R.id.btnSave)
        override fun onClick(v: View?) {
            val myPermissionsWriteExternalStorage = 0
            if (ContextCompat.checkSelfPermission(
                    this@BarChartActivity,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this@BarChartActivity,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    myPermissionsWriteExternalStorage
                )
            } else {
                barChart.saveToGallery(resources.getString(R.string.tv_BarChart_text))
                Toast.makeText(
                    this@BarChartActivity,
                    this@BarChartActivity.resources.getText(R.string.toast_saved),
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        init {
            //attaching the PieChart by its id
            btnSave.setOnClickListener(this)
        }
    }


}