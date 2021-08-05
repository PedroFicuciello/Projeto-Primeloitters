package br.com.kprunnin.classes;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import br.com.kprunnin.Gui.KprunninGui;
import br.com.kprunnin.classes.Alerta;
import br.com.kprunnin.classes.InfoHardware;
import br.com.kprunnin.classes.InfoMaquina;
import br.com.kprunnin.classes.Logger;
import br.com.kprunnin.classes.Monitoramento;
import br.com.kprunnin.classes.Toolbox;
import br.com.kprunnin.modelo.Dado;
import oshi.hardware.GlobalMemory;

public class TelegramBot extends TelegramLongPollingBot {


    
    @Override    
    public void onUpdateReceived(Update update) {
        System.out.println(update.getMessage().getText());
        Toolbox tb = new Toolbox();
        String command = update.getMessage().getText();
        //String mensagemUser = update.getMessage().getText();
        //String teste = update.getMessage().getText();
        
        long chat_id = update.getMessage().getChatId();

        // o b j e t o s   m e n s a g e m
        SendMessage mensagem1 = new SendMessage().setChatId(chat_id);
        SendMessage mensagem2 = new SendMessage().setChatId(chat_id);
        SendMessage mensagem3 = new SendMessage().setChatId(chat_id);
        SendMessage mensagem4 = new SendMessage().setChatId(chat_id);
        SendMessage mensagem5 = new SendMessage().setChatId(chat_id);
        SendMessage mensagem6 = new SendMessage().setChatId(chat_id);

        //o b j e t o s   c l a s s e s
        Monitoramento monitoramento = new Monitoramento();
        InfoMaquina objMaquina = new InfoMaquina();
        InfoHardware objHardware = new InfoHardware();
        Alerta objAlerta = new Alerta();

        switch (command) {
            case "/start":
                mensagem1.setText("Olá, eu sou o KPRunnin, o que eu posso fazer por você?" + update.getMessage().getFrom().getFirstName());
                break;

            case "/maquina":
                mensagem1.setText("Sistema operacional: " + objMaquina.getSistemaOperacional());
                mensagem2.setText("Fabricante: " + objMaquina.getMarca());
                mensagem3.setText("Modelo: " + objMaquina.getModelo());
                mensagem4.setText("Número de série: " + objMaquina.getNumeroDeSerie());
                break;

            case "/dadospc":
                mensagem1.setText("Processador: " + objHardware.getProcessador());
                mensagem2.setText(String.format("Espaço livre do HD: %.2fGB", KprunninGui.espacoHD));
                mensagem3.setText("RAM total: " + objHardware.getMemoria());

                break;

            case "/documento":
                SendDocument sendDocument = new SendDocument();
                
                sendDocument.setDocument(new File("./logs/" + tb.data() + ".log"));
                sendDocument.setChatId(chat_id);
                try {
                    execute(sendDocument);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                break;
            case "/estadomaquina":
                mensagem1.setText("CPU atualmente: " + monitoramento.getCPU()[0] + "%");
                mensagem2.setText("Memória atualmente: " + monitoramento.getPorcentagemMem() + "%");
                mensagem3.setText("Disco atualmente: " + monitoramento.getDisco()[0] + "%");
                break;

            case "/erros":
                mensagem1.setText("Erros da CPU: " + objAlerta.getContagemErrosCpu());
                mensagem2.setText("Erros da Memória: " + objAlerta.getContagemErrosMemoria());
                mensagem3.setText("Erros do Disco: " + objAlerta.getContagemErrosDisco());
                mensagem4.setText("Erros totais registrados: " + objAlerta.getErrosRegistrados());
                break;
            default:
                break;
        }

        mensagem1.setChatId(update.getMessage().getChatId());
        mensagem2.setChatId(update.getMessage().getChatId());
        mensagem3.setChatId(update.getMessage().getChatId());
        mensagem4.setChatId(update.getMessage().getChatId());
        mensagem5.setChatId(update.getMessage().getChatId());
        mensagem6.setChatId(update.getMessage().getChatId());

        try {
            execute(mensagem1);
            execute(mensagem2);
            execute(mensagem3);
            execute(mensagem4);
            execute(mensagem5);
            execute(mensagem6);
        } catch (TelegramApiException e) {
            System.out.println("deu o erro inofensivo");
        }
    }

            
    @Override
    public String getBotUsername() {
        return "KPRunnin'";
    }

    @Override
    public String getBotToken() {
        return "1151853849:AAFv6jKSUii6uJqISDqi8z3HF-OkuDpiQc4";
    }
}
