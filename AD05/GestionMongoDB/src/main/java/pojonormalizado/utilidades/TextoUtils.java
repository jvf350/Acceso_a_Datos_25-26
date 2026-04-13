package pojonormalizado.utilidades;

import java.text.Normalizer;

public class TextoUtils {

    public static String normalizar(String texto) {
        if (texto == null) return null;

        return Normalizer.normalize(texto, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
                .toLowerCase()
                .trim();
    }
}

