package com.example.drones;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class DronesApplicationTests {


    @Mock
    private List<Drone> drones;

    @Mock
    private Map<String, List<Medication>> loadedMedications;

    @InjectMocks
    private DroneServiceImpl droneDataManager;

    @Test
    public void testRegisterDrone() {
        Drone drone = new Drone("001", DroneModel.CRUISERWEIGHT, 500, 100, DroneState.LOADED, null);
        droneDataManager.registerDrone(drone);
        verify(drones).add(drone);
    }

    @Test
    public void testLoadDrone() {
        String droneSerialNumber = "001";
        List<Medication> medications = Arrays.asList(
                new Medication("Med1", 100, "CODE1", "image1.png", null),
                new Medication("Med2", 200, "CODE2", "image2.png", null)
        );
        droneDataManager.loadDrone(medications);
        verify(loadedMedications).put(droneSerialNumber, medications);
//        verify(droneService).updateDroneState(droneSerialNumber, "LOADED");
    }

    @Test
    public void testGetLoadedMedications() {
        String droneSerialNumber = "001";
        List<Medication> medications = Arrays.asList(
                new Medication("Med1", 100, "CODE1", "image1.png", null),
                new Medication("Med2", 200, "CODE2", "image2.png", null)
        );
        when(loadedMedications.get(droneSerialNumber)).thenReturn(medications);
        List<Medication> result = droneDataManager.getLoadedMedications(droneSerialNumber);
        assertEquals(medications, result);
    }

    @Test
    public void testGetAvailableDrones() {
        List<Drone> allDrones = Arrays.asList(
                new Drone("001", DroneModel.LIGHTWEIGHT, 500, 100,  DroneState.IDLE, null),
                new Drone("002", DroneModel.MIDDLEWEIGHT, 500, 50, DroneState.LOADED, null),
                new Drone("003", DroneModel.CRUISERWEIGHT, 500, 75, DroneState.IDLE, null),
                new Drone("004", DroneModel.HEAVYWEIGHT, 500, 25, DroneState.RETURNING, null)
        );
        when(drones.iterator()).thenReturn(allDrones.iterator());
        List<Drone> expectedDrones = Arrays.asList(
                new Drone("001", DroneModel.LIGHTWEIGHT, 500, 100,  DroneState.IDLE, null),
                new Drone("003", DroneModel.CRUISERWEIGHT, 500, 75, DroneState.IDLE, null)
        );
        List<Drone> result = droneDataManager.getAvailableDrones();
        assertEquals(expectedDrones, result);
    }

    @Test
    public void testGetDroneBatteryLevel() {
        String droneSerialNumber = "001";
        // TODO: mock getting drone battery level from the actual drone
        int expectedBatteryLevel = 80;
        int result = droneDataManager.getDroneBatteryLevel(droneSerialNumber);
        assertEquals(expectedBatteryLevel, result);
    }
}
