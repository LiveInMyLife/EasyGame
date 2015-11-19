package com.easyplay.easygame.model;

import cn.bmob.v3.BmobObject;

public class GameInfo extends BmobObject {
  private static final long serialVersionUID = 1L;
  private static final String TABLE_NAME = "easy_game_game";

  public String getGameName() {
    return gameName;
  }

  public void setGameName(String gameName) {
    this.gameName = gameName;
  }

  public String getGameSpell() {
    return gameSpell;
  }

  public void setGameSpell(String gameSpell) {
    this.gameSpell = gameSpell;
  }

  public String getHotWeight() {
    return hotWeight;
  }

  public void setHotWeight(String hotWeight) {
    this.hotWeight = hotWeight;
  }

  public String getGameLogo() {
    return gameLogo;
  }

  public void setGameLogo(String gameLogo) {
    this.gameLogo = gameLogo;
  }

  private String gameName;
  private String gameSpell;
  private String hotWeight;
  private String gameLogo;

  public GameInfo() {
    this.setTableName(TABLE_NAME);
  }

}
