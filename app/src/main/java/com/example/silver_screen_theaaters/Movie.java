package com.example.silver_screen_theaaters;

public class Movie {

    private  String title, type_seat, type_cost, time;
    private  int tickets;

    public Movie(String title, String type_seat, String type_cost, String time, int tickets) {
        this.title = title;
        this.type_seat = type_seat;
        this.type_cost = type_cost;
        this.time = time;
        this.tickets = tickets;
    }

    public Movie(String title){
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType_seat() {
        return type_seat;
    }

    public void setType_seat(String type_seat) {
        this.type_seat = type_seat;
    }

    public String getType_cost() {
        return type_cost;
    }

    public void setType_cost(String type_cost) {
        this.type_cost = type_cost;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getTickets() {
        return tickets;
    }

    public void setTickets(int tickets) {
        this.tickets = tickets;
    }
}
