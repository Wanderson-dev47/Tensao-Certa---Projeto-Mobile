package dominando.android.calculadoradetransformadores.ui.info;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import dominando.android.calculadoradetransformadores.databinding.FragmentInfoBinding;
import dominando.android.calculadoradetransformadores.ui.sobre.SobreActivity;


public class InfoFragment extends Fragment {

    private FragmentInfoBinding binding;

    public InfoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentInfoBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @RequiresApi(api = Build.VERSION_CODES.N_MR1)
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.sobreApp.setOnClickListener(visualizar -> {
            Intent intent = new Intent(requireActivity(), SobreActivity.class);
            startActivity(intent);
        });

        // Configuração do WebView
        WebView webView = binding.webView;
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Para mudar a cor da ação quando no tema escuro ou claro
        TypedValue typedValue = new TypedValue();
        requireContext().getTheme().resolveAttribute(android.R.attr.textColor, typedValue, true);
        int colorText = typedValue.data;

        String colorHexText = String.format("#%06X", (0xFFFFFF & colorText));

        requireContext().getTheme().resolveAttribute(android.R.attr.colorBackground, typedValue, true);
        int colorBackground = typedValue.data;

        String colorHexBack = String.format("#%06X", (0xFFFFFF & colorBackground));


        // HTML com MathJax
        String htmlContent = "<!DOCTYPE html>" +
                "<html lang=\"pt\">" +
                "<head>" +
                "<meta charset=\"UTF-8\">" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                "<title>Fórmulas</title>" +
                "<script src=\"https://cdnjs.cloudflare.com/ajax/libs/mathjax/2.7.7/MathJax.js?config=TeX-AMS_HTML\"></script>" +
                "<style>" +
                "body { font-family: Arial, sans-serif; background: " + colorHexBack + "; }" +
                "p { font-size: 14px; line-height: 1.5; color: " + colorHexText + "; }" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<p><b>Área do núcleo do transformador (A):</b></p>" +
                "<p>$$ A = b \\times h $$</p>" +
                "<br><p><b>Potência do transformador (P):</b></p>" +
                "<p>$$ P = A^2 $$</p>" +
                "<br><p><b>Corrente elétrica do primário (IP):</b></p>" +
                "<p>$$ I_P = \\frac{P}{E_P} $$</p>" +
                "<br><p><b>Seleção do condutor do primário (SP):</b></p>" +
                "<p>$$ S_P = \\frac{I_P}{4} $$</p>" +
                "<br><p><b>Número de espiras do primário (NP):</b></p>" +
                "<p>$$ N_P = \\frac{E_P \\times 41,9}{A} $$</p>" +
                "<br><p><b>Corrente elétrica do secundário (IS):</b></p>" +
                "<p>$$ I_S = \\frac{P}{E_S} $$</p>" +
                "<br><p><b>Seleção do condutor do secundário (SS):</b></p>" +
                "<p>$$ S_S = \\frac{I_S}{4} $$</p>" +
                "<br><p><b>Número de espiras do secundário (NS):</b></p>" +
                "<p>$$ N_S = \\frac{E_S \\times 41,9}{A} $$</p>" +
                "</body>" +
                "</html>";

        // Carregar o conteúdo HTML no WebView
        webView.loadDataWithBaseURL(null, htmlContent, "text/html", "UTF-8", null);

    }
}