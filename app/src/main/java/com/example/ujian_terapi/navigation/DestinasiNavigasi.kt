package com.example.ujian_terapi.navigation


interface DestinasiNavigasi {
    val route: String
    val titleRes: String
}

object DestinasiPasienList : DestinasiNavigasi {
    override val route = "pasien_list"
    override val titleRes = "Daftar Pasien"
}

object DestinasiPasienEntry : DestinasiNavigasi {
    override val route = "pasien_entry"
    override val titleRes = "Tambah Pasien"
}

object DestinasiPasienDetail : DestinasiNavigasi {
    override val route = "pasien_detail/{idPasien}"
    override val titleRes = "Detail Pasien"
    const val idPasienArg = "idPasien"

    fun createRoute(idPasien: Int): String = "pasien_detail/$idPasien"
}
