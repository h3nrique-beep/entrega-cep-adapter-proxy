package com.example.produtos;

import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

/** Cópia como bean: o container Spring (escopo singleton) garante a instância única. Só para comparação; não usada na versão final. */
@Component
public class SpringAppLogger {
    private final PrintWriter arquivo;

    public SpringAppLogger() throws IOException {
        arquivo = new PrintWriter(new FileWriter("app.log", true), true);
    }

    public synchronized void log(AppLogger.Nivel nivel, String msg) {
        String linha = LocalDateTime.now() + " [" + nivel + "] " + msg;
        System.out.println(linha);
        arquivo.println(linha);
    }

    public void debug(String msg) { log(AppLogger.Nivel.DEBUG, msg); }
    public void info(String msg) { log(AppLogger.Nivel.INFO, msg); }
    public void erro(String msg) { log(AppLogger.Nivel.ERRO, msg); }
}
