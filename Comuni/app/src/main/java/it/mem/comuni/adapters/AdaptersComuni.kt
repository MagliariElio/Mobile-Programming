package it.mem.comuni.adapters


import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.view.LayoutInflater
import android.widget.Filter
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import it.mem.comuni.databinding.LeftBinding
import it.mem.comuni.models.Comune
import it.mem.comuni.models.ListaComuni
import java.util.*

@Suppress("SpellCheckingInspection")
class AdapterComuni(contesto:Context,placesList: ListaComuni):
    RecyclerView.Adapter<AdapterComuni.ViewHolder>() {
    private var allPlacesList = ListaComuni()
    private var filteredPlacesList = ListaComuni()
    private lateinit var binding: LeftBinding
    private var context=contesto


    init {
        allPlacesList =placesList
        filteredPlacesList = allPlacesList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        binding = LeftBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(context, binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(position%2==0)
            holder.itemView.setBackgroundColor(Color.LTGRAY)
        else
            holder.itemView.setBackgroundColor(Color.WHITE)

        holder.comune = filteredPlacesList[position]


        

    }

    override fun getItemCount(): Int {
        return filteredPlacesList.listaComuni.size
    }

    override fun getItemViewType(position: Int): Int {
        return position % 2
    }

    @Suppress("unused")
    fun getFilter(): Filter{
        return object : Filter(){
            override fun publishResults(charSequence: CharSequence?, filterResults: FilterResults){
                filteredPlacesList = filterResults.values as ListaComuni
                notifyDataSetChanged()
            }

            override fun performFiltering(charSequence: CharSequence?): FilterResults{
                val queryString = charSequence?.toString()?.toUpperCase(Locale.getDefault())

                val filterResults = FilterResults()

                filterResults.values = if(queryString == null || queryString.isEmpty())
                    allPlacesList
                else
                    allPlacesList.filter{
                        it.comune.toUpperCase(Locale.getDefault()).startsWith(queryString)
                    }

                return filterResults
            }
        }

    }

    class ViewHolder(context: Context, binding: LeftBinding):RecyclerView.ViewHolder(binding.root){
        private val contesto = context
        var comune: Comune = Comune()
            set(value) {
                field =value
                tvComune.text = value.comune
                tvLink.text = value.link.toLowerCase(Locale.getDefault())
                tvProvincia.text = String.format("(%s)",
                    value.provincia.toUpperCase(Locale.getDefault()))
                tvCap.text=value.cap

                tvLink.setOnClickListener {
                    val url=tvLink.text.toString()
                    val intent=Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(url)
                    startActivity(contesto, intent, null)
                }

            }


        private var tvComune: TextView = binding.tvComune
        private var tvLink: TextView = binding.tvLink
        private var tvProvincia: TextView = binding.tvProvincia
        private var tvCap: TextView = binding.tvCap


    }

}