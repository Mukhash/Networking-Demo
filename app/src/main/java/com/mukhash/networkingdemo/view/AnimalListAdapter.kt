package com.mukhash.networkingdemo.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mukhash.networkingdemo.databinding.ItemAnimalBinding
import com.mukhash.networkingdemo.model.Animal

class AnimalListAdapter(private val animalList: ArrayList<Animal>) :
    RecyclerView.Adapter<AnimalListAdapter.AnimalViewHolder>() {

    fun updateList(newList: List<Animal>) {
        animalList.clear()
        animalList.addAll(newList)
        notifyDataSetChanged()
    }

    class AnimalViewHolder(private val binding: ItemAnimalBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(animal: Animal) {
            binding.animal = animal
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): AnimalViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemAnimalBinding.inflate(layoutInflater, parent, false)
                return AnimalViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalViewHolder {
        return AnimalViewHolder.from(parent)
    }

    override fun getItemCount() = animalList.size

    override fun onBindViewHolder(holder: AnimalViewHolder, position: Int) {
        holder.bind(animalList[position])
    }

}