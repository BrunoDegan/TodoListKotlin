package todolistkotlin.androidtutorial.brunodegan.todolistkotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.orhanobut.hawk.Hawk
import kotlinx.android.synthetic.main.activity_todo_hawk.*
import todolistkotlin.androidtutorial.brunodegan.todolistkotlin.adapters.ToDoAdapter
import todolistkotlin.androidtutorial.brunodegan.todolistkotlin.domain.ToDo
import todolistkotlin.androidtutorial.brunodegan.todolistkotlin.view.TaskDialogFragment

class TodoHawkActivity : AppCompatActivity() {
	
	val todoList = ArrayList<ToDo>()
	
	companion object {
		@JvmField val toDoListTag = "to_do_list_tag"
	}
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_todo_hawk)
		
		fab.setOnClickListener(View.OnClickListener {
			initTaskDialog()
		})
		
		initList()
		initRecycler()
	}
	
	private fun initList() {
		Hawk.init(this).build()
		
		if (!Hawk.contains(toDoListTag))
			Hawk.put(toDoListTag, todoList)
		else
			todoList.addAll(Hawk.get(toDoListTag))
	}
	
	private fun initRecycler() {
		rv_todo.setHasFixedSize(true)
		
		val layoutManager = LinearLayoutManager(this)
		rv_todo.layoutManager = layoutManager
		
		val divider = DividerItemDecoration(this, layoutManager.orientation)
		rv_todo.addItemDecoration(divider)
		
		val toDoAdapter = ToDoAdapter(context = this, todoList = todoList)
		rv_todo.adapter = toDoAdapter
	}
	
	private fun initTaskDialog() {
		val fragmentManager = supportFragmentManager
		
		val fragmentTransaction = fragmentManager.beginTransaction()
		
		val lastFrag = fragmentManager.findFragmentByTag(TaskDialogFragment.KEY)
		
		if (lastFrag != null) {
			fragmentTransaction.remove(lastFrag)
		}
		
		fragmentTransaction.addToBackStack(null)
		
		val taskDialog = TaskDialogFragment()
		
		taskDialog.show(getFragmentManager(),  TaskDialogFragment.KEY)
	}
	
	fun addToList(toDo: ToDo) {
		todoList.add(toDo)
//		todoList.sortWith(compareBy<ToDo> { it.date }
//				.thenByDescending{it.priority}
//				.thenByDescending{ it.duration })
		todoList.sort()
		
		Hawk.put(toDoListTag, todoList)
		rv_todo.adapter.notifyItemInserted(todoList.indexOf(toDo))
		rv_todo.adapter.notifyDataSetChanged()
	}
	fun removeFromList(adapterPosition: Int) {
		todoList.removeAt(adapterPosition)
		Hawk.put(toDoListTag, todoList)
		rv_todo.adapter.notifyItemRemoved(adapterPosition)
	}
	
	fun isRecyclerViewAnimating() = rv_todo.isAnimating || rv_todo.isComputingLayout
}