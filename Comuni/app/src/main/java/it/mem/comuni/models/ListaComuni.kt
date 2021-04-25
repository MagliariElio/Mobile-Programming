package it.mem.comuni.models

@Suppress("SpellCheckingInspection")

class ListaComuni {
    var listaComuni: MutableList<Comune> = mutableListOf()

    fun add(c: Comune) {
        listaComuni.add(c)
    }

    fun filter(flt: (Comune)->Boolean):ListaComuni{
        val lista = ListaComuni()
        for(c in listaComuni){
            if (flt(c))
                lista.add(c)
        }
        return lista
    }

    operator fun get(position: Int):Comune{
        return listaComuni[position]
    }
}