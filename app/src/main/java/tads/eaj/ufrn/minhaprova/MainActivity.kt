package tads.eaj.ufrn.minhaprova

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import tads.eaj.ufrn.minhaprova.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    val MYREQUESTCODE = 1
    val REQUESTCODEFORM = 2
    val PREFS = "files"
    var flag = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val settings = getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        flag = settings.getInt("FLAG", 0) //Se existir uma chave "FLAG" a variavel recebe o seu valor, se não recebe 0

        if(flag == 0){ //Caso flag seja 0, mostra o toast e muda o valor pra 1
            Toast.makeText(this, "Bem-Vindo", Toast.LENGTH_LONG).show()
            flag = 1;
        }

        binding.button1.setOnClickListener { //Ao clicar no botao 1, direciona para a ActivityAcao1 e passa o request code
            val intent = Intent(this, ActivityAcao1::class.java)
            startActivityForResult(intent, MYREQUESTCODE)
        }

        binding.button2.setOnClickListener { //Ao clicar no botao 2, mostra o dialog fragment
            val dialog = SendMessageDialogFragment()
            dialog.show(supportFragmentManager,"Dialog")
        }

        binding.button3.setOnClickListener { //Ao clicar no botao 3, direciona para a ActivityAcao2 e passa o request code
            val intent = Intent(this, ActivityAcao2::class.java)
            startActivityForResult(intent, REQUESTCODEFORM)
        }

        binding.button4.setOnClickListener { // Ao clicar no botao 4, direciona para a ActivityAcao3
            val intent = Intent(this, ActivityAcao3::class.java)
            startActivity(intent)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode){
            MYREQUESTCODE -> { //Só entrará se retornou da ActivityAcao1
                when(resultCode){
                    Activity.RESULT_OK -> {
                        binding.text1.text = data?.getStringExtra("RESULTADO")
                    }

                    Activity.RESULT_CANCELED -> {
                        Snackbar.make(binding.text1, "Cancelado", Snackbar.LENGTH_LONG).show()
                    }
                }
            }

            REQUESTCODEFORM -> { //Só entrará se retornou da ActivityAcao2
                when(resultCode){
                    Activity.RESULT_OK -> {
                        binding.text2.text = "Cadastrado"
                    }
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString("TEXT1", binding.text1.text.toString()) //Salva o valor da text view text1 em "TEXT1"
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        binding.text1.text = savedInstanceState.getString("TEXT1") //Atribui o valor de "TEXT1" a text view text1
    }

    override fun onStop() {
        super.onStop()

        val settings = getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        var editor = settings.edit()

        //Salva o valor da flag em "FLAG" caso o aplicativo seja encerrado
        editor.putInt("FLAG", flag)
        editor.commit()
    }
}