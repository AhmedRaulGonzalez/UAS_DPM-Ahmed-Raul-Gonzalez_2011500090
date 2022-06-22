package com.example.uasdpm2011500090

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast

class ADPTDosen(
    private val getContext: Context,
    private val customListItem: ArrayList<DATADosen>
): ArrayAdapter<DATADosen>(getContext, 0, customListItem) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var listLayout = convertView
        val holder: ViewHolder
        if(listLayout == null) {
            val inflateList = (getContext as Activity).layoutInflater
            listLayout = inflateList.inflate(R.layout.layout_data_dosen, parent, false)
            holder = ViewHolder()
            with(holder) {
                tvNmDosen = listLayout.findViewById(R.id.tvmmdosen)
                tvNidn = listLayout.findViewById(R.id.tvnidn)
                tvStudi = listLayout.findViewById(R.id.tvstudi)
                btnEdit = listLayout.findViewById(R.id.button_edit)
                btnHapus = listLayout.findViewById(R.id.button_hapus)
            }
            listLayout.tag = holder
        } else
            holder = listLayout.tag as ViewHolder
        val listItem = customListItem[position]
        holder.tvNmDosen!!.setText(listItem.NmDosen)
        holder.tvNidn!!.setText(listItem.Nidn)
        holder.tvStudi!!.setText(listItem.ProgramStudi)

        holder.btnEdit!!.setOnClickListener {
            val i = Intent(context, EntriDataDosen::class.java)
            i.putExtra("NIDN", listItem.Nidn)
            i.putExtra("nama_dosen", listItem.NmDosen)
            i.putExtra("Jabatan", listItem.jabatan)
            i.putExtra("golongan_pangkat", listItem.GolPangkat)
            i.putExtra("Pendidikan", listItem.pendidikan)
            i.putExtra("Keahlian", listItem.keahlian)
            i.putExtra("Program_studi", listItem.ProgramStudi)
            context.startActivity(i)
        }

        holder.btnHapus!!.setOnClickListener {
            val db = DATABase(context)
            val alb = AlertDialog.Builder(context)
            val NIDN = holder.tvNidn!!.text
            val nama_dosen = holder.tvNmDosen!!.text
            val Program_studi = holder.tvStudi!!.text

            with(alb) {
                setTitle("Konfirmasi Penghapusan")
                setCancelable(false)
                setMessage("""
                    Apakah Anda Yakin akan menghapus data ini??
                     
                                       $nama_dosen 
                                       [$NIDN-$Program_studi]
                                       """.trimIndent())
                setPositiveButton("Ya") { _, _ ->
                    if(db.hapus("$NIDN"))
                        Toast.makeText(
                            context,
                            "Data dosen berhasil dihapus",
                            Toast.LENGTH_SHORT
                        ).show()
                    else
                        Toast.makeText(
                            context,
                            "Data dosen gagal dihapus",
                            Toast.LENGTH_SHORT
                        ).show()
                }
                setNegativeButton("Tidak", null)
                create().show()
            }
        }

        return listLayout!!
    }

    class ViewHolder {
        internal var tvNmDosen: TextView? = null
        internal var tvNidn: TextView? = null
        internal var tvStudi: TextView? = null
        internal var btnEdit: ImageButton? = null
        internal var btnHapus: ImageButton? = null
    }
}