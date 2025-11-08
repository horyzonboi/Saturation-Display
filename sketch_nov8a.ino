#include <LedControl.h>

#define DIN_PIN 12
#define CS_PIN  11
#define CLK_PIN 10

LedControl lc = LedControl(DIN_PIN, CLK_PIN, CS_PIN, 1);

int foodLevel = 20;        // 0-20
float saturationLevel = 20.0; // 0-20
int lastFood = -1;
int lastSat = -1;

void setup() {
  Serial.begin(9600);

  lc.shutdown(0, false);
  lc.setIntensity(0, 8);
  lc.clearDisplay(0);
}

void loop() {
  // Read serial data
  if (Serial.available()) {
    String line = Serial.readStringUntil('\n');
    int comma = line.indexOf(',');
    if (comma > 0) {
      foodLevel = line.substring(0, comma).toInt();
      saturationLevel = line.substring(comma + 1).toFloat();

      Serial.print("Received -> Food: ");
      Serial.print(foodLevel);
      Serial.print("  Sat: ");
      Serial.println(saturationLevel);
    } else {
      Serial.println("Invalid line received: " + line);
    }
  }

  // Only update display when values change
  if (foodLevel != lastFood || (int)saturationLevel != lastSat) {
    drawBars();
    lastFood = foodLevel;
    lastSat = (int)saturationLevel;
  }
}

void drawBars() {
  lc.clearDisplay(0);

  // --- Food (top 3 rows: 0,1,2) ---
  for (int i = 0; i < foodLevel; i++) {
    int row = i / 8;        // 0,1,2
    int col = i % 8;
    if (row < 3) {          // only top 3 rows
      lc.setLed(0, row, col, true);
    }
  }

  // --- Saturation (bottom 3 rows: 5,6,7) ---
  for (int i = 0; i < (int)saturationLevel; i++) {
    int row = 7 - (i / 8);  // bottom row = 7, then 6, then 5
    if (row >= 5) {
      int col = i % 8;
      lc.setLed(0, row, col, true);
    }
  }

  // Rows 3 and 4 (middle) remain off
}
