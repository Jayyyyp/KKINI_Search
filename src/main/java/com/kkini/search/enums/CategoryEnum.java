package com.kkini.search.enums;
public enum CategoryEnum {
    KKINI(1, "KKINI"),
    KKINI_GREEN(2, "KKINI_Green"),
    FOOD(3, "음식"),
    NOTASTE(4, "맛없는거"),
    GRASS(5, "풀"),
    CHICKEN(6, "닭");

    private final int id;
    private final String name;

    CategoryEnum(int id, String name){
        this.id = id;
        this.name = name;
    }
    public int getId(){
        return id;
    }
    public String getName(){
        return name;
    }
}