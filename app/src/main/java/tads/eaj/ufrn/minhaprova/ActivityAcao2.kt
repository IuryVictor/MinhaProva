package tads.eaj.ufrn.minhaprova

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import tads.eaj.ufrn.minhaprova.databinding.ActivityAcao2Binding
import tads.eaj.ufrn.minhaprova.databinding.ActivityMainBinding

class ActivityAcao2 : AppCompatActivity() {
    lateinit var binding : ActivityAcao2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_acao2)

        binding.salvarButton.setOnClickListener { //Lê os valores nas edit texts, cria um objeto de livro e salva no banco de dados
            val titulo = binding.tituloEditText.text.toString()
            val autor = binding.autorEditText.text.toString()
            val ano = Integer.parseInt(binding.anoEditText.text.toString())
            val nota = binding.notaRatingBar.rating

            var l = Livro(0, titulo, autor, ano, nota)

            val bd = LivroDBOpener(this)
            bd.insert(l)

            setResult(Activity.RESULT_OK)
            finish()
        }

        binding.CancelButton.setOnClickListener { //Retorna "CANCELED" e encerra a activity
            setResult(Activity.RESULT_CANCELED)
            finish()
        }

    }
}