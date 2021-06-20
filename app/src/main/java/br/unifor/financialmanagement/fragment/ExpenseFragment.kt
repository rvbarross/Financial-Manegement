package br.unifor.financialmanagement.fragment

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
import br.unifor.financialmanagement.model.Expense
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ExpenseFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ExpenseFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    private lateinit var btnRegisterExpense : Button
    private lateinit var editTextExpenseName : EditText
    private lateinit var editTextExpensePrice : EditText
    private lateinit var database :FirebaseDatabase
    private lateinit var authentication :FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view :View = inflater.inflate(R.layout.fragment_expense, container, false)
        btnRegisterExpense = view.findViewById(R.id.fragment_expense_button_register)
        editTextExpenseName = view.findViewById(R.id.fragment_expense_edittext_name)
        editTextExpensePrice = view.findViewById(R.id.fragment_expense_edittext_price)

        database = FirebaseDatabase.getInstance()
        authentication = FirebaseAuth.getInstance()

        btnRegisterExpense.setOnClickListener {
            val expenseName = editTextExpenseName.text.toString()
            val expensePrice = editTextExpensePrice.text.toString()
            val handler = Handler(Looper.getMainLooper())
            var formFilled = true
            if (expenseName.isEmpty()) {
                editTextExpenseName.error = "Esse campo deve ser preenchido"
                formFilled = false
            }
            if (expensePrice.isEmpty()) {
                editTextExpensePrice.error = "Esse campo deve ser preenchido"
                formFilled = false
            }
            if (formFilled) {
                val expenseID =
                    database.getReference("/user/${authentication.uid}/expense").push().key

                val ref = database.getReference("/user/${authentication.uid}/expense/$expenseID")
                val expense = Expense(expenseID!!, expenseName, expensePrice)
                ref.setValue(expense)

                editTextExpensePrice.setText("")
                editTextExpenseName.setText("")
                handler.post {
                    Toast.makeText(
                        view.context,
                        "Registered Expense successfully",
                        Toast.LENGTH_SHORT).show()
                }
            }
        }
        return view
    }

}