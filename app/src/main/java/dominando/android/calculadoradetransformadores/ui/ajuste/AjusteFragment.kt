package dominando.android.calculadoradetransformadores.ui.ajuste

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import dominando.android.calculadoradetransformadores.databinding.FragmentAjusteBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AjusteFragment : Fragment() {

    private val binding: FragmentAjusteBinding by lazy {
        FragmentAjusteBinding.inflate(
            layoutInflater
        )
    }

    // Criação de Interface própria para salvar historico
    interface OnHistoricoListener {
        fun adicionarHistorico(tipoOpcao: String, linhaUm: String, linhaDois: String)
    }

    private var historicoListener: OnHistoricoListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val exibicaoTela = binding.root
        return exibicaoTela
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Certificação de que a MainActivity vai implementar a interface
        historicoListener = activity as? OnHistoricoListener

        binding.seekBar.max = 10
        var salvaContador = 0

        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            @SuppressLint("SetTextI18n")
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.ajusteContador.text = "$progress,0 V"
                salvaContador = progress
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
                //Toast.makeText(requireContext(), "Iniciou o toque no SeekBar", Toast.LENGTH_SHORT).show()
                Log.d("Tensao", "Iniciou o toque no SeekBar")
            }


            override fun onStopTrackingTouch(p0: SeekBar?) {
                Log.d("Tensao", "Parou o toque no SeekBar")
            }

        })

        binding.btnCalcular.setOnClickListener {
            calcularAjusteVoltagem(salvaContador)
        }

    }

    @SuppressLint("SetTextI18n")
    // Calcula o ajuste de tensão
    private fun calcularAjusteVoltagem(contador: Int) {
        Log.d("Tensao", "Chegou na função de cálculo")

        // Desabilitar o botão de cálculo para evitar múltiplos cliques, mostrar barra de progresso e esconder resultado
        binding.btnCalcular.isEnabled = false
        binding.containerResVoltagem.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE


        // Uso do coroutine para tratar a thread principal, com atraso de 500ms
        CoroutineScope(Dispatchers.Main).launch {
            delay(500)

            // Calcular e exibir o ajuste de tensão
            val voltasAjustadas = contador * 10
            binding.tvResVoltagem.text = "Ajuste fino: $voltasAjustadas voltas adicionais"

            // Habilitar o botão de cálculo e esconder a barra de progresso, mostrando resultado
            binding.containerResVoltagem.visibility = View.VISIBLE
            binding.progressBar.visibility = View.GONE
            binding.btnCalcular.isEnabled = true

            // Adicionar resultado ao histórico
            historicoListener?.adicionarHistorico(
                "Ajuste Fino de Voltagem",
                "Voltagem: ${contador},0 V",
                binding.tvResVoltagem.text.toString()
            )

            Log.d("Tensao", "Cálculo realizado com sucesso")
        }
    }
}