package com.epam.spring.core.model;

public class Order {

	private int id;
	
	private User user;
	
	private Event event;
	
	private int seat;
	
	private int price;

	public Order() {
	}

	public Order(User user, Event event, int seat, int price) {
		this.user = user;
		this.event = event;
		this.seat = seat;
		this.price = price;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSeat() {
		return seat;
	}

	public void setSeat(int seat) {
		this.seat = seat;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + "\n user=" + user + "\n event=" + event + "\n seat=" + seat + "\n price=" + price + "]";
	}

	/**
	 * Override method without User and Price fields
	 * 
	 * */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((event == null) ? 0 : event.hashCode());
		result = prime * result + seat;
		return result;
	}

	/**
	 * Override method without User and Price fields
	 * 
	 * */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		if (event == null) {
			if (other.event != null)
				return false;
		} else if (!event.equals(other.event))
			return false;
		if (seat != other.seat)
			return false;
		return true;
	}
	
}
