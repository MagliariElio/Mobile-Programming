package it.massimoregoli.mpacdemo.activities.chart

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.CandleStickChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.interfaces.datasets.ICandleDataSet
import it.massimoregoli.mpacdemo.R
import java.util.*
import kotlin.collections.ArrayList


class CandleStickChartActivity : AppCompatActivity() {
    lateinit var holder: Holder
    private var rnd: Random = Random()

    // ------- INSERIRE GESTIONE ERRORI DI INSERIMENTO DATI DA PARTE DELL'UTENTE --------
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_candle_stick_chart)
        holder = Holder()
        rnd = Random()


        //getting the intent and its data
        val intentData = intent
        val names = intentData.getStringArrayExtra("names")
        val xAxises = intentData.getStringArrayExtra("xAxises")
        val shadowH = intentData.getStringArrayExtra("shadowH")
        val shadowL = intentData.getStringArrayExtra("shadowL")
        val open = intentData.getStringArrayExtra("open")
        val close = intentData.getStringArrayExtra("close")
        val numberOfFunction = intentData.getIntExtra("n", 1)

        //creating the functions list of the CandleStickChart
        val setList: MutableList<ICandleDataSet> = ArrayList()
        for (i in 0 until numberOfFunction) {
            //each set is a function on the CandleStickChart
            val set = createDataSet(
                names!![i], formatDataToFloat(xAxises!![i].trim()), formatDataToFloat(shadowH!![i]),
                formatDataToFloat(shadowL!![i]), formatDataToFloat(open!![i]),
                formatDataToFloat(close!![i])
            ) as CandleDataSet

            //each set is added to the same list
            setList.add(set)
        }
        val candleStickData = CandleData(setList)

        //adding data to the CandleStickChart
        holder.candleStickChart.data = candleStickData
        holder.candleStickChart.invalidate()

        //customization of the CandleStickChart
        personalizeChart(holder.candleStickChart)
        holder.candleStickChart.invalidate()
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

    private fun createDataSet(name: String, xAxis: List<Float>, shadowH: List<Float>,
                              shadowL:List<Float>, open:List<Float>, close:List<Float>): DataSet<*> {
        val numberOfEntry = xAxis.size.coerceAtMost(shadowH.size).coerceAtMost(shadowL.size)
            .coerceAtMost(open.size).coerceAtMost(close.size)

        //creation of Entry and list of Entry
        val entries: MutableList<CandleEntry> = ArrayList()
        for (i in 0 until numberOfEntry) {
            entries.add(
                CandleEntry(
                    xAxis[i],
                    shadowH[i],
                    shadowL[i],
                    open[i],
                    close[i]
                )
            )
            Log.v("Elemento i", "${shadowH[i]}, ${shadowL[i]}, $i")
        }

        //dataset creation
        val set = CandleDataSet(entries, name)

        //customization of the DataSet
        personalizeDataSet(set)
        return set
    }

    private fun personalizeDataSet(set: CandleDataSet) {
        set.color = Color.rgb(rnd.nextInt(255), rnd.nextInt(255), rnd.nextInt(255))
        set.valueTextColor = getColor(R.color.colorTextChart)
        set.shadowColor=Color.rgb(rnd.nextInt(255), rnd.nextInt(255), rnd.nextInt(255))
        set.shadowWidth=0.7f
        set.decreasingColor= Color.rgb(rnd.nextInt(255), rnd.nextInt(255), rnd.nextInt(255))
        set.decreasingPaintStyle=Paint.Style.FILL
        set.increasingColor= Color.rgb(rnd.nextInt(255), rnd.nextInt(255), rnd.nextInt(255))
        set.increasingPaintStyle=Paint.Style.STROKE
        set.neutralColor= Color.rgb(rnd.nextInt(255), rnd.nextInt(255), rnd.nextInt(255))
        set.valueTextColor= Color.rgb(rnd.nextInt(255), rnd.nextInt(255), rnd.nextInt(255))

    }

    private fun personalizeChart(chart: CandleStickChart) {
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
            holder.candleStickChart.saveToGallery(resources.getString(R.string.tv_PieChart_text))
            Toast.makeText(
                this@CandleStickChartActivity,
                this@CandleStickChartActivity.resources.getText(R.string.toast_saved),
                Toast.LENGTH_LONG
            ).show()
        } else {
            Toast.makeText(
                this@CandleStickChartActivity,
                this@CandleStickChartActivity.resources.getText(R.string.toast_no_permission),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    inner class Holder : View.OnClickListener {
        val candleStickChart: CandleStickChart = findViewById(R.id.candleStickChart)
        private val btnSave: Button = findViewById(R.id.btnSave)
        override fun onClick(v: View?) {
            val myPermissionWriteExternalStorage = 0
            if (ContextCompat.checkSelfPermission(
                    this@CandleStickChartActivity,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this@CandleStickChartActivity,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    myPermissionWriteExternalStorage
                )
            } else {
                candleStickChart.saveToGallery(resources.getString(R.string.tv_CandleStickChart_text))
                Toast.makeText(
                    this@CandleStickChartActivity,
                    this@CandleStickChartActivity.resources.getText(R.string.toast_saved),
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        init {
            //attaching the CandleStickChart by its id
            btnSave.setOnClickListener(this)
        }
    }
}
