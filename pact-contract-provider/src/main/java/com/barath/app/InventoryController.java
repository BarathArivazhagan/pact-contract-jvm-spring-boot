package com.barath.app;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class InventoryController {
	
	private final InventoryService inventoryService;
	
	
	public InventoryController(InventoryService inventoryService) {
		this.inventoryService=inventoryService;
	}
	
	@PostMapping(value="/inventory",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Inventory saveInventory(@RequestBody Inventory inventory){
		return inventoryService.saveInventory(inventory);
	}

}
