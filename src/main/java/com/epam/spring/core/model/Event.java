package com.epam.spring.core.model;

import java.util.Date;

public class Event {

	private int id;
	
	private String name;
	
	private Date startDate;
	
	private Date endDate;
	
	private int price;
	
	private int ticketPrice;
	
	private Rating rating;

	public Event() {

	}

    public Event(String name, Date startDate, Date endDate, int price, int ticketPrice, Rating rating) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
        this.ticketPrice = ticketPrice;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + price;
        result = prime * result + ((rating == null) ? 0 : rating.hashCode());
        result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Event other = (Event) obj;
        if (endDate == null) {
            if (other.endDate != null)
                return false;
        } else if (!endDate.equals(other.endDate))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (price != other.price)
            return false;
        if (rating != other.rating)
            return false;
        if (startDate == null) {
            if (other.startDate != null)
                return false;
        } else if (!startDate.equals(other.startDate))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Event [id=" + id + ", name=" + name + ", startDate=" + startDate + ", endDate=" + endDate + ", price="
                + price + ", ticketPrice=" + ticketPrice + ", rating=" + rating + "]";
    }

	
	
}
