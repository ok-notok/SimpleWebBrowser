package com.example.simplewebbrowser;

import android.content.Context;
import java.util.ArrayList;
import java.util.List;

public class TabManager {

    private List<BrowserTab> tabs;
    private int currentTabIndex;
    private Context context;

    public TabManager(Context context) {
        this.context = context;
        tabs = new ArrayList<>();
        currentTabIndex = -1;
    }

    public void createNewTab() {
        BrowserTab newTab = new BrowserTab(context);
        tabs.add(newTab);
        currentTabIndex = tabs.size() - 1;
    }

    public void closeTab(int index) {
        if (index >= 0 && index < tabs.size()) {
            tabs.get(index).destroy();
            tabs.remove(index);

            if (tabs.size() > 0) {
                currentTabIndex = (index == tabs.size()) ? index - 1 : index;
            } else {
                currentTabIndex = -1;
            }
        }
    }

    public BrowserTab getCurrentTab() {
        if (currentTabIndex >= 0) {
            return tabs.get(currentTabIndex);
        }
        return null;
    }

    public void switchTab(int index) {
        if (index >= 0 && index < tabs.size()) {
            currentTabIndex = index;
        }
    }

    public int getCurrentTabIndex() {
        return currentTabIndex;
    }

    public int getTabCount() {
        return tabs.size();
    }
}
