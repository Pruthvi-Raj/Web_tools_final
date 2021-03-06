package neu.edu.entity;
// Generated Apr 28, 2017 7:49:59 AM by Hibernate Tools 5.2.1.Final

import java.util.HashSet;
import java.util.Set;

/**
 * Category generated by hbm2java
 */
public class Category implements java.io.Serializable {

	private Integer id;
	private String name;
	private String description;
	private Byte isEnabled;
	private Byte isDeleted;
	private Set<Projects> projectses = new HashSet<Projects>(0);

	public Category() {
	}

	public Category(String name, String description, Byte isEnabled, Byte isDeleted, Set<Projects> projectses) {
		this.name = name;
		this.description = description;
		this.isEnabled = isEnabled;
		this.isDeleted = isDeleted;
		this.projectses = projectses;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Byte getIsEnabled() {
		return this.isEnabled;
	}

	public void setIsEnabled(Byte isEnabled) {
		this.isEnabled = isEnabled;
	}

	public Byte getIsDeleted() {
		return this.isDeleted;
	}

	public void setIsDeleted(Byte isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Set<Projects> getProjectses() {
		return this.projectses;
	}

	public void setProjectses(Set<Projects> projectses) {
		this.projectses = projectses;
	}

}
