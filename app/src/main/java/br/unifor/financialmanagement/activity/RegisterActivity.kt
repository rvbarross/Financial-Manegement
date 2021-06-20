package br.unifor.financialmanagement.activity

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import br.unifor.financialmanagement.R
import br.unifor.financialmanagement.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var authentication :FirebaseAuth
    private lateinit var database :FirebaseDatabase

    private lateinit var editTextFullName :EditText
    private lateinit var editTextEmail :EditText
    private lateinit var editTextUser :EditText
    private lateinit var editTextPassword :EditText
    private lateinit var editTextPasswordConfirmation :EditText
    private lateinit var editTextPhone :EditText
    private lateinit var btnRegister :Button
    private lateinit var progressBar: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        authentication = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        editTextFullName = findViewById(R.id.activity_register_edittext_full_name)
        editTextEmail = findViewById(R.id.activity_register_edittext_email)
        editTextUser = findViewById(R.id.activity_register_edittext_username)
        editTextPassword = findViewById(R.id.activity_register_edittext_password)
        editTextPasswordConfirmation = findViewById(R.id.activity_register_edittext_password_confirmation)
        editTextPhone = findViewById(R.id.activity_register_edittext_phone)
        btnRegister = findViewById(R.id.activity_register_button_register)
        progressBar = findViewById(R.id.activity_register_progressbar)

        btnRegister.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.activity_register_button_register -> {
                var filledFields = true

                if(editTextFullName.text.isEmpty()){ editTextFullName.error = "Este campo deve ser preenchido"; filledFields = false }
                if(editTextEmail.text.isEmpty()){ editTextEmail.error = "Este campo deve ser preenchido"; filledFields = false }
                if(editTextUser.text.isEmpty()){ editTextUser.error = "Este campo deve ser preenchido"; filledFields = false }
                if(editTextPassword.text.isEmpty()){ editTextPassword.error = "Este campo deve ser preenchido"; filledFields = false }
                if(editTextPasswordConfirmation.text.isEmpty()){ editTextPasswordConfirmation.error = "Este campo deve ser preenchido"; filledFields = false }
                if(editTextPhone.text.isEmpty()){ editTextPhone.error = "Este campo deve ser preenchido"; filledFields = false }

                if(filledFields) {
                    if (editTextPassword.text.toString().equals(editTextPasswordConfirmation.text.toString())) {
                        val email = editTextEmail.text.toString()
                        val password = editTextPassword.text.toString()
                        val fullname = editTextFullName.text.toString()
                        val phone = editTextPhone.text.toString()
                        val username = editTextUser.text.toString()
                        progressBar.visibility = View.VISIBLE

                        authentication.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener {
                                val handler = Handler(Looper.getMainLooper())
                                if(it.isSuccessful){
                                    val user = User(fullname, email, username, phone)
                                    val ref = database.getReference("user/${authentication.uid}")
                                    ref.setValue(user)
                                    progressBar.visibility = View.GONE
                                    handler.post {
                                        Toast.makeText(applicationContext,
                                            "User sucessfuly created",
                                            Toast.LENGTH_SHORT).show()
                                        finish()
                                    }
                                } else {
                                    Log.i("ERROR", it.exception?.message.toString())
                                    progressBar.visibility = View.GONE
                                    handler.post {
                                        Toast.makeText(
                                            applicationContext,
                                            it.exception?.message,
                                            Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }

                    }
                }
            }
        }
    }
}