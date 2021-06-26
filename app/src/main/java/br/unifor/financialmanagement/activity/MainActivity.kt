package br.unifor.financialmanagement.activity.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.unifor.financialmanagement.R
import br.unifor.financialmanagement.activity.RegisterRecipeOrExpense
import br.unifor.financialmanagement.adapter.RecipeExpenseAdapter
import br.unifor.financialmanagement.model.Expense
import br.unifor.financialmanagement.model.Recipe
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var btnRegisterRecipeOrExpense :FloatingActionButton
    private lateinit var recyclerViewRecipe :RecyclerView
    private lateinit var recyclerViewExpense :RecyclerView
    private lateinit var listRecipe :ArrayList<Recipe>
    private lateinit var listExpense :ArrayList<Expense>
    private lateinit var progressBar :ProgressBar
    private lateinit var database :FirebaseDatabase
    private lateinit var auth :FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        btnRegisterRecipeOrExpense = findViewById(R.id.activity_main_floatingbutton)
        btnRegisterRecipeOrExpense.setOnClickListener(this)
        listExpense = ArrayList()
        listRecipe = ArrayList()

        recyclerViewRecipe = findViewById(R.id.activity_main_recyclerview_recipe)
        recyclerViewExpense = findViewById(R.id.activity_main_recyclerview_expense)
        progressBar = findViewById(R.id.progressBar)

    }

    override fun onStart() {
        super.onStart()
        val queryRecipe = database.reference.child("user/${auth.uid}/recipe").orderByKey()
        val queryExpense = database.reference.child("user/${auth.uid}/expense").orderByKey()
        progressBar.visibility = View.VISIBLE
        val handler = Handler(Looper.getMainLooper())

        queryRecipe.addValueEventListener(object :ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {
                listRecipe.clear()
                snapshot.children.forEach {
                    val recipe = it.getValue(Recipe::class.java)
                    Log.i("teste", recipe?.name!!)
                    listRecipe.add(recipe)
                }
                val emptyList :ArrayList<Expense> = ArrayList()
                handler.post {
                    recyclerViewRecipe.apply {
                        adapter = RecipeExpenseAdapter(listRecipe, emptyList)
                        layoutManager = LinearLayoutManager(applicationContext)
                    }
                }

            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })


        queryExpense.addValueEventListener(object :ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {
                listExpense.clear()
                snapshot.children.forEach {
                    val expense = it.getValue(Expense::class.java)
                    Log.i("teste", expense?.name!!)
                    listExpense.add(expense)
                }
                val emptyList :ArrayList<Recipe> = ArrayList()
                handler.post {
                    recyclerViewExpense.apply {
                        adapter = RecipeExpenseAdapter(emptyList, listExpense)
                        layoutManager = LinearLayoutManager(applicationContext)
                    }
                }
                progressBar.visibility = View.GONE
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })




    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.activity_main_floatingbutton -> {
                val intent = Intent(applicationContext, RegisterRecipeOrExpense::class.java)
                startActivity(intent)
            }
        }
    }
}