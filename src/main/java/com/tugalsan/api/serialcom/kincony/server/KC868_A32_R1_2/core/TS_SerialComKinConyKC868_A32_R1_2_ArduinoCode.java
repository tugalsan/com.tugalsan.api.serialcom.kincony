package com.tugalsan.api.serialcom.kincony.server.KC868_A32_R1_2.core;

public class TS_SerialComKinConyKC868_A32_R1_2_ArduinoCode {
    /*
    //------------------------------------ TODO -----------------------------------------------------------------------
//A BIG MEM FOR SNIFFING WHAT IS GOING ON
//TEST SURFACE TREATMENT BATH CODE

//------------------------------------ WARNING -----------------------------------------------------------------------
//FOR BOARD KinCony_KC868_A32_R1_2 PIN 1 is called pin 0 here for ease of array usage

//------------------------------------ DEFINE -----------------------------------------------------------------------
#define TA_SerialHandler_WAIT_UNTIL_CONNECTION false  //IF TRUE; WAITS FOR 3 SECONDS
#define TA_SerialHandler_WAIT_IN_BAUDRATE 115200
#define TA_SerialHandler_BUFFER_SIZE 60

#define TA_ChipHandler_KinCony_KC868_A32_R1_2_LOOP_DELAY_MS 20    //critical for digital input noise
#define TA_ChipHandler_KinCony_KC868_A32_R1_2_BUTTON_LIFE_MS 200  //critical for digital button
#define INFO_TA_ChipHandler_KinCony_KC868_A32_R1_2 false

#define TA_CommandHandler_KinCony_KC868_A32_R1_2_MEM_INT_TIMER_COUNT 16
#define TA_CommandHandler_KinCony_KC868_A32_R1_2_MEM_INT_DI_COUNT 32
#define TA_CommandHandler_KinCony_KC868_A32_R1_2_MEM_INT_DO_COUNT 32
// DI + DO + DO_OSCILATOR_COUNT + TIMER
#define INFO_TA_CommandHandler_KinCony_KC868_A32_R1_2 false

#define TA_SurfaceTreatmentBath16_TIMER_DEFAULT_VALUE 5
#define INFO_TA_SurfaceTreatmentBath16 true

//------------------------------------ STRING HANDLER -----------------------------------------------------------------------

//USAGE: stringHandler.isInt("ad") -> true/false
class TA_StringHandler {
public:
  TA_StringHandler();
  bool isInt(String st);
private:
};
TA_StringHandler::TA_StringHandler() {
}
bool TA_StringHandler::isInt(String str) {
  for (byte i = 0; i < str.length(); i++) {
    if (isDigit(str.charAt(i))) return true;
  }
  return false;
}
TA_StringHandler stringHandler;

//------------------------------------ STRING TOKENIZER -----------------------------------------------------------------------

// USAGE
// TA_StringTokenizer tokens("aa:bb:cc:dd:ee", ":");
// while (tokens.hasNext()) {
//   Serial.println(tokens.nextToken());
// }
class TA_StringTokenizer {
public:
  TA_StringTokenizer(String str, String del);
  bool hasNext();
  String nextToken();
private:
  String _str;
  String _del;
  int _ptr;
};
TA_StringTokenizer::TA_StringTokenizer(String str, String del) {
  _str = str;
  _del = del;
  _ptr = 0;
}
bool TA_StringTokenizer::hasNext() {
  if (_ptr < _str.length()) {
    return true;
  } else {
    return false;
  }
}
String TA_StringTokenizer::nextToken() {
  if (_ptr >= _str.length()) {
    _ptr = _str.length();
    return "";
  }
  String result = "";
  int delIndex = _str.indexOf(_del, _ptr);
  if (delIndex == -1) {
    result = _str.substring(_ptr);
    _ptr = _str.length();
    return result;
  } else {
    result = _str.substring(_ptr, delIndex);
    _ptr = delIndex + _del.length();
    return result;
  }
}

//------------------------------------ TIME HANDLER -----------------------------------------------------------------------

//USAGE: void loop() { time.loop();
class TA_TimeHandler {
public:
  TA_TimeHandler();
  unsigned long loop();
  unsigned long current();
  unsigned long previous();
  unsigned long delta();
private:
  unsigned long _current;
  unsigned long _previous;
  unsigned long _delta;
};
TA_TimeHandler::TA_TimeHandler() {
  _current = millis();
  loop();
}
unsigned long TA_TimeHandler::current() {
  return _current;
}
unsigned long TA_TimeHandler::previous() {
  return _previous;
}
unsigned long TA_TimeHandler::delta() {
  return _delta;
}
unsigned long TA_TimeHandler::loop() {
  _previous = _current;
  _current = millis();
  _delta = _current - _previous;
  return _current;
}
TA_TimeHandler timeHandler;

//------------------------------------ SERIAL HANDLER -----------------------------------------------------------------------
//COMMADS SHOULD START WITH !
//COMMADS SHOULD END WITH WITH \n
//\r char is irrelevant at anywhere
class TA_SerialHandler {
public:
  TA_SerialHandler(bool waitUntilConnection, int baudRate);
  //serialHandler
  void setup();
  //fetcher
  bool hasNextLine();
  String nextLine();
private:
  //serialHandler
  int _baudRate;
  bool _waitUntilConnection;
  //fetcher
  int _bufferSize;
  char _buffer[TA_SerialHandler_BUFFER_SIZE];
  int _bufferIdx;
};
TA_SerialHandler::TA_SerialHandler(bool waitUntilConnection, int baudRate) {
  //serialHandler
  _waitUntilConnection = waitUntilConnection;
  _baudRate = _baudRate;
  //fetcher
  _bufferSize = TA_SerialHandler_BUFFER_SIZE;
  int _bufferIdx = 0;
}
void TA_SerialHandler::setup() {
  Serial.begin(_baudRate);
  if (_waitUntilConnection) {
    while (!Serial)
      ;
  } else {
    while (!Serial && (millis() < 3 * 1000))
      ;
  }
}
String TA_SerialHandler::nextLine() {
  return String(_buffer);
}
bool TA_SerialHandler::hasNextLine() {
  bool lineFound = false;
  while (Serial.available() > 0) {
    char chr = Serial.read();
    if (_bufferIdx == 0 && chr != '!') {  //ignore
      continue;
    }
    if (chr == '\r' || (chr == '\n' && _bufferIdx == 0)) {  //ignore
      continue;
    }
    if (chr == '\n') {          //command received fully
      _buffer[_bufferIdx] = 0;  //ENDS STRING
      lineFound = true;
      _bufferIdx = 0;  //FOR THE NEXT ROUND
      continue;
    }
    if (_bufferIdx < _bufferSize && lineFound == false) {  //buffer up char
      _buffer[_bufferIdx++] = chr;
      continue;
    }
  }
  return lineFound;
}
TA_SerialHandler serialHandler(TA_SerialHandler_WAIT_UNTIL_CONNECTION, TA_SerialHandler_WAIT_IN_BAUDRATE);

//------------------------------------ CHIP HANDLER (TA_ChipHandler_KinCony_KC868_A32_R1_2) -----------------------------------------------------------------------

//TA_ChipHandler_KinCony_KC868_A32_R1_2
#include "PCF8574.h"
TwoWire _I2C_0 = TwoWire(0);
PCF8574 _pcf8574_I1(&_I2C_0, 0x24, 4, 5);
PCF8574 _pcf8574_I2(&_I2C_0, 0x25, 4, 5);
PCF8574 _pcf8574_I3(&_I2C_0, 0x21, 4, 5);
PCF8574 _pcf8574_I4(&_I2C_0, 0x22, 4, 5);
TwoWire _I2C_1 = TwoWire(1);
PCF8574 _pcf8574_R1(&_I2C_1, 0x24, 15, 13);
PCF8574 _pcf8574_R2(&_I2C_1, 0x25, 15, 13);
PCF8574 _pcf8574_R3(&_I2C_1, 0x21, 15, 13);
PCF8574 _pcf8574_R4(&_I2C_1, 0x22, 15, 13);
class TA_ChipHandler_KinCony_KC868_A32_R1_2 {
public:
  TA_ChipHandler_KinCony_KC868_A32_R1_2();
  void setup();
  void loop(unsigned long currentTime);
  void loop_testButtons();
  String name();
  bool isPinValid(int pin);
  bool getDI(int pin);
  bool getDO(int pin);
  bool setDO(int pin, bool value);
  bool getButtonCurrent(int pin);
  bool getButtonPrevious(int pin);
  bool oscillateIs(int pin);
  bool oscillateSet(int pin, int secDuration, int secGap, int count, unsigned long currentTime);
private:
  bool _setDO(int pin, bool value);
  bool __fetchDI(int pin);  //RUN ONCE EVERY LOOP!
  bool __fetchDO(int pin);  //RUN ONCE EVERY LOOP!
  unsigned long _oscillateStart[32];
  bool _oscillateReset(int pin);
  int _oscillateDuration[32];
  int _oscillateGap[32];
  int _oscillateCount[32];
  bool _DIButtonValuePrevious[32];
  bool _DIButtonValueCurrent[32];
  bool _DIButtonTimeCurrent[32];
  bool _DIRegister[32];
  bool _DORegister[32];
  bool _DIRegister_noisy0[32];
  bool _DORegister_noisy0[32];
  bool _DIRegister_noisy1[32];
  bool _DORegister_noisy1[32];
  bool _DIRegister_noisy2[32];
  bool _DORegister_noisy2[32];
  bool _DIRegister_noisy3[32];
  bool _DORegister_noisy3[32];
  bool _DIRegister_noisy4[32];
  bool _DORegister_noisy4[32];
  int _DORegister_noisyState;
};
TA_ChipHandler_KinCony_KC868_A32_R1_2::TA_ChipHandler_KinCony_KC868_A32_R1_2() {
}
void TA_ChipHandler_KinCony_KC868_A32_R1_2::loop_testButtons() {
  for (int i = 0; i < 32; i++) {
    if (getDI(i) != getDO(i)) {
      setDO(i, getDI(i));
    }
  }
}
bool TA_ChipHandler_KinCony_KC868_A32_R1_2::_oscillateReset(int pin) {
  if (!isPinValid(pin)) {
    return false;
  }
  _oscillateCount[pin] = 0;
  return true;
}
bool TA_ChipHandler_KinCony_KC868_A32_R1_2::oscillateSet(int pin, int secDuration, int secGap, int count, unsigned long currentTime) {
  if (!isPinValid(pin)) {
    return false;
  }
  _oscillateStart[pin] = currentTime;
  _oscillateDuration[pin] = secDuration * 1000;
  _oscillateGap[pin] = secGap * 1000;
  _oscillateCount[pin] = count;
  return true;
}
bool TA_ChipHandler_KinCony_KC868_A32_R1_2::oscillateIs(int pin) {
  return _oscillateCount[pin] != 0;
}
bool TA_ChipHandler_KinCony_KC868_A32_R1_2::isPinValid(int pin) {
  return pin >= 0 && pin < 32;
}
bool TA_ChipHandler_KinCony_KC868_A32_R1_2::getDI(int pin) {
  if (!isPinValid(pin)) {
    return false;
  }
  return _DIRegister[pin];
}
bool TA_ChipHandler_KinCony_KC868_A32_R1_2::getDO(int pin) {
  if (!isPinValid(pin)) {
    return false;
  }
  return _DORegister[pin];
}
bool TA_ChipHandler_KinCony_KC868_A32_R1_2::__fetchDI(int pin) {
  if (!isPinValid(pin)) {
    return false;
  }
  if (pin < 8) {
    if (pin == 0) return _pcf8574_I1.digitalRead(P0) == LOW;
    if (pin == 1) return _pcf8574_I1.digitalRead(P1) == LOW;
    if (pin == 2) return _pcf8574_I1.digitalRead(P2) == LOW;
    if (pin == 3) return _pcf8574_I1.digitalRead(P3) == LOW;
    if (pin == 4) return _pcf8574_I1.digitalRead(P4) == LOW;
    if (pin == 5) return _pcf8574_I1.digitalRead(P5) == LOW;
    if (pin == 6) return _pcf8574_I1.digitalRead(P6) == LOW;
    if (pin == 7) return _pcf8574_I1.digitalRead(P7) == LOW;
  }
  if (pin < 16) {
    if (pin == 8) return _pcf8574_I2.digitalRead(P0) == LOW;
    if (pin == 9) return _pcf8574_I2.digitalRead(P1) == LOW;
    if (pin == 10) return _pcf8574_I2.digitalRead(P2) == LOW;
    if (pin == 11) return _pcf8574_I2.digitalRead(P3) == LOW;
    if (pin == 12) return _pcf8574_I2.digitalRead(P4) == LOW;
    if (pin == 13) return _pcf8574_I2.digitalRead(P5) == LOW;
    if (pin == 14) return _pcf8574_I2.digitalRead(P6) == LOW;
    if (pin == 15) return _pcf8574_I2.digitalRead(P7) == LOW;
  }
  if (pin < 24) {
    if (pin == 16) return _pcf8574_I3.digitalRead(P0) == LOW;
    if (pin == 17) return _pcf8574_I3.digitalRead(P1) == LOW;
    if (pin == 18) return _pcf8574_I3.digitalRead(P2) == LOW;
    if (pin == 19) return _pcf8574_I3.digitalRead(P3) == LOW;
    if (pin == 20) return _pcf8574_I3.digitalRead(P4) == LOW;
    if (pin == 21) return _pcf8574_I3.digitalRead(P5) == LOW;
    if (pin == 22) return _pcf8574_I3.digitalRead(P6) == LOW;
    if (pin == 23) return _pcf8574_I3.digitalRead(P7) == LOW;
  }
  if (pin < 32) {
    if (pin == 24) return _pcf8574_I4.digitalRead(P0) == LOW;
    if (pin == 25) return _pcf8574_I4.digitalRead(P1) == LOW;
    if (pin == 26) return _pcf8574_I4.digitalRead(P2) == LOW;
    if (pin == 27) return _pcf8574_I4.digitalRead(P3) == LOW;
    if (pin == 28) return _pcf8574_I4.digitalRead(P4) == LOW;
    if (pin == 29) return _pcf8574_I4.digitalRead(P5) == LOW;
    if (pin == 30) return _pcf8574_I4.digitalRead(P6) == LOW;
    if (pin == 31) return _pcf8574_I4.digitalRead(P7) == LOW;
  }
}
bool TA_ChipHandler_KinCony_KC868_A32_R1_2::__fetchDO(int pin) {
  if (!isPinValid(pin)) {
    return false;
  }
  if (pin < 8) {
    if (pin == 0) return _pcf8574_R1.digitalRead(P0) == LOW;
    if (pin == 1) return _pcf8574_R1.digitalRead(P1) == LOW;
    if (pin == 2) return _pcf8574_R1.digitalRead(P2) == LOW;
    if (pin == 3) return _pcf8574_R1.digitalRead(P3) == LOW;
    if (pin == 4) return _pcf8574_R1.digitalRead(P4) == LOW;
    if (pin == 5) return _pcf8574_R1.digitalRead(P5) == LOW;
    if (pin == 6) return _pcf8574_R1.digitalRead(P6) == LOW;
    if (pin == 7) return _pcf8574_R1.digitalRead(P7) == LOW;
  }
  if (pin < 16) {
    if (pin == 8) return _pcf8574_R2.digitalRead(P0) == LOW;
    if (pin == 9) return _pcf8574_R2.digitalRead(P1) == LOW;
    if (pin == 10) return _pcf8574_R2.digitalRead(P2) == LOW;
    if (pin == 11) return _pcf8574_R2.digitalRead(P3) == LOW;
    if (pin == 12) return _pcf8574_R2.digitalRead(P4) == LOW;
    if (pin == 13) return _pcf8574_R2.digitalRead(P5) == LOW;
    if (pin == 14) return _pcf8574_R2.digitalRead(P6) == LOW;
    if (pin == 15) return _pcf8574_R2.digitalRead(P7) == LOW;
  }
  if (pin < 24) {
    if (pin == 16) return _pcf8574_R3.digitalRead(P0) == LOW;
    if (pin == 17) return _pcf8574_R3.digitalRead(P1) == LOW;
    if (pin == 18) return _pcf8574_R3.digitalRead(P2) == LOW;
    if (pin == 19) return _pcf8574_R3.digitalRead(P3) == LOW;
    if (pin == 20) return _pcf8574_R3.digitalRead(P4) == LOW;
    if (pin == 21) return _pcf8574_R3.digitalRead(P5) == LOW;
    if (pin == 22) return _pcf8574_R3.digitalRead(P6) == LOW;
    if (pin == 23) return _pcf8574_R3.digitalRead(P7) == LOW;
  }
  if (pin < 32) {
    if (pin == 24) return _pcf8574_R4.digitalRead(P0) == LOW;
    if (pin == 25) return _pcf8574_R4.digitalRead(P1) == LOW;
    if (pin == 26) return _pcf8574_R4.digitalRead(P2) == LOW;
    if (pin == 27) return _pcf8574_R4.digitalRead(P3) == LOW;
    if (pin == 28) return _pcf8574_R4.digitalRead(P4) == LOW;
    if (pin == 29) return _pcf8574_R4.digitalRead(P5) == LOW;
    if (pin == 30) return _pcf8574_R4.digitalRead(P6) == LOW;
    if (pin == 31) return _pcf8574_R4.digitalRead(P7) == LOW;
  }
}
bool TA_ChipHandler_KinCony_KC868_A32_R1_2::_setDO(int pin, bool value) {
  if (!isPinValid(pin)) {
    return false;
  }
  if (pin < 8) {
    if (pin == 0) _pcf8574_R1.digitalWrite(P0, value ? LOW : HIGH);
    if (pin == 1) _pcf8574_R1.digitalWrite(P1, value ? LOW : HIGH);
    if (pin == 2) _pcf8574_R1.digitalWrite(P2, value ? LOW : HIGH);
    if (pin == 3) _pcf8574_R1.digitalWrite(P3, value ? LOW : HIGH);
    if (pin == 4) _pcf8574_R1.digitalWrite(P4, value ? LOW : HIGH);
    if (pin == 5) _pcf8574_R1.digitalWrite(P5, value ? LOW : HIGH);
    if (pin == 6) _pcf8574_R1.digitalWrite(P6, value ? LOW : HIGH);
    if (pin == 7) _pcf8574_R1.digitalWrite(P7, value ? LOW : HIGH);
    return true;
  }
  if (pin < 16) {
    if (pin == 8) _pcf8574_R2.digitalWrite(P0, value ? LOW : HIGH);
    if (pin == 9) _pcf8574_R2.digitalWrite(P1, value ? LOW : HIGH);
    if (pin == 10) _pcf8574_R2.digitalWrite(P2, value ? LOW : HIGH);
    if (pin == 11) _pcf8574_R2.digitalWrite(P3, value ? LOW : HIGH);
    if (pin == 12) _pcf8574_R2.digitalWrite(P4, value ? LOW : HIGH);
    if (pin == 13) _pcf8574_R2.digitalWrite(P5, value ? LOW : HIGH);
    if (pin == 14) _pcf8574_R2.digitalWrite(P6, value ? LOW : HIGH);
    if (pin == 15) _pcf8574_R2.digitalWrite(P7, value ? LOW : HIGH);
  }
  if (pin < 24) {
    if (pin == 16) _pcf8574_R3.digitalWrite(P0, value ? LOW : HIGH);
    if (pin == 17) _pcf8574_R3.digitalWrite(P1, value ? LOW : HIGH);
    if (pin == 18) _pcf8574_R3.digitalWrite(P2, value ? LOW : HIGH);
    if (pin == 19) _pcf8574_R3.digitalWrite(P3, value ? LOW : HIGH);
    if (pin == 20) _pcf8574_R3.digitalWrite(P4, value ? LOW : HIGH);
    if (pin == 21) _pcf8574_R3.digitalWrite(P5, value ? LOW : HIGH);
    if (pin == 22) _pcf8574_R3.digitalWrite(P6, value ? LOW : HIGH);
    if (pin == 23) _pcf8574_R3.digitalWrite(P7, value ? LOW : HIGH);
  }
  if (pin < 32) {
    if (pin == 24) _pcf8574_R4.digitalWrite(P0, value ? LOW : HIGH);
    if (pin == 25) _pcf8574_R4.digitalWrite(P1, value ? LOW : HIGH);
    if (pin == 26) _pcf8574_R4.digitalWrite(P2, value ? LOW : HIGH);
    if (pin == 27) _pcf8574_R4.digitalWrite(P3, value ? LOW : HIGH);
    if (pin == 28) _pcf8574_R4.digitalWrite(P4, value ? LOW : HIGH);
    if (pin == 29) _pcf8574_R4.digitalWrite(P5, value ? LOW : HIGH);
    if (pin == 30) _pcf8574_R4.digitalWrite(P6, value ? LOW : HIGH);
    if (pin == 31) _pcf8574_R4.digitalWrite(P7, value ? LOW : HIGH);
  }
  return true;
}
bool TA_ChipHandler_KinCony_KC868_A32_R1_2::setDO(int pin, bool value) {
  return _oscillateReset(pin) && _setDO(pin, value);
}
void TA_ChipHandler_KinCony_KC868_A32_R1_2::setup() {
  for (int i = 0; i <= 7; i++) {
    _pcf8574_I1.pinMode(i, INPUT);
    _pcf8574_I2.pinMode(i, INPUT);
    _pcf8574_I3.pinMode(i, INPUT);
    _pcf8574_I4.pinMode(i, INPUT);
    _pcf8574_R1.pinMode(i, OUTPUT);
    _pcf8574_R2.pinMode(i, OUTPUT);
    _pcf8574_R3.pinMode(i, OUTPUT);
    _pcf8574_R4.pinMode(i, OUTPUT);
  }
  String error = F("ERROR_pcf8574_");
  if (!_pcf8574_I1.begin()) {
    Serial.print(error);
    Serial.println(F("I1"));
  }
  if (!_pcf8574_I2.begin()) {
    Serial.print(error);
    Serial.println(F("I2"));
  }
  if (!_pcf8574_I3.begin()) {
    Serial.print(error);
    Serial.println(F("I3"));
  }
  if (!_pcf8574_I4.begin()) {
    Serial.print(error);
    Serial.println(F("I4"));
  }
  if (!_pcf8574_R1.begin()) {
    Serial.print(error);
    Serial.println(F("R1"));
  }
  if (!_pcf8574_R2.begin()) {
    Serial.print(error);
    Serial.println(F("R2"));
  }
  if (!_pcf8574_R3.begin()) {
    Serial.print(error);
    Serial.println(F("R3"));
  }
  if (!_pcf8574_R4.begin()) {
    Serial.print(error);
    Serial.println(F("R4"));
  }
  for (int i = 0; i <= 7; i++) {
    _pcf8574_R1.digitalWrite(i, HIGH);
    _pcf8574_R2.digitalWrite(i, HIGH);
    _pcf8574_R3.digitalWrite(i, HIGH);
    _pcf8574_R4.digitalWrite(i, HIGH);
  }
}
String TA_ChipHandler_KinCony_KC868_A32_R1_2::name() {
  return String(ARDUINO_BOARD);
}
bool TA_ChipHandler_KinCony_KC868_A32_R1_2::getButtonCurrent(int pin) {
  if (!isPinValid(pin)) {
    return false;
  }
  return _DIButtonValueCurrent[pin];
}
bool TA_ChipHandler_KinCony_KC868_A32_R1_2::getButtonPrevious(int pin) {
  if (!isPinValid(pin)) {
    return false;
  }
  return _DIButtonValuePrevious[pin];
}
void TA_ChipHandler_KinCony_KC868_A32_R1_2::loop(unsigned long currentTime) {
  //REGISTER
  if (currentTime < TA_ChipHandler_KinCony_KC868_A32_R1_2_BUTTON_LIFE_MS) {
    for (int i = 0; i < 32; i++) {
      _DIRegister_noisy0[i] = false;
      _DIRegister_noisy1[i] = false;
      _DIRegister_noisy2[i] = false;
      _DIRegister_noisy3[i] = false;
      _DIRegister_noisy4[i] = false;
      _DIRegister[i] = false;
      _DORegister_noisy0[i] = false;
      _DORegister_noisy1[i] = false;
      _DORegister_noisy2[i] = false;
      _DORegister_noisy3[i] = false;
      _DORegister_noisy4[i] = false;
      _DORegister[i] = false;
    }
  } else {
    delay(TA_ChipHandler_KinCony_KC868_A32_R1_2_LOOP_DELAY_MS);
    if (_DORegister_noisyState == 0) {
      _DORegister_noisyState++;
      for (int i = 0; i < 32; i++) {
        _DIRegister_noisy0[i] = __fetchDI(i);
        _DORegister_noisy0[i] = __fetchDO(i);
      }
    } else if (_DORegister_noisyState == 1) {
      _DORegister_noisyState++;
      for (int i = 0; i < 32; i++) {
        _DIRegister_noisy1[i] = __fetchDI(i);
        _DORegister_noisy1[i] = __fetchDO(i);
      }
    } else if (_DORegister_noisyState == 2) {
      _DORegister_noisyState++;
      for (int i = 0; i < 32; i++) {
        _DIRegister_noisy2[i] = __fetchDI(i);
        _DORegister_noisy2[i] = __fetchDO(i);
      }
    } else if (_DORegister_noisyState == 3) {
      _DORegister_noisyState++;
      for (int i = 0; i < 32; i++) {
        _DIRegister_noisy3[i] = __fetchDI(i);
        _DORegister_noisy3[i] = __fetchDO(i);
      }
    } else if (_DORegister_noisyState == 4) {
      _DORegister_noisyState++;
      for (int i = 0; i < 32; i++) {
        _DIRegister_noisy4[i] = __fetchDI(i);
        _DORegister_noisy4[i] = __fetchDO(i);
      }
    } else if (_DORegister_noisyState == 5) {
      _DORegister_noisyState = 0;
      for (int i = 0; i < 32; i++) {
        if (INFO_TA_ChipHandler_KinCony_KC868_A32_R1_2 && i == 0) {
          Serial.print("_DIRegisteri ");
          Serial.print(_DIRegister_noisy0[i] ? "True  " : "False ");
          Serial.print(_DIRegister_noisy1[i] ? "True  " : "False ");
          Serial.print(_DIRegister_noisy2[i] ? "True  " : "False ");
          Serial.print(_DIRegister_noisy3[i] ? "True  " : "False ");
          Serial.print(_DIRegister_noisy4[i] ? "True  " : "False ");
          Serial.println(_DIRegister[i] ? "True  " : "False ");
        }
        if (_DIRegister_noisy0[i] == _DIRegister_noisy1[i] && _DIRegister_noisy0[i] == _DIRegister_noisy2[i] && _DIRegister_noisy0[i] == _DIRegister_noisy3[i] && _DIRegister_noisy0[i] == _DIRegister_noisy4[i]) _DIRegister[i] = _DIRegister_noisy0[i];
        if (_DORegister_noisy0[i] == _DORegister_noisy1[i] && _DORegister_noisy0[i] == _DORegister_noisy2[i] && _DORegister_noisy0[i] == _DORegister_noisy3[i] && _DORegister_noisy0[i] == _DORegister_noisy4[i]) _DORegister[i] = _DORegister_noisy0[i];
      }
    }
  }
  //BUTTON_LIFE
  for (int i = 0; i < 32; i++) {
    if (currentTime > _DIButtonTimeCurrent[i] + TA_ChipHandler_KinCony_KC868_A32_R1_2_BUTTON_LIFE_MS) {
      _DIButtonTimeCurrent[i] = currentTime;
      _DIButtonValuePrevious[i] = _DIButtonValueCurrent[i];
      _DIButtonValueCurrent[i] = _DIRegister[i];
    }
  }
  //OSCILLATE
  for (int i = 0; i < 32; i++) {
    if (!oscillateIs(i)) {
      continue;
    }
    bool pinState = getDO(i);
    unsigned long duration = _oscillateDuration[i];
    unsigned long gap = _oscillateGap[i];
    unsigned long period = duration + gap;
    unsigned long wholeDuration = period * _oscillateCount[i];
    unsigned long deltaDuration = currentTime - _oscillateStart[i];
    if (INFO_TA_ChipHandler_KinCony_KC868_A32_R1_2) {
      Serial.print("INFO_TA_ChipHandler_KinCony_KC868_A32_R1_2: OSCILLATE_CALC_PIN: ");
      Serial.print(i);
      Serial.print(", cur:");
      Serial.print(currentTime);
      Serial.print(", state:");
      Serial.print(pinState);
      Serial.print(", dur:");
      Serial.print(duration);
      Serial.print(", gap:");
      Serial.print(gap);
      Serial.print(", per:");
      Serial.print(period);
      Serial.print(", whlDur:");
      Serial.print(wholeDuration);
      Serial.print(", delDur:");
      Serial.println(deltaDuration);
    }
    if (deltaDuration > wholeDuration) {  //SHUTDOWN
      if (INFO_TA_ChipHandler_KinCony_KC868_A32_R1_2) {
        Serial.print("INFO_TA_ChipHandler_KinCony_KC868_A32_R1_2: OSCILLATE_PIN_SHUTDOWN: ");
        Serial.println(i);
      }
      setDO(i, false);
      continue;
    }
    while (deltaDuration > period) {  //SLIM UP DELTA INTO PERIOD
      if (INFO_TA_ChipHandler_KinCony_KC868_A32_R1_2) {
        Serial.print("INFO_TA_ChipHandler_KinCony_KC868_A32_R1_2: OSCILLATE_DELTA_DURATION_SHORTENED: ");
        Serial.println(i);
      }
      deltaDuration -= period;
    }
    if (INFO_TA_ChipHandler_KinCony_KC868_A32_R1_2) {  //PRINT SLIM DELTA
      Serial.print("INFO_TA_ChipHandler_KinCony_KC868_A32_R1_2: deltaDuration.short:");
      Serial.println(deltaDuration);
    }
    if (deltaDuration < duration) {  //IF CONTINUE AND IN DURATION
      if (INFO_TA_ChipHandler_KinCony_KC868_A32_R1_2) {
        Serial.print("INFO_TA_ChipHandler_KinCony_KC868_A32_R1_2: OSCILLATE_PIN_IN_DURATION: ");
        Serial.println(i);
      }
      if (!pinState) _setDO(i, true);
      continue;
    } else {  //IF CONTINUE AND IN GAP
      if (INFO_TA_ChipHandler_KinCony_KC868_A32_R1_2) {
        Serial.print("INFO_TA_ChipHandler_KinCony_KC868_A32_R1_2: OSCILLATE_PIN_IN_GAP: ");
        Serial.println(i);
      }
      if (pinState) _setDO(i, false);
      continue;
    }
  }
}
TA_ChipHandler_KinCony_KC868_A32_R1_2 chipHandler;

//------------------------------------ SERIAL COMMAND HANDLER FOR CHIP HANDLER (TA_ChipHandler_KinCony_KC868_A32_R1_2)------------------------------

class TA_CommandHandler_KinCony_KC868_A32_R1_2 {
public:
  TA_CommandHandler_KinCony_KC868_A32_R1_2();
  void setup();
  void loop(unsigned long currentTime);
  void setup_testMemGetAll();
  int mem_int[TA_CommandHandler_KinCony_KC868_A32_R1_2_MEM_INT_DI_COUNT + TA_CommandHandler_KinCony_KC868_A32_R1_2_MEM_INT_DO_COUNT * 2 + TA_CommandHandler_KinCony_KC868_A32_R1_2_MEM_INT_TIMER_COUNT];
private:
  bool _setup_testMemGetAll = false;
  void _forEachToken(String command, unsigned long currentTime);
  bool _IfCommandNotValid(String command);
  bool _IfThereIsNoNextToken(TA_StringTokenizer tokens, String command, String errorLabel);
  bool _IfCommand_chipHandlerName(String command, String cmdName);
  bool _IfCommand_DIGetAll(String command, String cmdName);
  bool _IfCommand_DOGetAll(String command, String cmdName);
  bool _IfCommand_MemIntGetAll(String command, String cmdName);
  bool _IfCommand_DOSetAllTrue(String command, String cmdName);
  bool _IfCommand_DOSetAllFalse(String command, String cmdName);
  bool _IfCommand_DIGetIdx(String command, String cmdName, int pin);
  bool _IfCommand_DOGetIdx(String command, String cmdName, int pin);
  bool _IfCommand_DOSetIdxTrue(String command, String cmdName, int pin);
  bool _IfCommand_DOSetIdxFalse(String command, String cmdName, int pin);
  bool _isNotValidInt(String command, String integerName, String errorLabel);
  bool _IfCommand_MemIntSetIdx(String command, String cmdName, int idx, int value);
  bool _IfCommand_MemIntSetAll(String command, String cmdName, String values);
  bool _IfCommand_DOSetIdxTrueUntil(String command, String cmdName, int pin, int duration, int gap, int count, unsigned long currentTime);
  void _error(String command, String errorLabel);
};
TA_CommandHandler_KinCony_KC868_A32_R1_2::TA_CommandHandler_KinCony_KC868_A32_R1_2() {
}
void TA_CommandHandler_KinCony_KC868_A32_R1_2::setup_testMemGetAll() {
  _setup_testMemGetAll = true;
}
void TA_CommandHandler_KinCony_KC868_A32_R1_2::loop(unsigned long currentTime) {
  //HANDLE CMD
  if (serialHandler.hasNextLine()) {
    _forEachToken(serialHandler.nextLine(), currentTime);
  }
  //MEM_INT LOAD
  for (int i = 0; i < TA_CommandHandler_KinCony_KC868_A32_R1_2_MEM_INT_DI_COUNT; i++) {
    mem_int[i] = chipHandler.getDI(i);
  }
  for (int i = TA_CommandHandler_KinCony_KC868_A32_R1_2_MEM_INT_DI_COUNT; i < TA_CommandHandler_KinCony_KC868_A32_R1_2_MEM_INT_DI_COUNT + TA_CommandHandler_KinCony_KC868_A32_R1_2_MEM_INT_DO_COUNT; i++) {
    mem_int[i] = chipHandler.getDO(i - TA_CommandHandler_KinCony_KC868_A32_R1_2_MEM_INT_DI_COUNT);
  }
  for (int i = TA_CommandHandler_KinCony_KC868_A32_R1_2_MEM_INT_DI_COUNT + TA_CommandHandler_KinCony_KC868_A32_R1_2_MEM_INT_DO_COUNT; i < TA_CommandHandler_KinCony_KC868_A32_R1_2_MEM_INT_DI_COUNT + TA_CommandHandler_KinCony_KC868_A32_R1_2_MEM_INT_DO_COUNT * 2; i++) {
    mem_int[i] = chipHandler.oscillateIs(i - TA_CommandHandler_KinCony_KC868_A32_R1_2_MEM_INT_DI_COUNT - TA_CommandHandler_KinCony_KC868_A32_R1_2_MEM_INT_DO_COUNT) ? 1 : 0;
  }
}
bool TA_CommandHandler_KinCony_KC868_A32_R1_2::_IfCommand_MemIntSetAll(String command, String cmdName, String values) {
  if (!cmdName.equals("!MEMINT_SET_ALL")) {
    return false;
  }
  TA_StringTokenizer tokensAll2(values, "-");
  int i = 0;
  while (tokensAll2.hasNext()) {
    int value = tokensAll2.nextToken().toInt();
    if (value == 0) {
      Serial.print(F("ERROR_VAL_0_NOT_VALID: "));
      Serial.println(command);
      return true;
    }
    i++;
  }
  if (i != TA_CommandHandler_KinCony_KC868_A32_R1_2_MEM_INT_TIMER_COUNT) {
    Serial.print(F("ERROR_VAL_SIZE_NOT_VALID: "));
    Serial.println(command);
    return true;
  }
  TA_StringTokenizer tokensAll(values, "-");
  i = 0;
  while (tokensAll.hasNext()) {
    mem_int[TA_CommandHandler_KinCony_KC868_A32_R1_2_MEM_INT_DI_COUNT + TA_CommandHandler_KinCony_KC868_A32_R1_2_MEM_INT_DO_COUNT * 2 + i] = tokensAll.nextToken().toInt();
    i++;
  }
  Serial.print(F("REPLY_OF:"));
  Serial.print(command);
  Serial.println(F("->DONE"));
  return true;
}
bool TA_CommandHandler_KinCony_KC868_A32_R1_2::_IfCommand_MemIntSetIdx(String command, String cmdName, int idx, int value) {
  if (!cmdName.equals("!MEMINT_SET_IDX")) {
    return false;
  }
  Serial.print(F("REPLY_OF:"));
  Serial.print(command);
  Serial.println(F("->DONE"));
  mem_int[TA_CommandHandler_KinCony_KC868_A32_R1_2_MEM_INT_DI_COUNT + TA_CommandHandler_KinCony_KC868_A32_R1_2_MEM_INT_DO_COUNT * 2 + idx] = value;
  return true;
}
bool TA_CommandHandler_KinCony_KC868_A32_R1_2::_IfCommand_DOSetIdxTrueUntil(String command, String cmdName, int pin, int duration, int gap, int count, unsigned long currentTime) {
  if (!cmdName.equals("!DO_SET_IDX_TRUE_UNTIL")) {
    return false;
  }
  Serial.print(F("REPLY_OF:"));
  Serial.print(command);
  Serial.println(F("->DONE"));
  return chipHandler.oscillateSet(pin, duration, gap, count, currentTime);
}
bool TA_CommandHandler_KinCony_KC868_A32_R1_2::_isNotValidInt(String command, String integerName, String errorLabel) {
  if (!stringHandler.isInt(integerName)) {
    Serial.print(errorLabel);
    Serial.print(F(": "));
    Serial.println(command);
    return true;
  }
  return false;
}
bool TA_CommandHandler_KinCony_KC868_A32_R1_2::_IfCommandNotValid(String command) {
  if (command.startsWith(F("!"))) {
    return false;
  }
  Serial.print(F("ERROR_CMD_NOT_VALID: "));
  Serial.println(command);
  return true;
}
bool TA_CommandHandler_KinCony_KC868_A32_R1_2::_IfThereIsNoNextToken(TA_StringTokenizer tokens, String command, String errorLabel) {
  if (tokens.hasNext()) {
    return false;
  }
  Serial.print(errorLabel);
  Serial.print(F(": "));
  Serial.println(command);
  return true;
}
bool TA_CommandHandler_KinCony_KC868_A32_R1_2::_IfCommand_chipHandlerName(String command, String cmdName) {
  if (!cmdName.equals("!CHIP_NAME")) {
    return false;
  }
  Serial.print(F("REPLY_OF:"));
  Serial.print(command);
  Serial.print(F("->"));
  Serial.println(chipHandler.name());
  return true;
}
bool TA_CommandHandler_KinCony_KC868_A32_R1_2::_IfCommand_MemIntGetAll(String command, String cmdName) {
  if (!cmdName.equals("!MEMINT_GET_ALL")) {
    return false;
  }
  Serial.print(F("REPLY_OF:"));
  Serial.print(command);
  Serial.print(F("->"));
  for (int i = 0; i < TA_CommandHandler_KinCony_KC868_A32_R1_2_MEM_INT_DI_COUNT + TA_CommandHandler_KinCony_KC868_A32_R1_2_MEM_INT_DO_COUNT * 2 + TA_CommandHandler_KinCony_KC868_A32_R1_2_MEM_INT_TIMER_COUNT; i++) {
    Serial.print(mem_int[i]);
    Serial.print(F(" "));
  }
  Serial.println(F(""));
  return true;
}
bool TA_CommandHandler_KinCony_KC868_A32_R1_2::_IfCommand_DIGetAll(String command, String cmdName) {
  if (!cmdName.equals("!DI_GET_ALL")) {
    return false;
  }
  Serial.print(F("REPLY_OF:"));
  Serial.print(command);
  Serial.print(F("->"));
  for (int i = 0; i < 32; i++) {
    Serial.print(chipHandler.getDI(i));
  }
  Serial.println(F(""));
  return true;
}
bool TA_CommandHandler_KinCony_KC868_A32_R1_2::_IfCommand_DOGetAll(String command, String cmdName) {
  if (!cmdName.equals("!DO_GET_ALL")) {
    return false;
  }
  Serial.print(F("REPLY_OF:"));
  Serial.print(command);
  Serial.print(F("->"));
  for (int i = 0; i < 32; i++) {
    Serial.print(chipHandler.getDO(i));
  }
  Serial.println(F(""));
  return true;
}
bool TA_CommandHandler_KinCony_KC868_A32_R1_2::_IfCommand_DOSetAllTrue(String command, String cmdName) {
  if (!cmdName.equals("!DO_SET_ALL_TRUE")) {
    return false;
  }
  Serial.print(F("REPLY_OF:"));
  Serial.print(command);
  Serial.print(F("->"));
  bool result = true;
  for (int i = 0; i < 32; i++) {
    bool innerResult = chipHandler.setDO(i, true);
    result = result && innerResult;
  }
  Serial.println(result ? F("->DONE") : F("->SKIPPED"));
  return true;
}
bool TA_CommandHandler_KinCony_KC868_A32_R1_2::_IfCommand_DOSetAllFalse(String command, String cmdName) {
  if (!cmdName.equals("!DO_SET_ALL_FALSE")) {
    return false;
  }
  Serial.print(F("REPLY_OF:"));
  Serial.print(command);
  Serial.print(F("->"));
  bool result = true;
  for (int i = 0; i < 32; i++) {
    result = result && chipHandler.setDO(i, false);
  }
  Serial.println(result ? F("->DONE") : F("->SKIPPED"));
  return true;
}
bool TA_CommandHandler_KinCony_KC868_A32_R1_2::_IfCommand_DIGetIdx(String command, String cmdName, int pin) {
  if (!cmdName.equals("!DI_GET_IDX")) {
    return false;
  }
  Serial.print(F("REPLY_OF:"));
  Serial.print(command);
  Serial.print(F("->"));
  Serial.println(chipHandler.getDI(pin));
  return true;
}
bool TA_CommandHandler_KinCony_KC868_A32_R1_2::_IfCommand_DOGetIdx(String command, String cmdName, int pin) {
  if (!cmdName.equals("!DO_GET_IDX")) {
    return false;
  }
  Serial.print(F("REPLY_OF:"));
  Serial.print(command);
  Serial.print(F("->"));
  Serial.println(chipHandler.getDO(pin));
  return true;
}
void TA_CommandHandler_KinCony_KC868_A32_R1_2::_error(String command, String errorLabel) {
  Serial.print(errorLabel);
  Serial.print(F(":"));
  Serial.println(command);
}
bool TA_CommandHandler_KinCony_KC868_A32_R1_2::_IfCommand_DOSetIdxFalse(String command, String cmdName, int pin) {
  if (!cmdName.equals("!DO_SET_IDX_FALSE")) {
    return false;
  }
  Serial.print(F("REPLY_OF:"));
  Serial.print(command);
  Serial.print(F("->"));
  if (chipHandler.setDO(pin, false)) {
    Serial.println(F("DONE"));
  } else {
    Serial.println(F("SKIPPED"));
  }
  return true;
}
bool TA_CommandHandler_KinCony_KC868_A32_R1_2::_IfCommand_DOSetIdxTrue(String command, String cmdName, int pin) {
  if (!cmdName.equals("!DO_SET_IDX_TRUE")) {
    return false;
  }
  Serial.print(F("REPLY_OF:"));
  Serial.print(command);
  Serial.print(F("->"));
  if (chipHandler.setDO(pin, true)) {
    Serial.println(F("DONE"));
  } else {
    Serial.println(F("SKIPPED"));
  }
  return true;
}
void TA_CommandHandler_KinCony_KC868_A32_R1_2::setup() {
  if (INFO_TA_CommandHandler_KinCony_KC868_A32_R1_2) {
    Serial.println(F("USAGE: GENERAL------------------------------------------"));
    Serial.println(F("USAGE: getChipName as (cmd) ex: !CHIP_NAME"));
    Serial.println(F("USAGE: DIGITAL IN GET-----------------------------------"));
    Serial.println(F("USAGE: getDigitalInAll as (cmd) ex: !DI_GET_ALL"));
    Serial.println(F("USAGE: getDigitalInIdx as (cmd, pin1-32) ex: !DI_GET_IDX 1"));
    Serial.println(F("USAGE: DIGITAL OUT GET----------------------------------"));
    Serial.println(F("USAGE: getDigitalOutAll as (cmd) ex: !DO_GET_ALL"));
    Serial.println(F("USAGE: getDigitalOutIdx as (cmd, pin1-32) ex: !DO_GET_IDX 1"));
    Serial.println(F("USAGE: DIGITAL OUT SET----------------------------------"));
    Serial.println(F("USAGE: setDigitalOutAllAsTrue as (cmd) ex: !DO_SET_ALL_TRUE"));
    Serial.println(F("USAGE: setDigitalOutAllAsFalse as (cmd) ex: !DO_SET_ALL_FALSE"));
    Serial.println(F("USAGE: setDigitalOutIdxTrue as (cmd, pin1-32) ex: !DO_SET_IDX_TRUE 1"));
    Serial.println(F("USAGE: setDigitalOutIdxFalse as (cmd, pin1-32) ex: !DO_SET_IDX_FALSE 1"));
    Serial.println(F("USAGE: DIGITAL OUT OSCILLATE---------------------------"));
    Serial.println(F("USAGE: setDigitalOutOscillating as (cmd, pin1-32, secDuration, secGap, count) ex: !DO_SET_IDX_TRUE_UNTIL 12 2 1 5"));
    Serial.println(F("USAGE: MEMORY-------------------------------------------"));
    Serial.println(F("USAGE: getMemIntAll as (cmd) ex: !MEMINT_GET_ALL"));
    Serial.println(F("USAGE: setMemIntIdx as (cmd, idx, secDuration) ex: !MEMINT_SET_IDX 5 2"));
  }
}
void TA_CommandHandler_KinCony_KC868_A32_R1_2::_forEachToken(String command, unsigned long currentTime) {
  if (_IfCommandNotValid(command)) return;
  TA_StringTokenizer tokens(command, F(" "));
  if (_IfThereIsNoNextToken(tokens, command, F("ERROR_CMD_UNCOMPLETE"))) return;
  String cmdName = tokens.nextToken();
  if (INFO_TA_CommandHandler_KinCony_KC868_A32_R1_2) {
    Serial.print("INFO_TA_CommandHandler_KinCony_KC868_A32_R1_2:cmdName:");
    Serial.println(cmdName);
  }
  if (_IfCommand_MemIntGetAll(command, cmdName)) return;
  if (_setup_testMemGetAll) {
    return;
  }
  if (_IfCommand_chipHandlerName(command, cmdName)) return;
  if (_IfCommand_DIGetAll(command, cmdName)) return;
  if (_IfCommand_DOGetAll(command, cmdName)) return;
  if (_IfCommand_DOSetAllTrue(command, cmdName)) return;
  if (_IfCommand_DOSetAllFalse(command, cmdName)) return;
  if (_IfThereIsNoNextToken(tokens, command, F("ERROR_CMD_PIN_NAME_UNCOMPLETE"))) return;
  String pinOrIdxName = tokens.nextToken();
  if (_IfCommand_MemIntSetAll(command, cmdName, pinOrIdxName)) return;
  int pinOrIdx = pinOrIdxName.toInt();
  if (INFO_TA_CommandHandler_KinCony_KC868_A32_R1_2) {
    Serial.print("INFO_TA_CommandHandler_KinCony_KC868_A32_R1_2:pinOrIdx:");
    Serial.println(pinOrIdx);
  }
  if (_IfCommand_DIGetIdx(command, cmdName, pinOrIdx)) return;
  if (_IfCommand_DOGetIdx(command, cmdName, pinOrIdx)) return;
  if (_IfCommand_DOSetIdxFalse(command, cmdName, pinOrIdx)) return;
  if (_IfCommand_DOSetIdxTrue(command, cmdName, pinOrIdx)) return;
  if (_IfThereIsNoNextToken(tokens, command, F("ERROR_CMD_DURATION_NAME_UNCOMPLETE"))) return;
  String durationName = tokens.nextToken();
  if (_isNotValidInt(command, durationName, F("ERROR_CMD_DURATION_NAME_NOT_INT"))) return;
  int duration = durationName.toInt();
  if (INFO_TA_CommandHandler_KinCony_KC868_A32_R1_2) {
    Serial.print("INFO_TA_CommandHandler_KinCony_KC868_A32_R1_2:duration:");
    Serial.println(duration);
  }
  if (_IfCommand_MemIntSetIdx(command, cmdName, pinOrIdx, duration)) return;
  if (_IfThereIsNoNextToken(tokens, command, F("ERROR_CMD_GAP_NAME_UNCOMPLETE"))) return;
  String gapName = tokens.nextToken();
  if (_isNotValidInt(command, gapName, F("ERROR_CMD_GAP_NAME_NOT_INT"))) return;
  int gap = gapName.toInt();
  if (INFO_TA_CommandHandler_KinCony_KC868_A32_R1_2) {
    Serial.print("INFO_TA_CommandHandler_KinCony_KC868_A32_R1_2:gap:");
    Serial.println(gap);
  }
  if (_IfThereIsNoNextToken(tokens, command, F("ERROR_CMD_COUNT_NAME_UNCOMPLETE"))) return;
  String countName = tokens.nextToken();
  if (_isNotValidInt(command, countName, F("ERROR_CMD_COUNT_NAME_NOT_INT"))) return;
  int count = countName.toInt();
  if (INFO_TA_CommandHandler_KinCony_KC868_A32_R1_2) {
    Serial.print("INFO_TA_CommandHandler_KinCony_KC868_A32_R1_2:count:");
    Serial.println(count);
  }
  if (_IfCommand_DOSetIdxTrueUntil(command, cmdName, pinOrIdx, duration, gap, count, currentTime)) return;
  _error(command, F("ERROR_CMD_NOT_SUPPORTED"));
}
TA_CommandHandler_KinCony_KC868_A32_R1_2 commandHandler;


//------------------------------------ SURFACE TREATMEMT BATH 16 FOR SERIAL COMMAND HANDLER FOR CHIP HANDLER (TA_ChipHandler_KinCony_KC868_A32_R1_2)------------------------------

//DI 1, 3, 5...31: sensor that detect sth in the bath
//DI 2, 4, 6...32: manual start(with timer)/stop(stop the alarm)
//DO 1, 3, 5...31: timer is running
//DO 2, 4, 6...32: alarm until [stop triggered] or [sth not in the bath anymore]
class TA_SurfaceTreatmentBath16 {
public:
  TA_SurfaceTreatmentBath16();
  void loop(unsigned long currentTime);
private:
  enum STATE {
    TIMER_RUNNING_BY_BUTTON,
    TIMER_RUNNING_BY_SENSOR,
    ALARM_RUNNING_BY_BUTTON,
    ALARM_RUNNING_BY_SENSOR,
    STOPPED
  };
  unsigned long _startTime[16];
  STATE _state[16];
};
TA_SurfaceTreatmentBath16::TA_SurfaceTreatmentBath16() {
  for (int bath = 0 + TA_CommandHandler_KinCony_KC868_A32_R1_2_MEM_INT_DI_COUNT + TA_CommandHandler_KinCony_KC868_A32_R1_2_MEM_INT_DO_COUNT * 2; bath < TA_CommandHandler_KinCony_KC868_A32_R1_2_MEM_INT_DI_COUNT + TA_CommandHandler_KinCony_KC868_A32_R1_2_MEM_INT_DO_COUNT * 2 + TA_CommandHandler_KinCony_KC868_A32_R1_2_MEM_INT_TIMER_COUNT; bath++) {
    _state[bath] = STOPPED;
    commandHandler.mem_int[bath] = TA_SurfaceTreatmentBath16_TIMER_DEFAULT_VALUE;
  }
}
void TA_SurfaceTreatmentBath16::loop(unsigned long currentTime) {
  for (int i = 0; i < 1; i += 2) {
    int bath = i / 2;
    int pin = i;       //ON PROCESS SENSOR AND LIGHT
    int pin2 = i + 1;  //START STOP BUTTON AND ALARM
    //PARAMS
    bool sensorActivePrev = chipHandler.getButtonPrevious(pin);
    bool sensorActiveCur = chipHandler.getButtonCurrent(pin);
    bool buttonActivePrev = chipHandler.getButtonPrevious(pin2);
    bool buttonActiveCur = chipHandler.getButtonCurrent(pin2);
    bool sensorActive = sensorActivePrev && sensorActivePrev;
    bool buttonReleased = buttonActivePrev && !buttonActiveCur;
    bool onProcessOrSensorActive = _state[bath] == TIMER_RUNNING_BY_BUTTON || _state[bath] == TIMER_RUNNING_BY_SENSOR || _state[bath] == ALARM_RUNNING_BY_BUTTON;
    if (INFO_TA_SurfaceTreatmentBath16) {
      Serial.print("sap:");
      Serial.print(sensorActivePrev);
      Serial.print(", sac:");
      Serial.print(sensorActiveCur);
      Serial.print(", bap:");
      Serial.print(buttonActivePrev);
      Serial.print(", bac:");
      Serial.print(buttonActiveCur);
      Serial.print(", sa:");
      Serial.print(sensorActive);
      Serial.print(", br:");
      Serial.print(buttonReleased);
      Serial.print(", op|sa:");
      Serial.print(onProcessOrSensorActive);
      Serial.print(", st:");
      if (_state[bath] == STOPPED) {
        Serial.println("STOPPED");
      } else if (_state[bath] == TIMER_RUNNING_BY_BUTTON) {
        Serial.println("TIMER_RUNNING_BY_BUTTON");
      } else if (_state[bath] == TIMER_RUNNING_BY_SENSOR) {
        Serial.println("TIMER_RUNNING_BY_SENSOR");
      } else if (_state[bath] == ALARM_RUNNING_BY_BUTTON) {
        Serial.println("ALARM_RUNNING_BY_BUTTON");
      } else if (_state[bath] == ALARM_RUNNING_BY_SENSOR) {
        Serial.println("ALARM_RUNNING_BY_SENSOR");
      }
    }

    //ON PROCESSS, BATH LIGHT INDICATOR
    if (chipHandler.getDO(pin) != onProcessOrSensorActive) chipHandler.setDO(pin, onProcessOrSensorActive);

    //STOPPED->TIMER_RUNNING
    if (_state[bath] == STOPPED) {
      if (buttonReleased) {
        if (INFO_TA_SurfaceTreatmentBath16) Serial.println("STOPPED.buttonReleased->TIMER_RUNNING_BY_BUTTON");
        _startTime[bath] = currentTime;
        _state[bath] = TIMER_RUNNING_BY_BUTTON;
        continue;
      } else if (sensorActive) {
        if (INFO_TA_SurfaceTreatmentBath16) Serial.println("STOPPED.buttonReleased->TIMER_RUNNING_BY_SENSOR");
        _startTime[bath] = currentTime;
        _state[bath] = TIMER_RUNNING_BY_SENSOR;
        continue;
      }
      continue;
    }
    //TIMER_RUNNING.currentTime?.STOP|NOT_YET|ALARM
    if (_state[bath] == TIMER_RUNNING_BY_BUTTON || _state[bath] == TIMER_RUNNING_BY_SENSOR) {
      if (_state[bath] == TIMER_RUNNING_BY_SENSOR && !sensorActive) {
        if (INFO_TA_SurfaceTreatmentBath16) Serial.println("TIMER_RUNNING_BY_SENSOR.!sensorActive->STOPPED");
        chipHandler.setDO(pin2, false);
        _state[bath] = STOPPED;
        continue;
      } else if (_state[bath] == TIMER_RUNNING_BY_BUTTON && buttonReleased) {
        if (INFO_TA_SurfaceTreatmentBath16) Serial.println("TIMER_RUNNING_BY_BUTTON.buttonReleased->STOPPED");
        chipHandler.setDO(pin2, false);
        _state[bath] = STOPPED;
        continue;
      } else if ((_startTime[bath] + commandHandler.mem_int[bath] * 1000) > currentTime) {  //NOT_YET
        if (INFO_TA_SurfaceTreatmentBath16) Serial.println("TIMER_RUNNING.x>currentTime->NOT_YET");
        continue;
      } else {
        if (INFO_TA_SurfaceTreatmentBath16) Serial.println("TIMER_RUNNING.x<=currentTime->ALARM");
        _state[bath] = _state[bath] == TIMER_RUNNING_BY_BUTTON ? ALARM_RUNNING_BY_BUTTON : ALARM_RUNNING_BY_SENSOR;
        continue;
      }
    }
    //ALARM->STOP|RING
    if (_state[bath] == ALARM_RUNNING_BY_SENSOR || _state[bath] == ALARM_RUNNING_BY_BUTTON) {
      if (_state[bath] == ALARM_RUNNING_BY_SENSOR && !sensorActive) {
        if (INFO_TA_SurfaceTreatmentBath16) Serial.println("ALARM.!sensorActive->STOPPED");
        chipHandler.setDO(pin2, false);
        _state[bath] = STOPPED;
        continue;
      } else if (_state[bath] == ALARM_RUNNING_BY_BUTTON && buttonReleased) {
        if (INFO_TA_SurfaceTreatmentBath16) Serial.println("ALARM.buttonReleased->STOPPED");
        chipHandler.setDO(pin2, false);
        _state[bath] = STOPPED;
        continue;
      } else {
        if (INFO_TA_SurfaceTreatmentBath16) {
          Serial.print("ALARM.RINGER:");
          Serial.println(_state[bath] == ALARM_RUNNING_BY_BUTTON ? "ALARM_RUNNING_BY_BUTTON" : "ALARM_RUNNING_BY_SENSOR");
        }
        if (!chipHandler.oscillateIs(pin2)) {
          chipHandler.oscillateSet(pin2, 1, 2, 9999, currentTime);
        }
      }
    }
  }
}
TA_SurfaceTreatmentBath16 surfaceTreatmentBath16;

//------------------------------------ PROGRAM -----------------------------------------------------------------------

//GLOBALS SO FAR
//TA_stringHandler stringHandler;
//TA_TimeHandler timeHandler;
//TA_SerialHandler serialHandler(TA_SerialHandler_WAIT_UNTIL_CONNECTION, TA_SerialHandler_WAIT_IN_BAUDRATE);
//TA_SerialHandler serialHandler;
//TA_ChipHandler_KinCony_KC868_A32_R1_2 chipHandler;
//TA_CommandHandler_KinCony_KC868_A32_R1_2 commandHandler;
//TA_SurfaceTreatmentBath16 surfaceTreatmentBath16;

//ARDUINO_MAIN
void setup() {
  serialHandler.setup();
  chipHandler.setup();
  commandHandler.setup();
  //commandHandler.setup_testMemGetAll();
  Serial.println(F("INFO setup->loop"));
}

//ARDUINO_THREAD
void loop() {
  unsigned long curTime = timeHandler.loop();
  chipHandler.loop(curTime);
  //chipHandler.loop_testButtons();
  commandHandler.loop(curTime);
  surfaceTreatmentBath16.loop(curTime);
}


     */
}
