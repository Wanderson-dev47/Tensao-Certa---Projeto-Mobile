package dominando.android.calculadoradetransformadores.ui.nucleo

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import dominando.android.calculadoradetransformadores.R
import dominando.android.calculadoradetransformadores.databinding.FragmentNucleoBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class NucleoFragment : Fragment() {

    private val binding: FragmentNucleoBinding by lazy {
        FragmentNucleoBinding.inflate(
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

        // Certificação que o MainActivity vai implementar essa interface
        historicoListener = activity as? OnHistoricoListener

        // Configuração do Spinner
        val materiais = resources.getStringArray(R.array.materiais)
        val adapterSpinner =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, materiais)
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Aplicando o adapter no Spinner
        binding.spinnerMaterial.adapter = adapterSpinner

        binding.btnCalcular.setOnClickListener {
            calcularNucleo()
        }

    }

    @SuppressLint("SetTextI18n")
    private fun calcularNucleo() {

        // Conversão de dados para string para validação
        val compri = binding.etCompri.text.toString()
        val largura = binding.etLargura.text.toString()

        // Validação dos campos de entrada
        val (valido, mensagemErro) = validarCampos(compri, largura)
        var validaCampos = false

        // Tratamento de erros
        if (!valido) {
            Log.e("Tensao", mensagemErro ?: "Erro desconhecido")
        } else {
            validaCampos = true
        }

        if (validaCampos) {
            Log.d("Tensao", "Chegou na função de cálculo")

            // Desabilitar o botão de cálculo para evitar múltiplos cliques
            binding.btnCalcular.isEnabled = false

            // Esconder os campos de resultados e mostrar a barra de progresso
            binding.containerResultados.visibility = View.GONE
            binding.progressBar.visibility = View.VISIBLE

            // Cálculo e exibição dos resultados
            CoroutineScope(Dispatchers.Main).launch {
                delay(700)

                // Conversão de dados para double para cálculo, com tratamento de nullable
                val compriValor = compri.toDouble()
                val larguraValor = largura.toDouble()

                val area = compriValor * larguraValor

                // Pega material selecionado e atribui o valor
                val materialSeleciondo = binding.spinnerMaterial.selectedItem.toString()
                val eficiencia = when (materialSeleciondo) {
                    "Silício" -> 0.95
                    "Ferrite" -> 0.85
                    "Selecione o material" -> {
                        Toast.makeText(requireContext(), "Nenhum material selecionado!", Toast.LENGTH_SHORT).show()
                        0.0
                    }
                    else -> 0.0
                }

                binding.tvResArea.text = "Área do núcleo: %.2f cm²".format(area)
                binding.tvResEficiencia.text =
                    "Eficiência estimada: %.0f%%".format(eficiencia * 100)

                // Desabilitar o botão de cálculo para evitar múltiplos cliques
                binding.btnCalcular.isEnabled = true

                // Esconder os campos de resultados e mostrar a barra de progresso
                binding.containerResultados.visibility = View.VISIBLE
                binding.progressBar.visibility = View.GONE

                binding.etCompri.text.clear()
                binding.etLargura.text.clear()

                // Esse código serve para exibir o dado no histórico
                val mostraMaterial = if (binding.spinnerMaterial.selectedItem == "Selecione o material") "Nenhum" else binding.spinnerMaterial.selectedItem.toString()

                // Adicionar resultado ao histórico
                historicoListener?.adicionarHistorico(
                    "Dados do Núcleo",
                    "Comprimento: ${compri.replace(".", ",")} cm \nLargura: ${largura.replace(".", ",")} cm \nMaterial: $mostraMaterial",
                    binding.tvResArea.text.toString() + "\n" + binding.tvResEficiencia.text.toString()
                )


            }

        }

    }

    // Validação dos campos de entrada
    private fun validarCampos(
        compriEntrada: String,
        larguraEntrada: String
    ): Pair<Boolean, String?> {

        // Validação dos campos de entrada, com possíveis erros
        return when {
            compriEntrada.isEmpty() -> {
                binding.etCompri.error = "Campo Obrigatório!"
                false to "Comprimento não pode ser vazio"
            }

            larguraEntrada.isEmpty() -> {
                binding.etLargura.error = "Campo Obrigatório!"
                false to "Largura não pode ser vazio"
            }

            compriEntrada.toDoubleOrNull() == null -> {
                binding.etCompri.error = "Valor inválido!"
                false to "Comprimento deve ser um número"
            }

            larguraEntrada.toDoubleOrNull() == null -> {
                binding.etLargura.error = "Valor inválido!"
                false to "Largura deve ser um número"
            }

            compriEntrada.toDouble() == 0.0 -> {
                binding.etCompri.error = "Zero não é permitido."
                false to "Comprimento não pode ser zero"
            }

            larguraEntrada.toDouble() == 0.0 -> {
                binding.etLargura.error = "Zero não é permitido."
                false to "Largura não pode ser zero"
            }

            // Isso garante a validação bem sucedida, pois, true para campos válidos e null para nenhuma mensagem de erro
            else -> true to null
        }
    }

}