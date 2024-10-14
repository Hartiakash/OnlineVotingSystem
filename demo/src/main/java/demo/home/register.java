package demo.home;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class register {

	private String name;
	@Id
	private long mobile_no;
	private String password;
	private String c_pass;

}
