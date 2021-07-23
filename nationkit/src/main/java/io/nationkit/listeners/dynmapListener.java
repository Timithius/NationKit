package io.nationkit.listeners;

import org.dynmap.DynmapCommonAPI;
import org.dynmap.DynmapCommonAPIListener;
import org.dynmap.markers.MarkerAPI;

public class dynmapListener extends DynmapCommonAPIListener implements DynmapCommonAPI{
    private DynmapCommonAPI dynmapCommonAPI;

    public void apiEnabled(DynmapCommonAPI api) {
        dynmapCommonAPI = api;
    }
    @Override
    public void apiDisabled(DynmapCommonAPI api) {
        dynmapCommonAPI = null;
    }


    @Override
    public MarkerAPI getMarkerAPI() {
        return dynmapCommonAPI.getMarkerAPI();
    }

    @Override
    public boolean markerAPIInitialized() {
        return false;
    }

    @Override
    public boolean sendBroadcastToWeb(String s, String s1) {
        return false;
    }

    @Override
    public int triggerRenderOfVolume(String s, int i, int i1, int i2, int i3, int i4, int i5) {
        return 0;
    }

    @Override
    public int triggerRenderOfBlock(String s, int i, int i1, int i2) {
        return 0;
    }

    @Override
    public void setPauseFullRadiusRenders(boolean b) {

    }

    @Override
    public boolean getPauseFullRadiusRenders() {
        return false;
    }

    @Override
    public void setPauseUpdateRenders(boolean b) {

    }

    @Override
    public boolean getPauseUpdateRenders() {
        return false;
    }

    @Override
    public void setPlayerVisiblity(String s, boolean b) {

    }

    @Override
    public boolean getPlayerVisbility(String s) {
        return false;
    }

    @Override
    public void assertPlayerInvisibility(String s, boolean b, String s1) {

    }

    @Override
    public void assertPlayerVisibility(String s, boolean b, String s1) {

    }

    @Override
    public void postPlayerMessageToWeb(String s, String s1, String s2) {

    }

    @Override
    public void postPlayerJoinQuitToWeb(String s, String s1, boolean b) {

    }

    @Override
    public String getDynmapCoreVersion() {
        return null;
    }

    @Override
    public boolean setDisableChatToWebProcessing(boolean b) {
        return false;
    }

    @Override
    public boolean testIfPlayerVisibleToPlayer(String s, String s1) {
        return false;
    }

    @Override
    public boolean testIfPlayerInfoProtected() {
        return false;
    }

    @Override
    public void processSignChange(String s, String s1, int i, int i1, int i2, String[] strings, String s2) {

    }
}
