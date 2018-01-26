package com.workaround.ajeesh.ajr_22012018_workaround_intents.Helpers;

import android.content.Intent;
import android.os.BatteryManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ajesh on 26-01-2018.
 */

public class BatteryStatusHelper {
    public int level;
    public int maxValue;
    public int batteryStatus;
    public int batteryHealth;
    public int batteryPlugged;
    public String batteryTech;
    public float batteryVoltage;
    public boolean battery;
    public float batteryTemp;
    public int chargedPct;

    public String getHealthString() {
        return healthValueMap.get(batteryHealth);
    }

    public String getStatusString() {
        return statusValueMap.get(batteryStatus);
    }

    public String getPluggedString() {
        return pluggedValueMap.get(batteryPlugged);
    }

    public BatteryStatusHelper() {
    }

    public BatteryStatusHelper(Intent batteryChangedIntent) {
        initialize(batteryChangedIntent);
    }

    public void initialize(Intent batteryChangedIntent) {
        level = batteryChangedIntent.getIntExtra("level", -1);
        maxValue = batteryChangedIntent.getIntExtra("scale", -1);
        batteryStatus = batteryChangedIntent.getIntExtra("status", -1);
        batteryHealth = batteryChangedIntent.getIntExtra("health", -1);
        batteryPlugged = batteryChangedIntent.getIntExtra("plugged", -1);
        batteryTech = batteryChangedIntent.getStringExtra("technology");
        batteryVoltage = (float) batteryChangedIntent.getIntExtra("voltage", -1) / 1000;
        battery = batteryChangedIntent.getBooleanExtra("present", false);
        batteryTemp = (float) batteryChangedIntent.getIntExtra("temperature", -1) / 10;
        chargedPct = (level * 100) / maxValue;

    }

    @Override
    public String toString() {
        return "Battery Info - Health=" + healthValueMap.get(batteryHealth) +
                "; " + "Status=" + statusValueMap.get(batteryStatus) + "; " + "Charged % = "
                + chargedPct + "%; " + "Plugged = " + pluggedValueMap.get(batteryPlugged) + "; " +
                "Type = " + batteryTech + "; " + "Voltage = " + batteryVoltage
                + " volts; " + "Temperature = " + batteryTemp + "; " + "Battery present = " +
                battery + ";";
    }

    // Map Battery health integer flags to meaningful text
    @SuppressWarnings("serial")
    private static final Map<Integer, String> healthValueMap = new HashMap<Integer, String>() {
        {
            put(BatteryManager.BATTERY_HEALTH_DEAD, "Dead");
            put(BatteryManager.BATTERY_HEALTH_GOOD, "Good");
            put(BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE, "Over voltage");
            put(BatteryManager.BATTERY_HEALTH_OVERHEAT, "Over heating");
            put(BatteryManager.BATTERY_HEALTH_UNKNOWN, "Unknown");
            put(BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE, "Failure, but unknown");
            put(-1, "Not Reported");
        }
    };

    // Map battery status integer flags to meaningful text
    @SuppressWarnings("serial")
    private static final Map<Integer, String> statusValueMap = new HashMap<Integer, String>() {
        {
            put(BatteryManager.BATTERY_STATUS_CHARGING, "Charging");
            put(BatteryManager.BATTERY_STATUS_DISCHARGING, "Discharging");
            put(BatteryManager.BATTERY_STATUS_FULL, "Full");
            put(BatteryManager.BATTERY_STATUS_NOT_CHARGING, "Not Charging");
            put(BatteryManager.BATTERY_STATUS_UNKNOWN, "Unknown");
            put(-1, "Not Reported");
        }
    };

    // Map plug type integer flags to meaningful text
    @SuppressWarnings("serial")
    private static final Map<Integer, String> pluggedValueMap = new HashMap<Integer, String>() {
        {
            put(BatteryManager.BATTERY_PLUGGED_AC, "On AC");
            put(BatteryManager.BATTERY_PLUGGED_USB, "On USB");
            put(-1, "Not Reported");
        }
    };
}
