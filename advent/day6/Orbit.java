package advent.day6;

public class Orbit {

	public Orbit orbitsAround;
	public String label;

	public Orbit(){
		this.orbitsAround = null;
		this.label = "COM";
	}

	public Orbit(String label, Orbit orbital){
		this.orbitsAround = orbital;
		this.label = label;
	}

	public int getOrbitalAmount(){
		return getLengthOfPath(OrbitFactory.getInstance().getNewOrbit("COM"));
	}

	public int getLengthOfPath(Orbit toOrbit){
		Orbit currentOrbit = this;
		int sum = 0;
		while(currentOrbit.orbitsAround != null && currentOrbit != toOrbit){
			sum++;
			currentOrbit = currentOrbit.orbitsAround;
		}
		return sum;
	}

	@Override
	public String toString() {
		return "Orbit: " + label + " Around: " + (this.orbitsAround == null ? "null" : orbitsAround.label);
	}

	@Override
	public boolean equals(Object obj) {
		Orbit orb = (Orbit) obj;
		return this.label.equals(orb.label);
	}
}
