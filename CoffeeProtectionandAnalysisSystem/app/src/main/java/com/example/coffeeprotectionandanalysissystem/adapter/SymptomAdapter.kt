package com.example.coffeeprotectionandanalysissystem.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeeprotectionandanalysissystem.R

class SymptomsAdapter(private var symptoms: List<String> = emptyList()) : RecyclerView.Adapter<SymptomsAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val symptomTextView: TextView = itemView.findViewById(R.id.symptomTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_symptom, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val symptom = symptoms[position]
        holder.symptomTextView.text = "• $symptom"
    }


    override fun getItemCount() = symptoms.size

    fun setSymptoms(symptoms: List<String>) {
        this.symptoms = symptoms
        notifyDataSetChanged()
    }

    class SymptomsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val symptomTextView: TextView = itemView.findViewById(R.id.symptomTextView)

        fun bind(symptom: String) {
            symptomTextView.text = symptom
        }
    }
}
