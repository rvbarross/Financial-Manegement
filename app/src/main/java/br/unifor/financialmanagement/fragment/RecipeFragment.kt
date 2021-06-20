package br.unifor.financialmanagement.fragment

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import br.unifor.financialmanagement.R
import br.unifor.financialmanagement.model.Recipe
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.net.PasswordAuthentication

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RecipeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RecipeFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    private lateinit var btnRegisterRecipe :Button
    private lateinit var editTextRecipeName :EditText
    private lateinit var editTextRecipePrice :EditText
    private lateinit var authentication :FirebaseAuth
    private lateinit var database :FirebaseDatabase
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view :View = inflater.inflate(R.layout.fragment_recipe, container, false)
        btnRegisterRecipe = view.findViewById(R.id.fragment_recipe_button_register)
        editTextRecipeName = view.findViewById(R.id.fragment_recipe_edittext_name)
        editTextRecipePrice = view.findViewById(R.id.fragment_recipe_edittext_price)


        database = FirebaseDatabase.getInstance()
        authentication = FirebaseAuth.getInstance()

        btnRegisterRecipe.setOnClickListener {
            val handler = Handler(Looper.getMainLooper())
            val recipeName = editTextRecipeName.text.toString()
            val recipePrice =  editTextRecipePrice.text.toString()
            var formFilled = true
            if(recipeName.isEmpty()){
                editTextRecipeName.error = "Esse campo deve ser preenchido"
                formFilled = false
            }
            if(recipePrice.isEmpty()){
                editTextRecipePrice.error = "Esse campo deve ser preenchido"
                formFilled = false
            }
            if(formFilled){
                val recipeID = database.reference.child("/user/${authentication.uid}/recipe").push().key
                val recipe = Recipe(recipeID!!, recipeName, recipePrice)
                val ref = database.getReference("/user/${authentication.uid}/recipe/$recipeID")
                ref.setValue(recipe)
                editTextRecipePrice.setText("")
                editTextRecipeName.setText("")
                handler.post {
                    Toast.makeText(
                        view.context,
                        "Registered Recipe successfully",
                        Toast.LENGTH_SHORT).show()
                }
            }

        }

        return view
    }


}