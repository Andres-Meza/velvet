package com.uniminuto.velvet.service.interfaces;

import com.uniminuto.velvet.model.dto.LocationDTO;

import java.util.List;

public interface LocationService {
	LocationDTO.SimpleLocation createLocation(LocationDTO.CreateLocation createLocation);
	LocationDTO.SimpleLocation updateLocation(LocationDTO.UpdateLocation updateLocation);
	LocationDTO.DetailsLocation getLocationById(Long id);
	List<LocationDTO.SimpleLocation> getAllLocations();
	List<LocationDTO.DetailsLocation> getAllLocationsWithDetails();
	boolean deleteLocation(Long id);
	boolean existsByName(String name);
}