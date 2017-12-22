package todolistkotlin.androidtutorial.brunodegan.todolistkotlin.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import todolistkotlin.androidtutorial.brunodegan.todolistkotlin.R
import todolistkotlin.androidtutorial.brunodegan.todolistkotlin.TodoHawkActivity
import todolistkotlin.androidtutorial.brunodegan.todolistkotlin.domain.ToDo

/**
 * Created by brunodegan on 7/14/17.
 */
class ToDoAdapter(val context : Context, val todoList : List<ToDo>) :
		RecyclerView.Adapter<ToDoAdapter.ViewHolder>() {
	
	inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView),
			CompoundButton.OnCheckedChangeListener {
		
		var ivPriority : ImageView
		var tvDate : TextView
		var tvTask : TextView
		var tvDuration : TextView
		var cbDone : CheckBox
		
		init {
			ivPriority = itemView.findViewById(R.id.iv_ic_priority)
			tvDate = itemView.findViewById(R.id.tv_date)
			tvTask = itemView.findViewById(R.id.tv_task)
			tvDuration = itemView.findViewById(R.id.tv_duration)
			cbDone = itemView.findViewById(R.id.cb_done)
			cbDone.setOnCheckedChangeListener(this)
			
		}
		
		fun setData(position : Int) {
			ivPriority.setImageResource(todoList[position].getPriorityIcon())
			tvDate.text = todoList[position].getDateFormated()
			tvTask.text = todoList[position].taskName
			tvDuration.text = context.resources.
					getStringArray(R.array.durations)[todoList[position].duration]
			cbDone.isChecked = false
			
		}
		
		override fun onCheckedChanged(checkBox: CompoundButton?, status: Boolean) {
			val ctx = context as TodoHawkActivity
			if(!ctx.isRecyclerViewAnimating())
				ctx.removeFromList(adapterPosition)
			else
				checkBox!!.isChecked = false
		}
	}
	
	override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
		holder?.setData(position)
	}
	
	override fun getItemCount(): Int {
		return todoList.size
	}
	
	override fun getItemId(position: Int): Long {
		return super.getItemId(position)
	}
	
	override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ToDoAdapter.ViewHolder {
		val layoutInflater = LayoutInflater.from(context).inflate(R.layout.item_todo, parent,false)
		return ViewHolder(layoutInflater)
	}
	
	override fun onViewRecycled(holder: ViewHolder?) {
		super.onViewRecycled(holder)
	}
	
}