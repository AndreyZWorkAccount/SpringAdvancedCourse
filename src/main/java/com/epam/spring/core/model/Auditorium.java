package com.epam.spring.core.model;

import java.util.List;

public class Auditorium {

	private int id;
	
	private String name;
	
	private int seatsCount;
	
	private List<Integer> vipSeats;

	public Auditorium() {
	}

	public Auditorium(String name, int seatsCount, List<Integer> vipSeats) {
		this.name = name;
		this.seatsCount = seatsCount;
		this.vipSeats = vipSeats;
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

	public int getSeatsCount() {
		return seatsCount;
	}

	public void setSeatsCount(int seatsCount) {
		this.seatsCount = seatsCount;
	}

	public List<Integer> getVipSeats() {
		return vipSeats;
	}

	public void setVipSeats(List<Integer> vipSeats) {
		this.vipSeats = vipSeats;
	}

	@Override
	public String toString() {
		return "Auditorium {id=" + id + ", name=" + name + ", seatsCount=" + seatsCount + ", vipSeats=" + vipSeats
				+ "}";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + seatsCount;
		result = prime * result + ((vipSeats == null) ? 0 : vipSeats.hashCode());
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
		Auditorium other = (Auditorium) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (seatsCount != other.seatsCount)
			return false;
		if (vipSeats == null) {
			if (other.vipSeats != null)
				return false;
		} else if (!vipSeats.equals(other.vipSeats))
			return false;
		return true;
	}
	
}
