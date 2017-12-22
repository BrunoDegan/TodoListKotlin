package todolistkotlin.androidtutorial.brunodegan.todolistkotlin.domain
import android.util.Log
import todolistkotlin.androidtutorial.brunodegan.todolistkotlin.R
import java.util.*
import kotlin.Comparator

/**
 * Created by brunodegan on 7/13/17.
 */
data class ToDo(
			val date : Long,
            val taskName :String,
            val duration: Int,
            val priority: Int) : Comparable<ToDo>{
	
	override fun compareTo(other: ToDo): Int {
		if (this.priority > other.priority && this.duration > other.duration) {
			Log.d("TESTE", "MAIOR")
			return 1
		} else {
			Log.d("TESTE", "MENOR")
			return 0
		}
	}
	
	fun getDateFormated(): String {
		val calendar = Calendar.getInstance()
		
		
		var date = """
            ${getNumDate(calendar.get(Calendar.DAY_OF_MONTH))}/
            ${getNumDate(calendar.get(Calendar.MONTH))}/
            ${calendar.get(Calendar.YEAR)}
        """
		
		print(date)
		
		return date.replace("\n", "").replace(" ", "").trim()
	}
	
	private fun getNumDate(num : Int) : String{
		if (num < 10) {
			return "0$num"
		} else {
			return "$num"
		}
	}
	
	fun getPriorityIcon(): Int {
		if (priority <= 1) {
			return R.drawable.ic_priority_low
		} else if(priority == 2) {
			return R.drawable.ic_priority_medium
		} else {
			return R.drawable.ic_priority_high
		}
	}
}