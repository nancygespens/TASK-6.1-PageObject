package ru.netology.web.data;

import lombok.Value;

import java.util.Arrays;
import java.util.List;

public class DataHelper {
  private DataHelper() {
  }

  @Value
  public static class AuthInfo {
    private String login;
    private String password;
  }

  public static AuthInfo getAuthInfo() {
    return new AuthInfo("vasya", "qwerty123");
  }

  @Value
  public static class VerificationCode {
    private String code;
  }

  public static VerificationCode getVerificationCodeFor(AuthInfo authInfo) {
    return new VerificationCode("12345");
  }

  @Value
  public static class CardNumber {
    private String CardNumber1 = "5559 0000 0000 0001";
    private String CardNumber2 = "5559 0000 0000 0002";
  }
  public static String getNumberCard1() {

    CardNumber number = new CardNumber();
    String numberCard1 = number.CardNumber1;
    return numberCard1;
  }

  public static String getNumberCard2() {

    CardNumber number = new CardNumber();
    String numberCard2 = number.CardNumber2;
    return numberCard2;
  }

  @Value
  public static class CardsIdList {
    List cardIdList = Arrays.asList("92df3f1c-a033-48e6-8390-206f6b1f56c0", "0f3f5c2a-249e-4c3d-8287-09f7a039391d");
  }

  public static String getIdCard1() {
    String idCard1;
    CardsIdList numbersList = new CardsIdList();
    idCard1 = numbersList.cardIdList.get(0).toString();
    return idCard1;
  }

  public static String getIdCard2() {
    String idCard2;
    CardsIdList numbersList = new CardsIdList();
    idCard2 = numbersList.cardIdList.get(1).toString();
    return idCard2;
  }
}



