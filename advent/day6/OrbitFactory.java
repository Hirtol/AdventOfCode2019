package advent.day6;

import java.util.ArrayList;
import java.util.List;

public class OrbitFactory {

	public List<Orbit> orbits;
	private static OrbitFactory instance;

	private OrbitFactory(){
		orbits = new ArrayList<>();
		orbits.add(new Orbit());
	}

	public static OrbitFactory getInstance(){
		if(instance == null)
			instance = new OrbitFactory();
		return instance;
	}

	public Orbit getOrbitFromName(String label){
		return getNewOrbit(label).orbitsAround;
	}

	public Orbit getNewOrbit(String label){
		return orbits.stream().filter(orb -> orb.label.equals(label)).findFirst().orElse(new Orbit(label, null));
	}

	public Orbit getNewOrbit(String label, Orbit orbitsAround){
		return new Orbit(label, orbitsAround);
	}

	public void addOrbit(Orbit orbit){
		if(orbits.stream().anyMatch(orb -> orb.equals(orbit)))
			updateOrbit(orbits.get(orbits.indexOf(orbit)), orbit.orbitsAround);
		else
			orbits.add(orbit);
	}

	public void updateOrbit(Orbit orbit, Orbit orbitsAroundNew){
		if(orbitsAroundNew != null) {
			orbit.orbitsAround = orbitsAroundNew;
		}
	}

}
