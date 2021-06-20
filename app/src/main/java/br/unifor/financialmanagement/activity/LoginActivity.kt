package br.unifor.financialmanagement.activity.activity

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.*
import br.unifor.financialmanagement.R
import br.unifor.financialmanagement.activity.RegisterActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var editTextEmail :EditText
    private lateinit var editTextPassword :EditText
    private lateinit var textViewSignUp :TextView
    private lateinit var btnLogin :Button
    private lateinit var progressBar :ProgressBar
    private lateinit var authentication :FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        editTextEmail = findViewById(R.id.activity_login_edittext_email)
        editTextPassword = findViewById(R.id.activity_login_edittext_password)
        btnLogin = findViewById(R.id.activity_login_button_login)
        textViewSignUp = findViewById(R.id.activity_login_textview_signup)
        progressBar = findViewById(R.id.activity_login_progressbar)

        authentication = FirebaseAuth.getInstance()

        btnLogin.setOnClickListener(this)
        textViewSignUp.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.activity_login_button_login -> {
                progressBar.visibility = View.VISIBLE
                val email = editTextEmail.text.toString()
                val pass = editTextPassword.text.toString()


                authentication.signInWithEmailAndPassword(email, pass)
                    .addOnCompleteListener {
                        val handler = Handler(Looper.getMainLooper())
                        if(it.isSuccessful){
                            progressBar.visibility = View.GONE
                            val intent = Intent(applicationContext, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            progressBar.visibility = View.GONE
                            Log.i("ERROR", it.exception?.message.toString())
                            handler.post {
                                Toast.makeText(
                                    applicationContext,
                                    it.exception?.message,
                                    Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
            }

            R.id.activity_login_textview_signup -> {
                val intent = Intent(this, RegisterActivity::class.java)
                startActivity(intent)
            }
        }
    }
}