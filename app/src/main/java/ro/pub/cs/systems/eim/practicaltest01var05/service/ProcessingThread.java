package ro.pub.cs.systems.eim.practicaltest01var05.service;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Random;

import ro.pub.cs.systems.eim.practicaltest01var05.general.Constants;

public class ProcessingThread extends Thread {

    private Context context = null;
    private boolean isRunning = true;

    private Random random = new Random();

    private String text_content;

    public ProcessingThread(Context context, String text) {
        this.context = context;

        text_content = text;
    }

    @Override
    public void run() {
        Log.d(Constants.PROCESSING_THREAD_TAG, "Thread has started! PID: " );
        while (isRunning) {
            sendMessage();
            sleep();
        }
        Log.d(Constants.PROCESSING_THREAD_TAG, "Thread has stopped!");
    }

    private void sendMessage() {
        Intent intent = new Intent();
        intent.setAction(Constants.actionTypes);
        intent.putExtra(Constants.BROADCAST_RECEIVER_EXTRA,
                text_content);
        context.sendBroadcast(intent);
    }

    private void sleep() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }

    public void stopThread() {
        isRunning = false;
    }
}