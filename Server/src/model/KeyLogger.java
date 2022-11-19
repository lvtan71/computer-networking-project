package model;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

import java.util.ArrayList;

public class KeyLogger implements NativeKeyListener {

    public ArrayList<String> logs = new ArrayList<String>();

    private void addLog(String log) {
        logs.add(log);
    }

    public void clearLogs() {
        logs = new ArrayList<String>();
    }

    public void nativeKeyPressed(NativeKeyEvent e) {
        addLog(NativeKeyEvent.getKeyText(e.getKeyCode()) + " ");
        System.out.println("Key Pressed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
    }

    public void nativeKeyReleased(NativeKeyEvent e) {

    }

    public void nativeKeyTyped(NativeKeyEvent e) {

    }
}