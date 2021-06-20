package br.unifor.financialmanagement.activity

import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import br.unifor.financialmanagement.R
import br.unifor.financialmanagement.fragment.ExpenseFragment
import br.unifor.financialmanagement.fragment.RecipeFragment

class RegisterRecipeOrExpense : AppCompatActivity(), View.OnClickListener {
    private lateinit var btnRecipe :Button
    private lateinit var btnExpense :Button
    private lateinit var fragmentRecipe: RecipeFragment
    private lateinit var fragmentExpense: ExpenseFragment
    private lateinit var backImg :ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_recipe_expense)

        btnRecipe = findViewById(R.id.activity_register_recipe_expense_button_recipe)
        btnExpense = findViewById(R.id.activity_register_recipe_expense_button_expense)
        backImg = findViewById(R.id.activity_register_recipe_imageview_arrow_back)

        btnRecipe.setOnClickListener(this)
        btnExpense.setOnClickListener(this)
        backImg.setOnClickListener(this)

        fragmentRecipe = RecipeFragment()
        fragmentExpense = ExpenseFragment()

        setFragment(fragmentRecipe)


    }

    private fun setFragment(fragment :Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.activity_register_recipe_expense_framelayout, fragment)
        fragmentTransaction.commit()
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.activity_register_recipe_expense_button_recipe -> {
                setFragment(fragmentRecipe)
            }
            R.id.activity_register_recipe_expense_button_expense -> {
                setFragment(fragmentExpense)
            }
            R.id.activity_register_recipe_imageview_arrow_back -> {
                finish()
            }
        }
    }
}