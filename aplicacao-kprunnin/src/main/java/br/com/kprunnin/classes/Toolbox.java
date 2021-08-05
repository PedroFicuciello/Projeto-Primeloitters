package br.com.kprunnin.classes;

import br.com.kprunnin.modelo.Maquina;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Toolbox {

    String origem = this.getClass().getSimpleName();

    public float HSBFloat(int cor) {
        float porcentagemInvertida = cor - 100;
        float porcentagemInvertidaPositiva = porcentagemInvertida * -1;
        float corHsbFloat = porcentagemInvertidaPositiva / 333;
        return corHsbFloat;
    }

    /*
    public static String add(int i) {
        String resultado;
        if (i < 10) {
            resultado = "0" + i;
        } else {
            resultado = "" + i;
        }
        return resultado;
    }

    public String dia() {
        Date d = new Date();
        return add(d.getDate());
    }

    public String mes() {
        Date d = new Date();
        return add((d.getMonth() + 1));
    }

    public String ano() {
        Date d = new Date();
        return add((d.getYear() + 1900));
    }

    public String hora() {
        Date d = new Date();
        return add(d.getHours());

    }

    public String minuto() {
        Date d = new Date();
        return add(d.getMinutes());

    }

    public String segundo() {
        Date d = new Date();
        System.out.println(add(d.getMinutes()));
        return add(d.getMinutes());
    }
     */
    public String data() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    public String horas() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    public boolean validaMaquina(Maquina maquina) {
        boolean verificada = false;

        if (!(maquina.getMarcaMaquina() == null) || !(maquina.getSistemaOperacional() == null) || !(maquina.getMemoriaTotal() == null)
                || !(maquina.getInfoProcessador() == null) || !(maquina.getModelo() == null) || !(maquina.getNumeroSerie() == null)
                || !(maquina.getEspacoTotalHd() == null)) {
            verificada = true;
        }

        return verificada;
    }
}
