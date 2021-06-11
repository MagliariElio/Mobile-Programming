package it.massimoregoli.mpacdemo.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import it.massimoregoli.mpacdemo.R
import it.massimoregoli.mpacdemo.activities.InsertDataActivity


class ChartAdapter(val context: Context, private val chartNameList: List<String>) :
    RecyclerView.Adapter<ChartAdapter.Holder>(), View.OnClickListener {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        //inflating the layout for choosing the chart
        val constraintLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view_chart_selection, parent, false) as ConstraintLayout
        constraintLayout.setOnClickListener(this)
        return Holder(constraintLayout)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        //filling the RecyclerView with name and image of the chart on the basis of the position
        val name = chartNameList[position]
        holder.tvChart.text = name
        when (name) {
            context.resources.getString(R.string.tv_LineChart_text) -> {
                holder.ivChart.setImageResource(R.drawable.line_chart)
            }
            context.resources.getString(R.string.tv_BarChart_text) -> {
                holder.ivChart.setImageResource(R.drawable.bar_chart)
            }
            context.resources.getString(R.string.tv_PieChart_text) -> {
                holder.ivChart.setImageResource(R.drawable.pie_chart)
            }
            context.resources.getString(R.string.tv_BubbleChart_text) -> {
                holder.ivChart.setImageResource(R.drawable.bubble_chart)
            }
            context.resources.getString(R.string.tv_ScatterChart_text) -> {
                holder.ivChart.setImageResource(R.drawable.scatter_chart)
            }
            context.resources.getString(R.string.tv_CandleStickChart_text) -> {
                holder.ivChart.setImageResource(R.drawable.candle_stick_chart)
            }
        }
    }

    override fun getItemCount(): Int {
        return chartNameList.size
    }

    override fun onClick(v: View) {
        //creating the intent and putting into it the position of the chart in the RecyclerView
        when (val position = (v.parent as RecyclerView).getChildAdapterPosition(v)) {
            0, 1, 2, 3, 4, 5 -> {
                val intent = Intent(context, InsertDataActivity::class.java)
                intent.putExtra("value", position)

                //starting the activity of the clicked chart with the created intent
                context.startActivity(intent)
            }
        }
    }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvChart: TextView = itemView.findViewById(R.id.tvChart)
        val ivChart: ImageView = itemView.findViewById(R.id.ivChart)
    }

}