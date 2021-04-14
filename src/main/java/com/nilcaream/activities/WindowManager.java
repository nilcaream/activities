package com.nilcaream.activities;

import java.util.List;

public interface WindowManager {

    List<Window> get();

    void close(Window window);
}
