package br.unifor.financialmanagement.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.unifor.financialmanagement.R
import br.unifor.financialmanagement.activity.RegisterRecipeOrExpense
import br.unifor.financialmanagement.model.Expense
import br.unifor.financialmanagement.model.Recipe

class RecipeExpenseAdapter(private val listRecipe :ArrayList<Recipe>, private val listExpense :ArrayList<Expense>) :RecyclerView.Adapter<RecipeExpenseAdapter.ViewHolder>(){
    class ViewHolder(view : View) :RecyclerView.ViewHolder(view) {
        val recipeOrExpenseName = view.findViewById<TextView>(R.id.item_list_recipe_expense_textview_recipe_expense_name)
        val recipeOrExpensePrice = view.findViewById<TextView>(R.id.item_list_recipe_expense_textview_recipe_expense_cost)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_recipe_expense, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(listRecipe.size > 0){
            holder.recipeOrExpenseName.text = listRecipe[listRecipe.size - (position+1)].name
            holder.recipeOrExpensePrice.text = "R$ ${listRecipe[listRecipe.size - (position + 1)].cost}"
        } else if(listExpense.size > 0){
            holder.recipeOrExpenseName.text = listExpense[listExpense.size - (position+1)].name
            holder.recipeOrExpensePrice.text = "R$ ${listExpense[listExpense.size - (position + 1)].cost}"
        }

    }

    override fun getItemCount(): Int {
        if(listRecipe.size > 5 || listExpense.size > 5){
            return 5
        }
        return listExpense.size+listRecipe.size
    }
}