package dominando.android.calculadoradetransformadores.principal

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import dominando.android.calculadoradetransformadores.R
import dominando.android.calculadoradetransformadores.databinding.ActivityMainBinding
import dominando.android.calculadoradetransformadores.model.Historico
import dominando.android.calculadoradetransformadores.ui.ajuste.AjusteFragment
import dominando.android.calculadoradetransformadores.ui.historico.HistoricoFragment
import dominando.android.calculadoradetransformadores.ui.home.PrimarioFragment
import dominando.android.calculadoradetransformadores.ui.nucleo.NucleoFragment
import dominando.android.calculadoradetransformadores.ui.secundario.SecundarioFragment
import dominando.android.calculadoradetransformadores.ui.sobre.SobreActivity

class MainActivity : AppCompatActivity(), PrimarioFragment.OnHistoricoListener,
    SecundarioFragment.OnHistoricoListener, AjusteFragment.OnHistoricoListener,
    NucleoFragment.OnHistoricoListener {

    private lateinit var binding: ActivityMainBinding

    // A lista geral ficará diretamente no Main
    private val historicoLista = ArrayList<Historico>()

    private var isHistoricoVisible = false

    @SuppressLint("SourceLockedOrientationActivity", "ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        // Vinculcação do Bottom Navigation View
        val navView: BottomNavigationView = binding.navView

        // Controle da navegação
        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        // Configuração de inserção dos fragments para manipulação das telas
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_secundario,
                R.id.navigation_ajuste,
                R.id.navigation_nucleo
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


        // Essa é a lógica para troca de telas com animações, com o navigation
        // Armazena o ID do último item selecionado
        var lastSelectedItemId = R.id.navigation_home // ou outro item padrão

        // Define a ordem dos itens da BottomNavigationView
        val navItemsOrder = listOf(
            R.id.navigation_home,
            R.id.navigation_secundario,
            R.id.navigation_ajuste,
            R.id.navigation_nucleo
        )

        navView.setOnItemSelectedListener { item ->
            // Se o item selecionado não mudou, não faça nada
            if (item.itemId == lastSelectedItemId) {
                return@setOnItemSelectedListener false
            }

            // Determina a direção da animação
            val navOptions = NavOptions.Builder().apply {
                val currentIndex = navItemsOrder.indexOf(item.itemId)
                val lastIndex = navItemsOrder.indexOf(lastSelectedItemId)

                // Navegando para a direita
                if (currentIndex > lastIndex) {
                    setEnterAnim(R.anim.slide_in_right) // Entrada do novo fragmento
                    setExitAnim(R.anim.slide_out_left)  // Saída do fragmento atual
                    setPopEnterAnim(R.anim.slide_in_left) // Voltar para a esquerda
                    setPopExitAnim(R.anim.slide_out_right) // Saída para a direita
                } else {
                    // Navegando para a esquerda
                    setEnterAnim(R.anim.slide_in_left) // Entrada do novo fragmento
                    setExitAnim(R.anim.slide_out_right) // Saída do fragmento atual
                    setPopEnterAnim(R.anim.slide_in_right) // Voltar para a direita
                    setPopExitAnim(R.anim.slide_out_left) // Saída para a esquerda
                }
            }.build()

            // Navegação para o item selecionado
            when (item.itemId) {
                R.id.navigation_home -> {
                    navController.navigate(R.id.navigation_home, null, navOptions)
                    lastSelectedItemId = R.id.navigation_home
                    true
                }

                R.id.navigation_secundario -> {
                    navController.navigate(R.id.navigation_secundario, null, navOptions)
                    lastSelectedItemId = R.id.navigation_secundario
                    true
                }

                R.id.navigation_ajuste -> {
                    navController.navigate(R.id.navigation_ajuste, null, navOptions)
                    lastSelectedItemId = R.id.navigation_ajuste
                    true
                }

                R.id.navigation_nucleo -> {
                    navController.navigate(R.id.navigation_nucleo, null, navOptions)
                    lastSelectedItemId = R.id.navigation_nucleo
                    true
                }

                else -> false
            }
        }


        // Essa função serve para que a UI não seja quebrada e callback funcione corretamente
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

                // Para encerrar o fragment
                if (isHistoricoVisible) {
                    alternarHistoricoApp()

                } else {
                    // Voltando ao comportamento normal do botão voltar caso não houver fragmentos visíveis
                    isEnabled = false
                    onBackPressedDispatcher.onBackPressed()
                }
            }
        })

        binding.btnSobre.setOnClickListener {
            val intent = Intent(this, SobreActivity::class.java)
            startActivity(intent)
        }

        binding.fabHistorico.setOnClickListener {
            alternarHistoricoApp()

            val fadeIn = ObjectAnimator.ofFloat(binding.fabHistorico, "alpha", 1f, 0.1f, 1f)
            fadeIn.duration = 500
            fadeIn.repeatMode = ObjectAnimator.REVERSE
            fadeIn.start()
        }

        // binding.root para quando o usuário clicar fora do histórico, ele também fechar automaticamente
        binding.root.setOnClickListener {
            if (isHistoricoVisible) {
                alternarHistoricoApp()
            }
        }



    }

    // Função que trata a exibição da tela historico
    private fun alternarHistoricoApp() {
        val transaction = supportFragmentManager.beginTransaction()

        if (isHistoricoVisible) {
            // Situação onde o usuário clica em voltar do historico
            animaOcultarHistorico()

            // Isso pra garantir que antes de remover a animação seja exibida sem problemas
            binding.exibeHistorico.animate()
                .setDuration(300)
                .withEndAction {
                    transaction.remove(supportFragmentManager.findFragmentById(R.id.exibeHistorico)!!)
                }
            isHistoricoVisible = false

        } else {
            animaExibirHistorico()

            // Situação onde o usuário clica no historico
            transaction.replace(R.id.exibeHistorico, HistoricoFragment())
            transaction.addToBackStack(null)

            // Garantia de que a animação aconteca após o fragment for adicionado
            binding.exibeHistorico.post {
                animaExibirHistorico()
            }

            isHistoricoVisible = true
        }

        transaction.commit()
    }


    // Função para adicionar o histórico
    override fun adicionarHistorico(tipoOpcao: String, linhaUm: String, linhaDois: String) {

        // Verificação para garantir que não haja dados repetidos
        val existe = historicoLista.any {
            it.tipoOpcao == tipoOpcao &&
                    it.linhaUm == linhaUm &&
                    it.linhaDois == linhaDois
        }

        if (!existe) {
            val novoHistorico = Historico(tipoOpcao, linhaUm, linhaDois)
            historicoLista.add(novoHistorico)

            // Atualização do fragment, caso esteja vísivel
            val fragmentHistorico =
                supportFragmentManager.findFragmentById(R.id.exibeHistorico) as? HistoricoFragment
            fragmentHistorico?.atualizarDados(novoHistorico)

            animarFab()
        } else {

            val snackbar =
                Snackbar.make(
                    binding.root,
                    "Este resultado já foi salvo!",
                    Snackbar.LENGTH_INDEFINITE
                )
            snackbar.setAction("Ok") {
                snackbar.dismiss()
            }
            snackbar.setActionTextColor(getColor(R.color.teal_200))
            snackbar.show()
        }

    }

    // Essa função vai passar o fragment quando necessário
    fun getHistoricoLista(): List<Historico> {
        return historicoLista
    }


    // Para quando um novo dado for salvo
    private fun animarFab() {
        val shakeAnimator =
            ObjectAnimator.ofFloat(binding.fabHistorico, "translationX", 0f, 15f, -15f, 0f)
        shakeAnimator.duration = 500
        shakeAnimator.repeatMode = ObjectAnimator.REVERSE
        shakeAnimator.start()
    }


    // Função para exibir o histórico com animação de deslizamento diagonal
    private fun animaExibirHistorico() {
        binding.exibeHistorico.translationX =
            binding.exibeHistorico.width.toFloat() // Começando fora da tela na diagonal
        binding.exibeHistorico.translationY = binding.exibeHistorico.height.toFloat()

        binding.exibeHistorico.visibility = View.VISIBLE

        binding.exibeHistorico.animate()
            .translationX(0f)
            .translationY(0f)
            .setDuration(500)
            .start()

        binding.sobreposicao.alpha = 0f // Opacidade 0
        binding.sobreposicao.visibility = View.VISIBLE
        binding.sobreposicao.animate()
            .alpha(1f) // Opacidade 1
            .setDuration(500)
            .start()
    }

    // Função para ocultar o histórico com animação de deslizamento diagonal
    private fun animaOcultarHistorico() {
        binding.exibeHistorico.animate()
            .translationX(binding.exibeHistorico.width.toFloat())
            .translationY(binding.exibeHistorico.height.toFloat())
            .setDuration(300)
            .withEndAction {
                binding.exibeHistorico.visibility = View.GONE
            } // Define a visibilidade como GONE após a animação
            .start()

        binding.sobreposicao.animate()
            .alpha(0f)
            .setDuration(300)
            .withEndAction { binding.sobreposicao.visibility = View.GONE }
            .start()
    }
}