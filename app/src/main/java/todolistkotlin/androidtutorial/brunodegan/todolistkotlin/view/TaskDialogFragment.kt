package todolistkotlin.androidtutorial.brunodegan.todolistkotlin.view

import android.app.Dialog
import android.app.DialogFragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_dialog_task.*
import todolistkotlin.androidtutorial.brunodegan.todolistkotlin.R
import todolistkotlin.androidtutorial.brunodegan.todolistkotlin.TodoHawkActivity
import todolistkotlin.androidtutorial.brunodegan.todolistkotlin.domain.ToDo
import java.util.*

/**
 * Created by brunodegan on 7/13/17.
 */
class TaskDialogFragment : DialogFragment(),
		View.OnClickListener,
		AdapterView.OnItemSelectedListener {
	
	
	/*
  * PARA PERMITIR O ACESSO A CHAVE DO DIALOG NA ATIVIDADE,
  * SEM NECESSIDADE DE USO DE VALORES MÁGICOS (String SEM
  * ESTAR EM UMA DETERMINADA PROPRIEDADE).
  * */
	companion object {
		val KEY = "task_dialog_fragment"
	}
	
	
	override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
		/*
         * PARA QUE SEJA REMOVIDA A BARRA DE TOPO DO DIALOG EM
         * DEVICES COM O ANDROID ABAIXO DA API 21.
         * */
		val dialog = super.onCreateDialog(savedInstanceState)
		dialog.window!!.requestFeature(Window.FEATURE_NO_TITLE)
		return dialog
	}
	
	/*
  * SOMENTE PARA A DEFINIÇÃO DO LAYOUT DO DIALOGFRAGMENT
  * */
	override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
	                          savedInstanceState: Bundle?): View {
		super.onCreateView(inflater, container, savedInstanceState)
		return inflater?.inflate(R.layout.fragment_dialog_task, null, false) as View
	}
	
	
	/*
     * PARA QUE SEJA POSSÍVEL ACESSAR AS VIEWS COM A SINTAXE
     * PERMITIDA PELO KOTLIN-ANDROID-EXTENSIONS, TEMOS DE
     * UTILIZAR O onResume() NO DIALOG OU QUALQUER OUTRO
     * MÉTODO DO CICLO DE VIDA DO FRAGMENT QUE VENHA DEPOIS
     * DE onCreateView(), POIS CASO CONTRÁRIO, MESMO ACESSANDO
     * A VIEW EM onCreateView(), SERÁ GERADA UMA NULLPOINTEREXCEPTION.
     * ISSO, POIS O LAYOUT AINDA NÃO FOI INICIALIZADO.
     * */
	
	override fun onResume() {
		super.onResume()
		bt_create_task.setOnClickListener(this)
		sp_months.onItemSelectedListener = this
	}
	
	override fun onClick(p0: View?) {
		val calendar = Calendar.getInstance()
		calendar.set(getSelectedYear(),
				sp_months.selectedItemPosition + 1,
				sp_days.selectedItemPosition + 1,
				0,0,0)
		
		val duration = sp_duration.selectedItemPosition
		val priority = sp_priority.selectedItemPosition
		var taskDescription = et_task.text.toString()
		
		if(taskDescription.isEmpty())
			taskDescription = """DEFAULT TEXT: NO TEXT"""
		
		
		var toDo = ToDo(
				calendar.timeInMillis,
				taskDescription,
				duration,
				priority)
		
		(activity as TodoHawkActivity).addToList(toDo)
		dismiss()
	}
	
	
	/*
	* SOBRESCRITA OBRIGATÓRIO DE MÉTODO DEVIDO A IMPLEMENTAÇÃO DA
	* INTERFACE OnItemSelectedListener. PORÉM O MÉTODO NÃO É UTILIZADO.
	* */
	
	override fun onNothingSelected(p0: AdapterView<*>?) {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}
	
	
	/*
     * RETORNA O RESOURCE DO ARRAY DE DIAS CORRETO DE ACORDO COM
     * O VALOR DE MÊS PASSADO COMO PARÂMETRO.
     * */
	private fun getArrayDayResource(month: Int) : Int{
		 if(month in arrayOf(0,2,4,6,7,9,11)) {
			return R.array.days_31
		} else if (month in arrayOf(3,5,8,10)) {
			return R.array.days_30
		} else {
			if(isLeapYear(getSelectedYear())) {
				return R.array.days_29
			} else {
				return R.array.days_28
			}
		}
	}
	
	
	/*
   * ATUALIZA O ARRAY DE DIAS VINCULADO AO Spinner DE DIAS DO
   * FORMULÁRIO, A ATUALIZAÇÃO OCORRE DE ACORDO COM A MUDANÇA DE
   * VALOR NO Spinner DE MÊS.
   * */
	
	
	override fun onItemSelected(parentView: AdapterView<*>?, view: View?, position: Int, id: Long) {
		var arrayDays = getArrayDayResource(position)
		val adapter = ArrayAdapter.createFromResource(activity,arrayDays,
				android.R.layout.simple_spinner_item)
		
		adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
		sp_days.adapter = adapter
	}
	
	/*getArrayDayResource
	* PARA OBTER O VALOR CORRETO DE ANO QUE ESTÁ EM CADA ITEM DO
	* Spinner DE ANOS DO FORMULÁRIO. ISSO, POIS O VALOR DO ITEM
	* SELECIONADO, APENAS, É PARTINDO DE 0 (ZERO), O QUE NÃO NOS
	* SERVE.
	* */
	
	private fun getSelectedYear() : Int =
			(sp_years.selectedView as TextView).text.toString().toInt()
	
	/*
     * VERIICA SE O ANO É BISSEXTO PARA A ESCOLHA DO ARRAY DE DIAS
     * CORRETO NO MÊS DE FEVEREIRO.
     * */
	private fun isLeapYear(year: Int) : Boolean {
		if (year % 4 == 0) {
			if (year % 100 == 0) {
				year % 400 == 0
			} else {
				return true
			}
		} else {
			return false
		}
		return false
	}
}