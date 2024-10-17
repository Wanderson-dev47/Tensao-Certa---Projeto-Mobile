package dominando.android.calculadoradetransformadores.adapter

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import dominando.android.calculadoradetransformadores.databinding.HistoricoListaBinding
import dominando.android.calculadoradetransformadores.model.Historico

/*
 * Copyright (c) 2024 Wanderson Brito
 * Todos os direitos reservados.
 * Este código não pode ser utilizado ou distribuído sem permissão explícita do autor.
 */

class AdapterHistorico(private val historicoLista: List<Historico>, private val context: Context) :
    Adapter<AdapterHistorico.HistoricoViewHolder>() {

    // Definição dos holders que serão utilizado para atualizar a lista
    class HistoricoViewHolder(val binding: HistoricoListaBinding) :
        RecyclerView.ViewHolder(binding.root)

    // Inflar layout usando binding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoricoViewHolder {
        val bindingExibicao =
            HistoricoListaBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return HistoricoViewHolder(bindingExibicao)
    }

    // Exibira pela quantidade inserida que há na lista
    override fun getItemCount() = historicoLista.size

    // Configuração dos dados que irão aparecer na lista
    override fun onBindViewHolder(holder: HistoricoViewHolder, position: Int) {

        // Inversão da ordem de exibição, para cada novo dado inserido aparecer no início
        val historico = historicoLista[historicoLista.size - 1 - position]

        holder.binding.tipoOpcao.text = historico.tipoOpcao
        holder.binding.tvLin1.text = historico.linhaUm
        holder.binding.tvLin2.text = historico.linhaDois

        // Configuração do botão copiar
        holder.binding.btnCopiar.setOnClickListener {
            copiarParaClipboard(historico)
            animarClique(holder.itemView)
        }

    }

    // Função para copiar para área de transferencia
    private fun copiarParaClipboard(historico: Historico) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText(
            "Histórico Copiado",
            "${historico.tipoOpcao}: \n\n${historico.linhaUm} \n\n${historico.linhaDois}"
        )
        clipboard.setPrimaryClip(clip)

        // O toast para exibir que foi copiado, é avisado no automático pelo sistema Android
    }

    private fun animarClique(view: View) {
        val scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1f, 1.1f, 1f)
        val scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1f, 1.1f, 1f)

        val animatorSet = AnimatorSet()
        animatorSet.play(scaleX).with(scaleY)
        animatorSet.duration = 200 // Duração da animação
        animatorSet.start()
    }

}


