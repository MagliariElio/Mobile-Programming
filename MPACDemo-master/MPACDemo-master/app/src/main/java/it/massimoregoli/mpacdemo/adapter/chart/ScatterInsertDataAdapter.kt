package it.massimoregoli.mpacdemo.adapter.chart

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.EditText
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import it.massimoregoli.mpacdemo.R


class ScatterInsertDataAdapter : RecyclerView.Adapter<ScatterInsertDataAdapter.Holder>() {
    private var c = 1
    private val holders: MutableList<Holder>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        //inflating the layout for insertion of the ScatterChart data
        val constraintLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view_scatter_insert_data, parent, false) as ConstraintLayout
        return Holder(constraintLayout)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holders.add(holder)
        val name = String.format("%s %d", holder.etScatterName.hint.toString(), position + 1)
        holder.etScatterName.setText(name)
        holder.etXAxis.isEnabled = !holder.cbDefaultValues.isChecked
    }

    override fun getItemCount(): Int {
        return c
    }

    fun addCard() {
        //item counter increment and adding a CardView for the insertion of ScatterChart data
        c++
        notifyItemInserted(c)
    }

    //getting function names from holders and putting them into an array of strings
    val names: Array<String?>
        get() {
            //getting function names from holders and putting them into an array of strings
            val values = arrayOfNulls<String>(c)
            for (i in 0 until c) {
                values[i] = holders[i].etScatterName.text.toString()
            }
            return values
        }

    //getting x-values of the function and putting them into an array of strings
    val xAxis: Array<String?>
        get() {
            //getting x-values of the function and putting them into an array of strings
            val values = arrayOfNulls<String>(c)
            for (i in 0 until c) {
                values[i] = holders[i].etXAxis.text.toString()
            }
            return values
        }

    //getting y-values of the function and putting them into an array of strings
    val yAxis: Array<String?>
        get() {
            //getting y-values of the function and putting them into an array of strings
            val values = arrayOfNulls<String>(c)
            for (i in 0 until c) {
                values[i] = holders[i].etYAxis.text.toString()
            }
            return values
        }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView),
        CompoundButton.OnCheckedChangeListener, TextWatcher {
        //        val tvScatterName: TextView = itemView.findViewById(R.id.tvNameScatter)
//        private val tvXAxis: TextView = itemView.findViewById(R.id.tvXAxis)
//        private val tvYAxis: TextView = itemView.findViewById(R.id.tvYAxis)
        val etScatterName: EditText = itemView.findViewById(R.id.etNameScatter)
        val etXAxis: EditText = itemView.findViewById(R.id.etXAxis)

        val etYAxis: EditText = itemView.findViewById(R.id.etYAxis)
        var cbDefaultValues: CheckBox = itemView.findViewById(R.id.cbDefaultValues)
        override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
            if (isChecked) {
                //self-generated x-values in ascending order if the CompoundButton is checked
                val n = countNumbers(etYAxis.text.toString())
                val numbers = StringBuilder()
                for (i in 0 until n) {
                    numbers.append(i + 1).append(" ")
                }
                //setting the self-generated x-values and disabling changes on the EditText etXAxis
                etXAxis.setText(numbers.toString())
                etXAxis.isEnabled = false
            } else {
                //enabling changes on the EditText etXAxis
                etXAxis.setText("")
                etXAxis.isEnabled = true
            }
        }

        private fun countNumbers(text: String): Int {
            //count of how many data are entered for the y-axis
            var n = 0
            if (text.isEmpty()) {
                return 0
            }
            if (Character.isDigit(text[0])) {
                n++
            }
            for (element in text) {
                if (element == ' ') {
                    n++
                }
            }
            if (text.endsWith(" ")) {
                n--
            }
            return n
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable) {
            //check if the Button cbDefaultValues was changed
            if (cbDefaultValues.isChecked) {
                onCheckedChanged(cbDefaultValues, true)
            }
        }

        init {
            //attaching views by their id

            //setting of listeners
            cbDefaultValues.setOnCheckedChangeListener(this)
            etYAxis.addTextChangedListener(this)
        }
    }

    init {
        holders = ArrayList()

    }
}