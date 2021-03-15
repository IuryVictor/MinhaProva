package tads.eaj.ufrn.minhaprova

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import tads.eaj.ufrn.minhaprova.databinding.ActivityAcao1Binding
import tads.eaj.ufrn.minhaprova.databinding.ActivityMainBinding

class ActivityAcao1 : AppCompatActivity() {
    lateinit var binding: ActivityAcao1Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_acao1)

        binding.okButton.setOnClickListener { //Ao clicar no botao OK, retorna "OK" e o valor lido na edit text e encerra a activity
            val intent = Intent()
            intent.putExtra("RESULTADO", binding.editText.text.toString())
            setResult(Activity.RESULT_OK, intent)
            finish()
        }

        binding.cancelarButton.setOnClickListener { //Ao clicar no botao Cancelar, retorna "CANCELED" e encerra a activity
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
    }
}