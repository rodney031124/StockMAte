package com.stockmate.app.utils;

import android.os.Handler;
import android.os.Looper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RealTimeDataManager<T> {
    private static final long LATENCY_THRESHOLD_MS = 100;
    private static final long DATA_EXPIRY_MS = 60000; // 1 minute cleanup
    
    private final List<RealTimeDataPoint<T>> realTimeBuffer = Collections.synchronizedList(new ArrayList<>());
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());
    
    private int totalReceived = 0;
    private int totalFiltered = 0;

    public interface RealTimeCallback<T> {
        void onRealTimeDataReceived(T data, long latency);
        void onStatsUpdated(int kept, int filtered);
    }

    public static class RealTimeDataPoint<T> {
        public final T data;
        public final long timestamp;
        public final long latency;

        public RealTimeDataPoint(T data, long timestamp, long latency) {
            this.data = data;
            this.timestamp = timestamp;
            this.latency = latency;
        }
    }

    public void processIncomingData(T data, long sourceTimestamp, RealTimeCallback<T> callback) {
        executorService.execute(() -> {
            long currentTimestamp = System.currentTimeMillis();
            long latency = currentTimestamp - sourceTimestamp;
            
            totalReceived++;

            if (latency >= 0 && latency < LATENCY_THRESHOLD_MS) {
                RealTimeDataPoint<T> point = new RealTimeDataPoint<>(data, currentTimestamp, latency);
                realTimeBuffer.add(point);
                
                mainHandler.post(() -> {
                    if (callback != null) {
                        callback.onRealTimeDataReceived(data, latency);
                        callback.onStatsUpdated(realTimeBuffer.size(), totalFiltered);
                    }
                });
            } else {
                totalFiltered++;
                mainHandler.post(() -> {
                    if (callback != null) {
                        callback.onStatsUpdated(realTimeBuffer.size(), totalFiltered);
                    }
                });
            }
            
            // Periodically clean up old data (e.g., every 10 items)
            if (totalReceived % 10 == 0) {
                cleanupOldData();
            }
        });
    }

    private void cleanupOldData() {
        long now = System.currentTimeMillis();
        synchronized (realTimeBuffer) {
            Iterator<RealTimeDataPoint<T>> iterator = realTimeBuffer.iterator();
            while (iterator.hasNext()) {
                if (now - iterator.next().timestamp > DATA_EXPIRY_MS) {
                    iterator.remove();
                }
            }
        }
    }

    public List<T> getActiveRealTimeData() {
        List<T> dataList = new ArrayList<>();
        synchronized (realTimeBuffer) {
            for (RealTimeDataPoint<T> point : realTimeBuffer) {
                dataList.add(point.data);
            }
        }
        return dataList;
    }
    
    public void shutdown() {
        executorService.shutdown();
    }
}