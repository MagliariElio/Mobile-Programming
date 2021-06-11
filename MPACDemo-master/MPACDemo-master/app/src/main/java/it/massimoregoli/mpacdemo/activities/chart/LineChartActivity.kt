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
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import it.massimoregoli.mpacdemo.R
import java.util.*
import kotlin.collections.ArrayList


class LineChartActivity : AppCompatActivity() {
    lateinit var holder: Holder
    private var rnd: Random = Random()

    // ------- INSERIRE GESTIONE ERRORI DI INSERIMENTO DATI DA PARTE DELL'UTENTE --------
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_line_chart)
        holder = Holder()
        rnd = Random()


        //getting the intent and its data
        val intentData = intent
        val names = intentData.getStringArrayExtra("names")
        val xAxises = intentData.getStringArrayExtra("xAxises")
        val yAxises = intentData.getStringArrayExtra("yAxises")
        val numberOfFunction = intentData.getIntExtra("n", 1)

        //creating the functions list of the LineChart
        val setList: MutableList<ILineDataSet> = ArrayList()
        for (i in 0 until numberOfFunction) {
            //each set is a function on the LineChart
            val set = createDataSet(
                names!![i], formatDataToFloat(xAxises!![i].trim()), formatDataToFloat(
                    yAxises!![i]
                )
            ) as LineDataSet

            //each set is added to the same list
            setList.add(set)
        }
        val lineData = LineData(setList)

        //adding data to the LineChart
        holder.lineChart.data = lineData
        holder.lineChart.invalidate()

        //customization of the LineChart
        personalizeChart(holder.lineChart)
        holder.lineChart.invalidate()
    }

    private fun formatString(string: String): String {
        //elimination of any multiple space between one data and another
        var localstring = string
        while (localstring.contains("  ")) {
            localstring = localstring.replace("  ", " ")
        }
        localstring = localstring.replace(" ", ",")
        return localstring
    }

    private fun formatDataToFloat(values: String): List<Float> {
        var localvalues = values
        val list: MutableList<Float> = ArrayList()

        //data format controls
        localvalues = formatString(localvalues)

        //conversion of data from String to Float
        val strings = localvalues.split(",".toRegex()).toTypedArray()
        for (string in strings) {
            list.add(java.lang.Float.valueOf(string))
        }
        return list
    }

    private fun createDataSet(name: String, xAxis: List<Float>, yAxis: List<Float>): DataSet<*> {
        val numberOfEntry = xAxis.size.coerceAtMost(yAxis.size)

        //creation of Entry and list of Entry
        val entries: MutableList<Entry> = ArrayList()
        for (i in 0 until numberOfEntry) {
            entries.add(
                Entry(
                    xAxis[i],
                    yAxis[i]
                )
            )
        }

        //dataset creation
        val set = LineDataSet(entries, name)

        //customization of the DataSet
        personalizeDataSet(set)
        return set
    }

    private fun personalizeDataSet(set: DataSet<*>) {
        set.color = Color.rgb(rnd.nextInt(255), rnd.nextInt(255), rnd.nextInt(255))
        set.valueTextColor = getColor(R.color.colorTextChart)
    }

    private fun personalizeChart(chart: LineChart) {
        //setting animation on the chart
        chart.animateX(1000, Easing.Linear)

        //customization of x-axis position
        chart.xAxis.position = XAxis.XAxisPosition.BOTTOM

        //setting colors
        chart.xAxis.textColor = getColor(R.color.colorTextChart)
        chart.axisLeft.textColor = getColor(R.color.colorTextChart)
        chart.axisRight.textColor = getColor(R.color.colorTextChart)
        chart.legend.textColor = getColor(R.color.colorTextChart)

        //disabling description of the chart
        chart.description.isEnabled = false
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            holder.lineChart.saveToGallery(resources.getString(R.string.tv_PieChart_text))
            Toast.makeText(
                this@LineChartActivity,
                this@LineChartActivity.resources.getText(R.string.toast_saved),
                Toast.LENGTH_LONG
            ).show()
        } else {
            Toast.makeText(
                this@LineChartActivity,
                this@LineChartActivity.resources.getText(R.string.toast_no_permission),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    inner class Holder : View.OnClickListener {
        val lineChart: LineChart = findViewById(R.id.lineChart)
        private val btnSave: Button = findViewById(R.id.btnSave)
        override fun onClick(v: View?) {
            val myPermissionWriteExternalStorage = 0
            if (ContextCompat.checkSelfPermission(
                    this@LineChartActivity,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this@LineChartActivity,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    myPermissionWriteExternalStorage
                )
            } else {
                lineChart.saveToGallery(resources.getString(R.string.tv_LineChart_text))
                Toast.makeText(
                    this@LineChartActivity,
                    this@LineChartActivity.resources.getText(R.string.toast_saved),
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        init {
            //attaching the LineChart by its id
            btnSave.setOnClickListener(this)
        }
    }
}
