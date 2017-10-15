package com.barath.app;

public class Inventory {
	
	private Long inventoryId;
	
	private String productName;
	
	private String locationName;
	
	private int quantity;

	public Long getInventoryId() {
		return inventoryId;
	}

	public void setInventoryId(Long inventoryId) {
		this.inventoryId = inventoryId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Inventory(Long inventoryId, String productName, String locationName, int quantity) {
		super();
		this.inventoryId = inventoryId;
		this.productName = productName;
		this.locationName = locationName;
		this.quantity = quantity;
	}

	public Inventory() {
		super();
		
	}

	@Override
	public String toString() {
		return "Inventory [inventoryId=" + inventoryId + ", productName=" + productName + ", locationName="
				+ locationName + ", quantity=" + quantity + "]";
	}
	
	

}
