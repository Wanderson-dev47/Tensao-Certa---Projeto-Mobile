package dominando.android.calculadoradetransformadores.ui.sobre;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import dominando.android.calculadoradetransformadores.R;
import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

public class SobreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String descricao = "A Calculadora de Transformadores é uma ferramenta especializada e intuitiva, desenvolvida para engenheiros elétricos, estudantes e entusiastas da eletrônica. Ela facilita o cálculo exato das voltagens primárias e secundárias de transformadores, oferecendo uma interface simples e eficiente, projetada para fornecer resultados precisos com base em fórmulas matemáticas bem estabelecidas. Além disso, o aplicativo permite ajustes finos de tensão, possibilitando simulações práticas que auxiliam no design e na verificação de transformadores, tornando o processo mais rápido e acessível. Com a Calculadora de Transformadores, profissionais têm uma solução prática na palma da mão para lidar com as complexidades de seus projetos.";

        Element desenvolvedorDescricao = getElement();

        Element versao = new Element();
        versao.setTitle("Versão 1.0.0");

        View sobrePagina = new AboutPage(this)
                .setImage(R.drawable.icon_logo)
                .setDescription(descricao)
                .addGroup("Entre em contato")
                .addEmail("wanderson.brito.wbda@gmail.com", "Envie um e-mail")
                .addGroup("Sobre o Desenvolvedor")
                .addItem(desenvolvedorDescricao)
                .addGroup("Redes Sociais")
                .addFacebook("wanderson.brito.5249", "Facebook")
                .addInstagram("wanderson.szn", "Instagram")
                .addGitHub("Wanderson-dev47", "GitHub")
                .addItem(versao)
                .create();

        setContentView(sobrePagina);
    }

    private static @NonNull Element getElement() {
        Element desenvolvedor = new Element();
        desenvolvedor.setTitle("Sou Wanderson, um desenvolvedor independente e apaixonado por tecnologia, focado em criar soluções eficientes e práticas para desafios complexos. Com expertisse em desenvolvimento Android, estou sempre em busca de formas de otimizar processos e ajudar profissionais a obterem resultados precisos em seu trabalho. Cada linha de código que escrevo é pensada para proporcionar a melhor experiência ao usuário, valorizando a simplicidade e a usabilidade.\n" +
                "\n" +
                "Atuando de forma independente, tenho como missão oferecer ferramentas acessíveis e funcionais para o mercado técnico, mantendo um diálogo próximo com meus clientes para aperfeiçoar as soluções criadas. A Calculadora de Transformadores é o reflexo desse compromisso em facilitar o trabalho daqueles que atuam na área elétrica e eletrônica."
        );
        return desenvolvedor;
    }
}
