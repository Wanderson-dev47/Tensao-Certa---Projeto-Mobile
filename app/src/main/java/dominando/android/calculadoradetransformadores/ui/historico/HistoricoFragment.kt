package dominando.android.calculadoradetransformadores.ui.historico

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import dominando.android.calculadoradetransformadores.principal.MainActivity
import dominando.android.calculadoradetransformadores.adapter.AdapterHistorico
import dominando.android.calculadoradetransformadores.databinding.FragmentHistoricoBinding
import dominando.android.calculadoradetransformadores.model.Historico

class HistoricoFragment : Fragment() {

    private val historicoLista = ArrayList<Historico>()
    private lateinit var adapterHistorico: AdapterHistorico

    private val binding: FragmentHistoricoBinding by lazy {
        FragmentHistoricoBinding.inflate(
            layoutInflater
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicializa o Adapter e o RecyclerView
        adapterHistorico = AdapterHistorico(historicoLista, requireContext())
        val layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerHistorico.layoutManager = layoutManager
        binding.recyclerHistorico.setHasFixedSize(true)
        binding.recyclerHistorico.adapter = adapterHistorico

        // Obtém a lista de historico da MainActivity
        val activity = activity as? MainActivity
        activity?.getHistoricoLista()?.let {
            historicoLista.clear()
            historicoLista.addAll(it)
            adapterHistorico.notifyDataSetChanged()
        }

        // Para tratar quando estiver sem dado, informando ao usuário dentro do fragment de histórico
        if (historicoLista.isEmpty()) {
            binding.avisoAusenciaDados.visibility = View.VISIBLE
            binding.recyclerHistorico.visibility = View.GONE
        } else {
            binding.avisoAusenciaDados.visibility = View.GONE
            binding.recyclerHistorico.visibility = View.VISIBLE
        }

    }

    // Função para atualizar o histórico de maneira dinamica
    @SuppressLint("NotifyDataSetChanged")
    fun atualizarDados(novoHistorico: Historico) {
        historicoLista.add(novoHistorico)
        adapterHistorico.notifyDataSetChanged()
    }


}
