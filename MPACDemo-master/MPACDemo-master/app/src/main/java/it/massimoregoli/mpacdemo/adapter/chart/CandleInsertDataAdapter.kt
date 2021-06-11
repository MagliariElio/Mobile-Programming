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


class CandleInsertDataAdapter : RecyclerView.Adapter<CandleInsertDataAdapter.Holder>() {
    private var c = 1
    private val holders: MutableList<Holder>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        //inflating the layout for insertion of the CandleChart data
        val constraintLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view_candle_insert_data, parent, false) as ConstraintLayout
        return Holder(constraintLayout)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holders.add(holder)
        val name = String.format("%s %d", holder.etCandleName.hint.toString(), position + 1)
        holder.etCandleName.setText(name)
        holder.etXAxis.isEnabled = !holder.cbDefaultValues.isChecked
    }

    override fun getItemCount(): Int {
        return c
    }

    fun addCard() {
        //item counter increment and adding a CardView for the insertion of CandleChart data
        c++
        notifyItemInserted(c)
    }

    //getting function names from holders and putting them into an array of strings
    val names: Array<String?>
        get() {
            //getting function names from holders and putting them into an array of strings
            val values = arrayOfNulls<String>(c)
            for (i in 0 until c) {
                values[i] = holders[i].etCandleName.text.toString()
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

    val shadowL: Array<String?>
        get() {
            val values = arrayOfNulls<String>(c)
            for (i in 0 until c) {
                values[i] = holders[i].etShadowL.text.toString()
            }
            return values
        }

    val shadowH: Array<String?>
        get() {
            val values = arrayOfNulls<String>(c)
            for (i in 0 until c) {
                values[i] = holders[i].etShadowH.text.toString()
            }
            return values
        }

    val open: Array<String?>
        get() {
            val values = arrayOfNulls<String>(c)
            for (i in 0 until c) {
                values[i] = holders[i].etOpen.text.toString()
            }
            return values
        }

    val close: Array<String?>
        get() {
            val values = arrayOfNulls<String>(c)
            for (i in 0 until c) {
                values[i] = holders[i].etClose.text.toString()
            }
            return values
        }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView),
        CompoundButton.OnCheckedChangeListener, TextWatcher {
        //        val tvCandleName: TextView = itemView.findViewById(R.id.tvNameCandle)
//        private val tvXAxis: TextView = itemView.findViewById(R.id.tvXAxis)
//        private val tvYAxis: TextView = itemView.findViewById(R.id.tvYAxis)
        val etCandleName: EditText = itemView.findViewById(R.id.etNameCandle)
        val etXAxis: EditText = itemView.findViewById(R.id.etXAxis)

        val etShadowH: EditText = itemView.findViewById(R.id.etShadowH)
        val etShadowL: EditText = itemView.findViewById(R.id.etShadowL)
        val etOpen: EditText = itemView.findViewById(R.id.etOpen)
        val etClose: EditText = itemView.findViewById(R.id.etClose)

        var cbDefaultValues: CheckBox = itemView.findViewById(R.id.cbDefaultValues)
        override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
            if (isChecked) {
                //self-generated x-values in ascending order if the CompoundButton is checked
                val n = countNumbers(etShadowH.text.toString()).coerceAtMost(countNumbers(etShadowL.text.toString()))
                    .coerceAtMost(countNumbers(etOpen.text.toString()))
                    .coerceAtMost(countNumbers(etClose.text.toString()))
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
            etShadowH.addTextChangedListener(this)
            etShadowL.addTextChangedListener(this)
            etOpen.addTextChangedListener(this)
            etClose.addTextChangedListener(this)

        }
    }

    init {
        holders = ArrayList()

    }
}