package it.massimoregoli.mpacdemo.adapter.chart

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import it.massimoregoli.mpacdemo.R


class PieInsertDataAdapter : RecyclerView.Adapter<PieInsertDataAdapter.Holder>() {
    private var c = 1
    private val holders: MutableList<Holder>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        //inflating the layout for insertion of the PieChart data
        val constraintLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view_pie_insert_data, parent, false) as ConstraintLayout
        return Holder(constraintLayout)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: Holder, position: Int) {
        holders.add(holder)
        holder.etSliceName.setText(holder.etSliceName.hint.toString() + " " + (position + 1))
    }

    override fun getItemCount(): Int {
        return c
    }

    fun addCard() {
        //item counter increment and adding a CardView for the insertion of PieChart data
        c++
        notifyItemInserted(c)
    }

    //getting slice names from holders and putting them into an array of strings
    val names: Array<String?>
        get() {
            //getting slice names from holders and putting them into an array of strings
            val values = arrayOfNulls<String>(c)
            for (i in 0 until c) {
                values[i] = holders[i].etSliceName.text.toString()
            }
            return values
        }

    fun getvaluesSlice(): Array<String?> {
        //getting slices values and putting them into an array of strings
        val values = arrayOfNulls<String>(c)
        for (i in 0 until c) {
            values[i] = holders[i].etSliceValue.text.toString()
        }
        return values
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val tvSliceName: TextView = itemView.findViewById(R.id.tvSliceName)
//        val tvSliceValue: TextView = itemView.findViewById(R.id.tvSliceValue)
        val etSliceName: EditText = itemView.findViewById(R.id.etSliceName)
        val etSliceValue: EditText = itemView.findViewById(R.id.etSliceValue)

    }

    init {
        holders = ArrayList()
    }


}