package com.example.produtos;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

/** Singleton clássico: construtor privado, lazy, thread-safe (volatile + dupla checagem). */
public final class AppLogger {
    public enum Nivel { DEBUG, INFO, ERRO }

    private static volatile AppLogger instance;
    private final PrintWriter arquivo;

    private AppLogger() {
        try {
            arquivo = new PrintWriter(new FileWriter("app.log", true), true);
        } catch (IOException e) {
            throw new IllegalStateException("não foi possível abrir app.log", e);
        }
    }

    public static AppLogger getInstance() {
        if (instance == null) {
            synchronized (AppLogger.class) {
                if (instance == null) instance = new AppLogger();
            }
        }
        return instance;
    }

    public synchronized void log(Nivel nivel, String msg) {
        String linha = LocalDateTime.now() + " [" + nivel + "] " + msg;
        System.out.println(linha);
        arquivo.println(linha);
    }

    public void debug(String msg) { log(Nivel.DEBUG, msg); }
    public void info(String msg) { log(Nivel.INFO, msg); }
    public void erro(String msg) { log(Nivel.ERRO, msg); }
}
