package com.aa.act.interview.org;

import java.util.Optional;

public abstract class Organization {

	private Position root;
	
	public Organization() {
		root = createOrganization();
	}
	
	protected abstract Position createOrganization();
	
	/**
	 * hire the given person as an employee in the position that has that title
	 * 
	 * @param person
	 * @param title
	 * @return the newly filled position or empty if no position has that title
	 */
	public Optional<Position> checkPos (Position pos, String title) {
		Optional<Position> posMatch = Optional.of(pos);
		// check if positions match
		if (posMatch.get().getTitle().equalsIgnoreCase(title)) return posMatch;
		else {

			for (Position p : pos.getDirectReports()) {
				posMatch = checkPos(p, title);
				if (posMatch.isPresent()) return posMatch;
			}
		}

		return Optional.empty();
	}

	public void hire (Name person, String title) {
		Optional<Position> position = checkPos(root, title);
		Random rand = new Random();
		int employeeId = rand.nextInt(20);
		// if the position exists, then assign the employee to the position
		position.ifPresent(value -> value.setEmployee(Optional.of(new Employee(employeeId, person))));
	}
	@Override
	public String toString() {
		return printOrganization(root, "");
	}
	
	private String printOrganization(Position pos, String prefix) {
		StringBuffer sb = new StringBuffer(prefix + "+-" + pos.toString() + "\n");
		for(Position p : pos.getDirectReports()) {
			sb.append(printOrganization(p, prefix + "\t"));
		}
		return sb.toString();
	}
}
