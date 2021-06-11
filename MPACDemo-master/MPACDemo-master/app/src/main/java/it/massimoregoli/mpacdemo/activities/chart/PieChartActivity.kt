package it.massimoregoli.mpacdemo.activities.chart

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import it.massimoregoli.mpacdemo.R


class PieChartActivity : AppCompatActivity() {
    lateinit var holder: Holder
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pie_chart)
        holder = Holder()


        //getting the intent and its data
        val intentData = intent
        val names = intentData.getStringArrayExtra("names")
        val values = intentData.getStringArrayExtra("values")
        val n = intentData.getIntExtra("n", 0)

        //creation of PieEntry and list of PieEntry
        val entries: MutableList<PieEntry> = ArrayList()
        for (i in 0 until n) {
            entries.add(PieEntry(values!![i].toFloat(), names!![i]))
        }

        //dataset creation
        val set = PieDataSet(entries, "")

        //customization of the DataSet
        personalizeDataSet(set)
        val data = PieData(set)

        //Set the percent next to the values
        data.setValueFormatter(PercentFormatter(holder.pieChart))

        //adding data to the PieChart
        holder.pieChart.data = data
        holder.pieChart.invalidate()

        //customization of the PieChart
        personalizeChart(holder.pieChart)
        holder.pieChart.invalidate()
    }

     private fun addColor(): ArrayList<Int> {
        //creating the list of colors used in the chart
        val colors: ArrayList<Int> = ArrayList()
        colors.add(getColor(R.color.Color1))
        colors.add(getColor(R.color.colorPrimary))
        colors.add(getColor(R.color.Color2))
        colors.add(getColor(R.color.Color3))
        colors.add(getColor(R.color.Color4))
        colors.add(getColor(R.color.Color5))
        return colors
    }

    private fun personalizeDataSet(set: PieDataSet) {
        set.colors = addColor()
        set.valueTextColor = getColor(R.color.colorTextChart)
        set.valueTextSize = 15f
    }

    private fun personalizeChart(chart: PieChart) {
        chart.setHoleColor(getColor(R.color.background))
        chart.setUsePercentValues(true)

        //customization of the legend
        val legend = chart.legend
        legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        legend.orientation = Legend.LegendOrientation.VERTICAL
        legend.formSize = resources.getDimension(R.dimen.legend)
        legend.textSize = resources.getDimension(R.dimen.legend_text)
        legend.textColor = getColor(R.color.colorTextChart)

        chart.description.isEnabled = false
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            holder.pieChart.saveToGallery(resources.getString(R.string.tv_PieChart_text))
            Toast.makeText(
                this@PieChartActivity,
                this@PieChartActivity.resources.getText(R.string.toast_saved),
                Toast.LENGTH_LONG
            ).show()
        } else {
            Toast.makeText(
                this@PieChartActivity,
                this@PieChartActivity.resources.getText(R.string.toast_no_permission),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    inner class Holder : View.OnClickListener {
        val pieChart: PieChart = findViewById(R.id.pieChart)
        private val btnSave: Button = findViewById(R.id.btnSave)
        override fun onClick(v: View?) {
            val myPermissionsExternalStorage = 0
            if (ContextCompat.checkSelfPermission(
                    this@PieChartActivity,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this@PieChartActivity,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    myPermissionsExternalStorage
                )
            } else {
                pieChart.saveToGallery(resources.getString(R.string.tv_PieChart_text))
                Toast.makeText(
                    this@PieChartActivity,
                    this@PieChartActivity.resources.getText(R.string.toast_saved),
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
