package dominando.android.calculadoradetransformadores.ui.home

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
import dominando.android.calculadoradetransformadores.databinding.FragmentCalculoBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.Locale

/*
 * Copyright (c) 2024 Wanderson Brito
 * Todos os direitos reservados.
 * Este código não pode ser utilizado ou distribuído sem permissão explícita do autor.
 */

class CalculoFragment : Fragment() {

    private val binding: FragmentCalculoBinding by lazy {
        FragmentCalculoBinding.inflate(
            layoutInflater
        )
    }

    // Interface própria para salvar historico
    interface OnHistoricoListener {
        fun adicionarHistorico(tipoOpcao: String, linhaUm: String, linhaDois: String)
    }

    private var historicoListener: OnHistoricoListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        val exibicaoTela = binding.root
        return exibicaoTela
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Certificação de que a MainActivity vai implementar a interface
        historicoListener = activity as? OnHistoricoListener

        // Configuração do Spinner
        val materiais = resources.getStringArray(R.array.materiais)
        val adapterSpinner =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, materiais)
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Aplicando o adapter no Spinner
        binding.spinnerMaterial.adapter = adapterSpinner

        binding.btnCalcular.setOnClickListener { _ ->
            Log.d("Tensao", "Botão Calcular pressionado")

            // Iniciar o cálculo em uma coroutine
            calcularTransformador()
        }
    }


    @SuppressLint("DefaultLocale", "SetTextI18n")
    private fun calcularTransformador() {

        // Conversão dos dados para string para validação
        val base = binding.etBase.text.toString()
        val altura = binding.etAltura.text.toString()
        val vin = binding.etVin.text.toString()
        val vout = binding.etVout.text.toString()

        // Validação dos campos de entrada
        val (valido, mensagemErro) = validarCampos(base, altura, vin, vout)
        var validaCampos = false

        // Para tratamento de erros
        if (!valido) {
            Log.e("Tensao", mensagemErro ?: "Erro desconhecido")
        } else {
            validaCampos = true
        }

        if (validaCampos) {
            Log.d("Tensao", "Chegou na função de cálculo")
            // Conversão dos dados para double para cálculo
            val baseValor = base.toDouble()
            val alturaValor = altura.toDouble()
            val vinValor = vin.toDouble()
            val voutValor = vout.toDouble()


            // Desabilitar o botão de cálculo para evitar múltiplos cliques
            binding.btnCalcular.isEnabled = false

            // Esconder os campos de resultados e mostrar a barra de progresso
            binding.containerResultado.visibility = View.GONE
            binding.progressBar.visibility = View.VISIBLE

            // Coroutine para tratar a thread principal, com atraso de 700ms
            CoroutineScope(Dispatchers.Main).launch {
                delay(1000)

                // Cálculos e exibição dos resultados

                // Definição das constantes
                val constanteSP = 4.0 // Seleção do condutor do primário
                val constanteNP = 41.9 // Número de espiras do primário

                val areaNucleo = baseValor * alturaValor
                val potenciaNucleo = areaNucleo * areaNucleo


                // Cálculos do Primário
                val correnteEletricaIP = potenciaNucleo / vinValor // Corrente elétrica do primário
                val selecaoCondutorSP =
                    correnteEletricaIP / constanteSP // Seleção do condutor do primário
                val qtdEspirasPri = (vinValor * constanteNP) / areaNucleo

                // Cálculos do Secundário
                val correnteEletricaIS = potenciaNucleo / voutValor // Corrente do secundário
                val selecaoCondutorSS =
                    correnteEletricaIS / constanteSP // Seleção do condutor do secundário
                val qtdEspirasSec = (voutValor * constanteNP) / areaNucleo


                // Pega material selecionado e atribui o valor
                val materialSeleciondo = binding.spinnerMaterial.selectedItem.toString()
                val eficiencia = when (materialSeleciondo) {
                    "Silício" -> 0.95
                    "Ferrite" -> 0.85
                    "Selecione o material" -> {
                        Toast.makeText(
                            requireContext(),
                            "Nenhum material selecionado!",
                            Toast.LENGTH_SHORT
                        ).show()
                        0.0
                    }

                    else -> 0.0
                }

                val resultado = """
                    
    Área do núcleo (A):
    ${formatar(areaNucleo)} cm²
    
    Potência do transformador (P): 
    ${formatar(potenciaNucleo)} VA
    
    Corrente no primário (IP): 
    ${formatar(correnteEletricaIP)} A
    
    Seleção do condutor no primário (SP):
    ${formatar(selecaoCondutorSP)} mm²
     
    Nº de espiras no primário (NP): 
    ${formatar(qtdEspirasPri)} voltas


    Corrente no secundário (IS): 
    ${formatar(correnteEletricaIS)} A
    
    Seleção do condutor no secundário (SS): 
    ${formatar(selecaoCondutorSS)} mm²
    
    Nº de espiras no secundário (NS): 
    ${formatar(qtdEspirasSec)} voltas
    
    ${"Eficiência estimada: %.0f%%".format(eficiencia * 100)} 
    
""".trimIndent()

                // Esse código serve para exibir o dado no histórico
                val mostraMaterial =
                    if (binding.spinnerMaterial.selectedItem == "Selecione o material") "Nenhum" else binding.spinnerMaterial.selectedItem.toString()

                // Atualização do campos de texto com os resultados
                binding.tvResultado.text = resultado

                val entradasFormatado = "Base: ${base.replace(".", ",")} cm " +
                        "\nAltura: ${altura.replace(".", ",")} cm " +
                        "\nVin: ${vin.replace(".", ",")} " +
                        "\nVout: ${vout.replace(".", ",")} " +
                        "\nMaterial: $mostraMaterial"

                // Adicionar resultado ao histórico
                historicoListener?.adicionarHistorico(
                    "Dados do Transformador", entradasFormatado, binding.tvResultado.text.toString()
                )

                // Esconder a barra de progresso e habilitar o botão de cálculo
                binding.progressBar.visibility = View.GONE
                binding.btnCalcular.isEnabled = true

                // Exibição do campo de resultado
                binding.containerResultado.visibility = View.VISIBLE

                // Limpeza dos campos de entrada
                binding.etBase.text.clear()
                binding.etAltura.text.clear()
                binding.etVin.text.clear()
                binding.etVout.text.clear()
            }

            Log.d("Tensao", "Cálculo realizado com sucesso")

        }
    }

    private fun formatar(valor: Double): String {
        // Conversão para BigDecimal somente para evitar notação científica, ex: "1.0E8"
        val bigDecimalValor = BigDecimal(valor)

        val formatacao = NumberFormat.getInstance(Locale("pt", "BR"))
        formatacao.minimumFractionDigits = 2
        formatacao.maximumFractionDigits = 2
        return formatacao.format(bigDecimalValor)
    }


    private fun validarCampos(
        baseEntrada: String, alturaEntrada: String, vinEntrada: String, voutSaida: String
    ): Pair<Boolean, String?> {

        // Validação dos campos de entrada, com possíveis erros
        return when {

            baseEntrada.isEmpty() -> {
                binding.etBase.error = "Campo Obrigatório!"
                false to "Base não pode ser vazio"
            }

            alturaEntrada.isEmpty() -> {
                binding.etAltura.error = "Campo Obrigatório!"
                false to "Altura não pode ser vazio"
            }

            baseEntrada.toDoubleOrNull() == null -> {
                binding.etBase.error = "Valor inválido!"
                false to "Base deve ser um número"
            }

            alturaEntrada.toDoubleOrNull() == null -> {
                binding.etAltura.error = "Valor inválido!"
                false to "Altura deve ser um número"
            }

            baseEntrada.toDouble() == 0.0 -> {
                binding.etBase.error = "Zero não é permitido."
                false to "Base não pode ser zero"
            }

            alturaEntrada.toDouble() == 0.0 -> {
                binding.etAltura.error = "Zero não é permitido."
                false to "Altura não pode ser zero"
            }

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