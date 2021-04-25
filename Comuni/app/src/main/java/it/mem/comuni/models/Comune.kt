package it.mem.comuni.models

data class Comune (var istat:String="", var comune: String ="",
                   var provincia: String="", var regione: String="",
                   var prefisso: String="", var cap: String="",
                   var codFisco: String="", var abitanti: String="", var link: String=""){

    constructor(info:List<String>): this(info[0],
        info[1], info[2], info[3], info[4],
        info[5], info[6], info[7], info[8])

    constructor(info:String): this(info.split(";"))

}