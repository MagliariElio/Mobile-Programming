package it.mem.codiceFiscale.viewmodels

data class CountryModel(var description: String,
                        var province: String,
                        var code: String) {
    override fun toString(): String {
        return  description
    }
}