package model;

public class Patient {
	private String name;
	private String lastName;
	private String phone;
	private Status status;

	public Patient() {

	}

	public Patient(String name, String lastName, String phone, Status status) {
		super();
		this.name = name;
		this.lastName = lastName;
		this.phone = phone;
		this.status = status;
	}

	public Patient(String name, String lastName, String phone) {
		super();
		this.name = name;
		this.lastName = lastName;
		this.phone = phone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Patient (name=" + name + ", lastName=" + lastName + ", phone=" + phone + ", status=" + status + ")";
	}

}
