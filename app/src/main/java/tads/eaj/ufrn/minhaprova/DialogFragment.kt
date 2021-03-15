package tads.eaj.ufrn.minhaprova

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import java.lang.IllegalStateException

class SendMessageDialogFragment : DialogFragment(){
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setMessage("Gostaria de uma xícara de café?")
                    .setTitle("Pergunta Importante")

                    .setPositiveButton("Sim", DialogInterface.OnClickListener { dialog, id ->
                        Toast.makeText(it.baseContext, "Ótimo", Toast.LENGTH_LONG).show()
                    })

                    .setNegativeButton("Não", DialogInterface.OnClickListener {dialog, id ->
                        Toast.makeText(it.baseContext, "Fica para a próxima", Toast.LENGTH_LONG).show()
                    })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}