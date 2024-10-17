package dominando.android.calculadoradetransformadores.principal

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.util.TypedValue
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
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
import dominando.android.calculadoradetransformadores.ui.home.CalculoFragment

class MainActivity : AppCompatActivity(), CalculoFragment.OnHistoricoListener,
    AjusteFragment.OnHistoricoListener {

    private lateinit var binding: ActivityMainBinding

    // A lista geral ficará diretamente no Main
    private val historicoLista = ArrayList<Historico>()

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
                R.id.navigation_ajuste,
                R.id.navigation_historico,
                R.id.navigation_info
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


        // Essa é a lógica para troca de telas com animações, com o navigation
        // Armazena o ID do último item selecionado
        var lastSelectedItemId = R.id.navigation_home

        // Define a ordem dos itens da BottomNavigationView
        val navItemsOrder = listOf(
            R.id.navigation_home,
            R.id.navigation_ajuste,
            R.id.navigation_historico,
            R.id.navigation_info
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

                R.id.navigation_ajuste -> {
                    navController.navigate(R.id.navigation_ajuste, null, navOptions)
                    lastSelectedItemId = R.id.navigation_ajuste
                    true
                }

                R.id.navigation_historico -> {
                    navController.navigate(R.id.navigation_historico, null, navOptions)
                    lastSelectedItemId = R.id.navigation_historico
                    true
                }

                R.id.navigation_info -> {
                    navController.navigate(R.id.navigation_info, null, navOptions)
                    lastSelectedItemId = R.id.navigation_info
                    true
                }

                else -> false
            }
        }


        // Lógica para o botão de voltar com controle da navegação
        this.onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

                // Se for diferente de home ele fecha e volta pro início
                if (navController.currentDestination?.id != R.id.navigation_home) {
                    navController.popBackStack(R.id.navigation_home, false)
                } else {
                    // Retorna ao comportamento normal do botão back
                    isEnabled = false
                    onBackPressedDispatcher.onBackPressed()
                    finish() // Fecha o aplicativo se estiver na tela inicial
                }
            }
        })

    }


    // Função para adicionar o histórico
    @RequiresApi(Build.VERSION_CODES.N_MR1)
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
                supportFragmentManager.findFragmentById(R.id.atualizaHistorico) as? HistoricoFragment
            fragmentHistorico?.atualizarDados(novoHistorico)

        } else {

            // Para mudar a cor da ação quando no tema escuro ou claro
            val typedValue = TypedValue()
            theme.resolveAttribute(
                android.R.attr.colorSecondary,
                typedValue,
                true
            )
            val color = typedValue.data

            val snackbar =
                Snackbar.make(
                    binding.root,
                    "Este resultado já foi salvo!",
                    Snackbar.LENGTH_INDEFINITE
                )
            snackbar.setAction("Certo") {
                snackbar.dismiss()
            }
            snackbar.setActionTextColor(color)
            snackbar.show()
        }

    }

    // Essa função vai passar o fragment quando necessário
    fun getHistoricoLista(): List<Historico> {
        return historicoLista
    }




}