package dominando.android.calculadoradetransformadores.ui.secundario

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dominando.android.calculadoradetransformadores.databinding.FragmentSecundarioBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SecundarioFragment : Fragment() {

    private val binding: FragmentSecundarioBinding by lazy {
        FragmentSecundarioBinding.inflate(
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

        binding.btnCalcular.setOnClickListener { _ ->
            Log.d("Tensao", "Botão Calcular pressionado")

            // Iniciar o cálculo em uma coroutine
            calcularSecundario()
        }
    }


    @SuppressLint("DefaultLocale", "SetTextI18n")
    private fun calcularSecundario() {

        // Conversão dos dados para string para validação
        val vin = binding.etVin.text.toString()
        val vout = binding.etVout.text.toString()

        // Validação dos campos de entrada
        val (valido, mensagemErro) = validarCampos(vin, vout)
        var validaCampos = false

        // Para tratamento de erros
        if (!valido) {
            Log.e("Tensao", mensagemErro ?: "Erro desconhecido")
        } else {
            validaCampos = true
        }

        if (validaCampos) {
            Log.d("Tensao", "Chegou na função de cálculo")
            // Conversão dos dados para double para cálculo, com tratamento de nullable
            val vinValor = vin.toDouble()
            val voutValor = vout.toDouble()

            // Desabilitar o botão de cálculo para evitar múltiplos cliques
            binding.btnCalcular.isEnabled = false

            // Esconder os campos de resultados e mostrar a barra de progresso
            binding.containerResultados.visibility = View.GONE
            binding.progressBar.visibility = View.VISIBLE

            // Coroutine para tratar a thread principal, com atraso de 700ms
            CoroutineScope(Dispatchers.Main).launch {
                delay(700)

                // Cálculo e exibição dos resultados
                val voltasPrimario = (vinValor / voutValor) * 100
                val voltasSecundario = (voutValor / vinValor) * 100

                val voltasPrimFormatado = String.format("%.0f", voltasPrimario)
                val voltasSecunFormatado = String.format("%.0f", voltasSecundario)

                // Atualização dos campos de texto com os resultados
                binding.tvResPrimario.text = "Voltas no primário: $voltasPrimFormatado"
                binding.tvResSecundario.text = "Voltas no secundário: $voltasSecunFormatado"

                // Esconder a barra de progresso e habilitar o botão de cálculo
                binding.progressBar.visibility = View.GONE
                binding.btnCalcular.isEnabled = true

                // Exibição dos campos de resultados
                binding.containerResultados.visibility = View.VISIBLE

                // Limpeza dos campos de entrada
                binding.etVin.text.clear()
                binding.etVout.text.clear()

                // Adicionar resultado ao histórico
                historicoListener?.adicionarHistorico(
                    "Secundário",
                    "Vin: ${vin.replace(".", ",")} \nVout: ${vout.replace(".", ",")}",
                    "Voltas Primário: $voltasPrimFormatado \nVoltas Secundário: $voltasSecunFormatado"
                )

            }

            Log.d("Tensao", "Cálculo realizado com sucesso")

        }
    }


    private fun validarCampos(vinEntrada: String, voutSaida: String): Pair<Boolean, String?> {

        // Validação dos campos de entrada, com possíveis erros
        return when {
            vinEntrada.isEmpty() -> {
                binding.etVin.error = "Campo Obrigatório!"
                false to "Vin não pode ser vazio"
            }

            voutSaida.isEmpty() -> {
                binding.etVout.error = "Campo Obrigatório!"
                false to "Vout não pode ser vazio"
            }

            vinEntrada.toDoubleOrNull() == null -> {
                binding.etVin.error = "Valor inválido!"
                false to "Vin deve ser um número"
            }

            voutSaida.toDoubleOrNull() == null -> {
                binding.etVout.error = "Valor inválido!"
                false to "Vout deve ser um número"
            }

            vinEntrada.toDouble() == 0.0 -> {
                binding.etVin.error = "Zero não é permitido."
                false to "Vin não pode ser zero"
            }

            voutSaida.toDouble() == 0.0 -> {
                binding.etVout.error = "Zero não é permitido."
                false to "Vout não pode ser zero"
            }

            // Isso garante a validação bem sucedida, pois, true para campos válidos e null para nenhuma mensagem de erro
            else -> true to null
        }
    }

}