package tads.eaj.ufrn.minhaprova

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import tads.eaj.ufrn.minhaprova.databinding.ActivityAcao2Binding
import tads.eaj.ufrn.minhaprova.databinding.ActivityAcao3Binding

class ActivityAcao3 : AppCompatActivity() {
    lateinit var binding : ActivityAcao3Binding
    var bd = LivroDBOpener(this)
    var id = 1 //Variavel contadora para fazer a busca por id

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_acao3)



        var listLivros = bd.findAll() //Busca por todos o livros cadastrados

        try {
            binding.anteriorButton.visibility = View.INVISIBLE //Botão anterior invisivel quando mostrar o primeiro livro cadastrado

            if(listLivros.size == 1){ //Se ouver só um livro cadastrado oculta o botão proximo
                binding.proximoButton.visibility = View.INVISIBLE
            }

            //Mostra o primeiro livro do banco
            var l = bd.findById(id)
            binding.valorTituloTextView.text = l.nome
            binding.valorAutorTextView.text = l.autor
            binding.valorAnoTextView.text = l.ano.toString()
            binding.valorNotaTextView.text = l.nota.toString()

            binding.proximoButton.setOnClickListener { //Mostra o proximo livro do banco de dados
                l = bd.findById(++id)
                binding.valorTituloTextView.text = l.nome
                binding.valorAutorTextView.text = l.autor
                binding.valorAnoTextView.text = l.ano.toString()
                binding.valorNotaTextView.text = l.nota.toString()

                if (id + 1 > listLivros.size){ //Caso não exista mais um livro, oculta o botão proximo
                    binding.proximoButton.visibility = View.INVISIBLE
                }

                if (id > 1){ //Caso não seja o primeiro livro do banco, mostra o botão anterior
                    binding.anteriorButton.visibility = View.VISIBLE
                }
            }

            binding.anteriorButton.setOnClickListener { //Mostra o livro anterior do banco de dados
                l = bd.findById(--id)
                binding.valorTituloTextView.text = l.nome
                binding.valorAutorTextView.text = l.autor
                binding.valorAnoTextView.text = l.ano.toString()
                binding.valorNotaTextView.text = l.nota.toString()

                if (id == 1){ //Caso seja o primeiro livro do banco, oculta o botão anterior
                    binding.anteriorButton.visibility = View.INVISIBLE
                }

                if (id < listLivros.size){ //Caso exista mais um livro, mostra o botão proximo
                    binding.proximoButton.visibility = View.VISIBLE
                }
            }
        } catch (exception: Exception) { //Se não houver nenhum livro cadastrado ocorrerá uma exceção, mostrará um toast e encerrará a activity
            Toast.makeText(this, "Nenhum livro cadastrado", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}