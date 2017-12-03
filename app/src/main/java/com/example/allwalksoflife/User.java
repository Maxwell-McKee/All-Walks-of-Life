package com.example.allwalksoflife;

public class User {
    private String  username,
                    location,
                    type,
                    fastestRun,
                    furthestRun,
                    longestRun;
    private int age;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFastestRun() {
        return fastestRun;
    }

    public void setFastestRun(String fastestRun) {
        this.fastestRun = fastestRun;
    }

    public String getFurthestRun() {
        return furthestRun;
    }

    public void setFurthestRun(String furthestRun) {
        this.furthestRun = furthestRun;
    }

    public String getLongestRun() {
        return longestRun;
    }

    public void setLongestRun(String longestRun) {
        this.longestRun = longestRun;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public User() {
        this.username = "User";
        this.location = "No Location";
        this.type = "Runner";
        this.fastestRun = "";
        this.furthestRun = "";
        this.longestRun = "";
        this.age = -1;
    }

    public User(String username, String location, String type, String fastestRun, String furthestRun, String longestRun, int age) {
        this.username = username;
        this.location = location;
        this.type = type;
        this.fastestRun = fastestRun;
        this.furthestRun = furthestRun;
        this.longestRun = longestRun;
        this.age = age;
    }
}
